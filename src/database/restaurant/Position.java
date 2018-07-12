package database.restaurant;

import database.Entity;
import database.EntityField;
import database.LogicException;
import database.Manager;
import java.util.HashMap;

public class Position extends Entity {

    private static final HashMap<String, String> LOCALIZATION = new HashMap<>();

    static {
        LOCALIZATION.put("id", "Идентификатор");
        LOCALIZATION.put("name", "Название");
        LOCALIZATION.put("salary", "Зарплата");
        LOCALIZATION.put("duties", "Обязанности");
        LOCALIZATION.put("demands", "Требования");
    }

    @EntityField(order = 0)
    public String name;

    @EntityField(order = 1)
    public double salary;

    @EntityField(order = 2)
    public String duties;

    @EntityField(order = 3)
    public String demands;

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
