package entities;

import database.Entity;
import database.Storable;

public class Position extends Entity {

    @Storable(order = 0)
    private String name;

    @Storable(order = 1)
    private double salary;

    @Storable(order = 2)
    private String duties;

    @Storable(order = 3)
    private String demands;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getDuties() {
        return duties;
    }

    public void setDuties(String duties) {
        this.duties = duties;
    }

    public String getDemands() {
        return demands;
    }

    public void setDemands(String demands) {
        this.demands = demands;
    }

    @Override
    public String toString() {
        return name;
    }

}
