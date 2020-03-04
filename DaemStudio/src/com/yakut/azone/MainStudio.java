package com.yakut.azone;

import com.yakut.azone.beans.User;
import com.yakut.azone.controller.UserController;
import com.yakut.azone.controller.UserManager;
import com.yakut.azone.gui.*;

import com.yakut.azone.util.Setting;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.TimeZone;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author yakut
 */
public class MainStudio extends javax.swing.JFrame {

        JButton[] yetkiList;
        Logger logger = Logger.getLogger(MainStudio.class);

        public MainStudio() {
                initComponents();
        }

        public void init() {
                String level = Setting.getSettings().getProperty("system.log.level", "info").toLowerCase();
                TimeZone.setDefault(TimeZone.getTimeZone(Setting.getSettings().getProperty("timezone", "Turkey")));
                if (level.equals("error")) {
                        Logger.getRootLogger().setLevel(Level.ERROR);
                } else if (level.equals("info")) {
                        Logger.getRootLogger().setLevel(Level.INFO);
                } else if (level.equals("off")) {
                        Logger.getRootLogger().setLevel(Level.OFF);
                } else if (level.equals("debug")) {
                        Logger.getRootLogger().setLevel(Level.DEBUG);
                }
                programIconuAyarla();
                ayarlariYukle();

                yetkiList = new JButton[]{
                        y1, y4, y5, y6
                };

        }

        private void programIconuAyarla() {
                try {
                        BufferedImage image = ImageIO.read(this.getClass().getResource("/com/yakut/resource/frameIcon.png"));
                        this.setIconImage(image);
                } catch (Exception ex) {
                }
        }

        public void otomatikAc() {
                List<User> list = getUserController().getUserList();
                boolean bulundu = false;

                for (User u : list) {
                        if (u.getHaklar().charAt(0) == '1') {
                                logger.warn("sistem " + u + " kullanıcısı tarafından otomatik olarak açılacak");
                                yetkiyeGoreGoster(u);
                                panelGoster(getCanliPanel());
                                bulundu = true;
                                UserManager.setUser(u);
                        }
                }

                if (!bulundu) {
                        reLogin();
                }

                hicKullaniciYoksaYeniKullaniciOlustur(list);
        }

        private void hicKullaniciYoksaYeniKullaniciOlustur(List<User> list) {
                if (list == null || list.isEmpty()) {
                        User u = new User();
                        u.setName("01");
                        u.setPassword("01");
                        u.setHaklar("1111111111111111111");
                        getUserController().persist(u);
                        jLabel5.setText("Kullanıcı adı:01 parola:01");
                        reLogin();
                }
        }

        public void ayarlariYukle() {
                int x = 0, w = 300, h = 300, y = 0;
                try {
                        x = Integer.parseInt(Setting.getSettings().getProperty("main.frame.x", "0"));
                        y = Integer.parseInt(Setting.getSettings().getProperty("main.frame.y", "0"));
                        w = Integer.parseInt(Setting.getSettings().getProperty("main.frame.w", "400"));
                        h = Integer.parseInt(Setting.getSettings().getProperty("main.frame.h", "400"));
                        this.setBounds(x, y, w, h);
                } catch (Exception e) {
                        x = 0;
                        w = 300;
                        h = 300;
                        y = 0;
                }
        }

        public void programiKapat() {
                if (JOptionPane.showConfirmDialog(this, "Programı Kapatmak istediğinizen emin misiniz?", "Dikkat", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        programiKapatOnaysiz();
                }
        }

        public void programiKapatOnaysiz() {
                programAyarlariniKaydet();
                portlariKapat();
                System.exit(0);
        }

        public void portlariKapat() {

        }

        public void programAyarlariniKaydet() {
                Setting.getSettings().setProperty("main.frame.x", "" + this.getX());
                Setting.getSettings().setProperty("main.frame.y", "" + this.getY());
                Setting.getSettings().setProperty("main.frame.w", "" + this.getWidth());
                Setting.getSettings().setProperty("main.frame.h", "" + this.getHeight());
                Setting.getSettings().saveProperties();
        }
        private JPanel activePanel = null;
        UserController userController;

        public UserController getUserController() {
                if (userController == null) {
                        userController = new UserController();
                }
                return userController;
        }

        public void panelGoster(JPanel panel) {
                if (activePanel != panel) {
                        activePanel = panel;
                        mainPanel.removeAll();
                        mainPanel.add(activePanel);
                        mainPanel.updateUI();
                }
        }
        KapiPanel kapiPanel;

        public KapiPanel getKapiPanel() {
                if (kapiPanel == null) {
                        kapiPanel = new KapiPanel();
                        kapiPanel.init();
                } else {
                        kapiPanel.refresh();
                }
                return kapiPanel;
        }
        AyarPanel ayarPanel;

        public AyarPanel getAyarPanel() {
                if (ayarPanel == null) {
                        ayarPanel = new AyarPanel();
                        ayarPanel.init();
                } else {
                        ayarPanel.refresh();
                }
                return ayarPanel;

        }
        PersonelPanel personelPanel;

        public PersonelPanel getPersonelPanel() {
                if (personelPanel == null) {
                        personelPanel = new PersonelPanel();
                        personelPanel.init();
                }
                personelPanel.refresh();

                return personelPanel;

        }
        //          --------------------- PARK YASAM -------------------------------------------------------------------      
        MainPanel canliPanel;

        public MainPanel getCanliPanel() {
                if (canliPanel == null) {
                        canliPanel = new MainPanel();
                        canliPanel.init();
                }
                return canliPanel;
        }

        UserPanel userPanel;

        public UserPanel getUserPanel() {
                if (userPanel == null) {
                        userPanel = new UserPanel();
                        userPanel.init();
                } else {
                        userPanel.refresh();
                }
                return userPanel;
        }
        RaporPanel raporPanel;

        public RaporPanel getRaporPanel() {
                if (raporPanel == null) {
                        raporPanel = new RaporPanel();
                        raporPanel.init();
                } else {
                        raporPanel.refresh();
                }
                return raporPanel;

        }
        Hakkinda hakkindaPanel;

        public Hakkinda getHakkindaPanel() {
                if (hakkindaPanel == null) {
                        hakkindaPanel = new Hakkinda();
                }
                return hakkindaPanel;
        }
        BolgePanel bolgePanel = null;

        public BolgePanel getBolgePanel() {
                if (bolgePanel == null) {
                        bolgePanel = new BolgePanel();
                        bolgePanel.init();
                } else {
                        bolgePanel.refresh();
                }
                return bolgePanel;
        }
        GrupPanel grupPanel = null;

        public GrupPanel getGrupPanel() {
                if (grupPanel == null) {
                        grupPanel = new GrupPanel();
                        grupPanel.init();
                } else {
                        grupPanel.refresh();
                }
                return grupPanel;
        }
        GrupHakPanel grupHakPanel = null;

        public GrupHakPanel getGrupHakPanel() {
                if (grupHakPanel == null) {
                        grupHakPanel = new GrupHakPanel();
                        grupHakPanel.init();
                } else {
                        grupHakPanel.refresh();
                }
                return grupHakPanel;
        }
        TerminalPanel terminalPanel;

        public TerminalPanel getTerminalPanel() {
                if (terminalPanel == null) {
                        terminalPanel = new TerminalPanel();
                        terminalPanel.init();
                }
                return terminalPanel;
        }

        User user = null;

        public void yetkiyeGoreGoster(User user) {
                logger.warn("Sistem " + user + " tarafından açıldı");
                this.user = user;
                int son = yetkiList.length < user.getHaklar().length() - 1 ? yetkiList.length : user.getHaklar().length() - 1;
                for (int k = 0; k < son; k++) {
                        if (user.getHaklar().charAt(k + 1) == '1') {
                                yetkiList[k].setEnabled(true);
                        } else {
                                yetkiList[k].setEnabled(false);
                        }
                }
                userNameLabel.setText(" User:" + user.getName());
                mainPanel.removeAll();
                mainPanel.updateUI();
        }

        public void yetkileriKapat() {
                for (int k = 0; k < yetkiList.length; k++) {
                        yetkiList[k].setEnabled(false);
                }
        }

        public JPanel getKilitPanel() {
                jLabel2.setText(user.getName());
                jPasswordField1.setText("");
                jLabel3.setText("Şifrenizi tekrar giriniz.");
                return kilitPanel;
        }

        public JPanel getLoginPanel() {
                jTextField1.setText("");
                jPasswordField2.setText("");
                jLabel5.setText("Kullanıcı adınızı ve şifrenizi giriniz.");
                return loginPanel;
        }

        public void reLogin() {
                yetkileriKapat();
                panelGoster(getLoginPanel());
        }

        public void kilitle() {
                if (user != null) {
                        yetkileriKapat();
                        panelGoster(getKilitPanel());
                }
        }

        private void girisYap() {
                User u = getUserController().getUser(jTextField1.getText());
                if (u != null) {
                        String pass = new String(jPasswordField2.getPassword());
//                        JOptionPane.showMessageDialog(null, pass);
//                        JOptionPane.showMessageDialog(null, u.getPassword());
                        if (u.getPassword().equals(pass)) {
                                yetkiyeGoreGoster(u);
                                UserManager.setUser(u);
                        } else {
                                jPasswordField2.setText("");
                                jLabel5.setText("Parolanız adınız yanlış.");
                                jPasswordField2.requestFocus();
                        }
                } else {
                        jTextField1.setText("");
                        jPasswordField2.setText("");
                        jLabel5.setText("Kullanıcı adınız yanlış.");
                        jTextField1.requestFocus();
                }
        }

        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents() {
                java.awt.GridBagConstraints gridBagConstraints;

                loginPanel = new javax.swing.JPanel();
                jPanel1 = new javax.swing.JPanel();
                jTextField1 = new javax.swing.JTextField();
                jLabel6 = new javax.swing.JLabel();
                jLabel4 = new javax.swing.JLabel();
                jPasswordField2 = new javax.swing.JPasswordField();
                jButton2 = new javax.swing.JButton();
                jLabel1 = new javax.swing.JLabel();
                jLabel5 = new javax.swing.JLabel();
                filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
                jLabel8 = new javax.swing.JLabel();
                filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
                kilitPanel = new javax.swing.JPanel();
                jLabel2 = new javax.swing.JLabel();
                jButton1 = new javax.swing.JButton();
                jPasswordField1 = new javax.swing.JPasswordField();
                jLabel3 = new javax.swing.JLabel();
                jLabel9 = new javax.swing.JLabel();
                jLabel10 = new javax.swing.JLabel();
                toolBarPanel = new javax.swing.JPanel();
                mainToolBar = new javax.swing.JToolBar();
                exitButton = new javax.swing.JButton();
                jSeparator1 = new javax.swing.JToolBar.Separator();
                jButton7 = new javax.swing.JButton();
                jButton8 = new javax.swing.JButton();
                jSeparator2 = new javax.swing.JToolBar.Separator();
                y1 = new javax.swing.JButton();
                y4 = new javax.swing.JButton();
                y5 = new javax.swing.JButton();
                y6 = new javax.swing.JButton();
                jButton4 = new javax.swing.JButton();
                filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
                jLabel7 = new javax.swing.JLabel();
                mainPanel = new javax.swing.JPanel();
                footerPanel = new javax.swing.JPanel();
                companyLabel = new javax.swing.JLabel();
                userNameLabel = new javax.swing.JLabel();

                loginPanel.setBackground(Setting.getSettings().getSkinBackgroundColor());
                loginPanel.setLayout(new java.awt.GridBagLayout());

                jPanel1.setBackground(Setting.getSettings().getSkinBackgroundColor());

                jTextField1.setColumns(15);
                jTextField1.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
                jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jTextField1.setBorder(null);
                jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
                        public void keyReleased(java.awt.event.KeyEvent evt) {
                                jTextField1KeyReleased(evt);
                        }
                });

                jLabel6.setForeground(new java.awt.Color(255, 255, 255));
                jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yakut/resource/cryptkeeper.png"))); // NOI18N
                jLabel6.setText("Login");

                jLabel4.setForeground(new java.awt.Color(255, 255, 255));
                jLabel4.setText("Parola");

                jPasswordField2.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
                jPasswordField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jPasswordField2.setBorder(null);
                jPasswordField2.addKeyListener(new java.awt.event.KeyAdapter() {
                        public void keyReleased(java.awt.event.KeyEvent evt) {
                                jPasswordField2KeyReleased(evt);
                        }
                });

                jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yakut/resource/exaile.png"))); // NOI18N
                jButton2.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton2ActionPerformed(evt);
                        }
                });

                jLabel1.setForeground(new java.awt.Color(255, 255, 255));
                jLabel1.setText("Kullanıcı");

                jLabel5.setForeground(new java.awt.Color(255, 255, 255));
                jLabel5.setText("Kullanıcı adınızı ve şifrenizi giriniz.");

                javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
                jPanel1.setLayout(jPanel1Layout);
                jPanel1Layout.setHorizontalGroup(
                        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(106, 106, 106)
                                                .addComponent(jLabel6))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(46, 46, 46)
                                                .addComponent(jLabel5))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(jLabel1)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(jLabel4)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
                jPanel1Layout.setVerticalGroup(
                        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel1))
                                                .addGap(10, 10, 10)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel4)))
                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );

                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 2;
                gridBagConstraints.gridwidth = 2;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
                loginPanel.add(jPanel1, gridBagConstraints);
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 2;
                gridBagConstraints.gridwidth = 2;
                gridBagConstraints.gridheight = 5;
                loginPanel.add(filler2, gridBagConstraints);

                jLabel8.setForeground(new java.awt.Color(255, 255, 255));
                jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yakut/resource/ecc-w48px.png"))); // NOI18N
                jLabel8.setText("www.eccsistem.com");
                jLabel8.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
                jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                jLabel8.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 1;
                gridBagConstraints.gridy = 3;
                loginPanel.add(jLabel8, gridBagConstraints);
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 1;
                gridBagConstraints.gridy = 1;
                gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                loginPanel.add(filler3, gridBagConstraints);

                kilitPanel.setBackground(Setting.getSettings().getSkinBackgroundColor());
                kilitPanel.setLayout(new java.awt.GridBagLayout());

                jLabel2.setForeground(new java.awt.Color(255, 255, 255));
                jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yakut/resource/cryptkeeper.png"))); // NOI18N
                jLabel2.setText("#{kullanıcı}");
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 0;
                kilitPanel.add(jLabel2, gridBagConstraints);

                jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yakut/resource/exaile.png"))); // NOI18N
                jButton1.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton1ActionPerformed(evt);
                        }
                });
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 1;
                gridBagConstraints.gridy = 1;
                kilitPanel.add(jButton1, gridBagConstraints);

                jPasswordField1.setColumns(15);
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 1;
                kilitPanel.add(jPasswordField1, gridBagConstraints);

                jLabel3.setForeground(new java.awt.Color(255, 255, 255));
                jLabel3.setText("Şifrenizi tekrar giriniz.");
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 2;
                gridBagConstraints.gridwidth = 2;
                kilitPanel.add(jLabel3, gridBagConstraints);

                jLabel9.setForeground(new java.awt.Color(255, 255, 255));
                jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yakut/resource/ecc-w48px.png"))); // NOI18N
                jLabel9.setText("www.eccsistem.com");
                jLabel9.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
                jLabel9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                jLabel9.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 4;
                gridBagConstraints.gridwidth = 2;
                kilitPanel.add(jLabel9, gridBagConstraints);

                jLabel10.setText("       ");
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 3;
                gridBagConstraints.gridwidth = 2;
                kilitPanel.add(jLabel10, gridBagConstraints);

                setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
                setTitle("Bizerba Studio - ecc sistem ");
                addWindowListener(new java.awt.event.WindowAdapter() {
                        public void windowClosing(java.awt.event.WindowEvent evt) {
                                onClose(evt);
                        }
                });

                toolBarPanel.setBackground(Setting.getSettings().getSkinBackgroundColor());
                toolBarPanel.setLayout(new java.awt.BorderLayout());

                mainToolBar.setFloatable(false);
                mainToolBar.setRollover(true);

                exitButton.setForeground(new java.awt.Color(255, 255, 255));
                exitButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yakut/resource/gnome-session-halt.png"))); // NOI18N
                exitButton.setText("Kapat");
                exitButton.setToolTipText("Çıkış");
                exitButton.setFocusable(false);
                exitButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                exitButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                exitButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                exitButtonActionPerformed(evt);
                        }
                });
                mainToolBar.add(exitButton);

                jSeparator1.setForeground(new java.awt.Color(255, 255, 255));
                mainToolBar.add(jSeparator1);

                jButton7.setForeground(new java.awt.Color(255, 255, 255));
                jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yakut/resource/cryptkeeper.png"))); // NOI18N
                jButton7.setText("Kilitle");
                jButton7.setToolTipText("Lock Session");
                jButton7.setFocusable(false);
                jButton7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                jButton7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                jButton7.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton7ActionPerformed(evt);
                        }
                });
                mainToolBar.add(jButton7);

                jButton8.setForeground(new java.awt.Color(255, 255, 255));
                jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yakut/resource/podcast-new.png"))); // NOI18N
                jButton8.setText("Login");
                jButton8.setToolTipText("ReLogin");
                jButton8.setFocusable(false);
                jButton8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                jButton8.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                jButton8.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton8ActionPerformed(evt);
                        }
                });
                mainToolBar.add(jButton8);

                jSeparator2.setForeground(new java.awt.Color(255, 255, 255));
                mainToolBar.add(jSeparator2);

                y1.setForeground(new java.awt.Color(255, 255, 255));
                y1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yakut/resource/exaile.png"))); // NOI18N
                y1.setText("Bizerba");
                y1.setToolTipText("Giriş - Çıkış");
                y1.setEnabled(false);
                y1.setFocusable(false);
                y1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                y1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                y1.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                y1ActionPerformed(evt);
                        }
                });
                mainToolBar.add(y1);

                y4.setForeground(new java.awt.Color(255, 255, 255));
                y4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yakut/resource/User.png"))); // NOI18N
                y4.setText(" User ");
                y4.setToolTipText("User");
                y4.setEnabled(false);
                y4.setFocusable(false);
                y4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                y4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                y4.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                y4ActionPerformed(evt);
                        }
                });
                mainToolBar.add(y4);

                y5.setForeground(new java.awt.Color(255, 255, 255));
                y5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yakut/resource/openofficeorg.png"))); // NOI18N
                y5.setText("Rapor");
                y5.setToolTipText("Rapor");
                y5.setEnabled(false);
                y5.setFocusable(false);
                y5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                y5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                y5.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                y5ActionPerformed(evt);
                        }
                });
                mainToolBar.add(y5);

                y6.setForeground(new java.awt.Color(255, 255, 255));
                y6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yakut/resource/ayar.png"))); // NOI18N
                y6.setText("  Ayar  ");
                y6.setToolTipText("Ayar");
                y6.setEnabled(false);
                y6.setFocusable(false);
                y6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                y6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                y6.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                y6ActionPerformed(evt);
                        }
                });
                mainToolBar.add(y6);

                jButton4.setForeground(new java.awt.Color(255, 255, 255));
                jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yakut/resource/ibus.png"))); // NOI18N
                jButton4.setText("Hakkında");
                jButton4.setToolTipText("hakkında");
                jButton4.setFocusable(false);
                jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                jButton4.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton4ActionPerformed(evt);
                        }
                });
                mainToolBar.add(jButton4);
                mainToolBar.add(filler1);

                jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yakut/resource/ecc-w48px.png"))); // NOI18N
                jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
                mainToolBar.add(jLabel7);

                toolBarPanel.add(mainToolBar, java.awt.BorderLayout.CENTER);

                getContentPane().add(toolBarPanel, java.awt.BorderLayout.PAGE_START);

                mainPanel.setBackground(new java.awt.Color(255, 255, 255));
                mainPanel.setForeground(new java.awt.Color(255, 255, 255));
                mainPanel.setLayout(new java.awt.BorderLayout());
                getContentPane().add(mainPanel, java.awt.BorderLayout.CENTER);

                footerPanel.setBackground(Setting.getSettings().getSkinBackgroundColor());
                footerPanel.setForeground(new java.awt.Color(255, 255, 255));
                footerPanel.setLayout(new java.awt.BorderLayout());

                companyLabel.setForeground(new java.awt.Color(255, 255, 255));
                companyLabel.setText("www.eccsistem.com ");
                companyLabel.setVerifyInputWhenFocusTarget(false);
                footerPanel.add(companyLabel, java.awt.BorderLayout.LINE_END);

                userNameLabel.setForeground(new java.awt.Color(255, 255, 255));
                userNameLabel.setText("#{username}");
                footerPanel.add(userNameLabel, java.awt.BorderLayout.LINE_START);

                getContentPane().add(footerPanel, java.awt.BorderLayout.PAGE_END);

                pack();
        }// </editor-fold>//GEN-END:initComponents

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
            programiKapat();
    }//GEN-LAST:event_exitButtonActionPerformed

    private void onClose(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_onClose
            programiKapat();
    }//GEN-LAST:event_onClose

    private void y6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_y6ActionPerformed
            panelGoster(getAyarPanel());
    }//GEN-LAST:event_y6ActionPerformed

          private void y1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_y1ActionPerformed
                  panelGoster(getCanliPanel());
          }//GEN-LAST:event_y1ActionPerformed

          private void y4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_y4ActionPerformed
                  panelGoster(getUserPanel());
          }//GEN-LAST:event_y4ActionPerformed

          private void y5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_y5ActionPerformed
                  panelGoster(getRaporPanel());
          }//GEN-LAST:event_y5ActionPerformed

          private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
                  panelGoster(getHakkindaPanel());
                  System.out.println(toolBarPanel.getBackground().getRGB());
          }//GEN-LAST:event_jButton4ActionPerformed

        private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
                girisYap();
        }//GEN-LAST:event_jButton2ActionPerformed

        private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
                kilitle();
        }//GEN-LAST:event_jButton7ActionPerformed

        private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
                reLogin();
        }//GEN-LAST:event_jButton8ActionPerformed

        private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
                if (user.getPassword().equals(new String(jPasswordField1.getPassword()))) {
                        yetkiyeGoreGoster(user);

                } else {
                        jLabel3.setText("Şifre Yanlış");
                        jPasswordField1.requestFocus();
                }
        }//GEN-LAST:event_jButton1ActionPerformed

        private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                        jPasswordField2.requestFocus();
                }
        }//GEN-LAST:event_jTextField1KeyReleased

        private void jPasswordField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPasswordField2KeyReleased
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                        girisYap();
                }
        }//GEN-LAST:event_jPasswordField2KeyReleased

        public static void main(String args[]) {
                UIManager.put("JComponent.sizeVariant", "small");

                //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
                try {
                        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                                if ("Nimbus".equals(info.getName())) {
                                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                                        break;
                                }
                        }
                } catch (Exception ex) {
                }
                //</editor-fold>

                final MainStudio u = new MainStudio();
                u.setVisible(true);
                java.awt.EventQueue.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                                u.init();
                                u.otomatikAc();
                        }
                });
        }
        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JLabel companyLabel;
        private javax.swing.JButton exitButton;
        private javax.swing.Box.Filler filler1;
        private javax.swing.Box.Filler filler2;
        private javax.swing.Box.Filler filler3;
        private javax.swing.JPanel footerPanel;
        private javax.swing.JButton jButton1;
        private javax.swing.JButton jButton2;
        private javax.swing.JButton jButton4;
        private javax.swing.JButton jButton7;
        private javax.swing.JButton jButton8;
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
        private javax.swing.JPasswordField jPasswordField1;
        private javax.swing.JPasswordField jPasswordField2;
        private javax.swing.JToolBar.Separator jSeparator1;
        private javax.swing.JToolBar.Separator jSeparator2;
        private javax.swing.JTextField jTextField1;
        private javax.swing.JPanel kilitPanel;
        private javax.swing.JPanel loginPanel;
        private javax.swing.JPanel mainPanel;
        private javax.swing.JToolBar mainToolBar;
        private javax.swing.JPanel toolBarPanel;
        private javax.swing.JLabel userNameLabel;
        private javax.swing.JButton y1;
        private javax.swing.JButton y4;
        private javax.swing.JButton y5;
        private javax.swing.JButton y6;
        // End of variables declaration//GEN-END:variables
}
