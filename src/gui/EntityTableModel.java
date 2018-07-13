package gui;

import database.Entity;
import database.EntityContext;
import database.Manager;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class EntityTableModel implements TableModel {

    private final EntityContext context;
    private final ArrayList<Entity> data = new ArrayList();
    private final ArrayList<TableModelListener> listeners = new ArrayList();

    public EntityTableModel(Manager manager, Class<? extends Entity> entityType) {
        context = manager.getEntityContext(entityType);
    }

    public void add(Entity e) {
        add(data.size(), e);
    }

    public void add(int row, Entity e) {
        if (e.getClass() != context.type) {
            throw new RuntimeException(String.format("Попытка добавления сущности %s в модель таблицы, созданную для сущностей %s!",
                    e.getClass().getCanonicalName(), context.type.getCanonicalName()));
        }
        data.add(row, e);
        listeners.forEach((TableModelListener l) -> {
            l.tableChanged(new TableModelEvent(this, row, row, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
        });
    }

    public void addAll(Collection<? extends Entity> es) {
        addAll(data.size(), es);
    }

    public void addAll(int row, Collection<? extends Entity> es) {
        es.stream().filter((Entity e) -> (e.getClass() != context.type)).forEachOrdered((Entity e) -> {
            throw new RuntimeException(String.format("Попытка добавления сущности %s в модел таблицы, созданную для сущностей %s!",
                    e.getClass().getCanonicalName(), context.type.getCanonicalName()));
        });
        data.addAll(row, es);
        listeners.forEach((TableModelListener l) -> {
            l.tableChanged(new TableModelEvent(this, row, row + es.size() - 1, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
        });
    }

    public Entity get(int row) {
        return data.get(row);
    }

    public void update(int row) {
        listeners.forEach((TableModelListener l) -> {
            l.tableChanged(new TableModelEvent(this, row));
        });
    }

    public void remove(int row) {
        data.remove(row);
        listeners.forEach((TableModelListener l) -> {
            l.tableChanged(new TableModelEvent(this, row, row, TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE));
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
        return context.fields.size();
    }

    @Override
    public String getColumnName(int columnIndex) {
        return context.getFieldName(context.fields.get(columnIndex));
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return context.fields.get(columnIndex).getType();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return context.getValue(data.get(rowIndex), context.fields.get(columnIndex));
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
