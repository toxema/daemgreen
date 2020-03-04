/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yakut.azone.test;

import com.yakut.azone.beans.Grup;
import com.yakut.azone.beans.Personel;
import com.yakut.azone.controller.GrupController;
import com.yakut.azone.controller.PersonelController;

import com.yakut.azone.util.DateUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yakut
 */
public class DataAktar extends javax.swing.JFrame {

    /**
     * Creates new form DataAktar
     */
    public DataAktar() {
        initComponents();
    }

    public void aktar() {
        try {
            PersonelController pc = new PersonelController();
            Scanner scan = new Scanner(new File("list.txt"));
            GrupController gc = new GrupController();
            List<Grup> gList = gc.getGrupList();
            Grup g = null;
            if (gList.size() > 0) {
                g = gList.get(0);
            } else {
                g = new Grup("GRUP1");
                gc.persist(g);
            }

            while (scan.hasNextLine()) {
                String line = scan.nextLine();

                jTextArea1.append(line + "\r\n");
                jLabel2.setText(line);
                String s[] = line.split(",");
                //06145,B4 03,Şsükrü ÇAMLIBEL,08518459
                String sicil = s[0];
                Personel p;
                boolean isNewPersonel = false;
                p = pc.getPersonelByEnroll("" + Integer.parseInt(s[0]));
                if (p == null) {
                    p = new Personel();
                    isNewPersonel = true;
                }
                p.setSicil(s[0]);
                //   p.setBlok(s[1].split(" ")[0]);
                //  p.setDaire(s[1].split(" ")[1]);
                String a = s[2];
                int k = a.split(" ").length;
                String ad = "";
                String soyad = "";
                if (k == 2) {
                    ad = a.split(" ")[0];
                    soyad = a.split(" ")[1];
                } else {
                    ad = a.split(" ")[0] + " " + a.split(" ")[1];
                    soyad = a.split(" ")[2];
                }
                p.setAd(ad);
                p.setSoyad(soyad);

                p.setKartNo(s[3]);
                p.setCihazNo("" + Integer.parseInt(s[0]));
                p.setGecerlilikBaslangicTarihi(new Date());
                p.setGecerlilikBitisTarihi(DateUtil.parseDate("01.01.2020"));
                p.setGrup(g);

                if (isNewPersonel) {
                    jTextArea1.append("YENI KAYIT:\t" + p + "\r\n");
                    pc.persist(p);
                } else {
                    jTextArea1.append("GUNCELLE:\t" + p + "\r\n");
                    pc.merge(p);
                }
            }
            jLabel2.setText("İşlem Tamamlandı");
        } catch (FileNotFoundException ex) {
            jTextArea1.append("hata :" + ex.getMessage() + "\r\n");
            ex.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
            // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
            private void initComponents() {

                        jButton1 = new javax.swing.JButton();
                        jScrollPane1 = new javax.swing.JScrollPane();
                        jTextArea1 = new javax.swing.JTextArea();
                        jLabel1 = new javax.swing.JLabel();
                        jLabel2 = new javax.swing.JLabel();
                        jLabel3 = new javax.swing.JLabel();

                        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

                        jButton1.setText("Aktar");
                        jButton1.addActionListener(new java.awt.event.ActionListener() {
                                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                jButton1ActionPerformed(evt);
                                    }
                        });

                        jTextArea1.setColumns(20);
                        jTextArea1.setRows(5);
                        jScrollPane1.setViewportView(jTextArea1);

                        jLabel1.setText("Dosya: list.txt");

                        jLabel2.setText("ornek : 06145,B4 03,Şükrü ÇAMLIBEL,08518459");

                        jLabel3.setText("format :sicil,blok daire,ad soyad,kartno");

                        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                        getContentPane().setLayout(layout);
                        layout.setHorizontalGroup(
                                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                        .addComponent(jLabel1)
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addComponent(jButton1))
                                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                        .addComponent(jScrollPane1)
                                                                        .addContainerGap())
                                                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE)))
                        );
                        layout.setVerticalGroup(
                                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                            .addComponent(jButton1)
                                                            .addComponent(jLabel1))
                                                .addGap(9, 9, 9)
                                                .addComponent(jLabel3)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jScrollPane1)
                                                .addContainerGap())
                        );

                        pack();
            }// </editor-fold>//GEN-END:initComponents

          private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
              new Thread() {
                  @Override
                  public void run() {
                      jButton1.setText("Lütfen Bekleyin");
                      jButton1.setEnabled(false);
                      aktar();
                      // sporAktar();
                      jButton1.setText("Aktar");
                      jButton1.setEnabled(true);

                  }
              }.start();
          }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
                     * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DataAktar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DataAktar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DataAktar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DataAktar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DataAktar().setVisible(true);
            }
        });
    }
            // Variables declaration - do not modify//GEN-BEGIN:variables
            private javax.swing.JButton jButton1;
            private javax.swing.JLabel jLabel1;
            private javax.swing.JLabel jLabel2;
            private javax.swing.JLabel jLabel3;
            private javax.swing.JScrollPane jScrollPane1;
            private javax.swing.JTextArea jTextArea1;
            // End of variables declaration//GEN-END:variables
}
