package bd;

import static bd.Utils.MAX_STRING_BYTE_LENGTH;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Employee implements Record { // Сотрудник

    public static final int SIZE = 14 + 4 * Utils.MAX_STRING_BYTE_LENGTH;
    private int id; // Код сотрудника
    private String fio = "";
    private int age;
    private char gender; //пол
    private String address = "";
    private String phone = "";
    private String passport = "";
    private int positionId; // Код должности

    public Employee() {
    }

    public Employee(int id) {
        this.id = id;
    }

    public Employee(int id, String fio, int age, char gender, String address, String phone, String passport, int positionId) throws Exception {
        this.id = id;
        setFio(fio);
        this.age = age;
        this.gender = gender;
        setAddress(address);
        setPhone(phone);
        setPassport(passport);
        this.positionId = positionId;
    }

    @Override
    public int getId() {
        return id;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public final void setAddress(String address) throws Exception {
        if (address == null) {
            address = "";
        }
        if (address.getBytes().length > MAX_STRING_BYTE_LENGTH) {
            throw new Exception("Адрес превышает допустимую длину!");
        }
        this.address = address;
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

    public String getPassport() {
        return passport;
    }

    public final void setPassport(String passport) throws Exception {
        if (passport == null) {
            passport = "";
        }
        if (passport.getBytes().length > MAX_STRING_BYTE_LENGTH) {
            throw new Exception("Паспортные данные превышает допустимую длину!");
        }
        this.passport = passport;
    }

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    @Override
    public void write(RandomAccessFile f) throws IOException, Exception {
        f.writeInt(id);
        Utils.writeString(f, fio);
        f.writeInt(age);
        f.writeChar(gender);
        Utils.writeString(f, address);
        Utils.writeString(f, phone);
        Utils.writeString(f, passport);
        f.writeInt(positionId);
    }

    @Override
    public void read(RandomAccessFile f) throws IOException {
        id = f.readInt();
        fio = Utils.readString(f);
        age = f.readInt();
        gender = f.readChar();
        address = Utils.readString(f);
        phone = Utils.readString(f);
        passport = Utils.readString(f);
        positionId = f.readInt();
    }

    @Override
    public int getSize() {
        return SIZE;
    }

    @Override
    public Record cloneWithoutId(int id) throws Exception {
        return new Employee(id, fio, age, gender, address, phone, passport, positionId);
    }

    @Override
    public String toString() {
        return fio;
    }
}
