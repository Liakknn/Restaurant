package database.restaurant;

import database.Entity;
import database.Storable;

public class Position extends Entity {

    @Storable(order = 0)
    public String name;

    @Storable(order = 1)
    public double salary;

    @Storable(order = 2)
    public String duties;

    @Storable(order = 3)
    public String demands;

    @Override
    public String toString() {
        return name;
    }

}
