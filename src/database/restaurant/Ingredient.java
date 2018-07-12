package database.restaurant;

import database.EntityField;
import database.Entity;
import database.LogicException;
import database.Manager;
import java.time.LocalDate;
import java.util.HashMap;

public class Ingredient extends Entity {

    private static final HashMap<String, String> LOCALIZATION = new HashMap<>();

    static {
        LOCALIZATION.put("id", "Идентификатор");
        LOCALIZATION.put("name", "Название");
        LOCALIZATION.put("productionDate", "Дата производства");
        LOCALIZATION.put("expirationDate", "Дата истечения срока годности");
        LOCALIZATION.put("quantity", "Количество");
        LOCALIZATION.put("price", "Цена");
        LOCALIZATION.put("provider", "Поставщик");
    }

    @EntityField(order = 0)
    public String name;

    @EntityField(order = 1)
    public LocalDate productionDate;

    @EntityField(order = 2)
    public LocalDate expirationDate;

    @EntityField(order = 3)
    public double quantity;

    @EntityField(order = 4)
    public double price;

    @EntityField(order = 5)
    public String provider;

    @Override
    public void validateBeforeSave(Manager manager) throws LogicException {
    }

    @Override
    public void validateBeforeDelete(Manager manager) throws LogicException {
    }

    @Override
    public String getFieldLocalization(String fieldName) {
        return LOCALIZATION.get(fieldName);
    }

    @Override
    public String toString() {
        return name;
    }

}
