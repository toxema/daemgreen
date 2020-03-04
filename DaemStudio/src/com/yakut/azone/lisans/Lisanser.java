package com.yakut.azone.lisans;


/*
 * Lisanser.java
 *
 * Created on 10.Tem.2010, 11:32:50
 */
import com.yakut.azone.util.Task;
import java.awt.HeadlessException;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;

/**
 *
 * @author yakut
 */
public class Lisanser extends javax.swing.JDialog {
   static Logger logger=Logger.getLogger(Lisanser.class);
    String hdd = "";// adamın HDD numarasıymış..
    String uretilenDeger;
    String istenenDeger;

    /**
     * Creates new form Lisanser
     */
    public Lisanser() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    public Lisanser(String key) {
        this();
        this.hdd = key;
    }

    public static void lisansiKontrolEt(String[] params) throws HeadlessException {
        String regFile = "driver.dll";
        Lisans lisans = null;
        Lisanser lisanser;
        File f;
        if (params.length == 0) {
            f = new File(regFile);
            lisanser = new Lisanser();
            try {
                lisans = new Lisans(new SimpleDateFormat("dd.MM.yyyy").parse("24.12.1984"), " ");
            } catch (ParseException ex) {
            }
            lisanser.ziple(regFile, lisans);
            logger.warn("lisans bilgisi alınamadı, sistem bir süre çalışacak");
            JOptionPane.showMessageDialog(null, "Lisans Bilgisi Alınamadı\nSistem 10 dk Demo Modda çalışacak...");
            
             
          
            Task.addTask( new Task.LittleTask(){  
                int time=0;
                @Override
                public void execute() {
                    time++;
                    logger.warn("demo timer:"+time+"s");
                    if(time>5*60){
                        logger.warn("system shutting down");
                        System.exit(0);
                    }
                 }
            });
            return;
            
            //System.exit(0);
        }
        String s = params[0];
        lisanser = new Lisanser(s);
        f = new File(regFile);
        if (!f.exists()) {
            lisanser.showLisansDialog();
        } else {
            lisans = lisanser.zipAc(regFile);

            if (lisans.getHdd().equals(s) && ((lisans.getSonTarih().getTime() - new Date().getTime()) > 0)) {
            } else {
                lisanser.showLisansDialog();
                System.exit(0);
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 481, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 134, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Ürün Anahtarı Gir");
        getContentPane().setLayout(new java.awt.BorderLayout());

        jButton2.setText("Vazgeç");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel2.setText("Ürün Anahtarı");

        jLabel1.setText("Ürün No:");

        jButton1.setText("Tamam");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jTextField1, jTextField2});

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        istenenDeger = jTextField1.getText();
        if (istenenDeger.length() > 13) {
            if (kodlamaDogrumu(istenenDeger)) {
                Date sonTarih = getSonTarih(istenenDeger);
                if (sonTarih != null) {
                    Lisans lisans = new Lisans(sonTarih, hdd);
                    this.ziple("driver.dll", lisans);
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Girdiğiniz değer yanlış. Sistemden Çıkılıyor");
                    System.exit(0);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Girdiğiniz değer yanlış. Sistemden Çıkılıyor");
                System.exit(0);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Girdiğiniz değer yanlış. Sistemden Çıkılıyor");
            System.exit(0);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton2ActionPerformed

    public void ziple(String file, Lisans lisans) {
        try {
            ZipEntry zipEntry = new ZipEntry("Lisans");
            ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(file));
            zipOut.putNextEntry(zipEntry);
            ObjectOutputStream oout = new ObjectOutputStream(zipOut);
            // Lisans lisans = new Lisans(new SimpleDateFormat("dd.MM.yyyy").parse("24.12.2012"), "HDD-SeriNo");
            oout.writeObject(lisans);
            zipOut.closeEntry();
            oout.flush();
            oout.close();
        } catch (Exception ex) {
            System.out.println("Hata :" + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public Lisans zipAc(String file) {
        Lisans lisans = null;
        try {
            ZipInputStream zipIn = new ZipInputStream(new FileInputStream(file));
            ZipEntry zip = zipIn.getNextEntry();
            ObjectInputStream oin = new ObjectInputStream(zipIn);
            lisans = (Lisans) oin.readObject();
            oin.close();
            System.out.println(lisans + "");
        } catch (Exception ex) {
            System.out.println("Hata :" + ex.getMessage());
            ex.printStackTrace();
        }
        return lisans;
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Lisanser l = new Lisanser();
                l.kickRandomNumber();
                l.setVisible(true);
            }
        });
    }

    public void showLisansDialog() {
        this.setModal(true);
        jTextField2.setText("");
        jTextField1.setText("");
        kickRandomNumber();
        this.setLocation(350, 300);
        this.setVisible(true);
    }

    private void kickRandomNumber() {
        Random rand = new Random(System.currentTimeMillis());
        String serial = "";
        while (serial.length() < 14) {
            serial = ("" + (Math.abs(rand.nextLong() * 1000000000000000L)));
            System.out.println("uzunluk :" + serial.length());
        }
        uretilenDeger = serial.substring(0, 14);
        System.out.println("uzunluk :" + uretilenDeger.length());
        jTextField2.setText(uretilenDeger);
    }

    private boolean kodlamaDogrumu(String deger) {
        // TODO kodlama yöntemi bul buraya koy
        int[] d = new int[]{1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377};
        char[] c = uretilenDeger.toCharArray();//key.toCharArray();
        String newKey = "";
        for (int k = 0; k < 14; k++) {
            d[k] = (d[k] * c[k] * d[(13 - k)]) % 10;
            newKey += d[k] + "";
        }
        if (newKey.equals(deger)) {
            return true;
        } else {
            return false;
        }
    }

    private Date getSonTarih(String deger) {
        // TODO Tarih bulan fonksiyonuda buraya yaz

        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 500);
        date = c.getTime();
        System.out.println("istenen:" + date);
//        try {          
//            date = new SimpleDateFormat("dd.MM.yyyy").parse("30.06.2012");
//        } catch (ParseException ex) {
//            Logger.getLogger(Lisanser.class.getName()).log(Level.SEVERE, null, ex);
//        }
        return date;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
