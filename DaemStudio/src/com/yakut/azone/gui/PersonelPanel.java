/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yakut.azone.gui;

import com.yakut.azone.beans.Grup;
import com.yakut.azone.beans.Personel;
import com.yakut.azone.controller.GrupController;
import com.yakut.azone.controller.HareketController;
import com.yakut.azone.controller.PersonelController;
import com.yakut.azone.test.TestFrame;

import com.yakut.azone.util.DateUtil;
import com.yakut.azone.util.Excell;
import com.yakut.azone.util.Setting;
import com.yakut.azone.util.Utils;
 
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author yakut
 */
public class PersonelPanel extends javax.swing.JPanel {

            private PersonelController personelController;
            private GrupController grupController;
            private List<Personel> personeller;
            List<Grup> grupList;
            DefaultTableModel model;
            Personel personel;
            boolean isNewPersonel = false;

            public PersonelPanel() {
                        initComponents();
            }

            public void init() {
                        grupListele();
                        personelListele();

            }

            public void refresh() {
                        grupListele();
                        personelListele();
            }

            public GrupController getGrupController() {
                        if (grupController == null) {
                                    grupController = new GrupController();
                        }
                        return grupController;
            }

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

            public void personelListele() {
                        personelListele("");
            }

            public void grupListele() {
                        grupList = getGrupController().getGrupList();
                        DefaultComboBoxModel comboModel = new DefaultComboBoxModel();
                        for (Grup g : grupList) {
                                    comboModel.addElement(g);
                        }
                        jComboBox1.setModel(comboModel);
            }

            public void personelListele(String text) {
                        personeller = getPersonelController().getPersonelListByText(text);
                        model = new DefaultTableModel() {
                                    @Override
                                    public boolean isCellEditable(int row, int col) {
                                                return false;
                                    }
                        };
                        model.setColumnIdentifiers(new String[]{"Sicil", "Kart No", "Ad", "Soyad", "Grup", "Kart Bas", "Kart Bit", "Kalan Gün", "Son Hareket", "Konum"});

                        for (Personel a : personeller) {
                                    String[] row = getPersonelRow(a);
                                    model.addRow(row);
                        }
                        jTable1.setModel(model);
                        if (personeller.size() > 0) {
                                    personelGoster(0);
                        } else {
                                    alanlariBosalt();
                        }
                        jLabel7.setText(personeller.size()+" kayıt listelendi.");
                        
            }

            public void personelGoster(int index) { 
                        if (index < personeller.size()) {
                                    personelGoster(personeller.get(index));
                        }
            }

            public void personelGoster(Personel a) {
                        jTextField1.setText(a.getSicil());
                        jTextField2.setText(a.getKartNo());
                        jTextField5.setText(a.getAd());
                        jTextField3.setText(a.getSoyad());
                        jTextField6.setText(a.getCihazNo());
                        jTextField7.setText(a.getBlok());
                        jTextField8.setText(a.getDaire());
                        jTextField9.setText(a.getAciklama());
                        jTextField10.setText(a.getEmail());

                        if (a.getGrup() != null) {
                                    jComboBox1.setSelectedItem(a.getGrup());
                        }
                        jSpinner1.setValue(a.getGecerlilikBaslangicTarihi());
                        jSpinner3.setValue(a.getGecerlilikBitisTarihi());
                        jComboBox2.setSelectedIndex(a.getDurum());

                        jLabel14.setText(DateUtil.fullFormatDate(a.getSonHareket()));
                        if (a.getBolge() != null) {
                                    jLabel16.setText(a.getBolge().getAd());
                        }
                        isNewPersonel = false;
                        personel = a;
            }

            public void yeniPersonelKaydet() {
                        alanlariBosalt();
            }

            public void alanlariBosalt() {
                        jTextField1.setText("");
                        jTextField2.setText("");
                        jTextField3.setText("");
                        jTextField5.setText("");
                        jTextField6.setText("");
                        jTextField7.setText("");
                        jTextField8.setText("");
                        jTextField9.setText("");
                        jTextField10.setText("");
                        jComboBox1.setSelectedIndex(-1);
                        jSpinner1.setValue(new Date());
                        jSpinner3.setValue(new Date());

                        jComboBox2.setSelectedIndex(0);
                        jLabel14.setText("");
                        jLabel16.setText("");
                        isNewPersonel = true;
                        personel = null;
            }

            private void personelKaydet() {

                        if (isNewPersonel) {
                                    personel = new Personel();

                        }

                        personel.setSicil(jTextField1.getText());
                        personel.setKartNo(jTextField2.getText());
                        personel.setCihazNo(jTextField6.getText());
                        personel.setAd(jTextField5.getText());
                        personel.setSoyad(jTextField3.getText());
                        personel.setGrup((Grup) jComboBox1.getSelectedItem());

                        personel.setBlok(jTextField7.getText());
                        personel.setDaire(jTextField8.getText());

                        personel.setGecerlilikBaslangicTarihi((Date) jSpinner1.getValue());
                        personel.setGecerlilikBitisTarihi((Date) jSpinner3.getValue());
                        personel.setDurum(jComboBox2.getSelectedIndex());
                        personel.setAciklama(jTextField9.getText());
                        personel.setEmail(jTextField10.getText());
                        if (isNewPersonel) {
                                    getPersonelController().persist(personel);
                        } else {
                                    getPersonelController().merge(personel);
                        }
                        personelListele();
            }

            private void personelSil() throws HeadlessException {
                        if (JOptionPane.showConfirmDialog(null, "Bu Kişiyi silmek istediğinizden emin misiniz?.") == JOptionPane.YES_OPTION) {
                                    if (personel != null) {

                                                getPersonelController().delete(personel);
                                                personelListele();
                                    } else {
                                                JOptionPane.showMessageDialog(null, "Hiçbir kayıt seçmediniz.");
                                    }
                        }
            }

            public int kalanGun(Date tarih) {
                        int value;
                        long l = Math.abs(tarih.getTime())- Math.abs(new Date().getTime()) ;
                        value = (int) (l / (1000 * 60 * 60 * 24));
                        value++;
                        if (value < 0) {
                                    value = 0;
                        }
                        return value;
            }
            // <editor-fold defaultstate="collapsed" desc="getPersonelRow(personel)">

            private String[] getPersonelRow(Personel a) {
                        String[] row = new String[10];
                        Arrays.fill(row, "");
                        row[0] = a.getSicil();
                        row[1] = a.getKartNo();
                        row[2] = a.getAd();
                        row[3] = a.getSoyad();
//                        row[4] = a.getBlok();
//                        row[5] = a.getDaire();

                        row[4] = a.getGrup() != null ? a.getGrup().getAd() : "";
                        row[5] = DateUtil.formatDate(a.getGecerlilikBaslangicTarihi());
                        row[6] = DateUtil.formatDate(a.getGecerlilikBitisTarihi());
                        row[7] = "" + kalanGun(a.getGecerlilikBitisTarihi());
                        row[8] = DateUtil.fullFormatDate(a.getSonHareket());
                        row[9] = a.getBolge() != null ? a.getBolge().getAd() : "";

                        return row;
            }
//</editor-fold>

            public void tableSecildi() {
                        int index = jTable1.getSelectedRow();
                        if (index > -1) {
                                    personelGoster(index);
                        }
            }

            public static void main(String[] args) {
                        Utils.startNimbus();
                        PersonelPanel ap = new PersonelPanel();
                        TestFrame t = new TestFrame(ap);
                        ap.init();
            }

            @SuppressWarnings("unchecked")
            // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
            private void initComponents() {

                        jPanel1 = new javax.swing.JPanel();
                        jButton1 = new javax.swing.JButton();
                        jButton2 = new javax.swing.JButton();
                        jButton3 = new javax.swing.JButton();
                        jPanel4 = new javax.swing.JPanel();
                        jLabel9 = new javax.swing.JLabel();
                        jLabel3 = new javax.swing.JLabel();
                        jLabel2 = new javax.swing.JLabel();
                        jLabel15 = new javax.swing.JLabel();
                        jSpinner1 = new javax.swing.JSpinner();
                        jLabel18 = new javax.swing.JLabel();
                        jLabel4 = new javax.swing.JLabel();
                        jTextField6 = new javax.swing.JTextField();
                        jLabel14 = new javax.swing.JLabel();
                        jLabel12 = new javax.swing.JLabel();
                        jTextField3 = new javax.swing.JTextField();
                        jTextField5 = new javax.swing.JTextField();
                        jLabel16 = new javax.swing.JLabel();
                        jLabel5 = new javax.swing.JLabel();
                        jSpinner3 = new javax.swing.JSpinner();
                        jLabel17 = new javax.swing.JLabel();
                        jLabel19 = new javax.swing.JLabel();
                        jLabel10 = new javax.swing.JLabel();
                        jTextField2 = new javax.swing.JTextField();
                        jTextField8 = new javax.swing.JTextField();
                        jTextField7 = new javax.swing.JTextField();
                        jLabel6 = new javax.swing.JLabel();
                        jComboBox2 = new javax.swing.JComboBox();
                        jTextField1 = new javax.swing.JTextField();
                        jLabel13 = new javax.swing.JLabel();
                        jLabel8 = new javax.swing.JLabel();
                        jComboBox1 = new javax.swing.JComboBox();
                        jTextField9 = new javax.swing.JTextField();
                        jLabel1 = new javax.swing.JLabel();
                        jTextField10 = new javax.swing.JTextField();
                        jPanel2 = new javax.swing.JPanel();
                        jPanel3 = new javax.swing.JPanel();
                        jTextField4 = new javax.swing.JTextField();
                        jButton4 = new javax.swing.JButton();
                        jButton5 = new javax.swing.JButton();
                        jScrollPane1 = new javax.swing.JScrollPane();
                        jTable1 = new javax.swing.JTable();
                        jLabel7 = new javax.swing.JLabel();

                        setBackground(
                                    Setting.getSettings().getSkinBackground2Color());
                        setLayout(new java.awt.BorderLayout());

                        jPanel1.setBackground(Setting.getSettings().getSkinBackground2Color());

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

                        jPanel4.setBackground(Setting.getSettings().getSkinBackground2Color());

                        jLabel9.setForeground(Setting.getSettings().getSkinForeground2Color());
                        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                        jLabel9.setText("Ad");

                        jLabel3.setForeground(Setting.getSettings().getSkinForeground2Color());
                        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                        jLabel3.setText("Kart No");

                        jLabel2.setForeground(Setting.getSettings().getSkinForeground2Color());
                        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                        jLabel2.setText("Sicil");

                        jLabel15.setBackground(new java.awt.Color(255, 255, 255));
                        jLabel15.setForeground(Setting.getSettings().getSkinForeground2Color());
                        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                        jLabel15.setText("Konum");

                        jSpinner1.setModel(new javax.swing.SpinnerDateModel());
                        jSpinner1.setEditor(new javax.swing.JSpinner.DateEditor(jSpinner1, "dd.MM.yyyy"));
                        jSpinner1.getEditor().setBackground(Setting.getSettings().getSkinBackground2Color());

                        jLabel18.setForeground(Setting.getSettings().getSkinForeground2Color());
                        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                        jLabel18.setText("Daire");

                        jLabel4.setForeground(Setting.getSettings().getSkinForeground2Color());
                        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                        jLabel4.setText("Soyad");

                        jLabel14.setBackground(new java.awt.Color(255, 255, 255));
                        jLabel14.setFont(new java.awt.Font("DejaVu Sans", 1, 12)); // NOI18N
                        jLabel14.setForeground(Setting.getSettings().getSkinForeground2Color());
                        jLabel14.setText(" ");

                        jLabel12.setForeground(Setting.getSettings().getSkinForeground2Color());
                        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                        jLabel12.setText("Kart Bit.");

                        jLabel16.setBackground(new java.awt.Color(255, 255, 255));
                        jLabel16.setFont(new java.awt.Font("DejaVu Sans", 1, 12)); // NOI18N
                        jLabel16.setForeground(Setting.getSettings().getSkinForeground2Color());
                        jLabel16.setText(" ");

                        jLabel5.setForeground(Setting.getSettings().getSkinForeground2Color());
                        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                        jLabel5.setText("Grup");

                        jSpinner3.setModel(new javax.swing.SpinnerDateModel());
                        jSpinner3.setEditor(new javax.swing.JSpinner.DateEditor(jSpinner3, "dd.MM.yyyy"));
                        jSpinner3.getEditor().setBackground(Setting.getSettings().getSkinBackground2Color());

                        jLabel17.setForeground(Setting.getSettings().getSkinForeground2Color());
                        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                        jLabel17.setText("Enroll");

                        jLabel19.setForeground(Setting.getSettings().getSkinForeground2Color());
                        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                        jLabel19.setText("Açıklama");

                        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
                        jLabel10.setForeground(Setting.getSettings().getSkinForeground2Color());
                        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                        jLabel10.setText("Durum");

                        jLabel6.setForeground(Setting.getSettings().getSkinForeground2Color());
                        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                        jLabel6.setText("Kart  Bas.");

                        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "GECERLİ", "İPTAL", "KAYIP" }));

                        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
                        jLabel13.setForeground(Setting.getSettings().getSkinForeground2Color());
                        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                        jLabel13.setText("Son Hareket");

                        jLabel8.setForeground(Setting.getSettings().getSkinForeground2Color());
                        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                        jLabel8.setText("Blok");

                        jLabel1.setForeground(Setting.getSettings().getSkinForeground2Color());
                        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                        jLabel1.setText("Telefon");

                        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
                        jPanel4.setLayout(jPanel4Layout);
                        jPanel4Layout.setHorizontalGroup(
                                    jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jPanel4Layout.createSequentialGroup()
                                                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                                                                                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                                                                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                                    .addComponent(jTextField10)
                                                                                    .addComponent(jTextField9)
                                                                                    .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                    .addComponent(jSpinner3)
                                                                                    .addComponent(jSpinner1)
                                                                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                                                                                .addComponent(jTextField7, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                    .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                    .addComponent(jTextField6, javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                    .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                    .addComponent(jTextField5, javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                    .addComponent(jTextField3))))
                                                .addContainerGap())
                        );
                        jPanel4Layout.setVerticalGroup(
                                    jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(jLabel2))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                            .addComponent(jLabel17)
                                                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(3, 3, 3)
                                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                            .addComponent(jLabel3)
                                                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(3, 3, 3)
                                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(jLabel9))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                            .addComponent(jLabel4)
                                                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                            .addComponent(jLabel5)
                                                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                            .addComponent(jLabel8)
                                                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(jLabel18)
                                                            .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                            .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(jLabel6))
                                                .addGap(6, 6, 6)
                                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                            .addComponent(jSpinner3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(jLabel12))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(jLabel10))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                            .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(jLabel19))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                            .addComponent(jLabel1)
                                                            .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                            .addComponent(jLabel13)
                                                            .addComponent(jLabel14))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                            .addComponent(jLabel16)
                                                            .addComponent(jLabel15))
                                                .addContainerGap(26, Short.MAX_VALUE))
                        );

                        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
                        jPanel1.setLayout(jPanel1Layout);
                        jPanel1Layout.setHorizontalGroup(
                                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                                        .addComponent(jButton1)
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                                                                        .addComponent(jButton2)
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(jButton3))
                                                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addContainerGap())
                        );
                        jPanel1Layout.setVerticalGroup(
                                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                            .addComponent(jButton1)
                                                            .addComponent(jButton2)
                                                            .addComponent(jButton3))
                                                .addContainerGap())
                        );

                        add(jPanel1, java.awt.BorderLayout.WEST);

                        jPanel2.setBackground(Setting.getSettings().getSkinBackground2Color());
                        jPanel2.setLayout(new java.awt.BorderLayout());

                        jPanel3.setBackground(Setting.getSettings().getSkinBackground2Color());
                        jPanel3.setLayout(new java.awt.BorderLayout());

                        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
                                    public void keyReleased(java.awt.event.KeyEvent evt) {
                                                jTextField4KeyReleased(evt);
                                    }
                        });
                        jPanel3.add(jTextField4, java.awt.BorderLayout.CENTER);

                        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yakut/resource/ara.png"))); // NOI18N
                        jButton4.setToolTipText("Abone Ara");
                        jButton4.setIconTextGap(1);
                        jButton4.addActionListener(new java.awt.event.ActionListener() {
                                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                jButton4ActionPerformed(evt);
                                    }
                        });
                        jPanel3.add(jButton4, java.awt.BorderLayout.LINE_END);

                        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yakut/resource/openofficeorg.png"))); // NOI18N
                        jButton5.addActionListener(new java.awt.event.ActionListener() {
                                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                jButton5ActionPerformed(evt);
                                    }
                        });
                        jPanel3.add(jButton5, java.awt.BorderLayout.WEST);

                        jPanel2.add(jPanel3, java.awt.BorderLayout.PAGE_START);

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

                        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                        jLabel7.setText("liste");
                        jLabel7.setToolTipText("");
                        jPanel2.add(jLabel7, java.awt.BorderLayout.PAGE_END);

                        add(jPanel2, java.awt.BorderLayout.CENTER);
            }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
                yeniPersonelKaydet();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
                personelSil();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
                personelKaydet();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
                if (evt.getClickCount() == 2) {
                            int k = jTable1.getSelectedColumn();
                            int row = jTable1.getSelectedRow();
                            String s = "";
                            if (k == 4) {
                                        s = jTable1.getValueAt(row, 4).toString();

                            }
                            jTextField4.setText(s);
                            personelListele(s);
                } else {
                            tableSecildi();
                }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyReleased
                tableSecildi();
    }//GEN-LAST:event_jTable1KeyReleased

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
                personelListele(jTextField4.getText());
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTextField4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyReleased
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                            personelListele(jTextField4.getText());
                }
    }//GEN-LAST:event_jTextField4KeyReleased

            private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
                        new Excell().exceleAktar(jTable1);
            }//GEN-LAST:event_jButton5ActionPerformed
            // Variables declaration - do not modify//GEN-BEGIN:variables
            private javax.swing.JButton jButton1;
            private javax.swing.JButton jButton2;
            private javax.swing.JButton jButton3;
            private javax.swing.JButton jButton4;
            private javax.swing.JButton jButton5;
            private javax.swing.JComboBox jComboBox1;
            private javax.swing.JComboBox jComboBox2;
            private javax.swing.JLabel jLabel1;
            private javax.swing.JLabel jLabel10;
            private javax.swing.JLabel jLabel12;
            private javax.swing.JLabel jLabel13;
            private javax.swing.JLabel jLabel14;
            private javax.swing.JLabel jLabel15;
            private javax.swing.JLabel jLabel16;
            private javax.swing.JLabel jLabel17;
            private javax.swing.JLabel jLabel18;
            private javax.swing.JLabel jLabel19;
            private javax.swing.JLabel jLabel2;
            private javax.swing.JLabel jLabel3;
            private javax.swing.JLabel jLabel4;
            private javax.swing.JLabel jLabel5;
            private javax.swing.JLabel jLabel6;
            private javax.swing.JLabel jLabel7;
            private javax.swing.JLabel jLabel8;
            private javax.swing.JLabel jLabel9;
            private javax.swing.JPanel jPanel1;
            private javax.swing.JPanel jPanel2;
            private javax.swing.JPanel jPanel3;
            private javax.swing.JPanel jPanel4;
            private javax.swing.JScrollPane jScrollPane1;
            private javax.swing.JSpinner jSpinner1;
            private javax.swing.JSpinner jSpinner3;
            private javax.swing.JTable jTable1;
            private javax.swing.JTextField jTextField1;
            private javax.swing.JTextField jTextField10;
            private javax.swing.JTextField jTextField2;
            private javax.swing.JTextField jTextField3;
            private javax.swing.JTextField jTextField4;
            private javax.swing.JTextField jTextField5;
            private javax.swing.JTextField jTextField6;
            private javax.swing.JTextField jTextField7;
            private javax.swing.JTextField jTextField8;
            private javax.swing.JTextField jTextField9;
            // End of variables declaration//GEN-END:variables
}
