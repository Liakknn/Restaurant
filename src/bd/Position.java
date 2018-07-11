package bd;

import static bd.Utils.MAX_STRING_BYTE_LENGTH;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Position implements Record { // Должность

    public static final int SIZE = 12 + 3 * Utils.MAX_STRING_BYTE_LENGTH;
    private int id;
    private String name = "";
    private double salary; // Оклад
    private String duties = ""; // Обязанности
    private String demands = ""; // Требования

    Position() {
    }

    public Position(int id) {
        this.id = id;
    }

    public Position(int id, String name, double salary, String duties, String demands) throws Exception {
        this.id = id;
        setName(name);
        this.salary = salary;
        setDuties(duties);
        setDemands(demands);
    }

    @Override
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public final void setName(String name) throws Exception {
        if (name == null) {
            name = "";
        }
        if (name.getBytes().length > MAX_STRING_BYTE_LENGTH) {
            throw new Exception("Название должности превышает допустимую длину!");
        }
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getDuties() {
        return duties;
    }

    public final void setDuties(String duties) throws Exception {
        if (duties == null) {
            duties = "";
        }
        if (duties.getBytes().length > MAX_STRING_BYTE_LENGTH) {
            throw new Exception("Описание обязанностией превышает допустимую длину!");
        }
        this.duties = duties;
    }

    public String getDemands() {
        return demands;
    }

    public final void setDemands(String demands) throws Exception {
        if (demands == null) {
            demands = "";
        }
        if (demands.getBytes().length > MAX_STRING_BYTE_LENGTH) {
            throw new Exception("Описание требований превышает допустимую длину!");
        }
        this.demands = demands;
    }

    @Override
    public void write(RandomAccessFile f) throws IOException, Exception {
        f.writeInt(id);
        Utils.writeString(f, name);
        f.writeDouble(salary);
        Utils.writeString(f, duties);
        Utils.writeString(f, demands);
    }

    @Override
    public void read(RandomAccessFile f) throws IOException {
        id = f.readInt();
        name = Utils.readString(f);
        salary = f.readDouble();
        duties = Utils.readString(f);
        demands = Utils.readString(f);
    }

    @Override
    public int getSize() {
        return SIZE;
    }

    @Override
    public Record cloneWithoutId(int id) throws Exception {
        return new Position(id, name, salary, duties, demands);
    }

    @Override
    public String toString() {
        return name;
    }
}
