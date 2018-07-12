package database;

import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Manager implements Closeable {

    private final String path;
    private final Map<Class<? extends Entity>, EntityHelper> entityHelpers = new HashMap<>();

    public Manager(String path) {
        this.path = path;
    }

    public void save(Entity e) throws IOException, LogicException {
        EntityHelper eh = getEntityContext(e.getClass());
        e.validateBeforeSave(this);
        eh.validate(e);
        if (get(eh.type, e.getId()) != null) {
            eh.write(e);
            return;
        }
        if (e.getId() != 0) {
            throw new LogicException(String.format("Сохраняемая сущность имеет несуществующий ненулевой идентификатор %d!", e.getId()));
        }
        e.setId(getNewId(eh.type));
        eh.file.setLength(eh.file.length() + eh.size);
        // Если файл до расширения был пустой
        if (eh.file.length() == eh.size) {
            eh.file.seek(0);
            eh.write(e);
            return;
        }
        // Смещаем данные перед вставкой
        Entity t = eh.create();
        for (int i = (int) (eh.file.length() / eh.size - 2); i >= 0; --i) {
            eh.file.seek(eh.size * i);
            eh.read(t); // Считали позицию i
            if (t.getId() > e.getId()) {
                eh.write(t); // Записали в позицию i + 1
                if (i == 0) {
                    eh.file.seek(0);
                }
            } else {
                break;
            }
        }
        // Запись новой сущности
        eh.write(e);
    }

    public void delete(Entity e) throws IOException, LogicException {
        EntityHelper eh = getEntityContext(e.getClass());
        e.validateBeforeDelete(this);
        eh.validate(e);
        if (get(eh.type, e.getId()) == null) {
            throw new LogicException(String.format("Удаляемая сущность с идентификатором %d не найдена!", e.getId()));
        }
        Entity t = eh.create();
        for (int i = (int) (eh.file.getFilePointer() / eh.size); i < (int) (eh.file.length() / eh.size) - 1; ++i) {
            eh.file.seek(eh.size * (i + 1));
            eh.read(t);
            eh.file.seek(eh.size * i);
            eh.write(t);
        }
        eh.file.setLength(eh.file.length() - eh.size);
    }

    public <E extends Entity> Collection<E> get(Class<E> entityType) throws IOException {
        Collection<E> result = new ArrayList<>();
        EntityHelper eh = getEntityContext(entityType);
        eh.file.seek(0);
        for (int i = 0; i < eh.file.length() / eh.size; i++) {
            E e = (E) eh.create();
            eh.read(e);
            result.add(e);
        }
        return result;
    }

    public <E extends Entity> E get(Class<E> entityType, int id) throws IOException {
        EntityHelper eh = getEntityContext(entityType);
        int left = 0;
        int right = (int) ((eh.file.length() / eh.size) - 1);
        E e = (E) eh.create();
        while (right >= left) {
            int i = (right + left) / 2;
            eh.file.seek(eh.size * i);
            eh.read(e);
            if (e.getId() == id) {
                eh.file.seek(eh.size * i);
                return e;
            }
            if (e.getId() < id) {
                left = i + 1;
            } else {
                right = i - 1;
            }
        }
        return null;
    }

    @Override
    public void close() throws IOException {
        for (EntityHelper eh : entityHelpers.values()) {
            eh.file.close();
        }
        entityHelpers.clear();
    }

    private EntityHelper getEntityContext(Class<? extends Entity> entityType) throws FileNotFoundException {
        EntityHelper eh = entityHelpers.get(entityType);
        if (eh == null) {
            eh = new EntityHelper(path, entityType);
            entityHelpers.put(entityType, eh);
        }
        return eh;
    }

    private int getNewId(Class<? extends Entity> entityType) throws IOException, LogicException {
        EntityHelper eh = getEntityContext(entityType);
        if (eh.file.length() == 0) {
            return 1;
        }
        Entity e = eh.create();
        eh.file.seek(eh.file.length() - eh.size);
        eh.read(e);
        if (e.getId() == Integer.MAX_VALUE) {
            throw new LogicException("Не удалось найти свободный идентификатор!");
        }
        return e.getId() + 1;
    }

}
