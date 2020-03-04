/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yakut.azone.gui;

 
import com.yakut.azone.beans.User;
import com.yakut.azone.controller.UserController;
import com.yakut.azone.util.Setting;
import com.yakut.azone.util.Utils;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author yakut
 */
public class UserPanel extends javax.swing.JPanel {

          List<User> userList;
          User user;
          boolean isNewUser = false;
          JCheckBox[] checkList;

          public UserPanel() {
                    initComponents();
          }

          public void init() {
                    checkList = new JCheckBox[]{
                              jCheckBox1,
                              jCheckBox2,
                              jCheckBox3,
                              jCheckBox4,
                              jCheckBox5,
                              jCheckBox6,
                              jCheckBox7,
                            
                    };
                    userListele();
          }

          public void refresh() {
                    userListele();
          }
          UserController userController;

          public UserController getUserController() {
                    if (userController == null) {
                              userController = new UserController();
                    }
                    return userController;
          }

          public void userListele() {
                    userList = getUserController().getUserList();
                    DefaultTableModel model = new DefaultTableModel() {
                              @Override
                              public boolean isCellEditable(int row, int col) {
                                        return false;
                              }
                    };

                    model.setColumnIdentifiers(new String[]{
                                      "Kullanıcı Adı", "Parrola"
                            });

                    for (User u : userList) {
                              model.addRow(new String[]{u.getName(), "*********"});
                    }
                    jTable1.setModel(model);
                    if (userList.size() > 0) {
                              user = userList.get(0);
                              userGoster();
                    } else {
                              alanlariBosalt();
                    }
          }

          public void userGoster() {

                    jTextField1.setText(user.getName());
                    jPasswordField1.setText(user.getPassword());
                    int son = checkList.length > user.getHaklar().length() ? user.getHaklar().length() : checkList.length;

                    for (int k = 0; k < son; k++) {
                              if (user.getHaklar().charAt(k) == '1') {
                                        checkList[k].setSelected(true);
                              } else {
                                        checkList[k].setSelected(false);
                              }
                    }
                    isNewUser = false;
          }

          public void alanlariBosalt() {
                    jTextField1.setText("");
                    jPasswordField1.setText("");

                    for (int k = 0; k < checkList.length; k++) {
                              checkList[k].setSelected(false);
                    }
                    user = null;
                    isNewUser = true;
          }

          public void userSil() {
                    getUserController().delete(user);
                    userListele();
          }

          public void userKaydet() {
                    if (isNewUser) {
                              user = new User();
                    }

                    user.setName(jTextField1.getText());
                    user.setPassword(new String(jPasswordField1.getPassword()));
                    String hak = "";
                    for (int k = 0; k < checkList.length; k++) {
                              if (checkList[k].isSelected()) {
                                        hak += "1";
                              } else {
                                        hak += "0";
                              }
                    }
                    user.setHaklar(hak);
                    if (isNewUser) {
                              getUserController().persist(user);
                    } else {
                              getUserController().merge(user);
                    }
                    userListele();
          }

          public void tableSecildi() {
                    int index = jTable1.getSelectedRow();
                    user = userList.get(index);
                    userGoster();
          }

          public static void main(String[] args) {
                    Utils.startNimbus();
                    UserPanel u = new UserPanel();
                  
                    u.init();
          }

          @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        jCheckBox6 = new javax.swing.JCheckBox();
        jCheckBox7 = new javax.swing.JCheckBox();
        jPasswordField1 = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(Setting.getSettings().getSkinBackground2Color());
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        jLabel1.setForeground(Setting.getSettings().getSkinForeground2Color());
        jLabel1.setText("Kullanıcı");

        jLabel2.setForeground(Setting.getSettings().getSkinForeground2Color());
        jLabel2.setText("Parola");

        jCheckBox1.setForeground(Setting.getSettings().getSkinForeground2Color());
        jCheckBox1.setText("Otomatik Giriş");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jLabel3.setForeground(Setting.getSettings().getSkinForeground2Color());
        jLabel3.setText("Haklar");

        jCheckBox2.setForeground(Setting.getSettings().getSkinForeground2Color());
        jCheckBox2.setText(" programını kullan");

        jCheckBox3.setForeground(Setting.getSettings().getSkinForeground2Color());
        jCheckBox3.setText("Abone Tanımları");

        jCheckBox4.setForeground(Setting.getSettings().getSkinForeground2Color());
        jCheckBox4.setText("Kapi Tanımları");

        jCheckBox5.setForeground(Setting.getSettings().getSkinForeground2Color());
        jCheckBox5.setText("Yetkiler");

        jCheckBox6.setForeground(Setting.getSettings().getSkinForeground2Color());
        jCheckBox6.setText("Raporlar");

        jCheckBox7.setForeground(Setting.getSettings().getSkinForeground2Color());
        jCheckBox7.setText("Ayarlar");

        jPasswordField1.setText("jPasswordField1");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yakut/resource/package-purge.png"))); // NOI18N
        jButton1.setText("Sil");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yakut/resource/package-install.png"))); // NOI18N
        jButton2.setText("Yeni");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yakut/resource/mail-inbox.png"))); // NOI18N
        jButton3.setText("Kaydet");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField1))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(17, 17, 17)
                                        .addComponent(jCheckBox1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jPasswordField1))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jCheckBox2)
                                    .addComponent(jCheckBox3)
                                    .addComponent(jCheckBox4)
                                    .addComponent(jCheckBox5)
                                    .addComponent(jCheckBox6)
                                    .addComponent(jCheckBox7))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 123, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addGap(17, 17, 17))
        );

        add(jPanel1, java.awt.BorderLayout.WEST);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable1.setRowHeight(Setting.getSettings().getTableRowHeight());
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable1KeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

        private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
                  // TODO add your handling code here:
        }//GEN-LAST:event_jCheckBox1ActionPerformed

        private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
                  userSil();
        }//GEN-LAST:event_jButton1ActionPerformed

        private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
                  alanlariBosalt();
        }//GEN-LAST:event_jButton2ActionPerformed

        private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
                  userKaydet();
        }//GEN-LAST:event_jButton3ActionPerformed

        private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
                  tableSecildi();
        }//GEN-LAST:event_jTable1MouseClicked

        private void jTable1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyReleased
                  tableSecildi();
        }//GEN-LAST:event_jTable1KeyReleased
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JCheckBox jCheckBox7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
