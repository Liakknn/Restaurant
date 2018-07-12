package database;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class EntityContext {

    private static final int MAX_STRING_BYTE_LENGTH = 16;

    public final Class<? extends Entity> type;
    public final int size;
    public final List<Field> fields;
    private String singularName;
    private String pluralName;
    private final Map<String, String> fieldNames = new HashMap<>();

    EntityContext(Class<? extends Entity> entityType) {
        type = entityType;
        Class<?> current = entityType;
        int totalSize = 0;
        // Цикл вверх по всей иерархии классов (кроме Object)
        ArrayList<Field> allFields = new ArrayList<>();
        while (current.getSuperclass() != null) {
            Field[] declaredFields = current.getDeclaredFields();
            ArrayList<Field> currentFields = new ArrayList<>();
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

    public void set(String singularName, String pluralName, Map<String, String> fieldNames) {
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

    public String getFieldName(String field) {
        String name = fieldNames.get(field);
        return name != null ? name : field;
    }

    public Entity create() {
        try {
            return type.newInstance();
        } catch (IllegalAccessException | InstantiationException exc) {
            throw new RuntimeException(exc);
        }
    }

    public void validateBeforeSave(Manager manager, Entity e) throws LogicException, IOException {
        checkEntityType(e);
        try {
            validateStrings(e);
            for (Field f : fields) {
                Class<? extends Entity> key = f.getAnnotation(Storable.class).key();
                if (key != Entity.class && manager.get(key, f.getInt(e)) == null) {
                    throw new LogicException(String.format("Поле \"%s\" ссылается на несуществующую сущность %s с идентификатором %d!",
                            getFieldName(f.getName()), key.getCanonicalName(), f.getInt(e)));
                }
            }
        } catch (IllegalAccessException exc) {
            throw new RuntimeException(exc);
        }
    }

    public void validateBeforeDelete(Manager manager, Entity e) throws LogicException, IOException {
        checkEntityType(e);
        validateStrings(e);
        for (EntityContext ec : manager.getEntityContexts()) {
            if (ec == this) {
                continue;
            }
            for (Field f : ec.fields) {
                Class<? extends Entity> key = f.getAnnotation(Storable.class).key();
                if (key == type && manager.search(ec.type, f, e.getId()) != null) {
                    throw new LogicException("Нельзя удалить сущность, на которую есть ссылки!");
                }
            }
        }
    }

    public void write(RandomAccessFile raf, Entity e) throws IOException {
        checkEntityType(e);
        try {
            for (Field f : fields) {
                if (f.getType() == int.class) {
                    raf.writeInt(f.getInt(this));
                } else if (f.getType() == long.class) {
                    raf.writeLong(f.getInt(this));
                } else if (f.getType() == float.class) {
                    raf.writeFloat(f.getFloat(this));
                } else if (f.getType() == double.class) {
                    raf.writeDouble(f.getDouble(this));
                } else if (f.getType() == boolean.class) {
                    raf.writeBoolean(f.getBoolean(this));
                } else if (f.getType() == String.class) {
                    String value = (String) f.get(e);
                    if (value == null) {
                        value = "";
                    }
                    byte[] data = value.getBytes(StandardCharsets.UTF_8);
                    raf.write(data);
                    raf.write(new byte[MAX_STRING_BYTE_LENGTH - data.length]);
                } else if (f.getType() == LocalDate.class) {
                    LocalDate value = (LocalDate) f.get(e);
                    if (value == null) {
                        value = LocalDate.MIN;
                    }
                    raf.writeLong(value.toEpochDay());
                } else if (f.getType() == LocalDateTime.class) {
                    LocalDateTime value = (LocalDateTime) f.get(e);
                    if (value == null) {
                        value = LocalDateTime.MIN;
                    }
                    raf.writeLong(value.toLocalDate().toEpochDay());
                    raf.writeInt(value.toLocalTime().toSecondOfDay());
                } else {
                    throw new RuntimeException(String.format("Поле %s сущности %s имеет неподдерживаемый для записи тип %s.",
                            f.getName(), f.getDeclaringClass().getCanonicalName(), f.getType().getName()));
                }
            }
        } catch (IllegalAccessException exc) {
            throw new RuntimeException(exc);
        }
    }

    public void read(RandomAccessFile raf, Entity e) throws IOException {
        checkEntityType(e);
        try {
            for (Field f : fields) {
                if (f.getType() == int.class) {
                    f.setInt(this, raf.readInt());
                } else if (f.getType() == long.class) {
                    f.setLong(this, raf.readLong());
                } else if (f.getType() == float.class) {
                    f.setFloat(this, raf.readFloat());
                } else if (f.getType() == double.class) {
                    f.setDouble(this, raf.readDouble());
                } else if (f.getType() == boolean.class) {
                    f.setBoolean(this, raf.readBoolean());
                } else if (f.getType() == String.class) {
                    byte[] data = new byte[MAX_STRING_BYTE_LENGTH];
                    raf.read(data);
                    int count = data.length - 1;
                    while (count > 0 && data[count] != 0) {
                        --count;
                    }
                    f.set(e, new String(data, 0, count, StandardCharsets.UTF_8));
                } else if (f.getType() == LocalDate.class) {
                    LocalDate value = LocalDate.ofEpochDay(raf.readLong());
                    if (value == LocalDate.MIN) {
                        value = null;
                    }
                    f.set(e, value);
                } else if (f.getType() == LocalDateTime.class) {
                    LocalDate date = LocalDate.ofEpochDay(raf.readLong());
                    LocalTime time = LocalTime.ofSecondOfDay(raf.readInt());
                    LocalDateTime value = LocalDateTime.of(date, time);
                    if (value == LocalDateTime.MIN) {
                        value = null;
                    }
                    f.set(e, value);
                } else {
                    throw new RuntimeException(String.format("Поле %s сущности %s имеет неподдерживаемый для чтения тип %s.",
                            f.getName(), f.getDeclaringClass().getCanonicalName(), f.getType().getName()));
                }
            }
        } catch (IllegalAccessException exc) {
            throw new RuntimeException(exc);
        }
    }

    private void checkEntityType(Entity e) {
        if (e.getClass() != type) {
            throw new RuntimeException(String.format("Попытка работы с сущностью %s через контекст для сущностей %s!",
                    e.getClass().getCanonicalName(), type.getCanonicalName()));
        }
    }

    public void validateStrings(Entity e) throws LogicException {
        try {
            for (Field f : fields) {
                if (f.getType() == String.class) {
                    if (((String) f.get(e)).getBytes(StandardCharsets.UTF_8).length > MAX_STRING_BYTE_LENGTH) {
                        throw new LogicException(String.format("Длина строки для поля \"%s\" превышает допустимую длину!",
                                getFieldName(f.getName())));
                    }
                }
            }
        } catch (IllegalAccessException exc) {
            throw new RuntimeException(exc);
        }
    }

}

//package database.restaurant;
//
//import java.io.IOException;
//import java.io.RandomAccessFile;
//import java.time.Duration;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//
//public class Utils {
//
//    public static final int MAX_STRING_BYTE_LENGTH = 1024;
//
//    public static void writeString(RandomAccessFile f, String s) throws Exception {
//        byte[] mas = s.getBytes();
//        if (mas.length > MAX_STRING_BYTE_LENGTH) {
//            throw new Exception("Строка превышает допустимую длину!");
//        }
//        f.write(mas);
//        f.write(new byte[MAX_STRING_BYTE_LENGTH - mas.length]);
//    }
//
//    public static String readString(RandomAccessFile f) throws IOException {
//        byte[] mas = new byte[MAX_STRING_BYTE_LENGTH];
//        f.read(mas);
//        //ищем нулевой байт
//        int i = 0;
//        for (; i < mas.length; ++i) {
//            if (mas[i] == 0) {
//                break;
//            }
//        }
//        //создаем и возращаем строку из «полезной» части массива
//        return new String(mas, 0, i);
//    }
//
//    public static LocalDateTime readLocalDateTime(RandomAccessFile f) throws IOException {
//        long days = f.readLong();
//        int seconds = f.readInt();
//        return LocalDateTime.of(LocalDate.ofEpochDay(days), LocalTime.ofSecondOfDay(seconds));
//    }
//
//    public static void writeLocalDateTime(RandomAccessFile f, LocalDateTime dateTime) throws IOException {
//        long days = dateTime.toLocalDate().toEpochDay();
//        int seconds = dateTime.toLocalTime().toSecondOfDay();
//        f.writeLong(days);
//        f.writeInt(seconds);
//    }
//
//    public static Duration readDuration(RandomAccessFile f) throws IOException {
//        long seconds = f.readLong();
//        return Duration.ofSeconds(seconds);
//    }
//
//    public static void writeDuration(RandomAccessFile f, Duration d) throws IOException {
//        long seconds = d.getSeconds();
//        f.writeLong(seconds);
//    }
//}
