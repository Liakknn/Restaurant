package bd;

import static bd.Utils.MAX_STRING_BYTE_LENGTH;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.Duration;

public final class Dish implements Record {

    public static final int SIZE = 56 + Utils.MAX_STRING_BYTE_LENGTH;
    private int id;
    private String name = "";
    private int idIngred1;
    private double amountIngred1;
    private int idIngred2;
    private double amountIngred2;
    private int idIngred3;
    private double amountIngred3;
    private double price;
    private Duration cookingTime;

    public Dish() {
    }

    public Dish(int id) {
        this.id = id;
    }

    public Dish(int id, String name, int idIngred1, double amountIngred1, int idIngred2, double amountIngred2, int idIngred3, double amountIngred3, double value, Duration time) throws Exception {
        this.id = id;
        setName(name);
        this.idIngred1 = idIngred1;
        this.amountIngred1 = amountIngred1;
        this.idIngred2 = idIngred2;
        this.amountIngred2 = amountIngred2;
        this.idIngred3 = idIngred3;
        this.amountIngred3 = amountIngred3;
        this.price = value;
        this.cookingTime = time;
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
            throw new Exception("Название блюда превышает допустимую длину!");
        }
        this.name = name;
    }

    public int getIdIngred1() {
        return idIngred1;
    }

    public void setIdIngred1(int idIngred1) {
        this.idIngred1 = idIngred1;
    }

    public double getAmountIngred1() {
        return amountIngred1;
    }

    public void setAmountIngred1(double amountIngred1) {
        this.amountIngred1 = amountIngred1;
    }

    public int getIdIngred2() {
        return idIngred2;
    }

    public void setIdIngred2(int idIngred2) {
        this.idIngred2 = idIngred2;
    }

    public double getAmountIngred2() {
        return amountIngred2;
    }

    public void setAmountIngred2(double amountIngred2) {
        this.amountIngred2 = amountIngred2;
    }

    public int getIdIngred3() {
        return idIngred3;
    }

    public void setIdIngred3(int idIngred3) {
        this.idIngred3 = idIngred3;
    }

    public double getAmountIngred3() {
        return amountIngred3;
    }

    public void setAmountIngred3(double amountIngred3) {
        this.amountIngred3 = amountIngred3;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Duration getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(Duration cookingTime) {
        this.cookingTime = cookingTime;
    }

    @Override
    public void write(RandomAccessFile f) throws IOException, Exception {
        f.writeInt(id);
        Utils.writeString(f, name);
        f.writeInt(idIngred1);
        f.writeDouble(amountIngred1);
        f.writeInt(idIngred2);
        f.writeDouble(amountIngred2);
        f.writeInt(idIngred3);
        f.writeDouble(amountIngred3);
        f.writeDouble(price);
        Utils.writeDuration(f, cookingTime);
    }

    @Override
    public void read(RandomAccessFile f) throws IOException {
        id = f.readInt();
        name = Utils.readString(f);
        idIngred1 = f.readInt();
        amountIngred1 = f.readDouble();
        idIngred2 = f.readInt();
        amountIngred2 = f.readDouble();
        idIngred3 = f.readInt();
        amountIngred3 = f.readDouble();
        price = f.readDouble();
        cookingTime = Utils.readDuration(f);

    }

    @Override
    public int getSize() {
        return SIZE;
    }

    @Override
    public Record cloneWithoutId(int id) throws Exception {
        return new Dish(id, name, idIngred1, amountIngred1, idIngred2, amountIngred2, idIngred3, amountIngred3, price, cookingTime);
    }

    @Override
    public String toString() {
        return name;
    }

}
