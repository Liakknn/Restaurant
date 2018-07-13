package gui;

import database.Entity;
import database.Manager;
import entities.Dish;
import entities.Employee;
import entities.Ingredient;
import entities.Order;
import entities.Position;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Queries {

    public static class EmployeePosition {

        public Employee employee;
        public Position position;
    }

    public static class DishIngridients {

        public Dish dish;
        public Ingredient ingridient1;
        public Ingredient ingridient2;
        public Ingredient ingridient3;
    }

    public static class OrderEmployeeDishes {

        public Order order;
        public Dish dish1;
        public Dish dish2;
        public Dish dish3;
        public Employee employee;
    }

    public static List<EmployeePosition> queryEmployeePositions(Manager manager) throws IOException {
        ArrayList<EmployeePosition> list = new ArrayList();
        for (Employee employee : manager.get(Employee.class)) {
            EmployeePosition ep = new EmployeePosition();
            ep.employee = employee;
            ep.position = manager.get(Position.class, employee.getPositionId());
            list.add(ep);
        }
        return list;
    }

    public static List<DishIngridients> queryDishIngridients(Manager manager) throws IOException {
        ArrayList<DishIngridients> list = new ArrayList();
        for (Dish dish : manager.get(Dish.class)) {
            DishIngridients di = new DishIngridients();
            di.dish = dish;
            di.ingridient1 = manager.get(Ingredient.class, dish.getIngredientId1());
            di.ingridient2 = manager.get(Ingredient.class, dish.getIngredientId2());
            di.ingridient3 = manager.get(Ingredient.class, dish.getIngredientId3());
            list.add(di);
        }
        return list;
    }

    public static List<OrderEmployeeDishes> queryOrderEmployeeDishes(Manager manager) throws IOException {
        ArrayList<OrderEmployeeDishes> list = new ArrayList();
        for (Order order : manager.get(Order.class)) {
            OrderEmployeeDishes oed = new OrderEmployeeDishes();
            oed.order = order;
            oed.dish1 = manager.get(Dish.class, order.getDishId1());
            oed.dish2 = manager.get(Dish.class, order.getDishId2());
            oed.dish3 = manager.get(Dish.class, order.getDishId3());
            oed.employee = manager.get(Employee.class, order.getEmployeeId());
            list.add(oed);
        }
        return list;
    }

    public static List<EmployeePosition> filterByPosition(Manager manager, int positionId) throws IOException {
        List<EmployeePosition> eps = queryEmployeePositions(manager);
        return eps.stream().filter((EmployeePosition ep) -> ep.employee.getPositionId() == positionId).collect(Collectors.toList());
    }

    public static List<Ingredient> filterByProvider(Manager manager, String providerName) throws IOException {
        final String p = providerName.toLowerCase();
        List<Entity> entities = manager.searchAll(Ingredient.class, (Entity e) -> {
            String name = ((Ingredient) e).getProvider();
            if (name == null) {
                return false;
            }
            return name.toLowerCase().contains(p);
        });
        ArrayList<Ingredient> ingredients = new ArrayList();
        entities.forEach((Entity entity) -> {
            ingredients.add((Ingredient) entity);
        });
        return ingredients;
    }

    public static List<OrderEmployeeDishes> filterByCompleted(Manager manager, boolean completed) throws IOException {
        List<OrderEmployeeDishes> list = queryOrderEmployeeDishes(manager);
        return list.stream().filter((OrderEmployeeDishes oed) -> oed.order.isCompleted() == completed).collect(Collectors.toList());
    }
}
