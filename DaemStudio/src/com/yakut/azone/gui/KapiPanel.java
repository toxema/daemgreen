package com.yakut.azone.gui;

import com.yakut.azone.beans.Bolge;
import com.yakut.azone.beans.Kapi;
import com.yakut.azone.beans.Terminal;
import com.yakut.azone.controller.BolgeController;
import com.yakut.azone.controller.KapiController;
import com.yakut.azone.controller.TerminalController;
import com.yakut.azone.test.TestFrame;
import com.yakut.azone.util.Setting;
import com.yakut.azone.util.Utils;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author yakut
 */
public class KapiPanel extends javax.swing.JPanel {
          
          KapiController kapiController;
          BolgeController bolgeController;
          TerminalController terminalController;
          List<Kapi> kapilar;
          List<Bolge> bolgeList;
          List<Terminal> terminalList;
          Kapi kapi;
          DefaultTableModel model;
          boolean isNewKapi = false;
          
          public KapiPanel() {
                    initComponents();
          }
          
          public void init() {
                    bolgeListele();
                    terminalListele();
                    kapiListele();
          }
          
          public void refresh() {
                    kapiListele();
                    bolgeListele();
                    terminalListele();
          }
          
          public BolgeController getBolgeController() {
                    if (bolgeController == null) {
                              bolgeController = new BolgeController();
                    }
                    return bolgeController;
          }
          
          public TerminalController getTerminalController() {
                    if (terminalController == null) {
                              terminalController = new TerminalController();
                    }
                    return terminalController;
          }
          
          public KapiController getKapiController() {
                    if (kapiController == null) {
                              kapiController = new KapiController();
                    }
                    return kapiController;
          }
          
          public void bolgeListele() {
                    bolgeList = getBolgeController().getBolgeList();
                    DefaultComboBoxModel comboModel = new DefaultComboBoxModel();
                    for (Bolge b : bolgeList) {
                              comboModel.addElement(b);
                    }
                    bolgeComboBox.setModel(comboModel);
          }
          
          public void terminalListele() {
                    terminalList = getTerminalController().getTerminalList();
                    DefaultComboBoxModel comboModel = new DefaultComboBoxModel();
                    for (Terminal b : terminalList) {
                              comboModel.addElement(b);
                    }
                    terminalComboBox.setModel(comboModel);
          }
          
          public void kapiListele() {
                    kapilar = getKapiController().getKapiList();
                    model = new DefaultTableModel() {
                              @Override
                              public boolean isCellEditable(int row, int col) {
                                        return false;
                              }
                    };
                    model.setColumnIdentifiers(new String[]{"Ad", "Bolge", "Yön", "Terminal", "Okuyucu"});
                    for (Kapi k : kapilar) {
                              String[] row = getKapiRow(k);
                              model.addRow(row);
                    }
                    jTable1.setModel(model);
                    if (kapilar.size() > 0) {
                              kapiGoster(0);
                    } else {
                              alanlariBosalt();
                    }
          }
          
          private int getYonIndex(char yon) {
                    int value = 0;
                    switch (yon) {
                              case 'G':
                                        value = 0;
                                        break;
                              case 'C':
                                        value = 1;
                                        break;
                              case 'O':
                                        value = 2;
                                        break;
                              case 'X':
                                        value = 3;
                                        break;
                    }
                    return value;
          }
          
          private char getYonChar(int yon) {
                    char value = 0;
                    switch (yon) {
                              case 0:
                                        value = 'G';
                                        break;
                              case 1:
                                        value = 'C';
                                        break;
                              case 2:
                                        value = 'O';
                                        break;
                              case 3:
                                        value = 'X';
                                        break;
                    }
                    return value;
          }
          
          private String getYonAd(char yon) {
                    String value = "";
                    switch (yon) {
                              case 'G':
                                        value = "GİRİŞ";
                                        break;
                              case 'C':
                                        value = "ÇIKIŞ";
                                        break;
                              case 'O':
                                        value = "GiRİŞ / ÇIKIŞ";
                                        break;
                              case 'X':
                                        value = "GEÇİŞ YOK";
                                        break;
                    }
                    return value;
          }
          
          public void kapiGoster(Kapi k) {
                    jTextField1.setText(k.getAd());
                    bolgeComboBox.setSelectedItem(k.getBolge());
                    yonComboBox.setSelectedIndex(getYonIndex(k.getYon()));
                    terminalComboBox.setSelectedItem(k.getTerminal());
                    okuyucuNoSpinner.setValue(k.getOkuyucuNo());
                    kapi = k;
                    isNewKapi = false;
                    jButton1.setEnabled(true);
          }
          
          public void kapiGoster(int index) {
                    if (index > -1 && index < kapilar.size()) {
                              kapiGoster(kapilar.get(index));
                    }
          }
          
          public void alanlariBosalt() {
                    jTextField1.setText("");
                    bolgeComboBox.setSelectedIndex(-1);
                    terminalComboBox.setSelectedIndex(-1);
                    yonComboBox.setSelectedIndex(0);
                    okuyucuNoSpinner.setValue(0);
                    isNewKapi = true;
                    kapi = null;
                    jButton1.setEnabled(false);
          }
          
          private String[] getKapiRow(Kapi k) {
                    String[] row = new String[5];
                    row[0] = k.getAd();
                    row[1] = k.getBolge() != null ? k.getBolge().getAd() : "";
                    row[2] = getYonAd(k.getYon());
                    row[3] = k.getTerminal() != null ? k.getTerminal().getAd() : "";
                    row[4] = "" + k.getOkuyucuNo();
                    return row;
          }
          
          public void kapiSil() {
                    if (kapi != null) {
                              if (JOptionPane.showConfirmDialog(null, "Silmek istediğinizden emin misiniz?") == JOptionPane.YES_OPTION) {
                                        getKapiController().delete(kapi);
                                        kapiListele();
                              }
                    }
          }
          
          public void kapiKaydet() {
                    if (isNewKapi) {
                              kapi = new Kapi();
                    }
                    kapi.setAd(jTextField1.getText());
                    kapi.setBolge((Bolge) bolgeComboBox.getSelectedItem());
                    kapi.setTerminal((Terminal) terminalComboBox.getSelectedItem());
                    kapi.setYon(getYonChar(yonComboBox.getSelectedIndex()));
                    kapi.setOkuyucuNo((Integer) okuyucuNoSpinner.getValue());
                    if (isNewKapi) {
                              getKapiController().persist(kapi);
                    } else {
                              getKapiController().merge(kapi);
                    }
                    kapiListele();
                    
          }
          
          public void tableSecildi() {
                    int index = jTable1.getSelectedRow();
                    if (index > -1) {
                              kapiGoster(index);
                    }
          }
          
          public static void main(String[] args) {
                    
                    Utils.startNimbus();
                    KapiPanel ap = new KapiPanel();
                    TestFrame t = new TestFrame(ap);
                    ap.init();
                    
          }
          
          @SuppressWarnings("unchecked")
          // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
          private void initComponents() {

                    jPanel1 = new javax.swing.JPanel();
                    jLabel1 = new javax.swing.JLabel();
                    jLabel2 = new javax.swing.JLabel();
                    jLabel3 = new javax.swing.JLabel();
                    jLabel5 = new javax.swing.JLabel();
                    jLabel8 = new javax.swing.JLabel();
                    jTextField1 = new javax.swing.JTextField();
                    yonComboBox = new javax.swing.JComboBox();
                    jButton1 = new javax.swing.JButton();
                    jButton2 = new javax.swing.JButton();
                    jButton3 = new javax.swing.JButton();
                    okuyucuNoSpinner = new javax.swing.JSpinner();
                    jLabel4 = new javax.swing.JLabel();
                    bolgeComboBox = new javax.swing.JComboBox();
                    terminalComboBox = new javax.swing.JComboBox();
                    jPanel2 = new javax.swing.JPanel();
                    jScrollPane1 = new javax.swing.JScrollPane();
                    jTable1 = new javax.swing.JTable();

                    setLayout(new java.awt.BorderLayout());

                    jPanel1.setBackground(Setting.getSettings().getSkinBackground2Color());

                    jLabel1.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
                    jLabel1.setForeground(Setting.getSettings().getSkinForeground2Color());
                    jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                    jLabel1.setText("KAPI TANIMLARI");
                    jLabel1.setFocusable(false);
                    jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

                    jLabel2.setForeground(Setting.getSettings().getSkinForeground2Color());
                    jLabel2.setText("Kapı Adı");

                    jLabel3.setForeground(Setting.getSettings().getSkinForeground2Color());
                    jLabel3.setText("Bölge");

                    jLabel5.setForeground(Setting.getSettings().getSkinForeground2Color());
                    jLabel5.setText("Yön");

                    jLabel8.setForeground(Setting.getSettings().getSkinForeground2Color());
                    jLabel8.setText("Termiminal");

                    yonComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "GİRİŞ", "ÇIKIŞ", "GİRİŞ / ÇIKIŞ", "GEÇİŞ YOK" }));

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

                    jLabel4.setForeground(Setting.getSettings().getSkinForeground2Color());
                    jLabel4.setText("Okuyucu");

                    bolgeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

                    terminalComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

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
                                                                      .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                      .addGroup(jPanel1Layout.createSequentialGroup()
                                                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                          .addComponent(jLabel5)
                                                                                          .addComponent(jLabel8)
                                                                                          .addComponent(jLabel4)
                                                                                          .addComponent(jLabel3)
                                                                                          .addComponent(jLabel2))
                                                                                .addGap(28, 28, 28)
                                                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                          .addComponent(jTextField1)
                                                                                          .addComponent(yonComboBox, 0, 172, Short.MAX_VALUE)
                                                                                          .addComponent(okuyucuNoSpinner, javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                          .addComponent(bolgeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                          .addComponent(terminalComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
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
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                  .addComponent(jLabel3)
                                                  .addComponent(bolgeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                  .addComponent(jLabel5)
                                                  .addComponent(yonComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                  .addComponent(jLabel8)
                                                  .addComponent(terminalComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                  .addComponent(okuyucuNoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                  .addComponent(jLabel4))
                                        .addGap(55, 55, 55)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                  .addComponent(jButton1)
                                                  .addComponent(jButton2)
                                                  .addComponent(jButton3))
                                        .addContainerGap(22, Short.MAX_VALUE))
                    );

                    add(jPanel1, java.awt.BorderLayout.WEST);

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
          }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
              kapiSil();
    }//GEN-LAST:event_jButton1ActionPerformed
          
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
              alanlariBosalt();
    }//GEN-LAST:event_jButton2ActionPerformed
          
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
              kapiKaydet();
    }//GEN-LAST:event_jButton3ActionPerformed
          
    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
              tableSecildi();
    }//GEN-LAST:event_jTable1MouseClicked
          
    private void jTable1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyReleased
              tableSecildi();
    }//GEN-LAST:event_jTable1KeyReleased
          // Variables declaration - do not modify//GEN-BEGIN:variables
          private javax.swing.JComboBox bolgeComboBox;
          private javax.swing.JButton jButton1;
          private javax.swing.JButton jButton2;
          private javax.swing.JButton jButton3;
          private javax.swing.JLabel jLabel1;
          private javax.swing.JLabel jLabel2;
          private javax.swing.JLabel jLabel3;
          private javax.swing.JLabel jLabel4;
          private javax.swing.JLabel jLabel5;
          private javax.swing.JLabel jLabel8;
          private javax.swing.JPanel jPanel1;
          private javax.swing.JPanel jPanel2;
          private javax.swing.JScrollPane jScrollPane1;
          private javax.swing.JTable jTable1;
          private javax.swing.JTextField jTextField1;
          private javax.swing.JSpinner okuyucuNoSpinner;
          private javax.swing.JComboBox terminalComboBox;
          private javax.swing.JComboBox yonComboBox;
          // End of variables declaration//GEN-END:variables
}
