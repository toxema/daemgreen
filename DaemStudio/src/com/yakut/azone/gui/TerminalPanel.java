package com.yakut.azone.gui;

import com.yakut.azone.beans.Terminal;
import com.yakut.azone.controller.TerminalController;
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
public class TerminalPanel extends javax.swing.JPanel {
          
          TerminalController terminalController;
          List<Terminal> terminallar;
          Terminal terminal;
          DefaultTableModel model;
          boolean isNewTerminal = false;
          
          public TerminalPanel() {
                    initComponents();
          }
          
          public void init() {
                    terminalListele();
          }
          
          public void refresh() {
                    terminalListele();
          }
          
          public TerminalController getTerminalController() {
                    if (terminalController == null) {
                              terminalController = new TerminalController();
                    }
                    return terminalController;
          }
          
          public void terminalListele() {
                    terminallar = getTerminalController().getTerminalList();
                    model = new DefaultTableModel() {
                              @Override
                              public boolean isCellEditable(int row, int col) {
                                        return false;
                              }
                    };
                    model.setColumnIdentifiers(new String[]{"Ad", "Connection"});
                    for (Terminal k : terminallar) {
                              String[] row = getTerminalRow(k);
                              model.addRow(row);
                    }
                    jTable1.setModel(model);
                    if (terminallar.size() > 0) {
                              terminalGoster(0);
                    } else {
                              alanlariBosalt();
                    }
          }
          
          public void terminalGoster(Terminal b) {
                    jTextField1.setText(b.getAd());
                    jTextField2.setText(b.getConString());
                    terminal = b;
                    isNewTerminal = false;
                    jButton1.setEnabled(true);
          }
          
          public void terminalGoster(int index) {
                    if (index > -1 && index < terminallar.size()) {
                              terminalGoster(terminallar.get(index));
                    }
          }
          
          public void alanlariBosalt() {
                    jTextField1.setText("");
                    jTextField2.setText("");
                    isNewTerminal = true;
                    jButton1.setEnabled(false);
                    terminal = null;
                    
          }
          
          private String[] getTerminalRow(Terminal k) {
                    String[] row = new String[2];
                    row[0] = k.getAd();
                    row[1] = k.getConString();
                    
                    return row;
          }
          
          public void terminalSil() {
                    if (terminal != null) {
                              if (JOptionPane.showConfirmDialog(null, "Silmek istediğinizden emin misiniz?") == JOptionPane.YES_OPTION) {
                                        getTerminalController().delete(terminal);
                                        terminalListele();
                              }
                    }
          }
          
          public void terminalKaydet() {
                    if (isNewTerminal) {
                              terminal = new Terminal();
                    }
                    terminal.setAd(jTextField1.getText());
                    terminal.setConString(jTextField2.getText());
                    if (isNewTerminal) {
                              getTerminalController().persist(terminal);
                    } else {
                              getTerminalController().merge(terminal);
                    }
                    terminalListele();
                    
          }
          
          public void tableSecildi() {
                    int index = jTable1.getSelectedRow();
                    if (index > -1) {
                              terminalGoster(index);
                    }
          }
          
          public static void main(String[] args) {
                    
                    Utils.startNimbus();
                    TerminalPanel ap = new TerminalPanel();
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
                    jTextField2 = new javax.swing.JTextField();
                    jLabel3 = new javax.swing.JLabel();
                    jTextField3 = new javax.swing.JTextField();

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
                    jLabel1.setText("Terminal");
                    jLabel1.setFocusable(false);
                    jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

                    jLabel2.setForeground(Setting.getSettings().getSkinForeground2Color());
                    jLabel2.setText("Terminal Adı");

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

                    jLabel3.setForeground(Setting.getSettings().getSkinForeground2Color());
                    jLabel3.setText("Connection");

                    jTextField3.setBackground(Setting.getSettings().getSkinBackgroundColor());
                    jTextField3.setForeground(Setting.getSettings().getSkinForeground2Color());
                    jTextField3.setText("ORN:              COMX:COM4:9600:02:T6604:0:SCAN");
                    jTextField3.setBorder(null);
                    jTextField3.setOpaque(true);
                    jTextField3.addActionListener(new java.awt.event.ActionListener() {
                              public void actionPerformed(java.awt.event.ActionEvent evt) {
                                        jTextField3ActionPerformed(evt);
                              }
                    });

                    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
                    jPanel1.setLayout(jPanel1Layout);
                    jPanel1Layout.setHorizontalGroup(
                              jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                              .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                  .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                                            .addComponent(jButton1)
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 122, Short.MAX_VALUE)
                                                            .addComponent(jButton2)
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                            .addComponent(jButton3))
                                                  .addGroup(jPanel1Layout.createSequentialGroup()
                                                            .addContainerGap()
                                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                      .addComponent(jTextField3)
                                                                      .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                      .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                          .addComponent(jLabel2)
                                                                                          .addComponent(jLabel3))
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                          .addComponent(jTextField2)
                                                                                          .addComponent(jTextField1))))))
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
                                                  .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                  .addComponent(jLabel3))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(89, 89, 89)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                  .addComponent(jButton1)
                                                  .addComponent(jButton2)
                                                  .addComponent(jButton3))
                                        .addContainerGap(76, Short.MAX_VALUE))
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
                    terminalSil();
          }//GEN-LAST:event_jButton1ActionPerformed
          
          private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
                    alanlariBosalt();
          }//GEN-LAST:event_jButton2ActionPerformed
          
          private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
                    terminalKaydet();
          }//GEN-LAST:event_jButton3ActionPerformed

          private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
                    // TODO add your handling code here:
          }//GEN-LAST:event_jTextField3ActionPerformed

          // Variables declaration - do not modify//GEN-BEGIN:variables
          private javax.swing.JButton jButton1;
          private javax.swing.JButton jButton2;
          private javax.swing.JButton jButton3;
          private javax.swing.JLabel jLabel1;
          private javax.swing.JLabel jLabel2;
          private javax.swing.JLabel jLabel3;
          private javax.swing.JPanel jPanel1;
          private javax.swing.JPanel jPanel2;
          private javax.swing.JScrollPane jScrollPane1;
          private javax.swing.JTable jTable1;
          private javax.swing.JTextField jTextField1;
          private javax.swing.JTextField jTextField2;
          private javax.swing.JTextField jTextField3;
          // End of variables declaration//GEN-END:variables
}
