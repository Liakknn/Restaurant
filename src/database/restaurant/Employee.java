package database.restaurant;

import database.EntityField;
import database.Entity;
import database.LogicException;
import database.Manager;
import java.time.LocalDate;
import java.util.HashMap;

public class Employee extends Entity {

    private static final HashMap<String, String> LOCALIZATION = new HashMap<>();

    static {
        LOCALIZATION.put("id", "Идентификатор");
        LOCALIZATION.put("name", "ФИО");
        LOCALIZATION.put("birthday", "День рождения");
        LOCALIZATION.put("male", "Пол (М)");
        LOCALIZATION.put("address", "Адрес");
        LOCALIZATION.put("phone", "Телефон");
        LOCALIZATION.put("passport", "Паспорт");
        LOCALIZATION.put("positionId", "Должность");
    }

    @EntityField(order = 0)
    public String name;

    @EntityField(order = 1)
    public LocalDate birthday;

    @EntityField(order = 2)
    public boolean male;

    @EntityField(order = 3)
    public String address;

    @EntityField(order = 4)
    public String phone;

    @EntityField(order = 5)
    public String passport;

    @EntityField(order = 6, key = Position.class)
    public int positionId;

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
