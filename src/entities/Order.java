package entities;

import database.Entity;
import database.Storable;
import java.time.LocalDateTime;

public class Order extends Entity {

    @Storable(order = 0)
    private LocalDateTime orderTime;

    @Storable(order = 1)
    private String clientName;

    @Storable(order = 2)
    private String phone;

    @Storable(order = 3, key = Dish.class)
    private int dishId1;

    @Storable(order = 4, key = Dish.class)
    private int dishId2;

    @Storable(order = 5, key = Dish.class)
    private int dishId3;

    @Storable(order = 6)
    private double price;

    @Storable(order = 7)
    private boolean completed;

    @Storable(order = 8, key = Employee.class)
    private int employeeId;

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getDishId1() {
        return dishId1;
    }

    public void setDishId1(int dishId1) {
        this.dishId1 = dishId1;
    }

    public int getDishId2() {
        return dishId2;
    }

    public void setDishId2(int dishId2) {
        this.dishId2 = dishId2;
    }

    public int getDishId3() {
        return dishId3;
    }

    public void setDishId3(int dishId3) {
        this.dishId3 = dishId3;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

}
