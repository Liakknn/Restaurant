package restaurant;

import database.Entity;
import database.EntityTableModel;
import database.Manager;
import database.restaurant.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

public class MainFrame extends javax.swing.JFrame {

    private Manager manager = new Manager("");

    public MainFrame() {
        initComponents();
        chooserActionPerformed(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        chooserGroup = new javax.swing.ButtonGroup();
        chooserPanel = new javax.swing.JPanel();
        chooserEmployees = new javax.swing.JRadioButton();
        chooserPositions = new javax.swing.JRadioButton();
        chooserIngredients = new javax.swing.JRadioButton();
        chooserDishes = new javax.swing.JRadioButton();
        chooserOrders = new javax.swing.JRadioButton();
        tablePanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        buttonPanel = new javax.swing.JPanel();
        buttonNew = new javax.swing.JButton();
        buttonDelete = new javax.swing.JButton();
        menu = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        menuFileOpen = new javax.swing.JMenuItem();
        menuFileClose = new javax.swing.JMenuItem();
        menuFileSeparator = new javax.swing.JPopupMenu.Separator();
        menuFileExit = new javax.swing.JMenuItem();
        menuQueries = new javax.swing.JMenu();
        menuFilters = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Ресторан");
        setMinimumSize(new java.awt.Dimension(600, 400));
        setPreferredSize(new java.awt.Dimension(600, 400));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        chooserPanel.setLayout(new java.awt.GridBagLayout());

        chooserGroup.add(chooserEmployees);
        chooserEmployees.setSelected(true);
        chooserEmployees.setText("Сотрудники");
        chooserEmployees.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooserActionPerformed(evt);
            }
        });
        chooserPanel.add(chooserEmployees, new java.awt.GridBagConstraints());

        chooserGroup.add(chooserPositions);
        chooserPositions.setText("Должности");
        chooserPositions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooserActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        chooserPanel.add(chooserPositions, gridBagConstraints);

        chooserGroup.add(chooserIngredients);
        chooserIngredients.setText("Ингредиенты");
        chooserIngredients.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooserActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        chooserPanel.add(chooserIngredients, gridBagConstraints);

        chooserGroup.add(chooserDishes);
        chooserDishes.setText("Блюда");
        chooserDishes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooserActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        chooserPanel.add(chooserDishes, gridBagConstraints);

        chooserGroup.add(chooserOrders);
        chooserOrders.setText("Заказы");
        chooserOrders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooserActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        chooserPanel.add(chooserOrders, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 10);
        getContentPane().add(chooserPanel, gridBagConstraints);

        tablePanel.setLayout(new java.awt.BorderLayout());

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(table);

        tablePanel.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 10);
        getContentPane().add(tablePanel, gridBagConstraints);

        buttonPanel.setLayout(new java.awt.GridBagLayout());

        buttonNew.setText("Новая запись");
        buttonPanel.add(buttonNew, new java.awt.GridBagConstraints());

        buttonDelete.setText("Удалить запись");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        buttonPanel.add(buttonDelete, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(buttonPanel, gridBagConstraints);

        menuFile.setText("Файл");

        menuFileOpen.setText("Открыть БД");
        menuFileOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFileOpenActionPerformed(evt);
            }
        });
        menuFile.add(menuFileOpen);

        menuFileClose.setText("Закрыть БД");
        menuFileClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFileCloseActionPerformed(evt);
            }
        });
        menuFile.add(menuFileClose);
        menuFile.add(menuFileSeparator);

        menuFileExit.setText("Выход");
        menuFileExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFileExitActionPerformed(evt);
            }
        });
        menuFile.add(menuFileExit);

        menu.add(menuFile);

        menuQueries.setText("Запросы");
        menuQueries.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuQueriesActionPerformed(evt);
            }
        });
        menu.add(menuQueries);

        menuFilters.setText("Фильтры");
        menuFilters.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFiltersActionPerformed(evt);
            }
        });
        menu.add(menuFilters);

        setJMenuBar(menu);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuFileOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFileOpenActionPerformed
        JFileChooser dlg = new JFileChooser();
        dlg.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        dlg.setDialogTitle("Выберите папку с файлами БД");
        if (dlg.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        menuFileCloseActionPerformed(null);
        manager = new Manager(dlg.getSelectedFile().toString());
        menuFileClose.setEnabled(true);
        chooserEmployees.setEnabled(true);
        chooserPositions.setEnabled(true);
        chooserIngredients.setEnabled(true);
        chooserDishes.setEnabled(true);
        chooserOrders.setEnabled(true);
        buttonNew.setEnabled(true);
        chooserActionPerformed(null);
    }//GEN-LAST:event_menuFileOpenActionPerformed

    private void menuFileCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFileCloseActionPerformed
        if (manager != null) {
            try {
                manager.close();
            } catch (IOException exc) {
                // Игнорируем
            }
            manager = null;
        }

        menuFileClose.setEnabled(false);
        chooserEmployees.setEnabled(false);
        chooserPositions.setEnabled(false);
        chooserIngredients.setEnabled(false);
        chooserDishes.setEnabled(false);
        chooserOrders.setEnabled(false);
        buttonNew.setEnabled(false);
    }//GEN-LAST:event_menuFileCloseActionPerformed

    private void menuFileExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFileExitActionPerformed

    }//GEN-LAST:event_menuFileExitActionPerformed

    private void menuQueriesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuQueriesActionPerformed

    }//GEN-LAST:event_menuQueriesActionPerformed

    private void menuFiltersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFiltersActionPerformed

    }//GEN-LAST:event_menuFiltersActionPerformed

    private void chooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooserActionPerformed
        Class<? extends Entity> type = getCurrentEntityType();
        if (type == null) {
            table.setModel(new DefaultTableModel());
            return;
        }
        try {
            EntityTableModel etm = new EntityTableModel(type);
            etm.addAll(0, manager.get(type));
            table.setModel(etm);
        } catch (IOException exc) {
            table.setModel(new DefaultTableModel());
            JOptionPane.showMessageDialog(this, "Не удалось загрузить данные!", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_chooserActionPerformed

    private Class<? extends Entity> getCurrentEntityType() {
        if (chooserEmployees.getModel().isSelected()) {
            return Employee.class;
        }
        if (chooserPositions.getModel().isSelected()) {
            return Position.class;
        }
        if (chooserIngredients.getModel().isSelected()) {
            return Ingredient.class;
        }
        if (chooserDishes.getModel().isSelected()) {
            return null;
        }
        if (chooserOrders.getModel().isSelected()) {
            return null;
        }
        return null;
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
    private javax.swing.JButton buttonDelete;
    private javax.swing.JButton buttonNew;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JRadioButton chooserDishes;
    private javax.swing.JRadioButton chooserEmployees;
    private javax.swing.ButtonGroup chooserGroup;
    private javax.swing.JRadioButton chooserIngredients;
    private javax.swing.JRadioButton chooserOrders;
    private javax.swing.JPanel chooserPanel;
    private javax.swing.JRadioButton chooserPositions;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuBar menu;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenuItem menuFileClose;
    private javax.swing.JMenuItem menuFileExit;
    private javax.swing.JMenuItem menuFileOpen;
    private javax.swing.JPopupMenu.Separator menuFileSeparator;
    private javax.swing.JMenu menuFilters;
    private javax.swing.JMenu menuQueries;
    private javax.swing.JTable table;
    private javax.swing.JPanel tablePanel;
    // End of variables declaration//GEN-END:variables
}
