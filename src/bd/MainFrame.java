package bd;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

public class MainFrame extends javax.swing.JFrame {

    public MainFrame() {
        initComponents();
        try {
            bd = new Database("");
            updateAll();
            newRecord.setEnabled(true); //включаем кнопку
            removeRecord.setEnabled(true);
            menuFilter.setEnabled(true);
            menuQueries.setEnabled(true);
            return;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Не удалось загрузить данные!", "Ошибка загрузки базы данных", JOptionPane.ERROR_MESSAGE);
        }
        MenuCloseBdActionPerformed(null);
    }

    private Database bd;

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        tabbedPane = new javax.swing.JTabbedPane();
        employeePane = new javax.swing.JScrollPane();
        employeeTable = new javax.swing.JTable();
        positionPane = new javax.swing.JScrollPane();
        positionTable = new javax.swing.JTable();
        ingredientPane = new javax.swing.JScrollPane();
        ingredientTable = new javax.swing.JTable();
        dishPane = new javax.swing.JScrollPane();
        dishTable = new javax.swing.JTable();
        orderPane = new javax.swing.JScrollPane();
        orderTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        newRecord = new javax.swing.JButton();
        removeRecord = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuOpen = new javax.swing.JMenuItem();
        MenuCloseBd = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        menuExit = new javax.swing.JMenuItem();
        menuQueries = new javax.swing.JMenu();
        menuFilter = new javax.swing.JMenu();

        jMenuItem2.setText("jMenuItem2");

        jMenuItem3.setText("jMenuItem3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Ресторан");
        setMinimumSize(new java.awt.Dimension(800, 600));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        java.awt.GridBagLayout layout = new java.awt.GridBagLayout();
        layout.columnWidths = new int[] {0};
        layout.rowHeights = new int[] {0, 5, 0};
        getContentPane().setLayout(layout);

        employeeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Код сотрудника", "ФИО", "Возраст", "Пол", "Адрес", "Телефон", "Паспортные данные", "Код должности"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
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
        employeeTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                employeeTableMouseClicked(evt);
            }
        });
        employeePane.setViewportView(employeeTable);

        tabbedPane.addTab("Сотрудники", employeePane);

        positionTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Код должности", "Наименование должности", "Оклад", "Обязанности", "Требования"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        positionTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                positionTableMouseClicked(evt);
            }
        });
        positionPane.setViewportView(positionTable);

        tabbedPane.addTab("Должности", positionPane);

        ingredientTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Код игредиента", "Наименование игредиента", "Дата выпуска", "Объем", "Срок годности", "Стоимость", "Поставщик"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.Double.class, java.lang.Object.class, java.lang.Double.class, java.lang.String.class
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
        ingredientTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ingredientTableMouseClicked(evt);
            }
        });
        ingredientPane.setViewportView(ingredientTable);

        tabbedPane.addTab("Склад", ingredientPane);

        dishTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Код блюда", "Наименование блюда", "Код игредиента 1", "Объем игредиента 1", "Код игредиента 2", "Объем игредиента 2", "Код игредиента 3", "Объем игредиента 3", "Стоимость", "Время приготовления"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        dishTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dishTableMouseClicked(evt);
            }
        });
        dishPane.setViewportView(dishTable);

        tabbedPane.addTab("Меню", dishPane);

        orderTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Дата", "Время", "ФИО заказчика", "Телефон", "Код блюда 1", "Код блюда 2", "Код блюда 3", "Стоимость", "Отметка о выполнении", "Код сотрудника"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Boolean.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        orderTable.setGridColor(new java.awt.Color(128, 128, 239));
        orderTable.setRowHeight(17);
        orderTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                orderTableMouseClicked(evt);
            }
        });
        orderPane.setViewportView(orderTable);

        tabbedPane.addTab("Заказ", orderPane);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 808;
        gridBagConstraints.ipady = 86;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(tabbedPane, gridBagConstraints);

        newRecord.setText("Новая запись");
        newRecord.setEnabled(false);
        newRecord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newRecordActionPerformed(evt);
            }
        });
        jPanel1.add(newRecord);

        removeRecord.setText("Удалить запись");
        removeRecord.setEnabled(false);
        removeRecord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeRecordActionPerformed(evt);
            }
        });
        jPanel1.add(removeRecord);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 10);
        getContentPane().add(jPanel1, gridBagConstraints);

        jMenu1.setText("Файл");

        menuOpen.setText("Открыть БД");
        menuOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuOpenActionPerformed(evt);
            }
        });
        jMenu1.add(menuOpen);

        MenuCloseBd.setText("Закрыть БД");
        MenuCloseBd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuCloseBdActionPerformed(evt);
            }
        });
        jMenu1.add(MenuCloseBd);
        jMenu1.add(jSeparator1);

        menuExit.setText("Выход.");
        menuExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuExitActionPerformed(evt);
            }
        });
        jMenu1.add(menuExit);

        jMenuBar1.add(jMenu1);

        menuQueries.setText("Запросы");
        menuQueries.setEnabled(false);
        menuQueries.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuQueriesActionPerformed(evt);
            }
        });
        jMenuBar1.add(menuQueries);

        menuFilter.setText("Фильтры");
        menuFilter.setEnabled(false);
        menuFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFilterActionPerformed(evt);
            }
        });
        jMenuBar1.add(menuFilter);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuOpenActionPerformed
        JFileChooser file = new JFileChooser();
        file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        file.setDialogTitle("Выберите папку с файлами БД");
        if (file.showDialog(this, "Открыть") != JFileChooser.APPROVE_OPTION) {
            return;
        }
        try {
            bd = new Database(file.getSelectedFile().toString());
            updateAll();
            newRecord.setEnabled(true); //включаем кнопку
            removeRecord.setEnabled(true);
            menuFilter.setEnabled(true);
            menuQueries.setEnabled(true);
            return;
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Не найдены файлы базы данных!",
                    "Ошибка загрузки базы данных",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Не удалось загрузить данные!",
                    "Ошибка загрузки базы данных",
                    JOptionPane.ERROR_MESSAGE);
        }
        MenuCloseBdActionPerformed(null);
    }//GEN-LAST:event_menuOpenActionPerformed

    private void menuExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuExitActionPerformed
        if (bd != null) {
            try {
                bd.close();
            } catch (IOException ex) {
            }
        }
        dispose();
    }//GEN-LAST:event_menuExitActionPerformed

    private void newRecordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newRecordActionPerformed
        try {
            if (tabbedPane.getSelectedComponent() == employeePane) {
                EmployeeEditor editor = new EmployeeEditor(this, bd, null);
                editor.setLocationRelativeTo(this);
                editor.setVisible(true);
                if (editor.getResult() != null) {
                    ((DefaultTableModel) employeeTable.getModel()).addRow(updateOrCreateEmployee(editor.getResult(), null));
                }
            } else if (tabbedPane.getSelectedComponent() == positionPane) {
                PositionEditor editor = new PositionEditor(this, bd, null);
                editor.setLocationRelativeTo(this);
                editor.setVisible(true);
                if (editor.getResult() != null) {
                    ((DefaultTableModel) positionTable.getModel()).addRow(updateOrCreatePosition(editor.getResult(), null));
                }
            } else if (tabbedPane.getSelectedComponent() == ingredientPane) {
                IngredientEditor editor = new IngredientEditor(this, bd, null);
                editor.setLocationRelativeTo(this);
                editor.setVisible(true);
                if (editor.getResult() != null) {
                    ((DefaultTableModel) ingredientTable.getModel()).addRow(updateOrCreateIngredient(editor.getResult(), null));
                }
            } else if (tabbedPane.getSelectedComponent() == dishPane) {
                DishEditor editor = new DishEditor(this, bd, null);
                editor.setLocationRelativeTo(this);
                editor.setVisible(true);
                if (editor.getResult() != null) {
                    ((DefaultTableModel) dishTable.getModel()).addRow(updateOrCreateDish(editor.getResult(), null));
                }
            } else if (tabbedPane.getSelectedComponent() == orderPane) {
                OrderEditor editor = new OrderEditor(this, bd, null);
                editor.setLocationRelativeTo(this);
                editor.setVisible(true);
                if (editor.getResult() != null) {
                    ((DefaultTableModel) orderTable.getModel()).addRow(updateOrCreateOrder(editor.getResult(), null));
                }
            }
        } catch (IOException ex) {
        }
    }//GEN-LAST:event_newRecordActionPerformed

    private void removeRecordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeRecordActionPerformed
        try {
            if (tabbedPane.getSelectedComponent() == employeePane) {
                int row = employeeTable.getSelectedRow();
                if (row == -1) {
                    return;
                }
                int id = (int) employeeTable.getValueAt(row, 0);
                bd.remove(new Employee(id));
                DefaultTableModel m = (DefaultTableModel) employeeTable.getModel();
                m.removeRow(row);
            } else if (tabbedPane.getSelectedComponent() == positionPane) {
                int row = positionTable.getSelectedRow();
                if (row == -1) {
                    return;
                }
                int id = (int) positionTable.getValueAt(row, 0);
                bd.remove(new Position(id));
                DefaultTableModel m = (DefaultTableModel) positionTable.getModel();
                m.removeRow(row);
            } else if (tabbedPane.getSelectedComponent() == ingredientPane) {
                int row = ingredientTable.getSelectedRow();
                if (row == -1) {
                    return;
                }
                int id = (int) ingredientTable.getValueAt(row, 0);
                bd.remove(new Ingredient(id));
                DefaultTableModel m = (DefaultTableModel) ingredientTable.getModel();
                m.removeRow(row);
            } else if (tabbedPane.getSelectedComponent() == dishPane) {
                int row = dishTable.getSelectedRow();
                if (row == -1) {
                    return;
                }
                int id = (int) dishTable.getValueAt(row, 0);
                bd.remove(new Dish(id));
                DefaultTableModel m = (DefaultTableModel) dishTable.getModel();
                m.removeRow(row);
            } else if (tabbedPane.getSelectedComponent() == orderPane) {
                int row = orderTable.getSelectedRow();
                if (row == -1) {
                    return;
                }
                int id = (int) orderTable.getValueAt(row, 0);
                bd.remove(new Order(id));
                DefaultTableModel m = (DefaultTableModel) orderTable.getModel();
                m.removeRow(row);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_removeRecordActionPerformed

    private void menuQueriesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuQueriesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuQueriesActionPerformed

    private void menuFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFilterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuFilterActionPerformed

    private void MenuCloseBdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuCloseBdActionPerformed
        if (bd != null) {
            try {
                bd.close();
            } catch (IOException ex) {
            }
        }
        bd = null;
        try {
            updateAll();
        } catch (IOException ex) {
        }
        newRecord.setEnabled(false); //выключаем кнопку
        removeRecord.setEnabled(false);
        menuFilter.setEnabled(false);
        menuQueries.setEnabled(false);
    }//GEN-LAST:event_MenuCloseBdActionPerformed

    private void employeeTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_employeeTableMouseClicked
        if (evt.getClickCount() != 2) {
            return;
        }
        int row = employeeTable.getSelectedRow();
        if (row == -1) {
            return;
        }
        try {
            Employee e = bd.getEmployee((int) employeeTable.getValueAt(row, 0));
            EmployeeEditor editor = new EmployeeEditor(this, bd, e);
            editor.setLocationRelativeTo(this);
            editor.setVisible(true);
            if (editor.getResult() != null) {
                DefaultTableModel m = (DefaultTableModel) employeeTable.getModel();
                Vector v = (Vector) m.getDataVector().get(row);
                updateOrCreateEmployee(editor.getResult(), v);
                m.fireTableRowsUpdated(row, row);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Не удалось загрузить данные!",
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_employeeTableMouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if (bd != null) {
            try {
                bd.close();
            } catch (IOException ex) {
            }
        }
    }//GEN-LAST:event_formWindowClosing

    private void positionTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_positionTableMouseClicked
        if (evt.getClickCount() != 2) {
            return;
        }
        int row = positionTable.getSelectedRow();
        if (row == -1) {
            return;
        }
        try {
            Position e = bd.getPosition((int) positionTable.getValueAt(row, 0));
            PositionEditor editor = new PositionEditor(this, bd, e);
            editor.setLocationRelativeTo(this);
            editor.setVisible(true);
            if (editor.getResult() != null) {
                DefaultTableModel m = (DefaultTableModel) positionTable.getModel();
                Vector v = (Vector) m.getDataVector().get(row);
                updateOrCreatePosition(editor.getResult(), v);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Не удалось загрузить данные!",
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_positionTableMouseClicked

    private void ingredientTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ingredientTableMouseClicked
        if (evt.getClickCount() != 2) {
            return;
        }
        int row = ingredientTable.getSelectedRow();
        if (row == -1) {
            return;
        }
        try {
            Ingredient e = bd.getIngredient((int) ingredientTable.getValueAt(row, 0));
            IngredientEditor editor = new IngredientEditor(this, bd, e);
            editor.setLocationRelativeTo(this);
            editor.setVisible(true);
            if (editor.getResult() != null) {
                DefaultTableModel m = (DefaultTableModel) ingredientTable.getModel();
                Vector v = (Vector) m.getDataVector().get(row);
                updateOrCreateIngredient(editor.getResult(), v);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Не удалось загрузить данные!",
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_ingredientTableMouseClicked

    private void dishTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dishTableMouseClicked
        if (evt.getClickCount() != 2) {
            return;
        }
        int row = dishTable.getSelectedRow();
        if (row == -1) {
            return;
        }
        try {
            Dish e = bd.getDish((int) dishTable.getValueAt(row, 0));
            DishEditor editor = new DishEditor(this, bd, e);
            editor.setLocationRelativeTo(this);
            editor.setVisible(true);
            if (editor.getResult() != null) {
                DefaultTableModel m = (DefaultTableModel) dishTable.getModel();
                Vector v = (Vector) m.getDataVector().get(row);
                updateOrCreateDish(editor.getResult(), v);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Не удалось загрузить данные!",
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_dishTableMouseClicked

    private void orderTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_orderTableMouseClicked
        if (evt.getClickCount() != 2) {
            return;
        }
        int row = orderTable.getSelectedRow();
        if (row == -1) {
            return;
        }
        try {
            Order e = bd.getOrder((int) orderTable.getValueAt(row, 0));
            OrderEditor editor = new OrderEditor(this, bd, e);
            editor.setLocationRelativeTo(this);
            editor.setVisible(true);
            if (editor.getResult() != null) {
                DefaultTableModel m = (DefaultTableModel) orderTable.getModel();
                Vector v = (Vector) m.getDataVector().get(row);
                updateOrCreateOrder(editor.getResult(), v);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Не удалось загрузить данные!",
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_orderTableMouseClicked

    private Vector updateOrCreateEmployee(Employee e, Vector v) {
        if (v == null) {
            v = new Vector();
            v.setSize(8);
        }
        v.set(0, e.getId());
        v.set(1, e.getFio());
        v.set(2, e.getAge());
        v.set(3, e.getGender());
        v.set(4, e.getAddress());
        v.set(5, e.getPhone());
        v.set(6, e.getPassport());
        v.set(7, e.getPositionId());
        return v;
    }

    private Vector updateOrCreatePosition(Position e, Vector v) {
        if (v == null) {
            v = new Vector();
            v.setSize(5);
        }
        v.set(0, e.getId());
        v.set(1, e.getName());
        v.set(2, e.getSalary());
        v.set(3, e.getDuties());
        v.set(4, e.getDemands());
        return v;
    }

    private Vector updateOrCreateIngredient(Ingredient e, Vector v) {
        if (v == null) {
            v = new Vector();
            v.setSize(7);
        }
        v.set(0, e.getId());
        v.set(1, e.getName());
        v.set(2, e.getProductionDate());
        v.set(3, e.getAmount());
        v.set(4, e.getShelfLife());
        v.set(5, e.getPrice());
        v.set(6, e.getProvider());
        return v;
    }

    private Vector updateOrCreateDish(Dish e, Vector v) {
        if (v == null) {
            v = new Vector();
            v.setSize(10);
        }
        v.set(0, e.getId());
        v.set(1, e.getName());
        v.set(2, e.getIdIngred1());
        v.set(3, e.getAmountIngred1());
        v.set(4, e.getIdIngred2());
        v.set(5, e.getAmountIngred2());
        v.set(6, e.getIdIngred3());
        v.set(7, e.getAmountIngred3());
        v.set(8, e.getPrice());
        v.set(9, e.getCookingTime());
        return v;
    }

    private Vector updateOrCreateOrder(Order e, Vector v) {
        if (v == null) {
            v = new Vector();
            v.setSize(10);
        }
        v.set(0, e.getId());
        v.set(1, e.getOrderTime());
        v.set(2, e.getFio());
        v.set(3, e.getPhone());
        v.set(4, e.getIdDish1());
        v.set(5, e.getIdDish2());
        v.set(6, e.getIdDish3());
        v.set(7, e.getPrice());
        v.set(8, e.isReady());
        v.set(9, e.getIdEm());

        return v;
    }

    private void updateEmployees() throws IOException {
        DefaultTableModel model = (DefaultTableModel) employeeTable.getModel();
        model.getDataVector().clear();
        if (bd == null) {
            return;
        }
        try {
            for (Employee e : bd.getEmployees()) {
                model.addRow(updateOrCreateEmployee(e, null));
            }
        } catch (IOException ex) {
            model.getDataVector().clear();
            throw ex;
        }
    }

    private void updatePositions() throws IOException {
        DefaultTableModel model = (DefaultTableModel) positionTable.getModel();
        model.getDataVector().clear();
        if (bd == null) {
            return;
        }
        try {
            for (Position e : bd.getPositions()) {
                Vector v = new Vector();
                v.add(e.getId());
                v.add(e.getName());
                v.add(e.getSalary());
                v.add(e.getDuties());
                v.add(e.getDemands());
                model.addRow(v);
            }
        } catch (IOException ex) {
            model.getDataVector().clear();
            throw ex;
        }
    }

    private void updateIngredients() throws IOException {
        DefaultTableModel model = (DefaultTableModel) ingredientTable.getModel();
        model.getDataVector().clear();
        if (bd == null) {
            return;
        }
        try {
            for (Ingredient e : bd.getIngredients()) {
                Vector v = new Vector();
                v.add(e.getId());
                v.add(e.getName());
                v.add(e.getProductionDate());
                v.add(e.getAmount());
                v.add(e.getShelfLife());
                v.add(e.getPrice());
                v.add(e.getProvider());
                model.addRow(v);
            }
        } catch (IOException ex) {
            model.getDataVector().clear();
            throw ex;
        }
    }

    private void updateDishes() throws IOException {
        DefaultTableModel model = (DefaultTableModel) dishTable.getModel();
        model.getDataVector().clear();
        if (bd == null) {
            return;
        }
        try {
            for (Dish e : bd.getDishes()) {
                Vector v = new Vector();
                v.add(e.getId());
                v.add(e.getName());
                v.add(e.getIdIngred1());
                v.add(e.getAmountIngred1());
                v.add(e.getIdIngred2());
                v.add(e.getAmountIngred2());
                v.add(e.getIdIngred3());
                v.add(e.getAmountIngred3());
                v.add(e.getPrice());
                v.add(e.getCookingTime());
                model.addRow(v);
            }
        } catch (IOException ex) {
            model.getDataVector().clear();
            throw ex;
        }
    }

    private void updateOrders() throws IOException {
        DefaultTableModel model = (DefaultTableModel) orderTable.getModel();
        model.getDataVector().clear();
        if (bd == null) {
            return;
        }
        try {
            for (Order e : bd.getOrders()) {
                Vector v = new Vector();
                v.add(e.getId());
                v.add(e.getOrderTime());
                v.add(e.getFio());
                v.add(e.getPhone());
                v.add(e.getIdDish1());
                v.add(e.getIdDish2());
                v.add(e.getIdDish3());
                v.add(e.getPrice());
                v.add(e.isReady());
                v.add(e.getIdEm());
                model.addRow(v);
            }
        } catch (IOException ex) {
            model.getDataVector().clear();
            throw ex;
        }
    }

    private void updateAll() throws IOException {
        updateEmployees();
        updatePositions();
        updateIngredients();
        updateDishes();
        updateOrders();
    }

    public static void main(String args[]) throws FileNotFoundException, Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        java.awt.EventQueue.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem MenuCloseBd;
    private javax.swing.JScrollPane dishPane;
    private javax.swing.JTable dishTable;
    private javax.swing.JScrollPane employeePane;
    private javax.swing.JTable employeeTable;
    private javax.swing.JScrollPane ingredientPane;
    private javax.swing.JTable ingredientTable;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JMenuItem menuExit;
    private javax.swing.JMenu menuFilter;
    private javax.swing.JMenuItem menuOpen;
    private javax.swing.JMenu menuQueries;
    private javax.swing.JButton newRecord;
    private javax.swing.JScrollPane orderPane;
    private javax.swing.JTable orderTable;
    private javax.swing.JScrollPane positionPane;
    private javax.swing.JTable positionTable;
    private javax.swing.JButton removeRecord;
    private javax.swing.JTabbedPane tabbedPane;
    // End of variables declaration//GEN-END:variables
}
