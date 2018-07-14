package database;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class EntityContext {

    private static final int MAX_STRING_BYTE_LENGTH = 128;

    public final Class<? extends Entity> type;
    public final int size;
    public final List<Field> fields;
    private String singularName;
    private String pluralName;
    private final Map<String, String> fieldNames = new HashMap();

    EntityContext(Class<? extends Entity> entityType) {
        type = entityType;
        Class<?> current = entityType;
        int totalSize = 0;
        // Цикл вверх по всей иерархии классов (кроме Object)
        ArrayList<Field> allFields = new ArrayList();
        while (current.getSuperclass() != null) {
            Field[] declaredFields = current.getDeclaredFields();
            ArrayList<Field> currentFields = new ArrayList();
            // Цикл по всем полям в классе
            for (Field f : declaredFields) {
                // Отсеиваем поля без соответствующей аннотации
                if (f.getAnnotation(Storable.class) == null) {
                    continue;
                }
                // Прибавляем размер поля к размеру сущности
                if (f.getType() == int.class) {
                    totalSize += Integer.BYTES;
                } else if (f.getType() == long.class) {
                    totalSize += Long.BYTES;
                } else if (f.getType() == float.class) {
                    totalSize += Float.BYTES;
                } else if (f.getType() == double.class) {
                    totalSize += Double.BYTES;
                } else if (f.getType() == boolean.class) {
                    ++totalSize;
                } else if (f.getType() == String.class) {
                    totalSize += MAX_STRING_BYTE_LENGTH;
                } else if (f.getType() == LocalDate.class) {
                    totalSize += Long.BYTES;
                } else if (f.getType() == LocalDateTime.class) {
                    totalSize += Long.BYTES + Integer.BYTES;
                } else {
                    throw new RuntimeException(String.format("Поле \"%s\" класса \"%s\" имеет неподдерживаемый для определения размера тип \"%s\"!",
                            f.getName(), f.getDeclaringClass().getCanonicalName(), f.getType().getName()));
                }
                // Добавляем поле в список рассматриваемых
                currentFields.add(f);
            }
            // Сортируем поля по соответствующему значению аннотации
            // Если эти хначения равны, тогда сортируем по имени поля
            currentFields.sort((Field lhs, Field rhs) -> {
                int x = lhs.getAnnotation(Storable.class).order();
                int y = rhs.getAnnotation(Storable.class).order();
                int r = Integer.compare(x, y);
                return r != 0 ? r : lhs.getName().compareTo(rhs.getName());
            });
            // Добавляем отсортированный список полей в глобальный список
            allFields.addAll(0, currentFields);
            current = current.getSuperclass();
        }
        size = totalSize;
        fields = Collections.unmodifiableList(allFields);
    }

    public void setNames(String singularName, String pluralName, Map<String, String> fieldNames) {
        this.singularName = singularName;
        this.pluralName = pluralName;
        fieldNames.entrySet().forEach((kv) -> {
            this.fieldNames.put(kv.getKey(), kv.getValue());
        });
    }

    public String getSingularName() {
        return singularName != null ? singularName : type.getSimpleName();
    }

    public String getPluralName() {
        return pluralName != null ? pluralName : type.getSimpleName();
    }

    public String getFieldName(Field field) {
        String key = String.format("%s.%s", field.getDeclaringClass().getCanonicalName(), field.getName());
        String name = fieldNames.get(key);
        return name != null ? name : field.getName();
    }

    public Entity create() {
        try {
            return type.newInstance();
        } catch (IllegalAccessException | InstantiationException exc) {
            throw new RuntimeException(exc);
        }
    }

    public void validateBeforeSave(Manager manager, Entity entity) throws LogicException, IOException {
        checkEntityType(entity);
        validateStrings(entity);
        for (Field f : fields) {
            Class<? extends Entity> key = f.getAnnotation(Storable.class).key();
            if (key != Entity.class && manager.get(key, (int) getValue(entity, f)) == null) {
                if ((int) getValue(entity, f) != 0) {
                    throw new LogicException(String.format("Поле \"%s\" ссылается на несуществующую сущность \"%s\" с идентификатором \"%d\"!",
                            getFieldName(f), manager.getEntityContext(key).getSingularName(), getValue(entity, f)));
                }
            }
        }
    }

    public void validateBeforeDelete(Manager manager, Entity entity) throws LogicException, IOException {
        checkEntityType(entity);
        validateStrings(entity);
        for (EntityContext ec : manager.getEntityContexts()) {
            if (ec == this) {
                continue;
            }
            for (Field f : ec.fields) {
                Class<? extends Entity> key = f.getAnnotation(Storable.class).key();
                if (key == type) {
                    if (manager.searchFirst(ec.type, (Entity e) -> entity.getId() == (int) manager.getEntityContext(e.getClass()).getValue(e, f)) != null) {
                        throw new LogicException("Нельзя удалить сущность, на которую есть ссылки!");
                    }
                }
            }
        }
    }

    public Object getValue(Entity entity, Field f) {
        checkEntityType(entity);
        try {
            if (!fields.contains(f)) {
                throw new RuntimeException(String.format("Свойство \"%s.%s\" для сущности \"%s\" не найдено!",
                        f.getDeclaringClass().getCanonicalName(), f.getName(), type.getCanonicalName()));
            }
            String getMethodName = (f.getType() != boolean.class ? "get" : "is") + Character.toUpperCase(f.getName().charAt(0)) + f.getName().substring(1);
            Method getMethod = type.getMethod(getMethodName);
            return getMethod.invoke(entity);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException exc) {
            throw new RuntimeException(exc);
        }
    }

    public void setValue(Entity entity, Field f, Object value) {
        checkEntityType(entity);
        try {
            if (!fields.contains(f)) {
                throw new RuntimeException(String.format("Свойство \"%s.%s\" для сущности \"%s\" не найдено!",
                        f.getDeclaringClass().getCanonicalName(), f.getName(), type.getCanonicalName()));
            }
            if (f == fields.get(0)) {
                entity.setId((int) value);
            } else {
                String setMethodName = "set" + Character.toUpperCase(f.getName().charAt(0)) + f.getName().substring(1);
                Method setMethod = type.getMethod(setMethodName, f.getType());
                setMethod.invoke(entity, value);
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException exc) {
            throw new RuntimeException(exc);
        }
    }

    public void write(RandomAccessFile raf, Entity entity) throws IOException {
        checkEntityType(entity);
        for (Field f : fields) {
            if (f.getType() == int.class) {
                raf.writeInt((int) getValue(entity, f));
            } else if (f.getType() == long.class) {
                raf.writeLong((long) getValue(entity, f));
            } else if (f.getType() == float.class) {
                raf.writeFloat((float) getValue(entity, f));
            } else if (f.getType() == double.class) {
                raf.writeDouble((double) getValue(entity, f));
            } else if (f.getType() == boolean.class) {
                raf.writeBoolean((boolean) getValue(entity, f));
            } else if (f.getType() == String.class) {
                String value = (String) getValue(entity, f);
                if (value == null) {
                    value = "";
                }
                byte[] data = value.getBytes(StandardCharsets.UTF_8);
                raf.write(data);
                raf.write(new byte[MAX_STRING_BYTE_LENGTH - data.length]);
            } else if (f.getType() == LocalDate.class) {
                LocalDate value = (LocalDate) getValue(entity, f);
                if (value == null) {
                    value = LocalDate.MIN;
                }
                raf.writeLong(value.toEpochDay());
            } else if (f.getType() == LocalDateTime.class) {
                LocalDateTime value = (LocalDateTime) getValue(entity, f);
                if (value == null) {
                    value = LocalDateTime.MIN;
                }
                raf.writeLong(value.toLocalDate().toEpochDay());
                raf.writeInt(value.toLocalTime().toSecondOfDay());
            } else {
                throw new RuntimeException(String.format("Поле \"%s\" сущности \"%s\" имеет неподдерживаемый для записи тип \"%s\".",
                        f.getName(), f.getDeclaringClass().getCanonicalName(), f.getType().getName()));
            }
        }
    }

    public void read(RandomAccessFile raf, Entity entity) throws IOException {
        checkEntityType(entity);
        for (Field f : fields) {
            if (f.getType() == int.class) {
                setValue(entity, f, raf.readInt());
            } else if (f.getType() == long.class) {
                setValue(entity, f, raf.readLong());
            } else if (f.getType() == float.class) {
                setValue(entity, f, raf.readFloat());
            } else if (f.getType() == double.class) {
                setValue(entity, f, raf.readDouble());
            } else if (f.getType() == boolean.class) {
                setValue(entity, f, raf.readBoolean());
            } else if (f.getType() == String.class) {
                byte[] data = new byte[MAX_STRING_BYTE_LENGTH];
                if (raf.read(data) != data.length) {
                    throw new IOException();
                }
                int count = data.length - 1;
                while (count > 0 && data[count] == 0) {
                    --count;
                }
                setValue(entity, f, new String(data, 0, count, StandardCharsets.UTF_8));
            } else if (f.getType() == LocalDate.class) {
                LocalDate value = LocalDate.ofEpochDay(raf.readLong());
                if (value.equals(LocalDate.MIN)) {
                    value = null;
                }
                setValue(entity, f, value);
            } else if (f.getType() == LocalDateTime.class) {
                LocalDate date = LocalDate.ofEpochDay(raf.readLong());
                LocalTime time = LocalTime.ofSecondOfDay(raf.readInt());
                LocalDateTime value = LocalDateTime.of(date, time);
                if (value.equals(LocalDateTime.MIN)) {
                    value = null;
                }
                setValue(entity, f, value);
            } else {
                throw new RuntimeException(String.format("Поле \"%s\" сущности \"%s\" имеет неподдерживаемый для чтения тип \"%s\".",
                        f.getName(), f.getDeclaringClass().getCanonicalName(), f.getType().getName()));
            }
        }
    }

    private void checkEntityType(Entity entity) {
        if (entity.getClass() != type) {
            throw new RuntimeException(String.format("Попытка работы с сущностью \"%s\" через контекст для сущностей \"%s\"!",
                    entity.getClass().getCanonicalName(), type.getCanonicalName()));
        }
    }

    private void validateStrings(Entity entity) throws LogicException {
        for (Field f : fields) {
            if (f.getType() == String.class) {
                String value = (String) getValue(entity, f);
                if (value != null && value.getBytes(StandardCharsets.UTF_8).length > MAX_STRING_BYTE_LENGTH) {
                    throw new LogicException(String.format("Длина строки для поля \"%s\" превышает допустимую длину!",
                            getFieldName(f)));
                }
            }
        }
    }

}
