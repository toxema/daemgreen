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
import com.yakut.azone.util.Setting;
import com.yakut.terminal.Device;
import com.yakut.terminal.DeviceListener;
import com.yakut.terminal.Move;
import com.yakut.terminal.util.DeviceFactory;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import org.apache.log4j.Logger;

/**
 *
 * @author yakut
 */
public class CanliPanelParkYasamDemo extends javax.swing.JPanel implements DeviceListener {

            TerminalController terminalController;
            Logger logger = Logger.getLogger(CanliPanelParkYasamDemo.class);
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

            public CanliPanelParkYasamDemo() {
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
                                    jPanel2.add(button);
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
                                                            reader.hata();
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

                        jLabel1.setText(h.getPersonel() != null ? p.getAd() + "  " + p.getSoyad() : "--");
                        jLabel2.setText(h.getAciklama() + " " + DateUtil.fullFormatDate(h.getTarih()));
                        jLabel4.setText((h.getKapi().getBolge() != null ? h.getKapi().getBolge().getAd() : ""));
                        jLabel5.setText(getYon(h.getYon()));

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

                    jPanel1 = new javax.swing.JPanel();
                    jLabel1 = new javax.swing.JLabel();
                    jLabel2 = new javax.swing.JLabel();
                    jLabel4 = new javax.swing.JLabel();
                    jLabel5 = new javax.swing.JLabel();
                    jPanel2 = new javax.swing.JPanel();
                    jScrollPane1 = new javax.swing.JScrollPane();
                    jTable1 = new javax.swing.JTable();

                    addComponentListener(new java.awt.event.ComponentAdapter() {
                              public void componentResized(java.awt.event.ComponentEvent evt) {
                                        formComponentResized(evt);
                              }
                    });
                    setLayout(new java.awt.BorderLayout());

                    jPanel1.setBackground(Setting.getSettings().getSkinBackgroundColor());

                    jLabel1.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N
                    jLabel1.setForeground(new java.awt.Color(255, 255, 255));
                    jLabel1.setText(" ");
                    jLabel1.setToolTipText("");

                    jLabel2.setForeground(new java.awt.Color(255, 255, 255));
                    jLabel2.setText(" ");

                    jLabel4.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
                    jLabel4.setForeground(new java.awt.Color(255, 255, 255));
                    jLabel4.setText(" ");

                    jLabel5.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
                    jLabel5.setForeground(new java.awt.Color(255, 255, 255));
                    jLabel5.setText(" ");

                    jPanel2.setBackground(Setting.getSettings().getSkinBackgroundColor());
                    jPanel2.setLayout(new java.awt.GridLayout(0, 1));

                    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
                    jPanel1.setLayout(jPanel1Layout);
                    jPanel1Layout.setHorizontalGroup(
                              jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                              .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                  .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                  .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                  .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)))
                                        .addContainerGap())
                    );
                    jPanel1Layout.setVerticalGroup(
                              jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                              .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                  .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                  .addGroup(jPanel1Layout.createSequentialGroup()
                                                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                      .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                                                                      .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                            .addGap(0, 13, Short.MAX_VALUE)))
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
          // Variables declaration - do not modify//GEN-BEGIN:variables
          private javax.swing.JLabel jLabel1;
          private javax.swing.JLabel jLabel2;
          private javax.swing.JLabel jLabel4;
          private javax.swing.JLabel jLabel5;
          private javax.swing.JPanel jPanel1;
          private javax.swing.JPanel jPanel2;
          private javax.swing.JScrollPane jScrollPane1;
          private javax.swing.JTable jTable1;
          // End of variables declaration//GEN-END:variables
}
