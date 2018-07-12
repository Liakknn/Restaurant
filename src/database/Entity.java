package database;

public abstract class Entity {

    @Storable
    private int id;

    public final int getId() {
        return id;
    }

    final void setId(int id) {
        this.id = id;
    }

}
