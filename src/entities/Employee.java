package entities;

import database.Entity;
import database.Storable;
import java.time.LocalDate;

public class Employee extends Entity {

    @Storable(order = 0)
    private String name;

    @Storable(order = 1)
    private LocalDate birthday;

    @Storable(order = 2)
    private boolean male;

    @Storable(order = 3)
    private String address;

    @Storable(order = 4)
    private String phone;

    @Storable(order = 5)
    private String passport;

    @Storable(order = 6, key = Position.class)
    private int positionId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    @Override
    public String toString() {
        return name;
    }

}
