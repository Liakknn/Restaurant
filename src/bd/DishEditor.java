package bd;

import java.awt.Frame;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import javax.swing.ButtonModel;
import javax.swing.JOptionPane;

public class DishEditor extends javax.swing.JDialog {

    private final Database bd;
    private Dish e;

    public DishEditor(Frame owner, Database bd, Dish e) throws IOException {
        super(owner, true);
        initComponents();
        if (e == null) {
            e = new Dish();
        }
        id.setText(e.getId() + "");
        name.setText(e.getName());
        ArrayList<Ingredient> list = bd.getIngredients();
        list.sort((p1, p2) -> {
            return p1.getName().compareTo(p2.getName());
        });
        for (Ingredient p : list) {
            ingredient.addItem(p);
            if (p.getId() == e.getIdIngred1() || p.getId() == e.getIdIngred2()
                    || p.getId() == e.getIdIngred3()) {
                ingredient.setSelectedItem(p);
            }
        }
        //вопрос
        amount.setText(e.getAmountIngred1() + "");
        amount.setText(e.getAmountIngred2() + "");
        amount.setText(e.getAmountIngred3() + "");
        price.setText(e.getPrice() + "");
        timeCooking.setText(e.getCookingTime().getSeconds() / (24 * 60) + "");
        this.bd = bd;
        this.e = e;
    }

    public Dish getResult() {
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
        id = new javax.swing.JTextField();
        name = new javax.swing.JTextField();
        price = new javax.swing.JTextField();
        timeCooking = new javax.swing.JTextField();
        amount = new javax.swing.JTextField();
        ingredient = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        saveButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

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
        layout.rowHeights = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
        getContentPane().setLayout(layout);

        jLabel1.setText("Код:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        getContentPane().add(jLabel1, gridBagConstraints);

        jLabel2.setText("Наименование:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        getContentPane().add(jLabel2, gridBagConstraints);

        jLabel3.setText("Ингредиент:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        getContentPane().add(jLabel3, gridBagConstraints);

        jLabel4.setText("Объем ингпредиента:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        getContentPane().add(jLabel4, gridBagConstraints);

        jLabel5.setText("Стоимость:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        getContentPane().add(jLabel5, gridBagConstraints);

        jLabel6.setText("Время приготовления(минуты):");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        getContentPane().add(jLabel6, gridBagConstraints);

        id.setText("jTextField1");
        id.setEnabled(false);
        id.setPreferredSize(new java.awt.Dimension(200, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 10);
        getContentPane().add(id, gridBagConstraints);

        name.setText("jTextField2");
        name.setPreferredSize(new java.awt.Dimension(200, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        getContentPane().add(name, gridBagConstraints);

        price.setText("jTextField5");
        price.setPreferredSize(new java.awt.Dimension(200, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        getContentPane().add(price, gridBagConstraints);

        timeCooking.setText("jTextField6");
        timeCooking.setPreferredSize(new java.awt.Dimension(200, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        getContentPane().add(timeCooking, gridBagConstraints);

        amount.setText("jTextField7");
        amount.setPreferredSize(new java.awt.Dimension(200, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        getContentPane().add(amount, gridBagConstraints);

        ingredient.setPreferredSize(new java.awt.Dimension(200, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        getContentPane().add(ingredient, gridBagConstraints);

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
        gridBagConstraints.gridy = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LAST_LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 10);
        getContentPane().add(jPanel1, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        try {
            e.setName(name.getText());
            //вопрос
            e.setIdIngred1(((Ingredient) ingredient.getSelectedItem()).getId());
            e.setIdIngred2(((Ingredient) ingredient.getSelectedItem()).getId());
            e.setIdIngred3(((Ingredient) ingredient.getSelectedItem()).getId());
            e.setAmountIngred1(Double.parseDouble(amount.getText()));
            e.setAmountIngred2(Double.parseDouble(amount.getText()));
            e.setAmountIngred3(Double.parseDouble(amount.getText()));
            e.setPrice(Double.parseDouble(price.getText()));
            int minute = Integer.parseInt(timeCooking.getText());
            e.setCookingTime(Duration.ofMinutes(minute));

            e = (Dish) bd.save(e);
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Не удалось преобразовать строку в число!", "Ошибка", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_saveButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        e = null;
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        int r = JOptionPane.showConfirmDialog(this, "Сохранить изменения?", "Подтверждение выхода", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (r == JOptionPane.YES_OPTION) {
            saveButtonActionPerformed(null);
        } else {
            cancelButtonActionPerformed(null);
        }
    }//GEN-LAST:event_formWindowClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField amount;
    private javax.swing.JButton cancelButton;
    private javax.swing.JTextField id;
    private javax.swing.JComboBox<Ingredient> ingredient;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField name;
    private javax.swing.JTextField price;
    private javax.swing.JButton saveButton;
    private javax.swing.JTextField timeCooking;
    // End of variables declaration//GEN-END:variables
}
