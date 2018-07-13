package entities;

import database.Entity;
import database.Storable;
import java.time.LocalDateTime;

public class Dish extends Entity {

    @Storable(order = 0)
    private String name;

    @Storable(order = 1, key = Ingredient.class)
    private int ingredientId1;

    @Storable(order = 2)
    private double ingredientQuantity1;

    @Storable(order = 3, key = Ingredient.class)
    private int ingredientId2;

    @Storable(order = 4)
    private double ingredientQuantity2;

    @Storable(order = 5, key = Ingredient.class)
    private int ingredientId3;

    @Storable(order = 6)
    private double ingredientQuantity3;

    @Storable(order = 7)
    private double price;

    @Storable(order = 8)
    private int cookingTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIngredientId1() {
        return ingredientId1;
    }

    public void setIngredientId1(int ingredientId1) {
        this.ingredientId1 = ingredientId1;
    }

    public double getIngredientQuantity1() {
        return ingredientQuantity1;
    }

    public void setIngredientQuantity1(double ingredientQuantity1) {
        this.ingredientQuantity1 = ingredientQuantity1;
    }

    public int getIngredientId2() {
        return ingredientId2;
    }

    public void setIngredientId2(int ingredientId2) {
        this.ingredientId2 = ingredientId2;
    }

    public double getIngredientQuantity2() {
        return ingredientQuantity2;
    }

    public void setIngredientQuantity2(double ingredientQuantity2) {
        this.ingredientQuantity2 = ingredientQuantity2;
    }

    public int getIngredientId3() {
        return ingredientId3;
    }

    public void setIngredientId3(int ingredientId3) {
        this.ingredientId3 = ingredientId3;
    }

    public double getIngredientQuantity3() {
        return ingredientQuantity3;
    }

    public void setIngredientQuantity3(double ingredientQuantity3) {
        this.ingredientQuantity3 = ingredientQuantity3;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(int cookingTime) {
        this.cookingTime = cookingTime;
    }

    @Override
    public String toString() {
        return name;
    }

}
