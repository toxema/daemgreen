/*
 * AyarPanel.java
 *
 * Created on 19.Ağu.2011, 06:02:02
 */
package com.yakut.azone.gui;

import com.yakut.azone.util.Setting;
import com.yakut.azone.util.Utils;
import java.awt.Color;
import java.util.Enumeration;
import java.util.Properties;
import javax.swing.JColorChooser;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author yakut
 */
public class AyarPanel extends javax.swing.JPanel {

    Properties p;

    /**
     * Creates new form AyarPanel
     */
    public AyarPanel() {
        initComponents();
    }

    public void init() {
        ayarlariYukle();
    }

    public void refresh() {
        ayarlariYukle();
    }

    public void ayarlariYukle() {
        p = Setting.getSettings().getProperties();
        Enumeration keysEnum = p.keys();
        String key;
        String value;
        Setting.getSettings().tableYuksekligiKaydet(jTable1, "com.yakut.ayartable");
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0) {
                    return false;
                }
                return true;
            }
        };
        model.setColumnIdentifiers(new String[]{"Anahtar", "Değer"});
        while (keysEnum.hasMoreElements()) {
            key = (String) keysEnum.nextElement();
            value = p.getProperty(key);
            model.addRow(new String[]{key, value});
        }
        jTable1.setModel(model);
        Setting.getSettings().tableYuksekligiAl(jTable1, "com.yakut.ayartable");
    }

    public void ayarlariKaydet() {
        for (int k = 0; k < jTable1.getRowCount(); k++) {
            p.setProperty((String) jTable1.getValueAt(k, 0), (String) jTable1.getValueAt(k, 1));
        }
        Setting.getSettings().saveProperties();
        Setting.getSettings().notifySettingChanged();
    }

    public static void main(String[] as) throws UnsupportedLookAndFeelException {
        Utils.startNimbus();
        AyarPanel a = new AyarPanel();
        a.init();
    }

    @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                jScrollPane1 = new javax.swing.JScrollPane();
                jTable1 = new javax.swing.JTable();
                jPanel1 = new javax.swing.JPanel();
                jButton5 = new javax.swing.JButton();
                jButton6 = new javax.swing.JButton();

                setBackground(Setting.getSettings().getSkinBackground2Color());
                setLayout(new java.awt.BorderLayout());

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
                jScrollPane1.setViewportView(jTable1);

                add(jScrollPane1, java.awt.BorderLayout.CENTER);

                jButton5.setBackground(new java.awt.Color(204, 0, 51));
                jButton5.setForeground(new java.awt.Color(255, 255, 255));
                jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yakut/resource/ara.png"))); // NOI18N
                jButton5.setText("Yükle");
                jButton5.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton5ActionPerformed(evt);
                        }
                });

                jButton6.setBackground(new java.awt.Color(204, 0, 51));
                jButton6.setForeground(new java.awt.Color(255, 255, 255));
                jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yakut/resource/ayar.png"))); // NOI18N
                jButton6.setText("Kaydet");
                jButton6.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton6ActionPerformed(evt);
                        }
                });

                javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
                jPanel1.setLayout(jPanel1Layout);
                jPanel1Layout.setHorizontalGroup(
                        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 399, Short.MAX_VALUE)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                );
                jPanel1Layout.setVerticalGroup(
                        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton6)
                                .addComponent(jButton5))
                );

                add(jPanel1, java.awt.BorderLayout.PAGE_END);
        }// </editor-fold>//GEN-END:initComponents

          private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
              if (evt.getClickCount() == 2) {

                  int k = jTable1.getSelectedRow();
                  if (k > -1) {
                      String text = (String) jTable1.getValueAt(k, 0);
                      if (text.toLowerCase().indexOf(".color") > -1) {
                          JColorChooser jc = new JColorChooser(new Color(Integer.parseInt(jTable1.getValueAt(k, 1).toString())));
                          Color c = JColorChooser.showDialog(this, "Renk Seç", new Color(Integer.parseInt(jTable1.getValueAt(k, 1).toString())));
                          if (c != null) {
                              jTable1.setValueAt("" + c.getRGB(), k, 1);
                          }
                      }
                  }
              }
          }//GEN-LAST:event_jTable1MouseClicked

        private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
            ayarlariYukle();
        }//GEN-LAST:event_jButton5ActionPerformed

        private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
            ayarlariKaydet();
        }//GEN-LAST:event_jButton6ActionPerformed

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JButton jButton5;
        private javax.swing.JButton jButton6;
        private javax.swing.JPanel jPanel1;
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JTable jTable1;
        // End of variables declaration//GEN-END:variables
}
