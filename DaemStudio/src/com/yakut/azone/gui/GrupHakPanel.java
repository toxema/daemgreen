package com.yakut.azone.gui;

import com.yakut.azone.beans.Bolge;
import com.yakut.azone.beans.Grup;
import com.yakut.azone.beans.GrupHak;
import com.yakut.azone.controller.BolgeController;
import com.yakut.azone.controller.GrupController;
import com.yakut.azone.controller.GrupHakController;
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
public class GrupHakPanel extends javax.swing.JPanel {
        
        GrupController grupController;
        List<Grup> gruplar;
        List<Bolge> bolgeler;
        GrupHak grupHak;
        DefaultTableModel model;
        boolean isNewGrupHak = false;
        
        public GrupHakPanel() {
                initComponents();
        }
        
        public void init() {
                grupListele();
                bolgeListele();
                grupHakListele();
        }
        
        public void refresh() {
                init();
        }
        
        public GrupController getGrupController() {
                if (grupController == null) {
                        grupController = new GrupController();
                }
                return grupController;
        }
        GrupHakController grupHakController = null;
        
        public GrupHakController getGrupHakController() {
                if (grupHakController == null) {
                        grupHakController = new GrupHakController();
                }
                return grupHakController;
        }
        BolgeController bolgeController = null;
        
        public BolgeController getBolgeController() {
                if (bolgeController == null) {
                        bolgeController = new BolgeController();
                }
                return bolgeController;
        }
        
        public void grupListele() {
                gruplar = getGrupController().getGrupList();
                DefaultComboBoxModel comboModel = new DefaultComboBoxModel();
                for (Grup g : gruplar) {
                        comboModel.addElement(g);
                }
                jComboBox1.setModel(comboModel);
                
        }
        
        public void bolgeListele() {
                bolgeler = getBolgeController().getBolgeList();
                DefaultComboBoxModel comboModel = new DefaultComboBoxModel();
                for (Bolge g : bolgeler) {
                        comboModel.addElement(g);
                }
                jComboBox2.setModel(comboModel);
        }
        List<GrupHak> grupHakList;
        
        public void grupHakListele() {
                grupHakList = getGrupHakController().getGrupHakList();
                model = new DefaultTableModel() {
                        
                        @Override
                        public boolean isCellEditable(int row, int col) {
                                return false;
                        }
                };
                model.setColumnIdentifiers(new String[]{"Grup", "Bolge", "Mukerrer"});
                for (GrupHak k : grupHakList) {
                        String[] row = getGrupHakRow(k);
                        model.addRow(row);
                }
                jTable1.setModel(model);
                if (grupHakList.size() > 0) {
                        grupHakGoster(0);
                } else {
                        alanlariBosalt();
                }
        }
        
        public void grupHakGoster(GrupHak b) {
                if (b.getGrup() != null) {
                        jComboBox1.setSelectedItem(b.getGrup());
                }
                
                if (b.getBolge() != null) {
                        jComboBox2.setSelectedItem(b.getBolge());
                }
                
                jCheckBox1.setSelected(b.isMukerrerSerbest());
                
                grupHak = b;
                isNewGrupHak = false;
                jButton1.setEnabled(true);
        }
        
        public void grupHakGoster(int index) {
                if (index > -1 && index < grupHakList.size()) {
                        grupHakGoster(grupHakList.get(index));
                }
        }
        
        public void alanlariBosalt() {
                jComboBox1.setSelectedIndex(-1);
                jComboBox2.setSelectedIndex(-1);
                isNewGrupHak = true;
                jButton1.setEnabled(false);
                grupHak = null;
                
        }
        
        private String[] getGrupHakRow(GrupHak k) {
                String[] row = new String[3];
                row[0] = k.getGrup() != null ? k.getGrup().getAd() : "";
                row[1] = k.getBolge() != null ? k.getBolge().getAd() : "";
                row[2] = k.isMukerrerSerbest() ? "EVET" : "HAYIR";
                return row;
        }
        
        public void grupHakSil() {
                if (grupHak != null) {
                        if (JOptionPane.showConfirmDialog(null, "Silmek istediğinizden emin misiniz?") == JOptionPane.YES_OPTION) {
                                getGrupHakController().delete(grupHak);
                                grupHakListele();
                        }
                }
        }
        
        public void grupHakKaydet() {
                if (isNewGrupHak) {
                        grupHak = new GrupHak();
                }
                grupHak.setGrup((Grup) jComboBox1.getSelectedItem());
                grupHak.setBolge((Bolge) jComboBox2.getSelectedItem());
                grupHak.setMukerrerSerbest(jCheckBox1.isSelected());
                if (isNewGrupHak) {
                        getGrupHakController().persist(grupHak);
                } else {
                        getGrupHakController().merge(grupHak);
                }
                grupHakListele();
                
        }
        
        public void tableSecildi() {
                int index = jTable1.getSelectedRow();
                if (index > -1) {
                        grupHakGoster(index);
                }
        }
        
        public static void main(String[] args) {
                
                Utils.startNimbus();
                GrupHakPanel ap = new GrupHakPanel();
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
                        jButton1 = new javax.swing.JButton();
                        jButton2 = new javax.swing.JButton();
                        jButton3 = new javax.swing.JButton();
                        jLabel3 = new javax.swing.JLabel();
                        jComboBox1 = new javax.swing.JComboBox();
                        jLabel2 = new javax.swing.JLabel();
                        jComboBox2 = new javax.swing.JComboBox();
                        jCheckBox1 = new javax.swing.JCheckBox();
                        jSpinner1 = new javax.swing.JSpinner();
                        jSpinner2 = new javax.swing.JSpinner();
                        jComboBox3 = new javax.swing.JComboBox();
                        jLabel4 = new javax.swing.JLabel();
                        jLabel5 = new javax.swing.JLabel();

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
                        jLabel1.setText("GRUPHAKLARI");
                        jLabel1.setFocusable(false);
                        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

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
                        jLabel3.setText("Grup");

                        jLabel2.setForeground(Setting.getSettings().getSkinForeground2Color());
                        jLabel2.setText("Bölge");

                        jCheckBox1.setForeground(Setting.getSettings().getSkinForeground2Color());
                        jCheckBox1.setText("Mükerrer Geçiş Yapar");
                        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
                                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                jCheckBox1ActionPerformed(evt);
                                    }
                        });

                        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "GİRİŞ", "ÇIKIŞ" }));

                        jLabel4.setText("Zaman");

                        jLabel5.setText("Yön");

                        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
                        jPanel1.setLayout(jPanel1Layout);
                        jPanel1Layout.setHorizontalGroup(
                                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                    .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                                                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                                                                                        .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                                        .addComponent(jSpinner2))
                                                                                                            .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                                            .addComponent(jCheckBox1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                                                                .addContainerGap())))
                                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addContainerGap())
                                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                                        .addComponent(jButton1)
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                                                                        .addComponent(jButton2)
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(jButton3))))
                        );
                        jPanel1Layout.setVerticalGroup(
                                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jLabel1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                            .addComponent(jLabel3)
                                                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                            .addComponent(jLabel2)
                                                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                            .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(jLabel4))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                            .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(jLabel5))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jCheckBox1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                            .addComponent(jButton1)
                                                            .addComponent(jButton3)
                                                            .addComponent(jButton2)))
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
                  grupHakSil();
          }//GEN-LAST:event_jButton1ActionPerformed
        
          private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
                  alanlariBosalt();
          }//GEN-LAST:event_jButton2ActionPerformed
        
          private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
                  grupHakKaydet();
          }//GEN-LAST:event_jButton3ActionPerformed
        
        private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
                // 
        }//GEN-LAST:event_jCheckBox1ActionPerformed
            // Variables declaration - do not modify//GEN-BEGIN:variables
            private javax.swing.JButton jButton1;
            private javax.swing.JButton jButton2;
            private javax.swing.JButton jButton3;
            private javax.swing.JCheckBox jCheckBox1;
            private javax.swing.JComboBox jComboBox1;
            private javax.swing.JComboBox jComboBox2;
            private javax.swing.JComboBox jComboBox3;
            private javax.swing.JLabel jLabel1;
            private javax.swing.JLabel jLabel2;
            private javax.swing.JLabel jLabel3;
            private javax.swing.JLabel jLabel4;
            private javax.swing.JLabel jLabel5;
            private javax.swing.JPanel jPanel1;
            private javax.swing.JPanel jPanel2;
            private javax.swing.JScrollPane jScrollPane1;
            private javax.swing.JSpinner jSpinner1;
            private javax.swing.JSpinner jSpinner2;
            private javax.swing.JTable jTable1;
            // End of variables declaration//GEN-END:variables
}
