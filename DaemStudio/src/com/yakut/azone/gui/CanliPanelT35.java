/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yakut.azone.gui;

import com.yakut.azone.beans.Bolge;
import com.yakut.azone.beans.Grup;
import com.yakut.azone.beans.GrupHak;
import com.yakut.azone.beans.Hareket;
import com.yakut.azone.beans.Kapi;
import com.yakut.azone.beans.Personel;
import com.yakut.azone.beans.Terminal;
import com.yakut.azone.controller.GrupHakController;
import com.yakut.azone.controller.HareketController;
import com.yakut.azone.controller.KapiController;
import com.yakut.azone.controller.PersonelController;
import com.yakut.azone.controller.TerminalController;
import com.yakut.azone.util.DateUtil;
import com.yakut.azone.util.FileIO;
import com.yakut.azone.util.Setting;
import com.yakut.terminal.Device;
import com.yakut.terminal.DeviceListener;
import com.yakut.terminal.Move;
import com.yakut.terminal.util.DeviceFactory;
import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import org.apache.log4j.Logger;

/**
 *
 * @author yakut
 */
public class CanliPanelT35 extends javax.swing.JPanel implements DeviceListener {

            TerminalController terminalController;
            Logger logger = Logger.getLogger(CanliPanelT35.class);
            List<JButton> buttonList = new ArrayList<JButton>();

            public TerminalController getTerminalController() {
                        if (terminalController == null) {
                                    terminalController = new TerminalController();
                        }
                        return terminalController;
            }
            KapiController kapiController;

            public KapiController getKapiController() {
                        if (kapiController == null) {
                                    kapiController = new KapiController();
                        }
                        return kapiController;
            }
            PersonelController personelController;

            public PersonelController getPersonelController() {
                        if (personelController == null) {
                                    personelController = new PersonelController();
                        }
                        return personelController;
            }
            HareketController hareketController;

            public HareketController getHareketController() {
                        if (hareketController == null) {
                                    hareketController = new HareketController();
                        }
                        return hareketController;
            }
            GrupHakController grupHakController = null;

            public GrupHakController getGrupHakController() {
                        if (grupHakController == null) {
                                    grupHakController = new GrupHakController();
                        }
                        return grupHakController;
            }

            public CanliPanelT35() {
                        initComponents();
            }

            public void init() {
                        terminalleriYukle();

            }
            List<Terminal> terminalList;
            List<Device> deviceList = new ArrayList<Device>();

            public void terminalleriYukle() {
                        logger.info("Terminaller bağlanılıyor");
                        terminalList = getTerminalController().getTerminalList();
                        for (Terminal t : terminalList) {
                                    Device term = DeviceFactory.getDeviceFactory().createDevice(t.getConString(), this);
                                    deviceList.add(term);
                                    t.setDevice(term);
                                    JButton button = new JButton(t.getAd());
                                    buttonList.add(button);
                                    //    jPanel2.add(button);
                                    term.basla();

                        }
                        logger.info("Terminallere yüklendi");
            }

            public void refresh() {
            }

            @Override
            public void connected(Device device) {
                        int index = deviceList.indexOf(device);
                        if (index > -1) {
                                    buttonList.get(index).setBackground(Color.GREEN);
                        }
                        logger.info(device + " cihazı bağlandı");
            }

            @Override
            public void disconnected(Device device) {
                        int index = deviceList.indexOf(device);
                        if (index > -1) {
                                    buttonList.get(index).setBackground(Color.RED);
                        }
                        logger.info(device + " cihazı bağlandı");
            }

            @Override
            public void read(Device device, Move move) {
                        logger.info(device + " onRead() " + move);
                        isMantigi(move, device);
            }

            public Kapi getKapi(Move move, Device reader) {
                        Kapi kapi = null;
                        int index = deviceList.indexOf(reader);
                        if (index > -1) {
                                    Terminal term = terminalList.get(index);
                                    kapi = getKapiController().getKapiByTerminal(term);
                                    if (kapi != null) {
                                                kapi.setTerminal(term);
                                    }
                        }
                        return kapi;
            }

            public Personel getPersonel(String enroll) {
                        Personel p = getPersonelController().getPersonelByEnroll(enroll);
                        return p;
            }

            public int dif(Date b, Date e) {
                        long dif = b.getTime() - e.getTime();
                        int fark = (int) (dif / (1000));
                        System.out.println("dif " + fark);
                        return fark;
            }
            final Object lock = new Object();

            public void isMantigi(Move move, Device reader) {
                        synchronized (lock) {
                                    Hareket h = new Hareket();
                                    h.setTarih(move.getTarih());
                                    Kapi kapi = getKapi(move, reader);
                                    if (kapi == null) {
                                                h.setAciklama(reader.getDeviceNo() + " Kapı Tanımsız");
                                                hataMesajiGoster("boyle bir kapı yok " + reader.getDeviceNo());
                                                reader.hata();
                                    } else {
                                                h.setKapi(kapi);
                                                h.setYon(kapi.getYon());
                                                Personel personel = getPersonel(move.getSicil());
                                                if (personel == null) {
                                                            h.setAciklama(move.getSicil() + " personel Kayıtlı değil");

                                                } else {
                                                            h.setPersonel(personel);
                                                            personel.setSonHareket(h.getTarih());
                                                            boolean kontrol = girisKontrol(h);
                                                            if (move.isOnline()) {
                                                                        if (kontrol) {
                                                                                    kapi.getTerminal().getDevice().ac();
                                                                        } else {
                                                                                    kapi.getTerminal().getDevice().hata();
                                                                        }
                                                            }
                                                            personelController.merge(personel);

                                                }
                                                hareketKaydet(h);
                                    }
                        }
            }

            public void hareketKaydet(Hareket h) {
                        logger.info(h);
                   
                                    if (h.getHareketTuru() == Hareket.ONAY) {
                                                getPersonelController().merge(h.getPersonel());
                                    }
                     
                        getHareketController().persist(h);
                        hareketGoster(h);
            }

            public String getYon(char c) {
                        String value = "";
                        switch (c) {
                                    case 'C':
                                                value = "ÇIKIŞ";
                                                break;
                                    case 'G':
                                                value = "GİRİŞ";
                                                break;

                        }
                        return value;
            }

            public void hareketGoster(Hareket h) {
                        Personel p = h.getPersonel();
                        if (p == null) {
                                    p = new Personel();
                                    p.setAd("TANIMSIZ PERSONEL");
                        }

                        jLabel3.setText((h.getPersonel() != null ? p.getAd() + "  " + p.getSoyad() : "Personel Kayıtlı Değil") + " - " + getYon(h.getYon()));
//                        jLabel2.setText(h.getAciklama() + " " + DateUtil.fullFormatDate(h.getTarih()));
//                        jLabel4.setText((h.getKapi().getBolge() != null ? h.getKapi().getBolge().getAd() : ""));
//                        jLabel5.setText(getYon(h.getYon()));

                        String s = ""
                                + "<html>"
                                + (h.getHareketTuru() == Hareket.HATA ? "<body bgcolor=red>" : "<body>")
                                + "<span   >"
                                + "<b><font size=\"6\">" + getYon(h.getYon()) + "</font></b><br>"
                                + "<i><center>" + (h.getKapi().getBolge() != null ? h.getKapi().getBolge().getAd() : "") + "</center></i>"
                                + "</span> "
                                + "</html>";

                        String s2 = ""
                                + "<html>"
                                + ("<body>")
                                + "<span style=\"float:left\">"
                                + "<b><font size=\"6\">" + p.getAd() + "  " + p.getSoyad() + "</font></b><br>  "
                                + "<i>" + DateUtil.fullFormatDate(h.getTarih()) + " - " + h.getAciklama() + "</i>"
                                + "</span> "
                                + "</body>"
                                + "</html>"
                                + "";


                        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();


                        model.addRow(new Object[]{
                                            s, s2
//                                      "<html>"
//                                      + (h.getHareketTuru() == Hareket.HATA ? "<body bgcolor=red>" : "<body>")
//                                      + "<span><b><font size=\"6\">" + p.getAd() + " " + p.getSoyad() + "</font></b>" + h.getAciklama() + "<br>  "
//                                      + "<i>Tarih :" + DateUtil.fullFormatDate(h.getTarih()) + "</i></span> </body>></html>"
                                });
            }

            public boolean girisKontrol(Hareket h) {

                        Personel personel = h.getPersonel();
                        Kapi kapi = h.getKapi();
                        Grup grup = personel.getGrup();
                        if (grup == null) {
                                    h.setAciklama("Grup Tanımlı değil");
                        } else {
                                    Bolge bolge = kapi.getBolge();
                                    if (bolge == null) {
                                                h.setAciklama("Bölge Tanımsız.");
                                    } else {
                                                personel.setBolge(bolge);
                                                GrupHak hak = getGrupHakController().getHak(grup, bolge);
                                                if (hak == null) {
                                                            h.setAciklama("Yetkisiz Giriş");
                                                } else {

                                                            if ((personel.getYon() == kapi.getYon()) && !hak.isMukerrerSerbest()) {
                                                                        h.setAciklama("Mükerrer Hareket");
                                                            } else {
                                                                        if (!(new Date().after(personel.getGecerlilikBaslangicTarihi()))) {
                                                                                    h.setAciklama("Kart kullanıma başlamamış.");
                                                                        } else {
                                                                                    if (!(new Date().before(personel.getGecerlilikBitisTarihi()))) {
                                                                                                h.setAciklama("Kart kullanımı bitmiş.");
                                                                                    } else {
                                                                                                if (personel.getYon() == Kapi.CIKIS) {
                                                                                                            personel.setBolge(null);
                                                                                                }
                                                                                                personel.setYon(kapi.getYon());
                                                                                                h.setAciklama("Normal Hareket");
                                                                                                h.setHareketTuru(Hareket.ONAY);
                                                                                    }
                                                                        }

                                                            }
                                                }
                                    }
                        }

                        return h.getHareketTuru() == Hareket.ONAY ? true : false;
            }

            public void hataMesajiGoster(String m) {
                        logger.info(m);
            }

            public void end() {
//                    for (Terminal t : terminalList) {
//                              t.getDevice().disconnect();
//                    }
            }

            @SuppressWarnings("unchecked")
          // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
          private void initComponents() {

                    jDialog1 = new javax.swing.JDialog();
                    jPanel1 = new javax.swing.JPanel();
                    jButton1 = new javax.swing.JButton();
                    jLabel3 = new javax.swing.JLabel();
                    jScrollPane1 = new javax.swing.JScrollPane();
                    jTable1 = new javax.swing.JTable();

                    javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
                    jDialog1.getContentPane().setLayout(jDialog1Layout);
                    jDialog1Layout.setHorizontalGroup(
                              jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                              .addGap(0, 381, Short.MAX_VALUE)
                    );
                    jDialog1Layout.setVerticalGroup(
                              jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                              .addGap(0, 233, Short.MAX_VALUE)
                    );

                    addComponentListener(new java.awt.event.ComponentAdapter() {
                              public void componentResized(java.awt.event.ComponentEvent evt) {
                                        formComponentResized(evt);
                              }
                    });
                    setLayout(new java.awt.BorderLayout());

                    jPanel1.setBackground(Setting.getSettings().getSkinBackgroundColor());

                    jButton1.setText("Veri Topla");
                    jButton1.addActionListener(new java.awt.event.ActionListener() {
                              public void actionPerformed(java.awt.event.ActionEvent evt) {
                                        jButton1ActionPerformed(evt);
                              }
                    });

                    jLabel3.setText("Ad Soyad");

                    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
                    jPanel1.setLayout(jPanel1Layout);
                    jPanel1Layout.setHorizontalGroup(
                              jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                              .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton1)
                                        .addContainerGap())
                    );
                    jPanel1Layout.setVerticalGroup(
                              jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                              .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                  .addGroup(jPanel1Layout.createSequentialGroup()
                                                            .addComponent(jButton1)
                                                            .addGap(0, 0, Short.MAX_VALUE))
                                                  .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addContainerGap())
                    );

                    add(jPanel1, java.awt.BorderLayout.PAGE_START);

                    jTable1.setModel(new javax.swing.table.DefaultTableModel(
                              new Object [][] {

                              },
                              new String [] {
                                        "Yön", "Açıklama"
                              }
                    ) {
                              boolean[] canEdit = new boolean [] {
                                        false, false
                              };

                              public boolean isCellEditable(int rowIndex, int columnIndex) {
                                        return canEdit [columnIndex];
                              }
                    });
                    jTable1.setRowHeight(60);
                    jScrollPane1.setViewportView(jTable1);

                    add(jScrollPane1, java.awt.BorderLayout.CENTER);
          }// </editor-fold>//GEN-END:initComponents

          private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
                      int firstCol = (int) (this.getWidth() * 0.3);
                      int secondCol = (int) (this.getWidth() * 0.7);
                      int k = Integer.parseInt(Setting.getSettings().getProperty("canli.table.col.size", "80"));
                      jTable1.getColumnModel().getColumn(0).setPreferredWidth(k);
                      jTable1.getColumnModel().getColumn(1).setPreferredWidth((int) (this.getWidth() - k));
          }//GEN-LAST:event_formComponentResized

            public void hareketTopla() {
                        Runtime r = Runtime.getRuntime();
                        Process p;
                        try {
                                    logger.info("data24 çalıştırılıyor");
                                    p = r.exec("data24.exe");
                                    int k = p.waitFor();
                                    logger.info("data24 çalışması Bitti");

                                    File f = new File("bilgi.dat");
                                    if (f.exists()) {
                                                List<String> list = FileIO.getLines(f);
                                                for (String s : list) {
                                                            System.out.println("----------------------------------");
                                                            System.out.println(s);
                                                            String[] row = s.split(",");
                                                            //   1,08142,01.01.2013 01:01:00
                                                            Move m = new Move(1, row[1], DateUtil.fullParseDate(row[2]), Integer.parseInt(row[0]), false);
                                                            isMantigi(m, terminalList.get(0).getDevice());
                                                }

                                                if (f.delete()) {
                                                            logger.info("bilgi dosyası silindi");
                                                } else {
                                                            logger.info("bilgi dosyası silinemedi");
                                                }
                                    }

                        } catch (Exception ex) {
                                    logger.error(ex);
                        }
            }

            private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

                        new Thread() {
                                    @Override
                                    public void run() {
                                                jLabel3.setText("Kayıtlar alınıyor lütfen bekleyin.");
                                                jButton1.setEnabled(false);
                                                hareketTopla();
                                                jButton1.setEnabled(true);
                                    }
                        }.start();


            }//GEN-LAST:event_jButton1ActionPerformed
          // Variables declaration - do not modify//GEN-BEGIN:variables
          private javax.swing.JButton jButton1;
          private javax.swing.JDialog jDialog1;
          private javax.swing.JLabel jLabel3;
          private javax.swing.JPanel jPanel1;
          private javax.swing.JScrollPane jScrollPane1;
          private javax.swing.JTable jTable1;
          // End of variables declaration//GEN-END:variables
}
