package database.restaurant;

import database.Entity;
import database.Storable;
import java.time.LocalDateTime;

public class Order extends Entity {

    @Storable(order = 0)
    public LocalDateTime time;

    @Storable(order = 1)
    public String clientName;

    @Storable(order = 2)
    public String phone;

    @Storable(order = 3, key = Dish.class)
    public int dishId1;

    @Storable(order = 4, key = Dish.class)
    public int dishId2;

    @Storable(order = 5, key = Dish.class)
    public int dishId3;

    @Storable(order = 6)
    public double price;

    @Storable(order = 7)
    public boolean completed;

    @Storable(order = 8, key = Employee.class)
    public int employeeId;

    @Override
    public String toString() {
        return String.format("%s %s", time.toString(), clientName);
    }

}
