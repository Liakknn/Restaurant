package gui;

import database.Entity;
import database.EntityContext;
import database.LogicException;
import database.Manager;
import entities.Dish;
import entities.Employee;
import entities.Ingredient;
import entities.Order;
import entities.Position;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

public class MainFrame extends javax.swing.JFrame {

    private final Map<JRadioButton, Class<? extends Entity>> buttonEntityTypeMap = new HashMap();
    private Manager manager = new Manager("");

    public MainFrame() {
        initComponents();
        table.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            buttonDelete.setEnabled(table.getSelectedRow() != -1);
        });
        openProject("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        chooserGroup = new javax.swing.ButtonGroup();
        chooserPanel = new javax.swing.JPanel();
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
        setMinimumSize(new java.awt.Dimension(800, 600));
        setPreferredSize(new java.awt.Dimension(600, 400));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        chooserPanel.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 10);
        getContentPane().add(chooserPanel, gridBagConstraints);

        tablePanel.setLayout(new java.awt.BorderLayout());

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table);

        tablePanel.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 10);
        getContentPane().add(tablePanel, gridBagConstraints);

        buttonPanel.setLayout(new java.awt.GridBagLayout());

        buttonNew.setText("Новая запись");
        buttonNew.setEnabled(false);
        buttonNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonNewActionPerformed(evt);
            }
        });
        buttonPanel.add(buttonNew, new java.awt.GridBagConstraints());

        buttonDelete.setText("Удалить запись");
        buttonDelete.setEnabled(false);
        buttonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDeleteActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        buttonPanel.add(buttonDelete, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
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
        menuFileClose.setEnabled(false);
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
        menuQueries.setEnabled(false);
        menuQueries.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuQueriesActionPerformed(evt);
            }
        });
        menu.add(menuQueries);

        menuFilters.setText("Фильтры");
        menuFilters.setEnabled(false);
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
        openProject(dlg.getSelectedFile().toString());
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
        menuQueries.setEnabled(false);
        menuFilters.setEnabled(false);
        buttonEntityTypeMap.entrySet().stream().forEach((kv) -> {
            chooserGroup.remove(kv.getKey()); // Удаляем RadioButton из группы кнопок
            chooserPanel.remove(kv.getKey()); // Удаляем RadioButton с формы
        });
        buttonEntityTypeMap.clear();
        pack();
        table.setModel(new DefaultTableModel());
        buttonNew.setEnabled(false);
    }//GEN-LAST:event_menuFileCloseActionPerformed

    private void menuFileExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFileExitActionPerformed
        menuFileCloseActionPerformed(null);
        dispose();
    }//GEN-LAST:event_menuFileExitActionPerformed

    private void menuQueriesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuQueriesActionPerformed

    }//GEN-LAST:event_menuQueriesActionPerformed

    private void menuFiltersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFiltersActionPerformed

    }//GEN-LAST:event_menuFiltersActionPerformed

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        if (evt.getClickCount() != 2 || table.getSelectedRow() == -1 || !(table.getModel() instanceof EntityTableModel)) {
            return;
        }
        EntityTableModel model = (EntityTableModel) table.getModel();
        Entity e = model.get(table.getSelectedRow());
        try {
            EntityEditor editor = new EntityEditor(this, manager, e);
            editor.setLocationRelativeTo(this);
            editor.setVisible(true);
            if (editor.getResult() != null) {
                model.update(table.getSelectedRow());
            }
        } catch (IllegalAccessException | IOException exc) {
            JOptionPane.showMessageDialog(this, "Не удалось открыть редактор сущности!", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_tableMouseClicked

    private void buttonNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonNewActionPerformed
        Class<? extends Entity> entityType = getEntityType();
        if (entityType == null) {
            return;
        }
        try {
            EntityEditor editor = new EntityEditor(this, manager, entityType);
            editor.setLocationRelativeTo(this);
            editor.setVisible(true);
            if (editor.getResult() != null) {
                ((EntityTableModel) table.getModel()).add(editor.getResult());
            }
        } catch (IOException exc) {
            JOptionPane.showMessageDialog(this, "Не удалось открыть редактор сущности!", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_buttonNewActionPerformed

    private void buttonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDeleteActionPerformed
        if (table.getSelectedRow() == -1 || !(table.getModel() instanceof EntityTableModel)) {
            return;
        }
        EntityTableModel model = (EntityTableModel) table.getModel();
        Entity e = model.get(table.getSelectedRow());
        try {
            manager.delete(e.getClass(), e.getId());
            model.remove(table.getSelectedRow());
        } catch (IOException exc) {
            JOptionPane.showMessageDialog(this, "Не удалось удалить сущность!", "Ошибка", JOptionPane.ERROR_MESSAGE);
        } catch (LogicException exс) {
            JOptionPane.showMessageDialog(this, exс.getMessage(), "Ошибка", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_buttonDeleteActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        menuFileCloseActionPerformed(null);
    }//GEN-LAST:event_formWindowClosing

    private void openProject(String path) {
        menuFileCloseActionPerformed(null);
        manager = new Manager(path);
        setLocalization(manager);
        menuFileClose.setEnabled(true);
        menuQueries.setEnabled(true);
        menuFilters.setEnabled(true);
        addTable(Employee.class).doClick();
        addTable(Position.class);
        addTable(Ingredient.class);
        addTable(Dish.class);
        addTable(Order.class);
        pack();
        buttonNew.setEnabled(true);
    }

    private JRadioButton addTable(Class<? extends Entity> entityType) {
        JRadioButton rb = new JRadioButton();
        rb.addActionListener((ActionEvent e) -> switchTable(entityType));
        rb.setText(manager.getEntityContext(entityType).getPluralName());
        buttonEntityTypeMap.put(rb, entityType);
        chooserGroup.add(rb);
        chooserPanel.add(rb);
        return rb;
    }

    private void switchTable(Class<? extends Entity> entityType) {
        EntityTableModel model = new EntityTableModel(manager, entityType);
        try {
            model.addAll(manager.get(entityType));
        } catch (IOException exc) {
            model.clear();
            JOptionPane.showMessageDialog(this, "Не удалось загрузить данные!", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
        table.setModel(model);
    }

    private Class<? extends Entity> getEntityType() {
        for (JRadioButton rb : buttonEntityTypeMap.keySet()) {
            if (rb.isSelected()) {
                return buttonEntityTypeMap.get(rb);
            }
        }
        return null;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        throw new UnsupportedOperationException();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException();
    }

    public static void main(String args[]) throws FileNotFoundException, Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        java.awt.EventQueue.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    private static void setLocalization(Manager manager, Class<? extends Entity> entityType, String singularName, String pluralName, String... fieldNames) {
        EntityContext ec = manager.getEntityContext(entityType);
        HashMap<String, String> fieldNamesMap = new HashMap();
        for (int i = 0; i < fieldNames.length / 2; ++i) {
            fieldNamesMap.put(fieldNames[2 * i], fieldNames[2 * i + 1]);
        }
        ec.setNames(singularName, pluralName, fieldNamesMap);
    }

    private static void setLocalization(Manager manager) {
        setLocalization(manager, Employee.class, null, null);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonDelete;
    private javax.swing.JButton buttonNew;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.ButtonGroup chooserGroup;
    private javax.swing.JPanel chooserPanel;
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
