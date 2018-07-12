package database.restaurant;

import database.Entity;
import database.Storable;
import java.time.LocalDateTime;

public class Dish extends Entity {

    @Storable(order = 0)
    public String name;

    @Storable(order = 1, key = Ingredient.class)
    public int ingredientId1;

    @Storable(order = 2)
    public double ingredientQuantity1;

    @Storable(order = 3, key = Ingredient.class)
    public int ingredientId2;

    @Storable(order = 4)
    public double ingredientQuantity2;

    @Storable(order = 5, key = Ingredient.class)
    public int ingredientId3;

    @Storable(order = 6)
    public double ingredientQuantity3;

    @Storable(order = 7)
    public double price;

    @Storable(order = 8)
    public LocalDateTime cookingTime;

    @Override
    public String toString() {
        return name;
    }

}
