package gui;

import database.Manager;
import java.awt.Frame;
import java.io.IOException;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class QueryDialog extends javax.swing.JDialog {

    public QueryDialog(Frame owner, Manager manager) {
        super(owner, true);
        initComponents();
        {
            DefaultTableModel model = (DefaultTableModel) employeePositions.getModel();
            try {
                Queries.queryEmployeePositions(manager).forEach((Queries.EmployeePosition ep) -> {
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
                JOptionPane.showMessageDialog(this, "Не удалось загрузить таблицу \"Сотрудники и должности\"!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
            model.fireTableDataChanged();
        }
        {
            DefaultTableModel model = (DefaultTableModel) dishIngredients.getModel();
            try {
                Queries.queryDishIngridients(manager).forEach((Queries.DishIngridients di) -> {
                    Vector v = new Vector();
                    v.add(di.dish.getName());
                    v.add(di.ingridient1 != null ? di.ingridient1.getName() : null);
                    v.add(di.dish.getIngredientQuantity1());
                    v.add(di.ingridient2 != null ? di.ingridient2.getName() : null);
                    v.add(di.dish.getIngredientQuantity2());
                    v.add(di.ingridient3 != null ? di.ingridient3.getName() : null);
                    v.add(di.dish.getIngredientQuantity3());
                    v.add(di.dish.getPrice());
                    model.getDataVector().add(v);
                });
            } catch (IOException exc) {
                model.getDataVector().clear();
                JOptionPane.showMessageDialog(this, "Не удалось загрузить таблицу \"Блюда и ингредиенты\"!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
            model.fireTableDataChanged();
        }
        {
            DefaultTableModel model = (DefaultTableModel) orders.getModel();
            try {
                Queries.queryOrderEmployeeDishes(manager).forEach((Queries.OrderEmployeeDishes oed) -> {
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
                JOptionPane.showMessageDialog(this, "Не удалось загрузить таблицу \"Блюда и ингредиенты\"!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
            model.fireTableDataChanged();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        employeePositions = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        dishIngredients = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        orders = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Запросы");

        jTabbedPane1.setToolTipText("");

        jPanel1.setLayout(new java.awt.BorderLayout());

        employeePositions.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(employeePositions);

        jPanel1.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Сотрудники и должности", jPanel1);

        jPanel2.setLayout(new java.awt.BorderLayout());

        dishIngredients.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Блюдо", "Игредиент 1", "Объём 1", "Игредиент 2", "Объём 2", "Игредиент 3", "Объём 3", "Стоимость"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.Object.class, java.lang.Double.class, java.lang.Object.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(dishIngredients);

        jPanel2.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Блюда и ингредиенты", jPanel2);

        jPanel3.setMinimumSize(new java.awt.Dimension(800, 500));
        jPanel3.setPreferredSize(new java.awt.Dimension(800, 500));
        jPanel3.setLayout(new java.awt.BorderLayout());

        orders.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(orders);

        jPanel3.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Заказы", jPanel3);

        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable dishIngredients;
    private javax.swing.JTable employeePositions;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable orders;
    // End of variables declaration//GEN-END:variables
}
