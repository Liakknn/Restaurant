package bd;
//заказ

import static bd.Utils.MAX_STRING_BYTE_LENGTH;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDateTime;

public class Order implements Record {

    public static final int SIZE = 41 + 2 * Utils.MAX_STRING_BYTE_LENGTH;
    private int id;
    private LocalDateTime orderTime;
    private String fio = "";
    private String phone = "";
    private int idDish1;
    private int idDish2;
    private int idDish3;
    private double price;
    private boolean ready; //выполнение
    private int idEm;

    public Order() {
    }

    public Order(int id) {
        this.id = id;
    }

    public Order(int id, LocalDateTime orderTime, String fio, String phone, int idDish1, int idDish2, int idDish3, double price, boolean ready, int idEm) throws Exception {
        this.id = id;
        this.orderTime = orderTime;
        setFio(fio);
        setPhone(phone);
        this.idDish1 = idDish1;
        this.idDish2 = idDish2;
        this.idDish3 = idDish3;
        this.price = price;
        this.ready = ready;
        this.idEm = idEm;
    }

    @Override
    public int getId() {
        return id;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public String getFio() {
        return fio;
    }

    public final void setFio(String fio) throws Exception {
        if (fio == null) {
            fio = "";
        }
        if (fio.getBytes().length > MAX_STRING_BYTE_LENGTH) {
            throw new Exception("ФИО превышает допустимую длину!");
        }
        this.fio = fio;
    }

    public String getPhone() {
        return phone;
    }

    public final void setPhone(String phone) throws Exception {
        if (phone == null) {
            phone = "";
        }
        if (phone.getBytes().length > MAX_STRING_BYTE_LENGTH) {
            throw new Exception("Телефон превышает допустимую длину!");
        }
        this.phone = phone;
    }

    public int getIdDish1() {
        return idDish1;
    }

    public void setIdDish1(int idDish1) {
        this.idDish1 = idDish1;
    }

    public int getIdDish2() {
        return idDish2;
    }

    public void setIdDish2(int idDish2) {
        this.idDish2 = idDish2;
    }

    public int getIdDish3() {
        return idDish3;
    }

    public void setIdDish3(int idDish3) {
        this.idDish3 = idDish3;
    }

    public double getValue() {
        return price;
    }

    public void setValue(double value) {
        this.price = value;
    }

    public int getIdEm() {
        return idEm;
    }

    public void setIdEm(int idEm) {
        this.idEm = idEm;
    }

    @Override
    public void write(RandomAccessFile f) throws IOException, Exception {
        f.writeInt(id);
        Utils.writeLocalDateTime(f, orderTime);
        Utils.writeString(f, fio);
        Utils.writeString(f, phone);
        f.writeInt(idDish1);
        f.writeInt(idDish2);
        f.writeInt(idDish3);
        f.writeDouble(price);
        f.writeBoolean(ready);
        f.writeInt(idEm);
    }

    @Override
    public void read(RandomAccessFile f) throws IOException {
        id = f.readInt();
        orderTime = Utils.readLocalDateTime(f);
        fio = Utils.readString(f);
        phone = Utils.readString(f);
        idDish1 = f.readInt();
        idDish2 = f.readInt();
        idDish3 = f.readInt();
        price = f.readDouble();
        ready = f.readBoolean();
        idEm = f.readInt();
    }

    @Override
    public int getSize() {
        return SIZE;
    }

    @Override
    public Record cloneWithoutId(int id) throws Exception {
        return new Order(id, orderTime, fio, phone, idDish1, idDish2, idDish3, price, ready, idEm);
    }

}
