package database;

import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public final class Manager implements Closeable {

    private final String path;
    private final Map<Class<? extends Entity>, EntityContext> entityContexts = new HashMap();
    private final Map<Class<? extends Entity>, RandomAccessFile> files = new HashMap();
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    public Manager(String path) {
        this.path = path;
    }

    public DateTimeFormatter getDateFormatter() {
        return dateFormatter;
    }

    public void setDateFormatter(DateTimeFormatter dateFormat) {
        this.dateFormatter = dateFormat;
    }

    public DateTimeFormatter getDateTimeFormatter() {
        return dateTimeFormatter;
    }

    public void setDateTimeFormatter(DateTimeFormatter dateTimeFormat) {
        this.dateTimeFormatter = dateTimeFormat;
    }

    public EntityContext getEntityContext(Class<? extends Entity> entityType) {
        EntityContext ec = entityContexts.get(entityType);
        if (ec == null) {
            ec = new EntityContext(entityType);
            entityContexts.put(entityType, ec);
        }
        return ec;
    }

    public Collection<EntityContext> getEntityContexts() {
        return Collections.unmodifiableCollection(entityContexts.values());
    }

    public <E extends Entity> List<E> get(Class<E> entityType) throws IOException {
        ArrayList<E> result = new ArrayList();
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
        Entity entity = getEntityContext(entityType).create();
        entity = restore(entity, id);
        return (E) entity;
    }

    public Entity restore(Entity entity, int id) throws IOException {
        EntityContext context = getEntityContext(entity.getClass());
        RandomAccessFile raf = getFile(context.type);
        int left = 0;
        int right = (int) ((raf.length() / context.size) - 1);
        while (right >= left) {
            int i = (right + left) / 2;
            raf.seek(context.size * i);
            context.read(raf, entity);
            if (entity.getId() == id) {
                raf.seek(context.size * i);
                return entity;
            }
            if (entity.getId() < id) {
                left = i + 1;
            } else {
                right = i - 1;
            }
        }
        return null;
    }

    public Entity searchFirst(Class<? extends Entity> entityType, Predicate<Entity> predicate) throws IOException {

        EntityContext context = getEntityContext(entityType);
        RandomAccessFile raf = getFile(entityType);
        Entity entity = context.create();
        raf.seek(0);
        for (int i = 0; i < raf.length() / context.size; i++) {
            context.read(raf, entity);
            if (predicate.test(entity)) {
                return entity;
            }
        }
        return null;
    }

    public List<Entity> searchAll(Class<? extends Entity> entityType, Predicate<Entity> predicate) throws IOException {
        ArrayList<Entity> list = new ArrayList();
        EntityContext context = getEntityContext(entityType);
        RandomAccessFile raf = getFile(entityType);
        Entity entity = context.create();
        raf.seek(0);
        for (int i = 0; i < raf.length() / context.size; i++) {
            context.read(raf, entity);
            if (predicate.test(entity)) {
                list.add(entity);
                entity = context.create();
            }
        }
        return list;
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

    public void delete(Class<? extends Entity> entityType, int id) throws IOException, LogicException {
        EntityContext context = getEntityContext(entityType);
        RandomAccessFile raf = getFile(context.type);
        Entity entity = get(entityType, id);
        context.validateBeforeDelete(this, entity);
        if (get(context.type, entity.getId()) == null) {
            throw new LogicException(String.format("Удаляемая сущность с идентификатором %d не найдена!", entity.getId()));
        }
        Entity t = context.create();
        for (int i = (int) (raf.getFilePointer() / context.size); i < (int) (raf.length() / context.size) - 1; ++i) {
            raf.seek(context.size * (i + 1));
            context.read(raf, t);
            raf.seek(context.size * i);
            context.write(raf, t);
        }
        raf.setLength(raf.length() - context.size);
    }

    @Override
    public void close() throws IOException {
        for (RandomAccessFile raf : files.values()) {
            raf.close();
        }
        files.clear();
    }

    private RandomAccessFile getFile(Class<? extends Entity> entityType) throws FileNotFoundException {
        RandomAccessFile raf = files.get(entityType);
        if (raf == null) {
            raf = new RandomAccessFile(Paths.get(path, entityType.getCanonicalName()).toString(), "rw");
            files.put(entityType, raf);
        }
        return raf;
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
