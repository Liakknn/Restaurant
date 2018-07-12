package database;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class EntityTableModel implements TableModel {

    private final Class<? extends Entity> type;
    private final ArrayList<Field> fields = new ArrayList<>();
    private final ArrayList<Entity> data = new ArrayList<>();
    private final ArrayList<TableModelListener> listeners = new ArrayList<>();
    private final Entity temp;

    public EntityTableModel(Class<? extends Entity> entityType) {
        type = entityType;
        Iterable<Field> fs = EntityHelper.getFields(entityType);
        for (Field f : fs) {
            fields.add(f);
        }
        temp = EntityHelper.create(entityType);
    }

    public void add(Entity e) {
        add(data.size(), e);
    }

    public void add(int index, Entity e) {
        if (e.getClass() != type) {
            throw new RuntimeException(String.format("Попытка добавления сущности %s в модель, созданную для сущностей %s!",
                    e.getClass().getCanonicalName(), type.getCanonicalName()));
        }
        data.add(index, e);
        listeners.forEach((TableModelListener l) -> {
            l.tableChanged(new TableModelEvent(this, index, index, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
        });
    }

    public void addAll(int index, Collection<? extends Entity> es) {
        es.stream().filter((e) -> (e.getClass() != type)).forEachOrdered((e) -> {
            throw new RuntimeException(String.format("Попытка добавления сущности %s в модель, созданную для сущностей %s!",
                    e.getClass().getCanonicalName(), type.getCanonicalName()));
        });
        data.addAll(index, es);
        listeners.forEach((TableModelListener l) -> {
            l.tableChanged(new TableModelEvent(this, index, index + es.size() - 1, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
        });
    }

    public void update(int index) {
        listeners.forEach((TableModelListener l) -> {
            l.tableChanged(new TableModelEvent(this, index));
        });
    }

    public void remove(int index) {
        data.remove(index);
        listeners.forEach((TableModelListener l) -> {
            l.tableChanged(new TableModelEvent(this, index, index, TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE));
        });
    }

    public void clear() {
        data.clear();
        listeners.forEach((TableModelListener l) -> {
            l.tableChanged(new TableModelEvent(this));
        });
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return fields.size();
    }

    @Override
    public String getColumnName(int columnIndex) {
        return temp.getFieldLocalization(fields.get(columnIndex).getName());
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return fields.get(columnIndex).getType();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            Entity e = data.get(rowIndex);
            return fields.get(columnIndex).get(e);
        } catch (IllegalAccessException exc) {
            throw new RuntimeException(exc);
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        listeners.add(l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        listeners.remove(l);
    }

}
