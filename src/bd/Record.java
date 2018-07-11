package bd;

import java.io.IOException;
import java.io.RandomAccessFile;

public interface Record {

    public int getId();

    public void write(RandomAccessFile f) throws IOException, Exception;

    public void read(RandomAccessFile f) throws IOException;

    public int getSize();

    public Record cloneWithoutId(int id) throws Exception;
}
