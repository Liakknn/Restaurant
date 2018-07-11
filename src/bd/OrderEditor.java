package bd;

import java.awt.Frame;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import javax.swing.ButtonModel;
import javax.swing.JOptionPane;

public class OrderEditor extends javax.swing.JDialog {

    private final Database bd;
    private Order e;
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public OrderEditor(Frame owner, Database bd, Order e) throws IOException {
        super(owner, true);
        initComponents();
        if (e == null) {
            e = new Order();
        }
        id.setText(e.getId() + "");
        if (e.getOrderTime() == null) {
            orderTime.setText("");
        } else {
            orderTime.setText(e.getOrderTime().format(FORMAT));
        }
        fio.setText(e.getFio());
        phone.setText(e.getPhone());
        ArrayList<Dish> list = bd.getDishes();
        list.sort((p1, p2) -> {
            return p1.getName().compareTo(p2.getName());
        });
        for (Dish p : list) {
            dish1.addItem(p);
            dish2.addItem(p);
            dish3.addItem(p);
            if (p.getId() == e.getIdDish1()) {
                dish1.setSelectedItem(p);
            }
            if (p.getId() == e.getIdDish2()) {
                dish2.setSelectedItem(p);
            }
            if (p.getId() == e.getIdDish3()) {
                dish3.setSelectedItem(p);
            }
        }
        price.setText(e.getPrice() + "");
        ready.setSelected(e.isReady());
        ArrayList<Employee> list1 = bd.getEmployees();
        list.sort((p1, p2) -> {
            return p1.getName().compareTo(p2.getName());
        });
        for (Employee p : list1) {
            employee.addItem(p);
            if (p.getId() == e.getIdEm()) {
                employee.setSelectedItem(p);
            }
        }

        this.bd = bd;
        this.e = e;
    }

    public Order getResult() {
        return e;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        id = new javax.swing.JTextField();
        orderTime = new javax.swing.JTextField();
        fio = new javax.swing.JTextField();
        phone = new javax.swing.JTextField();
        price = new javax.swing.JTextField();
        employee = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        saveButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        dish1 = new javax.swing.JComboBox<>();
        dish2 = new javax.swing.JComboBox<>();
        dish3 = new javax.swing.JComboBox<>();
        ready = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Редактирование сотрудника");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        java.awt.GridBagLayout layout = new java.awt.GridBagLayout();
        layout.columnWidths = new int[] {0, 5, 0};
        layout.rowHeights = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
        getContentPane().setLayout(layout);

        jLabel1.setText("Код:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        getContentPane().add(jLabel1, gridBagConstraints);

        jLabel2.setText("Дата:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        getContentPane().add(jLabel2, gridBagConstraints);

        jLabel3.setText("ФИО заказчика:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        getContentPane().add(jLabel3, gridBagConstraints);

        jLabel4.setText("Телефон:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        getContentPane().add(jLabel4, gridBagConstraints);

        jLabel5.setText("Блюда:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        getContentPane().add(jLabel5, gridBagConstraints);

        jLabel6.setText("Стоимость:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        getContentPane().add(jLabel6, gridBagConstraints);

        jLabel7.setText("Отметка о выполнении:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        getContentPane().add(jLabel7, gridBagConstraints);

        jLabel8.setText("Сотрудник:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        getContentPane().add(jLabel8, gridBagConstraints);

        id.setText("jTextField1");
        id.setEnabled(false);
        id.setPreferredSize(new java.awt.Dimension(200, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 10);
        getContentPane().add(id, gridBagConstraints);

        orderTime.setText("jTextField2");
        orderTime.setPreferredSize(new java.awt.Dimension(200, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        getContentPane().add(orderTime, gridBagConstraints);

        fio.setPreferredSize(new java.awt.Dimension(200, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        getContentPane().add(fio, gridBagConstraints);

        phone.setText("jTextField5");
        phone.setPreferredSize(new java.awt.Dimension(200, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        getContentPane().add(phone, gridBagConstraints);

        price.setText("jTextField6");
        price.setPreferredSize(new java.awt.Dimension(200, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        getContentPane().add(price, gridBagConstraints);

        employee.setPreferredSize(new java.awt.Dimension(200, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        getContentPane().add(employee, gridBagConstraints);

        saveButton.setText("Сохранить");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });
        jPanel1.add(saveButton);

        cancelButton.setText("Отмена");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        jPanel1.add(cancelButton);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LAST_LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 10);
        getContentPane().add(jPanel1, gridBagConstraints);

        dish1.setPreferredSize(new java.awt.Dimension(200, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        getContentPane().add(dish1, gridBagConstraints);

        dish2.setPreferredSize(new java.awt.Dimension(200, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        getContentPane().add(dish2, gridBagConstraints);

        dish3.setPreferredSize(new java.awt.Dimension(200, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        getContentPane().add(dish3, gridBagConstraints);

        ready.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                readyActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        getContentPane().add(ready, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        try {

            String s = orderTime.getText();
            e.setOrderTime(LocalDateTime.parse(s, FORMAT));
            e.setFio(orderTime.getText());
            e.setPhone(price.getText());
            Dish d1 = (Dish) dish1.getSelectedItem();
            e.setIdDish1(d1.getId());
            Dish d2 = (Dish) dish2.getSelectedItem();
            e.setIdDish2(d2.getId());
            Dish d3 = (Dish) dish3.getSelectedItem();
            e.setIdDish3(d3.getId());
            e.setPrice(Double.parseDouble(price.getText()));
            e.setReady(ready.isSelected());
            e.setIdEm(((Employee) employee.getSelectedItem()).getId());
            e = (Order) bd.save(e);
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Не удалось преобразовать строку в число!", "Ошибка", JOptionPane.ERROR_MESSAGE);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Неправильный формат даты производства (ожидаемый формат: 01.01.2001 13:45)!", "Ошибка", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_saveButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        e = null;
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void readyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_readyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_readyActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
          int r = JOptionPane.showConfirmDialog(this, "Сохранить изменения?", "Подтверждение выхода", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (r == JOptionPane.YES_OPTION) {
            saveButtonActionPerformed(null);
        } else {
            cancelButtonActionPerformed(null);
        }
    }//GEN-LAST:event_formWindowClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JComboBox<Dish> dish1;
    private javax.swing.JComboBox<Dish> dish2;
    private javax.swing.JComboBox<Dish> dish3;
    private javax.swing.JComboBox<Employee> employee;
    private javax.swing.JTextField fio;
    private javax.swing.JTextField id;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField orderTime;
    private javax.swing.JTextField phone;
    private javax.swing.JTextField price;
    private javax.swing.JCheckBox ready;
    private javax.swing.JButton saveButton;
    // End of variables declaration//GEN-END:variables
}
