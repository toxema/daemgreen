package com.yakut.azone.gui;

import com.yakut.azone.util.Database;
import com.yakut.azone.beans.BizerbaLog;
import com.yakut.azone.controller.BizerbaLogController;
import com.yakut.azone.test.TestFrame;
import com.yakut.azone.util.DateUtil;
import com.yakut.azone.util.Excell;
import com.yakut.azone.util.Setting;
import com.yakut.azone.util.Utils;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterException;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Hp
 */
public class RaporPanel extends javax.swing.JPanel {
        
        public RaporPanel() {
                initComponents();
        }
        
        public void init() {
                jSpinner3.setValue(DateUtil.getGunBaslangis());
                jSpinner4.setValue(DateUtil.getGunBitis());
                
                jSpinner5.setValue(DateUtil.getGunBaslangis());
                jSpinner6.setValue(DateUtil.getGunBitis());
        }
        
        public void refresh() {
        }
        BizerbaLogController bizerbaLogController = null;
        
        public BizerbaLogController getBizerbaLogController() {
                if (bizerbaLogController == null) {
                        bizerbaLogController = new BizerbaLogController();
                }
                return bizerbaLogController;
        }
        Excell excel;
        
        public Excell getExcel() {
                if (excel == null) {
                        excel = new Excell();
                }
                return excel;
        }
        
        private void bizerbaRaporuHazirla() {
                Date bas = (Date) jSpinner3.getValue();
                Date bit = (Date) jSpinner4.getValue();
                
                System.out.println("Date 1:" + DateUtil.fullFormatDate(bas));
                System.out.println("Date 2:" + DateUtil.fullFormatDate(bit));
                
                String urunBas = jTextField2.getText();
                String urunBit = jTextField3.getText();
                
                List<BizerbaLog> list;
                list = getBizerbaLogController().getBizerbaLogList(urunBas, urunBit, bas, bit);
                
                DefaultTableModel model = new DefaultTableModel();
                model.setColumnIdentifiers(new String[]{"ID", "Tarih ", "MAKINA KODU", "Ürün Kodu", "Ürün Adı", " Gerçek Ağırlık", "Barkod Ağırlığı", "Fark Ağırlık", "Yüzdelik Fark"});
                
                for (BizerbaLog o : list) {
                        
                        model.addRow(new String[]{
                                o.getId() + "",
                                DateUtil.fullFormatDate(o.getTarih()),
                                o.getMakinaAdi(),
                                o.getUrunKodu(),
                                o.getUrunAdi(),
                                o.getGercekAgirlik() + "",
                                o.getBarkodAgirlik() + "",
                                o.getAgirlikFark() + "",
                                format2.format(o.getYuzdelik()) + ""
                        });
                        
                }
                jTable2.setModel(model);
                jLabel8.setText(list.size() + " Adet kayıt listelendi.");
        }
        
        public static void main(String[] args) {
                Utils.stopLogging();
                Utils.startNimbus();
                RaporPanel ap = new RaporPanel();
                TestFrame t = new TestFrame(ap);
                ap.init();
                
        }
        DecimalFormat format2 = new DecimalFormat("0.00");//.format(1.199);

        private void getGunlukOzetRapor() {
                Date bas = (Date) jSpinner5.getValue();
                Date bit = (Date) jSpinner6.getValue();
                
                String urunBas = jTextField4.getText();
                String urunBit = jTextField5.getText();
                
                DefaultTableModel model = new DefaultTableModel();
                model.setColumnIdentifiers(new String[]{"Tarih ", "MAKINA KODU", "Ürün Kodu", "Ürün Adı", " Toplam Gercek  Ağırlık", "Toplam Barkod  Ağırlığı", "Toplam Fark Ağırlık", "Toplam Yüzdelik Fark"});
                List list = getBizerbaLogController().getBizerbaOzetList(urunBas, urunBit, bas, bit);
                for (Object o : list) {
                        Object[] row = (Object[]) o;
                        model.addRow(row);
                }
                for (int k = 0; k < model.getRowCount(); k++) {
                        int barkodAgirlik = Integer.parseInt(model.getValueAt(k, 5).toString());
                        int fark = Integer.parseInt(model.getValueAt(k, 6).toString());
                        float yuzdelikFark = 0.0f;
                        if (barkodAgirlik != 0) {
                                yuzdelikFark = ((float) fark / (float) barkodAgirlik) * 100.0f;
                        }
                        
                        model.setValueAt(format2.format(yuzdelikFark), k, 7);
                }
                jTable4.setModel(model);
                jLabel9.setText(list.size() + " Adet kayıt listelendi.");
                
        }
        ResultSet sonuc;
        Database db = null;
        
        private void sql() throws Exception {
                
                if (db == null) {
                        db = new Database(Setting.getSettings().getProperty("database.path", ""), Setting.getSettings().getProperty("database.server.ip", "localhost"));
                        db.begin();
                }
                long bas = System.currentTimeMillis();
                if (db != null) {
                        try {
                                String sql = "select CAST (tarih AS date) ,MAKINA_ADI,URUN_KODU,URUN_ADI,sum(BARKOD_AGIRLIK), sum(GERCEK_AGIRLIK), sum(FARK_AGIRLIK), sum(YUZDELIK)  from BizerbaLog "
                                        //    + "where  (tarih between :tar1 and :tar2) "
                                        //     + " and (urun_Kodu between :urunBas and :urunBit) "
                                        + " group by 1,2,3,4 ";
                                sonuc = db.sql(sql);
                                DefaultTableModel model = new DefaultTableModel();
                                int colCount = sonuc.getMetaData().getColumnCount();
                                model.setColumnIdentifiers(new String[]{"Tarih ", "MAKINA KODU", "Ürün Kodu", "Ürün Adı", " Toplam Gerçek Ağırlık", "Toplam Barkod Ağırlığı", "Toplam Fark Ağırlık", "Toplam Yüzdelik Fark"});
                                Object[] row = new Object[colCount];
                                while (sonuc.next()) {
                                        for (int k = 0; k < colCount; k++) {
                                                row[k] = sonuc.getObject(k + 1);
                                        }
                                        //    data.add(row);
                                        model.addRow(row);
                                }
                                jTable4.setModel(model);
                                jLabel9.setText(jTable1.getRowCount() + " kayıt Listlendi");
                                System.out.println("toplam Kayıt:" + jTable1.getRowCount());
                                
                        } catch (Exception ex) {
                        }
                } else {
                        JOptionPane.showMessageDialog(this, "Önce veritabanına bağlanın.");
                }
                
                System.out.println("2   :" + (System.currentTimeMillis() - bas));
        }
        
        @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jToolBar3 = new javax.swing.JToolBar();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        genelRaporPanel = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jSpinner3 = new javax.swing.JSpinner();
        jSpinner4 = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jProgressBar2 = new javax.swing.JProgressBar();
        jToolBar2 = new javax.swing.JToolBar();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        gunlukRaporPanel = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jSpinner5 = new javax.swing.JSpinner();
        jSpinner6 = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton12 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jProgressBar1 = new javax.swing.JProgressBar();
        jToolBar4 = new javax.swing.JToolBar();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();

        jPanel1.setLayout(new java.awt.BorderLayout());

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable3.setRowHeight(Setting.getSettings().getTableRowHeight());
        jScrollPane3.setViewportView(jTable3);

        jPanel1.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jToolBar3.setFloatable(false);
        jToolBar3.setRollover(true);

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yakut/resource/printer.png"))); // NOI18N
        jButton9.setFocusable(false);
        jButton9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton9.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jToolBar3.add(jButton9);

        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yakut/resource/openofficeorg.png"))); // NOI18N
        jButton10.setFocusable(false);
        jButton10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton10.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jToolBar3.add(jButton10);

        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yakut/resource/gtk-media-play-rtl.png"))); // NOI18N
        jButton11.setFocusable(false);
        jButton11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton11.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        jToolBar3.add(jButton11);

        jPanel1.add(jToolBar3, java.awt.BorderLayout.PAGE_START);

        jPanel7.setBackground(Setting.getSettings().getSkinBackground2Color());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.setRowHeight(Setting.getSettings().getTableRowHeight());
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("A");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addComponent(jTextField1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel7, java.awt.BorderLayout.LINE_START);

        setLayout(new java.awt.BorderLayout());

        genelRaporPanel.setLayout(new java.awt.BorderLayout());

        jPanel6.setBackground(Setting.getSettings().getSkinBackground2Color());

        jSpinner3.setModel(new javax.swing.SpinnerDateModel());
        jSpinner3.setEditor(new javax.swing.JSpinner.DateEditor(jSpinner3, "dd.MM.yyyy HH:mm:ss"));
        jSpinner3.getEditor().setBackground(Setting.getSettings().getSkinBackground2Color());

        jSpinner4.setModel(new javax.swing.SpinnerDateModel());
        jSpinner4.setEditor(new javax.swing.JSpinner.DateEditor(jSpinner4, "dd.MM.yyyy HH:mm:ss"));
        jSpinner4.getEditor().setBackground(Setting.getSettings().getSkinBackground2Color());

        jLabel3.setForeground(Setting.getSettings().getSkinForeground2Color());
        jLabel3.setText("İlk Tarih");

        jLabel4.setForeground(Setting.getSettings().getSkinForeground2Color());
        jLabel4.setText("Son Tarih");

        jButton5.setBackground(new java.awt.Color(204, 0, 51));
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yakut/resource/ara.png"))); // NOI18N
        jButton5.setText("Listele");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel8.setText("info");

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Ürün Kodu Başlangıç");

        jTextField2.setFont(new java.awt.Font("sansserif", 0, 24)); // NOI18N
        jTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField2.setText("00000");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Ürün Kodu Bitiş");

        jTextField3.setFont(new java.awt.Font("sansserif", 0, 24)); // NOI18N
        jTextField3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField3.setText("99999");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSpinner3)
                    .addComponent(jSpinner4)))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField3, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(17, 17, 17))
            .addComponent(jSeparator1)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jProgressBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jSpinner3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jSpinner4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(jProgressBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        genelRaporPanel.add(jPanel6, java.awt.BorderLayout.LINE_START);

        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yakut/resource/printer.png"))); // NOI18N
        jButton6.setFocusable(false);
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton6);

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yakut/resource/openofficeorg.png"))); // NOI18N
        jButton7.setFocusable(false);
        jButton7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton7);

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yakut/resource/gtk-media-play-rtl.png"))); // NOI18N
        jButton8.setFocusable(false);
        jButton8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton8.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton8);

        genelRaporPanel.add(jToolBar2, java.awt.BorderLayout.PAGE_START);

        jTable2.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable2.setRowHeight(Setting.getSettings().getTableRowHeight());
        jScrollPane2.setViewportView(jTable2);

        genelRaporPanel.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab(" Hareket Raporu ", genelRaporPanel);

        gunlukRaporPanel.setLayout(new java.awt.BorderLayout());

        jPanel8.setBackground(Setting.getSettings().getSkinBackground2Color());

        jSpinner5.setModel(new javax.swing.SpinnerDateModel());
        jSpinner5.setEditor(new javax.swing.JSpinner.DateEditor(jSpinner5, "dd.MM.yyyy HH:mm:ss"));
        jSpinner3.getEditor().setBackground(Setting.getSettings().getSkinBackground2Color());

        jSpinner6.setModel(new javax.swing.SpinnerDateModel());
        jSpinner6.setEditor(new javax.swing.JSpinner.DateEditor(jSpinner6, "dd.MM.yyyy HH:mm:ss"));
        jSpinner4.getEditor().setBackground(Setting.getSettings().getSkinBackground2Color());

        jLabel5.setForeground(Setting.getSettings().getSkinForeground2Color());
        jLabel5.setText("İlk Tarih");

        jLabel6.setForeground(Setting.getSettings().getSkinForeground2Color());
        jLabel6.setText("Son Tarih");

        jButton12.setBackground(new java.awt.Color(204, 0, 51));
        jButton12.setForeground(new java.awt.Color(255, 255, 255));
        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yakut/resource/ara.png"))); // NOI18N
        jButton12.setText("Listele");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jLabel9.setText("info");

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Ürün Kodu Başlangıç");

        jTextField4.setFont(new java.awt.Font("sansserif", 0, 24)); // NOI18N
        jTextField4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField4.setText("00000");

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Ürün Kodu Bitiş");

        jTextField5.setFont(new java.awt.Font("sansserif", 0, 24)); // NOI18N
        jTextField5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField5.setText("99999");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField5, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton12, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(17, 17, 17))
            .addComponent(jSeparator2)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSpinner5)
                    .addComponent(jSpinner6)))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jSpinner5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jSpinner6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9))
        );

        gunlukRaporPanel.add(jPanel8, java.awt.BorderLayout.LINE_START);

        jToolBar4.setFloatable(false);
        jToolBar4.setRollover(true);

        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yakut/resource/printer.png"))); // NOI18N
        jButton13.setFocusable(false);
        jButton13.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton13.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        jToolBar4.add(jButton13);

        jButton14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yakut/resource/openofficeorg.png"))); // NOI18N
        jButton14.setFocusable(false);
        jButton14.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton14.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        jToolBar4.add(jButton14);

        jButton15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yakut/resource/gtk-media-play-rtl.png"))); // NOI18N
        jButton15.setFocusable(false);
        jButton15.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton15.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        jToolBar4.add(jButton15);

        gunlukRaporPanel.add(jToolBar4, java.awt.BorderLayout.PAGE_START);

        jTable4.setAutoCreateRowSorter(true);
        jTable4.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable4.setRowHeight(Setting.getSettings().getTableRowHeight());
        jScrollPane4.setViewportView(jTable4);

        gunlukRaporPanel.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab(" Günlük Üretim Raporu ", gunlukRaporPanel);

        add(jTabbedPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

          private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
                  jPanel6.setVisible(!jPanel6.isVisible());
          }//GEN-LAST:event_jButton8ActionPerformed

          private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
                  getExcel().exceleAktar(jTable2);
          }//GEN-LAST:event_jButton7ActionPerformed

          private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
                  try {
                          jTable2.print();
                  } catch (PrinterException ex) {
                          Logger.getLogger(RaporPanel.class.getName()).log(Level.SEVERE, null, ex);
                  }
          }//GEN-LAST:event_jButton6ActionPerformed

          private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
                  new Thread() {
                          
                          @Override
                          public void run() {
                                  jLabel8.setText("Lütfen Bekleyin");
                                  jButton5.setEnabled(false);
                                  jProgressBar2.setVisible(true);
                                  jProgressBar2.setIndeterminate(true);
                                  try {
                                          bizerbaRaporuHazirla();
                                  } catch (Exception ex) {
                                  }
                                  jButton5.setEnabled(true);
                                  jProgressBar2.setVisible(false);
                                  
                          }
                  }.start();

          }//GEN-LAST:event_jButton5ActionPerformed

          private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
                  try {
                          jTable3.print();
                  } catch (PrinterException ex) {
                          Logger.getLogger(RaporPanel.class.getName()).log(Level.SEVERE, null, ex);
                  }
          }//GEN-LAST:event_jButton9ActionPerformed

          private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
                  getExcel().exceleAktar(jTable3);
          }//GEN-LAST:event_jButton10ActionPerformed

          private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
                  jPanel7.setVisible(!jPanel7.isVisible());
          }//GEN-LAST:event_jButton11ActionPerformed

          private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
          }//GEN-LAST:event_jTable1MouseClicked

          private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
          }//GEN-LAST:event_jTable1KeyPressed

          private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
          }//GEN-LAST:event_jButton1ActionPerformed

          private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
          }//GEN-LAST:event_jTextField1KeyPressed

          private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
                  if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                  }
          }//GEN-LAST:event_jTextField1KeyReleased

        private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
                
                new Thread() {
                        
                        @Override
                        public void run() {
                                jLabel9.setText("Lütfen Bekleyin");
                                jButton12.setEnabled(false);
                                jProgressBar1.setVisible(true);
                                jProgressBar1.setIndeterminate(true);
                                try {
                                        //    sql();//  

                                        getGunlukOzetRapor();
                                } catch (Exception ex) {
                                }
                                jButton12.setEnabled(true);
                                jProgressBar1.setVisible(false);
                                
                        }
                }.start();

        }//GEN-LAST:event_jButton12ActionPerformed

        private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
                getExcel().exceleAktar(jTable4);
        }//GEN-LAST:event_jButton13ActionPerformed

        private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
                
                try {
                        jTable4.print();
                } catch (PrinterException ex) {
                        Logger.getLogger(RaporPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
        }//GEN-LAST:event_jButton14ActionPerformed

        private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
                jPanel8.setVisible(!jPanel8.isVisible());
        }//GEN-LAST:event_jButton15ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JPanel genelRaporPanel;
    private javax.swing.JPanel gunlukRaporPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JProgressBar jProgressBar2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSpinner jSpinner3;
    private javax.swing.JSpinner jSpinner4;
    private javax.swing.JSpinner jSpinner5;
    private javax.swing.JSpinner jSpinner6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JToolBar jToolBar4;
    // End of variables declaration//GEN-END:variables
}
