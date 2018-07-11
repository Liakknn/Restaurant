package bd;

import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.nio.file.Paths;

public class Database implements Closeable {

    private final RandomAccessFile employeesFile;
    private final RandomAccessFile positionsFile;
    private final RandomAccessFile ingredientsFile;
    private final RandomAccessFile dishesFile;
    private final RandomAccessFile ordersFile;

    public Database(String path) throws FileNotFoundException {
        employeesFile = new RandomAccessFile(
                Paths.get(path, "employees").toString(), "rw");
        positionsFile = new RandomAccessFile(
                Paths.get(path, "positions").toString(), "rw");
        ingredientsFile = new RandomAccessFile(
                Paths.get(path, "ingredients").toString(), "rw");
        dishesFile = new RandomAccessFile(
                Paths.get(path, "dishes").toString(), "rw");
        ordersFile = new RandomAccessFile(
                Paths.get(path, "orders").toString(), "rw");
    }

    @Override
    public void close() throws IOException {
        employeesFile.close();
        positionsFile.close();
        ingredientsFile.close();
        dishesFile.close();
        ordersFile.close();
    }

    public ArrayList<Employee> getEmployees() throws IOException {
        ArrayList<Employee> list = new ArrayList<>();
        employeesFile.seek(0);
        for (int i = 0; i < employeesFile.length() / Employee.SIZE; i++) {
            Employee e = new Employee();
            e.read(employeesFile);
            list.add(e);
        }
        return list;
    }

    public ArrayList<Position> getPositions() throws IOException {
        ArrayList<Position> list = new ArrayList<>();
        positionsFile.seek(0);
        for (int i = 0; i < positionsFile.length() / Position.SIZE; i++) {
            Position e = new Position();
            e.read(positionsFile);
            list.add(e);
        }
        return list;
    }

    public ArrayList<Ingredient> getIngredients() throws IOException {
        ArrayList<Ingredient> list = new ArrayList<>();
        ingredientsFile.seek(0);
        for (int i = 0; i < ingredientsFile.length() / Ingredient.SIZE; i++) {
            Ingredient e = new Ingredient();
            e.read(employeesFile);
            list.add(e);
        }
        return list;
    }

    public ArrayList<Dish> getDishes() throws IOException {
        ArrayList<Dish> list = new ArrayList<>();
        dishesFile.seek(0);
        for (int i = 0; i < dishesFile.length() / Dish.SIZE; i++) {
            Dish e = new Dish();
            e.read(dishesFile);
            list.add(e);
        }
        return list;
    }

    public ArrayList<Order> getOrders() throws IOException {
        ArrayList<Order> list = new ArrayList<>();
        ordersFile.seek(0);
        for (int i = 0; i < ordersFile.length() / Order.SIZE; i++) {
            Order e = new Order();
            e.read(ordersFile);
            list.add(e);
        }
        return list;
    }

    public Employee getEmployee(int id) throws Exception {
        Employee e = new Employee(id);
        RandomAccessFile r = getFile(e);
        return (Employee) binarySearch(r, e);
    }

    public Position getPosition(int id) throws Exception {
        Position e = new Position(id);
        RandomAccessFile r = getFile(e);
        return (Position) binarySearch(r, e);
    }

    public Ingredient getIngredient(int id) throws Exception {
        Ingredient e = new Ingredient(id);
        RandomAccessFile r = getFile(e);
        return (Ingredient) binarySearch(r, e);
    }

    public Dish getDish(int id) throws Exception {
        Dish e = new Dish(id);
        RandomAccessFile r = getFile(e);
        return (Dish) binarySearch(r, e);
    }

    public Order getOrder(int id) throws Exception {
        Order e = new Order(id);
        RandomAccessFile r = getFile(e);
        return (Order) binarySearch(r, e);
    }

    public Record save(Record r) throws IOException, Exception {
        checkSave(r);
        RandomAccessFile f = getFile(r);
        Record found = binarySearch(f, r);
        if (found != null) {
            r.write(f);
            return r;
        } else {
            if (r.getId() != 0) {
                throw new Exception("Попытка вставки новой записи с ненулевым id!");
            }
            r = r.cloneWithoutId(generateId(r));
        }
        found = r.cloneWithoutId(0);
        f.setLength(f.length() + r.getSize());
        //если был файл пустой
        if (f.length() == r.getSize()) {
            f.seek(0);
            r.write(f);
            return r;
        }
        //смещаем данные перед ставкой
        for (int i = (int) (f.length() / r.getSize() - 2); i >= 0; i--) {
            f.seek(r.getSize() * i);
            found.read(f); //считали i-тую позицию
            if (r.getId() < found.getId()) {
                found.write(f); //записали в i+1 позицию
                f.seek(r.getSize() * i);
            } else {
                break;
            }
        }
        //запись новой сущности
        r.write(f);
        return r;
    }

    public void remove(Record r) throws IOException, Exception {
        RandomAccessFile f = getFile(r);
        Record found = binarySearch(f, r);
        if (found == null) {
            throw new IOException("Сущность не найдена!");
        }
        checkRemove(r);
        //сделать удаление, следущей элемент записать в текущий
        for (int i = (int) (f.getFilePointer() / r.getSize()); i < f.length() / r.getSize() - 1; i++) {
            f.seek(r.getSize() * (i + 1));
            found.read(f);
            f.seek(r.getSize() * i);
            found.write(f);
        }
        f.setLength(f.length() - r.getSize());
    }

    private RandomAccessFile getFile(Record r) {
        if (r instanceof Employee) {
            return employeesFile;
        } else if (r instanceof Position) {
            return positionsFile;
        } else if (r instanceof Ingredient) {
            return ingredientsFile;
        } else if (r instanceof Dish) {
            return dishesFile;
        } else if (r instanceof Order) {
            return ordersFile;
        } else {
            throw new RuntimeException("Тип сущности не поддерживается!");
        }
    }

    private void checkSave(Record r) throws IOException, Exception {
        if (r instanceof Employee) {
            Employee employee = (Employee) r;
            if (binarySearch(positionsFile, new Position(employee.getPositionId())) == null) {
                throw new Exception("Код должности не найден!");
            }
        } else if (r instanceof Position) {
            //проверки не требуется
        } else if (r instanceof Ingredient) {
            //проверки не требуется
        } else if (r instanceof Dish) {
            Dish dish = (Dish) r;
            if (binarySearch(ingredientsFile, new Ingredient(dish.getIdIngred1())) == null
                    || binarySearch(ingredientsFile, new Ingredient(dish.getIdIngred2())) == null
                    || binarySearch(ingredientsFile, new Ingredient(dish.getIdIngred3())) == null) {
                throw new Exception("Блюдо имеет неизвестный ингредиент!");
            }
        } else if (r instanceof Order) {
            Order order = (Order) r;
            if (binarySearch(dishesFile, new Dish(order.getIdDish1())) == null
                    || binarySearch(dishesFile, new Dish(order.getIdDish2())) == null
                    || binarySearch(dishesFile, new Dish(order.getIdDish3())) == null) {
                throw new Exception("Заказ имеет неизвестные блюда!");
            } else {
                throw new RuntimeException("Тип сущности не поддерживается!");
            }

        }
    }

    private void checkRemove(Record r) throws IOException, Exception {
        if (r instanceof Employee) {
            Order buf = new Order();
            ordersFile.seek(0);
            for (int i = 0; i < ordersFile.length() / Order.SIZE; i++) {
                buf.read(ordersFile);
                if (buf.getIdEm() == r.getId()) {
                    throw new Exception("Сотрудника нельзя удалить, он делает заказ!");
                }
            }

        } else if (r instanceof Position) {
            Employee buf = new Employee();
            employeesFile.seek(0);
            for (int i = 0; i < employeesFile.length() / Employee.SIZE; i++) {
                buf.read(employeesFile);
                if (buf.getPositionId() == r.getId()) {
                    throw new Exception("Должность нельзя удалить, на ней работает сотрудник(и)!");
                }
            }
        } else if (r instanceof Ingredient) {
            Dish buf = new Dish();
            dishesFile.seek(0);
            for (int i = 0; i < dishesFile.length() / Dish.SIZE; i++) {
                buf.read(dishesFile);
                if (buf.getIdIngred1() == r.getId()
                        || buf.getIdIngred2() == r.getId()
                        || buf.getIdIngred3() == r.getId()) {
                    throw new Exception("Игредиент нельзя удалить, он используется в заказе!");
                }
            }
        } else if (r instanceof Dish) {
            Order buf = new Order();
            ordersFile.seek(0);
            for (int i = 0; i < ordersFile.length() / Order.SIZE; i++) {
                buf.read(ordersFile);
                if (buf.getIdDish1() == r.getId()
                        || buf.getIdDish2() == r.getId()
                        || buf.getIdDish3() == r.getId()) {
                    throw new Exception("Блюдо используется в заказе, его нельзя удалить!");
                }
            }
        } else if (r instanceof Order) {
            //проверки не требуется
        } else {
            throw new RuntimeException("Тип сущности не поддерживается!");
        }
    }

    private int generateId(Record r) throws Exception {
        RandomAccessFile f = getFile(r);
        if (f.length() == 0) {
            return 1;
        }
        Record buf = r.cloneWithoutId(0);
        f.seek(f.length() - r.getSize());
        buf.read(f);
        return buf.getId() + 1;
    }

    /**
     * Метод двоичного поиска. Если объект найден, то позиция в файле будет
     * указывать на начало этого объекта.
     *
     * @param f файл в котором ищется сущность
     * @param r индефикатор этой сущности будет искаться в файле
     * @return найденный объект, если он найдет, иначе - null
     * @throws IOException
     */
    private Record binarySearch(RandomAccessFile f, Record r) throws Exception {
        int left = 0;
        int right = (int) ((f.length() / r.getSize()) - 1);
        Record buf = r.cloneWithoutId(0);
        while (right >= left) {
            int m = (right + left) / 2;
            f.seek(r.getSize() * m);
            buf.read(f);
            if (r.getId() == buf.getId()) {
                f.seek(r.getSize() * m);
                return buf;
            }
            if (r.getId() < buf.getId()) {
                right = m - 1;
            } else {
                left = m + 1;
            }
        }
        return null;
    }
}
