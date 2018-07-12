package database;

import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Manager implements Closeable {

    private final String path;
    private final Map<Class<? extends Entity>, EntityContext> entityContexts = new HashMap<>();
    private final Map<Class<? extends Entity>, RandomAccessFile> files = new HashMap<>();
    private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    public Manager(String path) {
        this.path = path;
    }

    public DateTimeFormatter getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(DateTimeFormatter dateFormat) {
        this.dateFormat = dateFormat;
    }

    public DateTimeFormatter getDateTimeFormat() {
        return dateTimeFormat;
    }

    public void setDateTimeFormat(DateTimeFormatter dateTimeFormat) {
        this.dateTimeFormat = dateTimeFormat;
    }

    public <E extends Entity> List<E> get(Class<E> entityType) throws IOException {
        ArrayList<E> result = new ArrayList<>();
        EntityContext ec = getEntityContext(entityType);
        RandomAccessFile raf = getFile(entityType);
        raf.seek(0);
        for (int i = 0; i < raf.length() / ec.size; i++) {
            E e = (E) ec.create();
            ec.read(raf, e);
            result.add(e);
        }
        return result;
    }

    public <E extends Entity> E get(Class<E> entityType, int id) throws IOException {
        EntityContext ec = getEntityContext(entityType);
        RandomAccessFile raf = getFile(entityType);
        int left = 0;
        int right = (int) ((raf.length() / ec.size) - 1);
        E e = (E) ec.create();
        while (right >= left) {
            int i = (right + left) / 2;
            raf.seek(ec.size * i);
            ec.read(raf, e);
            if (e.getId() == id) {
                raf.seek(ec.size * i);
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

    public void save(Entity e) throws IOException, LogicException {
        EntityContext ec = getEntityContext(e.getClass());
        RandomAccessFile raf = getFile(ec.type);
        ec.validateBeforeSave(this, e);
        if (get(ec.type, e.getId()) != null) {
            ec.write(raf, e);
            return;
        }
        if (e.getId() != 0) {
            throw new LogicException(String.format("Сохраняемая сущность имеет несуществующий ненулевой идентификатор %d!", e.getId()));
        }
        e.setId(getNewId(ec.type));
        raf.setLength(raf.length() + ec.size);
        // Если файл до расширения был пустой
        if (raf.length() == ec.size) {
            raf.seek(0);
            ec.write(raf, e);
            return;
        }
        // Смещаем данные перед вставкой
        Entity t = ec.create();
        for (int i = (int) (raf.length() / ec.size - 2); i >= 0; --i) {
            raf.seek(ec.size * i);
            ec.read(raf, t); // Считали позицию i
            if (t.getId() > e.getId()) {
                ec.write(raf, t); // Записали в позицию i + 1
                if (i == 0) {
                    raf.seek(0);
                }
            } else {
                break;
            }
        }
        // Запись новой сущности
        ec.write(raf, e);
    }

    public void delete(Entity e) throws IOException, LogicException {
        EntityContext ec = getEntityContext(e.getClass());
        RandomAccessFile raf = getFile(ec.type);
        ec.validateBeforeDelete(this, e);
        if (get(ec.type, e.getId()) == null) {
            throw new LogicException(String.format("Удаляемая сущность с идентификатором %d не найдена!", e.getId()));
        }
        Entity t = ec.create();
        for (int i = (int) (raf.getFilePointer() / ec.size); i < (int) (raf.length() / ec.size) - 1; ++i) {
            raf.seek(ec.size * (i + 1));
            ec.read(raf, t);
            raf.seek(ec.size * i);
            ec.write(raf, t);
        }
        raf.setLength(raf.length() - ec.size);
    }

    @Override
    public void close() throws IOException {
        for (RandomAccessFile raf : files.values()) {
            raf.close();
        }
        files.clear();
    }

    EntityContext getEntityContext(Class<? extends Entity> entityType) {
        EntityContext ec = entityContexts.get(entityType);
        if (ec == null) {
            ec = new EntityContext(entityType);
            entityContexts.put(entityType, ec);
        }
        return ec;
    }

    Collection<EntityContext> getEntityContexts() {
        return Collections.unmodifiableCollection(entityContexts.values());
    }

    RandomAccessFile getFile(Class<? extends Entity> entityType) throws FileNotFoundException {
        RandomAccessFile raf = files.get(entityType);
        if (raf == null) {
            raf = new RandomAccessFile(Paths.get(path, entityType.getCanonicalName()).toString(), "rw");
            files.put(entityType, raf);
        }
        return raf;
    }

    Entity search(Class<? extends Entity> entityType, Field f, Object value) throws IOException {
        EntityContext ec = getEntityContext(entityType);
        RandomAccessFile raf = getFile(entityType);
        Entity e = ec.create();
        raf.seek(0);
        try {
            for (int i = 0; i < raf.length() / ec.size; i++) {
                ec.read(raf, e);
                if (f.get(e).equals(value)) {
                    return e;
                }
            }
        } catch (IllegalAccessException exc) {
            throw new RuntimeException(exc);
        }
        return null;
    }

    private int getNewId(Class<? extends Entity> entityType) throws IOException, LogicException {
        EntityContext ec = getEntityContext(entityType);
        RandomAccessFile raf = files.get(entityType);
        if (raf.length() == 0) {
            return 1;
        }
        Entity e = ec.create();
        raf.seek(raf.length() - ec.size);
        ec.read(raf, e);
        if (e.getId() == Integer.MAX_VALUE) {
            throw new LogicException("Не удалось найти свободный идентификатор!");
        }
        return e.getId() + 1;
    }

}
