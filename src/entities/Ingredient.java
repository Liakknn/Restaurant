package entities;

import database.Entity;
import database.Storable;
import java.time.LocalDate;

public class Ingredient extends Entity {

    @Storable(order = 0)
    private String name;

    @Storable(order = 1)
    private LocalDate productionDate;

    @Storable(order = 2)
    private LocalDate expirationDate;

    @Storable(order = 3)
    private double quantity;

    @Storable(order = 4)
    private double price;

    @Storable(order = 5)
    private String provider;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(LocalDate productionDate) {
        this.productionDate = productionDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    @Override
    public String toString() {
        return name;
    }

}
