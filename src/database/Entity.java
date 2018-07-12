package database;

public abstract class Entity {

    @EntityField
    private int id;

    public final int getId() {
        return id;
    }

    final void setId(int id) {
        this.id = id;
    }

    public abstract void validateBeforeSave(Manager manager) throws LogicException;

    public abstract void validateBeforeDelete(Manager manager) throws LogicException;

    public abstract String getFieldLocalization(String fieldName);

}
