/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yakut.emkosis;

import com.yakut.azone.util.Utils;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author yakut
 */
public class EmkosisPanel extends javax.swing.JPanel {

    /**
     * Creates new form EmkosisPanel
     */
    public EmkosisPanel() {
        initComponents();
    }

    public static void main(String[] args) {
        Utils.startNimbus();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        EmkosisPanel panel = new EmkosisPanel();
        frame.getContentPane().add(panel);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                jPanel1 = new javax.swing.JPanel();
                jTabbedPane1 = new javax.swing.JTabbedPane();
                jPanel2 = new javax.swing.JPanel();
                jScrollPane1 = new javax.swing.JScrollPane();
                jTable1 = new javax.swing.JTable();
                jPanel3 = new javax.swing.JPanel();
                jPanel4 = new javax.swing.JPanel();
                jPanel5 = new javax.swing.JPanel();
                jPanel6 = new javax.swing.JPanel();
                jSeparator1 = new javax.swing.JSeparator();
                jToolBar1 = new javax.swing.JToolBar();
                jLabel1 = new javax.swing.JLabel();
                jButton1 = new javax.swing.JButton();
                jButton2 = new javax.swing.JButton();
                jButton4 = new javax.swing.JButton();
                jButton3 = new javax.swing.JButton();

                setBackground(new java.awt.Color(255, 255, 255));
                setLayout(new java.awt.BorderLayout());

                jPanel1.setLayout(new java.awt.BorderLayout());

                jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.LEFT);

                jPanel2.setLayout(new java.awt.BorderLayout());

                jTable1.setModel(new javax.swing.table.DefaultTableModel(
                        new Object [][] {
                                {},
                                {},
                                {},
                                {}
                        },
                        new String [] {

                        }
                ));
                jTable1.setRowHeight(24);
                jScrollPane1.setViewportView(jTable1);

                jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

                jTabbedPane1.addTab("", new javax.swing.ImageIcon(getClass().getResource("/com/yakut/appbizerbastudio/resources/calibre.png")), jPanel2); // NOI18N
                jTabbedPane1.addTab("", new javax.swing.ImageIcon(getClass().getResource("/com/yakut/appbizerbastudio/resources/apport.png")), jPanel3); // NOI18N
                jTabbedPane1.addTab("", new javax.swing.ImageIcon(getClass().getResource("/com/yakut/appbizerbastudio/resources/gparted.png")), jPanel4); // NOI18N
                jTabbedPane1.addTab("", new javax.swing.ImageIcon(getClass().getResource("/com/yakut/appbizerbastudio/resources/office-calendar.png")), jPanel5); // NOI18N

                jPanel1.add(jTabbedPane1, java.awt.BorderLayout.CENTER);

                add(jPanel1, java.awt.BorderLayout.CENTER);

                jPanel6.setBackground(new java.awt.Color(255, 255, 255));
                jPanel6.setLayout(new java.awt.BorderLayout());
                jPanel6.add(jSeparator1, java.awt.BorderLayout.PAGE_END);

                jToolBar1.setBackground(new java.awt.Color(199, 168, 168));
                jToolBar1.setFloatable(false);
                jToolBar1.setRollover(true);

                jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yakut/resource/ecc-w48px.png"))); // NOI18N
                jToolBar1.add(jLabel1);

                jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yakut/appbizerbastudio/resources/calibre.png"))); // NOI18N
                jButton1.setFocusable(false);
                jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                jButton1.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton1ActionPerformed(evt);
                        }
                });
                jToolBar1.add(jButton1);

                jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yakut/appbizerbastudio/resources/apport.png"))); // NOI18N
                jButton2.setFocusable(false);
                jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                jButton2.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton2ActionPerformed(evt);
                        }
                });
                jToolBar1.add(jButton2);

                jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yakut/appbizerbastudio/resources/office-calendar.png"))); // NOI18N
                jButton4.setFocusable(false);
                jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                jButton4.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton4ActionPerformed(evt);
                        }
                });
                jToolBar1.add(jButton4);

                jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yakut/appbizerbastudio/resources/gparted.png"))); // NOI18N
                jButton3.setFocusable(false);
                jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                jButton3.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton3ActionPerformed(evt);
                        }
                });
                jToolBar1.add(jButton3);

                jPanel6.add(jToolBar1, java.awt.BorderLayout.CENTER);

                add(jPanel6, java.awt.BorderLayout.PAGE_START);
        }// </editor-fold>//GEN-END:initComponents

    public void getData() {
        DefaultTableModel model = new DefaultTableModel();
        List<String> list = Http.get("DataLogger1");
        String[] headers = list.get(0).split(";");

        model.setColumnIdentifiers(headers);

        for (int k = 0; k < list.size(); k++) {
            String[] row = list.get(k).split(";");
            model.addRow(row);
        }
        jTable1.setModel(model);

    }
        private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
            getData();
        }//GEN-LAST:event_jButton1ActionPerformed

        private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
            // TODO add your handling code here:
        }//GEN-LAST:event_jButton2ActionPerformed

        private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
            // TODO add your handling code here:
        }//GEN-LAST:event_jButton3ActionPerformed

        private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
            // TODO add your handling code here:
        }//GEN-LAST:event_jButton4ActionPerformed


        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JButton jButton1;
        private javax.swing.JButton jButton2;
        private javax.swing.JButton jButton3;
        private javax.swing.JButton jButton4;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JPanel jPanel1;
        private javax.swing.JPanel jPanel2;
        private javax.swing.JPanel jPanel3;
        private javax.swing.JPanel jPanel4;
        private javax.swing.JPanel jPanel5;
        private javax.swing.JPanel jPanel6;
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JSeparator jSeparator1;
        private javax.swing.JTabbedPane jTabbedPane1;
        private javax.swing.JTable jTable1;
        private javax.swing.JToolBar jToolBar1;
        // End of variables declaration//GEN-END:variables
}
