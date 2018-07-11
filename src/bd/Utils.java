package bd;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Utils {

    public static final int MAX_STRING_BYTE_LENGTH = 1024;

    public static void writeString(RandomAccessFile f, String s) throws Exception {
        byte[] mas = s.getBytes();
        if (mas.length > MAX_STRING_BYTE_LENGTH) {
            throw new Exception("Строка превышает допустимую длину!");
        }
        f.write(mas);
        f.write(new byte[MAX_STRING_BYTE_LENGTH - mas.length]);
    }

    public static String readString(RandomAccessFile f) throws IOException {
        byte[] mas = new byte[MAX_STRING_BYTE_LENGTH];
        f.read(mas);
        //ищем нулевой байт
        int i = mas.length;
        for (i = 0; i < mas.length; ++i) {
            if (mas[i] == 0) {
                break;
            }
        }
        //создаем и возращаем строку из «полезной» части массива
        return new String(mas, 0, i);
    }

    public static LocalDateTime readLocalDateTime(RandomAccessFile f) throws IOException {
        long days = f.readLong();
        int seconds = f.readInt();
        return LocalDateTime.of(LocalDate.ofEpochDay(days), LocalTime.ofSecondOfDay(seconds));
    }

    public static void writeLocalDateTime(RandomAccessFile f, LocalDateTime dateTime) throws IOException {
        long days = dateTime.toLocalDate().toEpochDay();
        int seconds = dateTime.toLocalTime().toSecondOfDay();
        f.writeLong(days);
        f.writeInt(seconds);
    }

    public static Duration readDuration(RandomAccessFile f) throws IOException {
        long seconds = f.readLong();
        return Duration.ofSeconds(seconds);
    }

    public static void writeDuration(RandomAccessFile f, Duration d) throws IOException {
        long seconds = d.getSeconds();
        f.writeLong(seconds);
    }
}
