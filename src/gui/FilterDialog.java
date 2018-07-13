package gui;

import database.Manager;
import entities.Ingredient;
import entities.Position;
import java.awt.Frame;
import java.io.IOException;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FilterDialog extends javax.swing.JDialog {

    private final Manager manager;

    public FilterDialog(Frame owner, Manager manager) {
        super(owner, true);
        this.manager = manager;
        initComponents();
        // Загружаем список дожностей
        try {
            DefaultComboBoxModel model = (DefaultComboBoxModel) positions.getModel();
            for (Position p : manager.get(Position.class)) {
                model.addElement(p);
            }
        } catch (IOException exc) {
            JOptionPane.showMessageDialog(this, "Не удалось загрузить должности!", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
        // Загружаем последнюю таблицу
        orderCompletedActionPerformed(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        positionsTable = new javax.swing.JTable();
        positions = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        provider = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        ingredientsTable = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        ordersTable = new javax.swing.JTable();
        orderCompleted = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Фильтры");
        setMinimumSize(new java.awt.Dimension(800, 500));
        setPreferredSize(new java.awt.Dimension(800, 500));

        jPanel1.setLayout(new java.awt.GridBagLayout());

        positionsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ФИО", "Дата рождения", "Должность", "Оклад"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(positionsTable);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel1.add(jScrollPane2, gridBagConstraints);

        positions.setMinimumSize(new java.awt.Dimension(200, 20));
        positions.setPreferredSize(new java.awt.Dimension(200, 20));
        positions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                positionsActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 10);
        jPanel1.add(positions, gridBagConstraints);

        jTabbedPane1.addTab("По должности", jPanel1);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        provider.setMinimumSize(new java.awt.Dimension(200, 20));
        provider.setPreferredSize(new java.awt.Dimension(200, 20));
        provider.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                providerActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 10);
        jPanel2.add(provider, gridBagConstraints);

        ingredientsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ингредиент", "Объем", "Стоимость", "Поставщик"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Double.class, java.lang.Double.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(ingredientsTable);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel2.add(jScrollPane1, gridBagConstraints);

        jTabbedPane1.addTab("По поставщику", jPanel2);

        jPanel3.setLayout(new java.awt.GridBagLayout());

        ordersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Время", "Заказчик", "Блюдо 1", "Блюдо 2", "Блюдо 3", "Стоимость", "Сотрудник"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(ordersTable);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel3.add(jScrollPane3, gridBagConstraints);

        orderCompleted.setText("Выполненные");
        orderCompleted.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orderCompletedActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 10);
        jPanel3.add(orderCompleted, gridBagConstraints);

        jTabbedPane1.addTab("По выполненности", jPanel3);

        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void positionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_positionsActionPerformed
        Position p = (Position) positions.getSelectedItem();
        DefaultTableModel model = (DefaultTableModel) positionsTable.getModel();
        model.getDataVector().clear();
        if (p != null) {
            try {
                Queries.filterByPosition(manager, p.getId()).forEach((Queries.EmployeePosition ep) -> {
                    Vector v = new Vector();
                    v.add(ep.employee.getName());
                    v.add(ep.employee.getBirthday());
                    if (ep.position != null) {
                        v.add(ep.position.getName());
                        v.add(ep.position.getSalary());
                    } else {
                        v.add(null);
                        v.add(null);
                    }
                    model.getDataVector().add(v);
                });
            } catch (IOException exc) {
                model.getDataVector().clear();
                JOptionPane.showMessageDialog(this, "Не удалось загрузить данные!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
        model.fireTableDataChanged();
    }//GEN-LAST:event_positionsActionPerformed

    private void providerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_providerActionPerformed
        DefaultTableModel model = (DefaultTableModel) ingredientsTable.getModel();
        model.getDataVector().clear();
        String s = provider.getText();
        if (s == null) {
            s = "";
        }
        s = s.trim();
        if (s.length() > 0) {
            try {
                Queries.filterByProvider(manager, s).forEach((Ingredient ingredient) -> {
                    Vector v = new Vector();
                    v.add(ingredient.getName());
                    v.add(ingredient.getQuantity());
                    v.add(ingredient.getPrice());
                    v.add(ingredient.getProvider());
                    model.getDataVector().add(v);
                });
            } catch (IOException exc) {
                model.getDataVector().clear();
                JOptionPane.showMessageDialog(this, "Не удалось загрузить данные!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
        model.fireTableDataChanged();
    }//GEN-LAST:event_providerActionPerformed

    private void orderCompletedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orderCompletedActionPerformed
        DefaultTableModel model = (DefaultTableModel) ordersTable.getModel();
        model.getDataVector().clear();
        try {
            Queries.filterByCompleted(manager, orderCompleted.isSelected()).forEach((Queries.OrderEmployeeDishes oed) -> {
                Vector v = new Vector();
                v.add(oed.order.getOrderTime() != null ? oed.order.getOrderTime().format(manager.getDateTimeFormatter()) : null);
                v.add(oed.order.getClientName());
                v.add(oed.dish1 != null ? oed.dish1.getName() : null);
                v.add(oed.dish2 != null ? oed.dish2.getName() : null);
                v.add(oed.dish3 != null ? oed.dish3.getName() : null);
                v.add(oed.order.getPrice());
                v.add(oed.employee != null ? oed.employee.getName() : null);
                model.getDataVector().add(v);
            });
        } catch (IOException exc) {
            model.getDataVector().clear();
            JOptionPane.showMessageDialog(this, "Не удалось загрузить данные!", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
        model.fireTableDataChanged();
    }//GEN-LAST:event_orderCompletedActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable ingredientsTable;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JCheckBox orderCompleted;
    private javax.swing.JTable ordersTable;
    private javax.swing.JComboBox<String> positions;
    private javax.swing.JTable positionsTable;
    private javax.swing.JTextField provider;
    // End of variables declaration//GEN-END:variables
}
