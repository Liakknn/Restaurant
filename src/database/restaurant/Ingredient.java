package database.restaurant;

import database.Entity;
import database.Storable;
import java.time.LocalDate;

public class Ingredient extends Entity {

    @Storable(order = 0)
    public String name;

    @Storable(order = 1)
    public LocalDate productionDate;

    @Storable(order = 2)
    public LocalDate expirationDate;

    @Storable(order = 3)
    public double quantity;

    @Storable(order = 4)
    public double price;

    @Storable(order = 5)
    public String provider;

    @Override
    public String toString() {
        return name;
    }

}
