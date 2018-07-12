package database.restaurant;

import database.Entity;
import database.Storable;
import java.time.LocalDate;

public class Employee extends Entity {

    @Storable(order = 0)
    public String name;

    @Storable(order = 1)
    public LocalDate birthday;

    @Storable(order = 2)
    public boolean male;

    @Storable(order = 3)
    public String address;

    @Storable(order = 4)
    public String phone;

    @Storable(order = 5)
    public String passport;

    @Storable(order = 6, key = Position.class)
    public int positionId;

    @Override
    public String toString() {
        return name;
    }

}
