package gui;

import database.Entity;
import database.EntityContext;
import database.LogicException;
import database.Manager;
import database.Storable;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public final class EntityEditor extends javax.swing.JDialog {

    private final Manager manager;
    private final EntityContext context;
    private final Map<Field, JComponent> fieldComponentMap;
    private Entity entity;

    public EntityEditor(Frame owner, Manager manager, Class<? extends Entity> entityType) throws IOException {
        super(owner, true);
        this.manager = manager;
        this.context = manager.getEntityContext(entityType);
        this.fieldComponentMap = new HashMap();
        this.entity = context.create();
        initComponents();
        setupForm();
    }

    public EntityEditor(Frame owner, Manager manager, Entity entity) throws IOException, IllegalAccessException {
        super(owner, true);
        this.manager = manager;
        this.context = manager.getEntityContext(entity.getClass());
        this.fieldComponentMap = new HashMap();
        this.entity = entity;
        initComponents();
        setupForm();
    }

    public Entity getResult() {
        return entity;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        int r = JOptionPane.showConfirmDialog(this, "Сохранить изменения?", "Подтверждение выхода", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        switch (r) {
            case JOptionPane.YES_OPTION:
                save();
                break;
            case JOptionPane.NO_OPTION:
                cancel();
                break;
            default:
        }
    }//GEN-LAST:event_formWindowClosing

    private void setupForm() throws IOException {
        setTitle(context.getSingularName());
        Container pane = getContentPane();
        int row = 0;
        // Верхняя граница
        JPanel top = new JPanel();
        top.setPreferredSize(new Dimension(0, 0));
        GridBagConstraints topConstraints = new GridBagConstraints(0, row, 1, 1, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(10, 0, 0, 0), 0, 0);
        pane.add(top, topConstraints);
        ++row;
        // Редактируемые поля
        for (int i = 0; i < context.fields.size(); ++i, ++row) {
            Field f = context.fields.get(i);
            // Добавление подписи
            JLabel label = new JLabel(context.getFieldName(f));
            GridBagConstraints labelConstraints = new GridBagConstraints(0, row, 1, 1, 0, 0,
                    GridBagConstraints.EAST, GridBagConstraints.NONE,
                    new Insets(1, 10, 1, 5), 0, 0);
            pane.add(label, labelConstraints);
            // Создание элемента управления
            JComponent component;
            GridBagConstraints componentConstraints = new GridBagConstraints(1, row, 1, 1, 0, 0,
                    GridBagConstraints.WEST, GridBagConstraints.NONE,
                    new Insets(1, 0, 1, 10), 0, 0);
            Class<?> t = f.getType();
            Class<? extends Entity> key = f.getAnnotation(Storable.class).key();
            if (t == boolean.class) {
                JCheckBox c = new JCheckBox();
                c.setBorder(null);
                c.setSelected((boolean) context.getValue(entity, f));
                component = c;
            } else if (t == LocalDate.class) {
                JTextField c = new JTextField();
                c.setPreferredSize(new Dimension(100, c.getPreferredSize().height));
                LocalDate value = (LocalDate) context.getValue(entity, f);
                if (value != null) {
                    c.setText(value.format(manager.getDateFormatter()));
                }
                component = c;
            } else if (t == LocalDateTime.class) {
                JTextField c = new JTextField();
                c.setPreferredSize(new Dimension(100, c.getPreferredSize().height));
                LocalDateTime value = (LocalDateTime) context.getValue(entity, f);
                if (value != null) {
                    c.setText(value.format(manager.getDateTimeFormatter()));
                }
                component = c;
            } else if (t == int.class && key != Entity.class) {
                int value = (int) context.getValue(entity, f);
                JComboBox c = new JComboBox();
                c.setPreferredSize(new Dimension(200, c.getPreferredSize().height));
                DefaultComboBoxModel model = (DefaultComboBoxModel) c.getModel();
                List<? extends Entity> list = manager.get(key);
                list.sort((Entity lhs, Entity rhs) -> {
                    return lhs.toString().compareTo(rhs.toString());
                });
                model.addElement("");
                list.stream().forEach((e) -> {
                    model.addElement(e);
                    if (e.getId() == value) {
                        model.setSelectedItem(e);
                    }
                });
                if (model.getSelectedItem() == null) {
                    model.setSelectedItem(model.getElementAt(0));
                }
                component = c;
            } else {
                JTextField c = new JTextField();
                c.setPreferredSize(new Dimension(200, c.getPreferredSize().height));
                Object value = context.getValue(entity, f);
                if (value != null) {
                    c.setText(value.toString());
                }
                component = c;
            }
            // Настройка поля идентификатора
            if (i == 0) {
                component.setEnabled(false);
            } else {
                fieldComponentMap.put(f, component);
            }
            // Добавление элемента управления
            pane.add(component, componentConstraints);
        }
        // Нижняя панель кнопок
        JPanel bottom = new JPanel();
        bottom.setLayout(new FlowLayout(FlowLayout.LEFT, 9, 0));
        GridBagConstraints bottomConstraints = new GridBagConstraints(1, row, 1, 1, 0, 0,
                GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
                new Insets(10, 0, 10, 0), 0, 0);
        pane.add(bottom, bottomConstraints);
        ++row;
        // Кнопка СОХРАНИТЬ
        JButton saveButton = new JButton("Сохранить");
        saveButton.addActionListener((ActionEvent e) -> {
            save();
        });
        bottom.add(saveButton);
        // Кнопка ОТМЕНА
        JButton cancelButton = new JButton("Отмена");
        cancelButton.addActionListener((ActionEvent e) -> {
            cancel();
        });
        bottom.add(cancelButton);
        // Компоновка
        pack();
    }

    private void save() {
        try {
            for (Map.Entry<Field, JComponent> kv : fieldComponentMap.entrySet()) {
                Field f = kv.getKey();
                Class<?> t = f.getType();
                Class<? extends Entity> key = f.getAnnotation(Storable.class).key();
                if (t == boolean.class) {
                    JCheckBox component = (JCheckBox) kv.getValue();
                    context.setValue(entity, f, component.isSelected());
                } else if (t == LocalDate.class) {
                    String s = ((JTextField) kv.getValue()).getText();
                    if (s == null) {
                        s = "";
                    }
                    s = s.trim();
                    if (s.isEmpty()) {
                        context.setValue(entity, f, null);
                    } else {
                        try {
                            context.setValue(entity, f, LocalDate.parse(s, manager.getDateFormatter()));
                        } catch (DateTimeParseException exc) {
                            throw new LogicException("Неправильный формат даты для поля \"%s\". Пример правильного формата: %s.",
                                    context.getFieldName(f), LocalDate.now().format(manager.getDateFormatter()));
                        }
                    }
                } else if (t == LocalDateTime.class) {
                    String s = ((JTextField) kv.getValue()).getText();
                    if (s == null) {
                        s = "";
                    }
                    s = s.trim();
                    if (s.isEmpty()) {
                        context.setValue(entity, f, null);
                    } else {
                        try {
                            context.setValue(entity, f, LocalDateTime.parse(s, manager.getDateTimeFormatter()));
                        } catch (DateTimeParseException exc) {
                            throw new LogicException("Неправильный формат времени для поля \"%s\". Пример правильного формата: %s.",
                                    context.getFieldName(f), LocalDateTime.now().format(manager.getDateTimeFormatter()));
                        }
                    }
                } else if (t == int.class && key != Entity.class) {
                    Object value = ((JComboBox) kv.getValue()).getSelectedItem();
                    if (value instanceof String) {
                        context.setValue(entity, f, 0);
                    } else {
                        context.setValue(entity, f, ((Entity) value).getId());
                    }
                } else if (t == int.class) {
                    String s = ((JTextField) kv.getValue()).getText();
                    if (s == null) {
                        s = "0";
                    }
                    s = s.trim();
                    try {
                        context.setValue(entity, f, Integer.parseInt(s));
                    } catch (NumberFormatException exc) {
                        throw new LogicException("Неправильный формат числа для поля \"%s\".", context.getFieldName(f));
                    }
                } else if (t == long.class) {
                    String s = ((JTextField) kv.getValue()).getText();
                    if (s == null) {
                        s = "0";
                    }
                    s = s.trim();
                    try {
                        context.setValue(entity, f, Long.parseLong(s));
                    } catch (NumberFormatException exc) {
                        throw new LogicException("Неправильный формат числа для поля \"%s\".", context.getFieldName(f));
                    }
                } else if (t == float.class) {
                    String s = ((JTextField) kv.getValue()).getText();
                    if (s == null) {
                        s = "0";
                    }
                    s = s.trim();
                    try {
                        context.setValue(entity, f, Float.parseFloat(s));
                    } catch (NumberFormatException exc) {
                        throw new LogicException("Неправильный формат вещественного числа для поля \"%s\".", context.getFieldName(f));
                    }
                } else if (t == double.class) {
                    String s = ((JTextField) kv.getValue()).getText();
                    if (s == null) {
                        s = "0";
                    }
                    s = s.trim();
                    try {
                        context.setValue(entity, f, Double.parseDouble(s));
                    } catch (NumberFormatException exc) {
                        throw new LogicException("Неправильный формат вещественного числа для поля \"%s\".", context.getFieldName(f));
                    }
                } else if (t == String.class) {
                    String s = ((JTextField) kv.getValue()).getText();
                    if (s == null) {
                        s = "";
                    }
                    s = s.trim();
                    context.setValue(entity, f, !s.isEmpty() ? s : null);
                }
            }
            manager.save(entity);
            dispose();
        } catch (IOException exc) {
            JOptionPane.showMessageDialog(this, "Не удалось сохранить данные!", "Ошибка", JOptionPane.ERROR_MESSAGE);
        } catch (LogicException exc) {
            JOptionPane.showMessageDialog(this, exc.getMessage(), "Ошибка", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void cancel() {
        if (entity.getId() != 0) {
            try {
                manager.restore(entity, entity.getId());
            } catch (IOException exc) {
                JOptionPane.showMessageDialog(this, "Не удалось восстановить первоначальные значения!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
        entity = null;
        dispose();
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        throw new UnsupportedOperationException();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
