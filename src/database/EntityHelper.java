package database;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;

class EntityHelper {

    private static final int MAX_STRING_BYTE_LENGTH = 16;
    public final RandomAccessFile file;
    public final Class<? extends Entity> type;
    public final Iterable<Field> fields;
    public final int size;

    public EntityHelper(String path, Class<? extends Entity> entityType) throws FileNotFoundException {
        file = new RandomAccessFile(Paths.get(path, entityType.getCanonicalName()).toString(), "rw");
        type = entityType;
        fields = getFields(entityType);
        size = getSize(fields);
    }

    public Entity create() {
        return create(type);
    }

    public void validate(Entity e) throws LogicException {
        if (e.getClass() != type) {
            throw new RuntimeException(String.format("Попытка проверки сущности %s через контекст сущностей %s!",
                    e.getClass().getCanonicalName(), type.getCanonicalName()));
        }
        try {
            for (Field f : fields) {
                if (f.getType() == String.class) {
                    String value = (String) f.get(e);
                    if (value.getBytes(StandardCharsets.UTF_8).length > MAX_STRING_BYTE_LENGTH) {
                        String name = e.getFieldLocalization(f.getName());
                        throw new LogicException(String.format("Длина строки для поля %s превышает допустимую длину!",
                                name != null ? name : f.getName()));
                    }
                }
            }
        } catch (IllegalAccessException exc) {
            throw new RuntimeException(exc);
        }
    }

    public void write(Entity e) throws IOException {
        if (e.getClass() != type) {
            throw new RuntimeException(String.format("Попытка записи сущности %s через контекст сущностей %s!",
                    e.getClass().getCanonicalName(), type.getCanonicalName()));
        }
        try {
            for (Field f : fields) {
                if (f.getType() == int.class) {
                    file.writeInt(f.getInt(this));
                } else if (f.getType() == long.class) {
                    file.writeLong(f.getInt(this));
                } else if (f.getType() == boolean.class) {
                    file.writeBoolean(f.getBoolean(this));
                } else if (f.getType() == String.class) {
                    byte[] data = ((String) f.get(e)).getBytes(StandardCharsets.UTF_8);
                    file.write(data);
                    file.write(new byte[MAX_STRING_BYTE_LENGTH - data.length]);
                } else {
                    throw new RuntimeException(String.format("Поле %s сущности %s имеет неподдерживаемый для записи тип %s.",
                            f.getName(), f.getDeclaringClass().getCanonicalName(), f.getType().getName()));
                }
            }
        } catch (IllegalAccessException exc) {
            throw new RuntimeException(exc);
        }
    }

    public void read(Entity e) throws IOException {
        if (e.getClass() != type) {
            throw new RuntimeException(String.format("Попытка чтения сущности %s через контекст сущностей %s!",
                    e.getClass().getCanonicalName(), type.getCanonicalName()));
        }
        try {
            for (Field f : fields) {
                if (f.getType() == int.class) {
                    f.setInt(this, file.readInt());
                } else if (f.getType() == long.class) {
                    f.setLong(this, file.readLong());
                } else if (f.getType() == boolean.class) {
                    f.setBoolean(this, file.readBoolean());
                } else if (f.getType() == String.class) {
                    byte[] data = new byte[MAX_STRING_BYTE_LENGTH];
                    file.read(data);
                    int count = data.length - 1;
                    while (count > 0 && data[count] != 0) {
                        --count;
                    }
                    f.set(e, new String(data, 0, count, StandardCharsets.UTF_8));
                } else {
                    throw new RuntimeException(String.format("Поле %s сущности %s имеет неподдерживаемый для чтения тип %s.",
                            f.getName(), f.getDeclaringClass().getCanonicalName(), f.getType().getName()));
                }
            }
        } catch (IllegalAccessException exc) {
            throw new RuntimeException(exc);
        }
    }

    public static int getSize(Iterable<Field> fields) {
        int size = 0;
        for (Field f : fields) {
            if (f.getType() == int.class) {
                size += Integer.BYTES;
            } else if (f.getType() == long.class) {
                size += Long.BYTES;
            } else if (f.getType() == boolean.class) {
                ++size;
            } else if (f.getType() == String.class) {
                size += MAX_STRING_BYTE_LENGTH;
            } else {
                throw new RuntimeException(String.format("Поле %s класса %s имеет неподдерживаемый для определения размера тип %s.",
                        f.getName(), f.getDeclaringClass().getCanonicalName(), f.getType().getName()));
            }
        }
        return size;
    }

    public static Iterable<Field> getFields(Class<? extends Entity> entityType) {
        Class<?> current = entityType;
        ArrayList<Field> fields = new ArrayList<>();
        while (current.getSuperclass() != null) {
            ArrayList<Field> currentFields = new ArrayList<>();
            Field[] declaredFields = current.getDeclaredFields();
            for (Field f : declaredFields) {
                if (Modifier.isStatic(f.getModifiers())) {
                    continue;
                }
                if (f.getAnnotation(EntityField.class) == null) {
                    continue;
                }
                currentFields.add(f);
            }
            currentFields.sort((Field lhs, Field rhs) -> {
                int x = lhs.getAnnotation(EntityField.class).order();
                int y = rhs.getAnnotation(EntityField.class).order();
                int r = Integer.compare(x, y);
                return r != 0 ? r : lhs.getName().compareTo(rhs.getName());
            });
            fields.addAll(0, currentFields);
            current = current.getSuperclass();
        }
        return fields;
    }

    public static <E extends Entity> E create(Class<E> entityType) {
        try {
            return entityType.newInstance();
        } catch (IllegalAccessException | InstantiationException exc) {
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
