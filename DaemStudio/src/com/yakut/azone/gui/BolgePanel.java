package com.yakut.azone.gui;

import com.yakut.azone.beans.Bolge;
import com.yakut.azone.controller.BolgeController;
import com.yakut.azone.test.TestFrame;
import com.yakut.azone.util.Setting;
import com.yakut.azone.util.Utils;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author yakut
 */
public class BolgePanel extends javax.swing.JPanel {

          BolgeController bolgeController;
          List<Bolge> bolgelar;
          Bolge bolge;
          DefaultTableModel model;
          boolean isNewBolge = false;

          public BolgePanel() {
                    initComponents();
          }

          public void init() {
                    bolgeListele();
          }

          public void refresh() {
                    bolgeListele();
          }

          public BolgeController getBolgeController() {
                    if (bolgeController == null) {
                              bolgeController = new BolgeController();
                    }
                    return bolgeController;
          }

          public void bolgeListele() {
                    bolgelar = getBolgeController().getBolgeList();
                    model = new DefaultTableModel() {
                              @Override
                              public boolean isCellEditable(int row, int col) {
                                        return false;
                              }
                    };
                    model.setColumnIdentifiers(new String[]{"Ad", "Giriş ", "Çıkış",});
                    for (Bolge k : bolgelar) {
                              String[] row = getBolgeRow(k);
                              model.addRow(row);
                    }
                    jTable1.setModel(model);
                    if (bolgelar.size() > 0) {
                              bolgeGoster(0);
                    } else {
                              alanlariBosalt();
                    }
          }

          public void bolgeGoster(Bolge b) {
                    jTextField1.setText(b.getAd());
                    jCheckBox1.setSelected(b.isMukerrerGirisSerbest());
                    jCheckBox2.setSelected(b.isMukerrerCikisSerbest());
                    bolge = b;
                    isNewBolge = false;
                    jButton1.setEnabled(true);
          }

          public void bolgeGoster(int index) {
                    if (index > -1 && index < bolgelar.size()) {
                              bolgeGoster(bolgelar.get(index));
                    }
          }

          public void alanlariBosalt() {
                    jTextField1.setText("");
                    jCheckBox1.setSelected(false);
                    jCheckBox2.setSelected(false);
                    isNewBolge = true;
                    jButton1.setEnabled(false);
                    bolge = null;
                    
          }

          private String[] getBolgeRow(Bolge k) {
                    String[] row = new String[3];
                    row[0] = k.getAd();
                    row[1] = "" + k.isMukerrerGirisSerbest();
                    row[2] = "" + k.isMukerrerCikisSerbest();
                    return row;
          }

          public void bolgeSil() {
                    if (bolge != null) {
                              if (JOptionPane.showConfirmDialog(null, "Silmek istediğinizden emin misiniz?") == JOptionPane.YES_OPTION) {
                                        getBolgeController().delete(bolge);
                                        bolgeListele();
                              }
                    }
          }

          public void bolgeKaydet() {
                    if (isNewBolge) {
                              bolge = new Bolge();
                    }
                    bolge.setAd(jTextField1.getText());
                    bolge.setMukerrerGirisSerbest(jCheckBox1.isSelected());
                    bolge.setMukerrerCikisSerbest(jCheckBox2.isSelected());
                    if (isNewBolge) {
                              getBolgeController().persist(bolge);
                    } else {
                              getBolgeController().merge(bolge);
                    }
                    bolgeListele();

          }

          public void tableSecildi() {
                    int index = jTable1.getSelectedRow();
                    if (index > -1) {
                              bolgeGoster(index);
                    }
          }

          public static void main(String[] args) {

                    Utils.startNimbus();
                    BolgePanel ap = new BolgePanel();
                    TestFrame t = new TestFrame(ap);
                    ap.init();

          }

          @SuppressWarnings("unchecked")
          // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
          private void initComponents() {

                    jPanel2 = new javax.swing.JPanel();
                    jScrollPane1 = new javax.swing.JScrollPane();
                    jTable1 = new javax.swing.JTable();
                    jPanel1 = new javax.swing.JPanel();
                    jLabel1 = new javax.swing.JLabel();
                    jLabel2 = new javax.swing.JLabel();
                    jTextField1 = new javax.swing.JTextField();
                    jButton1 = new javax.swing.JButton();
                    jButton2 = new javax.swing.JButton();
                    jButton3 = new javax.swing.JButton();
                    jCheckBox1 = new javax.swing.JCheckBox();
                    jCheckBox2 = new javax.swing.JCheckBox();

                    setLayout(new java.awt.BorderLayout());

                    jPanel2.setLayout(new java.awt.BorderLayout());

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

                    jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

                    add(jPanel2, java.awt.BorderLayout.CENTER);

                    jPanel1.setBackground(Setting.getSettings().getSkinBackground2Color());

                    jLabel1.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
                    jLabel1.setForeground(Setting.getSettings().getSkinForeground2Color());
                    jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                    jLabel1.setText("BOLGE");
                    jLabel1.setFocusable(false);
                    jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

                    jLabel2.setForeground(Setting.getSettings().getSkinForeground2Color());
                    jLabel2.setText("Bölge adı");

                    jButton1.setText("Sil");
                    jButton1.addActionListener(new java.awt.event.ActionListener() {
                              public void actionPerformed(java.awt.event.ActionEvent evt) {
                                        jButton1ActionPerformed(evt);
                              }
                    });

                    jButton2.setText("Yeni");
                    jButton2.addActionListener(new java.awt.event.ActionListener() {
                              public void actionPerformed(java.awt.event.ActionEvent evt) {
                                        jButton2ActionPerformed(evt);
                              }
                    });

                    jButton3.setText("Kaydet");
                    jButton3.addActionListener(new java.awt.event.ActionListener() {
                              public void actionPerformed(java.awt.event.ActionEvent evt) {
                                        jButton3ActionPerformed(evt);
                              }
                    });

                    jCheckBox1.setForeground(Setting.getSettings().getSkinForeground2Color());
                    jCheckBox1.setText("Mükerrer giriş serbest");

                    jCheckBox2.setForeground(Setting.getSettings().getSkinForeground2Color());
                    jCheckBox2.setText("Mükerrer çıkış serbest");

                    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
                    jPanel1.setLayout(jPanel1Layout);
                    jPanel1Layout.setHorizontalGroup(
                              jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                              .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                  .addGroup(jPanel1Layout.createSequentialGroup()
                                                            .addComponent(jButton1)
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 122, Short.MAX_VALUE)
                                                            .addComponent(jButton2)
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                            .addComponent(jButton3))
                                                  .addGroup(jPanel1Layout.createSequentialGroup()
                                                            .addContainerGap()
                                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                      .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                      .addGroup(jPanel1Layout.createSequentialGroup()
                                                                                .addComponent(jLabel2)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                          .addComponent(jTextField1)
                                                                                          .addGroup(jPanel1Layout.createSequentialGroup()
                                                                                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                              .addComponent(jCheckBox2)
                                                                                                              .addComponent(jCheckBox1))
                                                                                                    .addGap(0, 0, Short.MAX_VALUE)))))))
                                        .addContainerGap())
                    );
                    jPanel1Layout.setVerticalGroup(
                              jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                              .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                  .addComponent(jLabel2)
                                                  .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jCheckBox1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jCheckBox2)
                                        .addGap(109, 109, 109)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                  .addComponent(jButton1)
                                                  .addComponent(jButton2)
                                                  .addComponent(jButton3))
                                        .addContainerGap(60, Short.MAX_VALUE))
                    );

                    add(jPanel1, java.awt.BorderLayout.WEST);
          }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
              tableSecildi();
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyReleased
              tableSecildi();
    }//GEN-LAST:event_jTable1KeyReleased

          private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
                    bolgeSil();
          }//GEN-LAST:event_jButton1ActionPerformed

          private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
                    alanlariBosalt();
          }//GEN-LAST:event_jButton2ActionPerformed

          private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
                    bolgeKaydet();
          }//GEN-LAST:event_jButton3ActionPerformed
          // Variables declaration - do not modify//GEN-BEGIN:variables
          private javax.swing.JButton jButton1;
          private javax.swing.JButton jButton2;
          private javax.swing.JButton jButton3;
          private javax.swing.JCheckBox jCheckBox1;
          private javax.swing.JCheckBox jCheckBox2;
          private javax.swing.JLabel jLabel1;
          private javax.swing.JLabel jLabel2;
          private javax.swing.JPanel jPanel1;
          private javax.swing.JPanel jPanel2;
          private javax.swing.JScrollPane jScrollPane1;
          private javax.swing.JTable jTable1;
          private javax.swing.JTextField jTextField1;
          // End of variables declaration//GEN-END:variables
}
