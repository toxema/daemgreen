package com.yakut.azone.util;

import java.awt.Color;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author yakut
 */
public class Setting {

    Properties prop;
    static Setting setting;
    File f;
    List<SettingListener> listeners = new ArrayList<SettingListener>();
    String deger;

    private Setting() {
        prop = new SortedProperties();
        f = new File("config.cfg");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            prop.load(new FileReader(f));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Hata\r\n" + ex.getMessage());

        }
    }
    final static Object lock = new Object();

    public static Setting getSettings() {
        synchronized (lock) {
            if (setting == null) {
                setting = new Setting();
            }
        }
        return setting;
    }

    public Color getSkinBackgroundColor() {
        Color backgroudColor;
        int colorValue = Integer.parseInt(Setting.getSettings().getProperty("theme.background.color", new Color(51, 51, 51).getRGB() + ""));
        backgroudColor = new Color(colorValue);
        return backgroudColor;
    }

    public Color getSkinBackground2Color() {
        Color backgroudColor;
        int colorValue = Integer.parseInt(Setting.getSettings().getProperty("theme.background2.color", new Color(51, 51, 51).getRGB() + ""));
        backgroudColor = new Color(colorValue);
        return backgroudColor;
    }

    public Color getSkinForegroundColor() {
        Color backgroudColor;
        int colorValue = Integer.parseInt(Setting.getSettings().getProperty("theme.foreground.color", new Color(51, 51, 51).getRGB() + ""));
        backgroudColor = new Color(colorValue);
        return backgroudColor;
    }

    public Color getSkinForeground2Color() {
        Color backgroudColor;
        int colorValue = Integer.parseInt(Setting.getSettings().getProperty("theme.foreground2.color", new Color(51, 51, 51).getRGB() + ""));
        backgroudColor = new Color(colorValue);
        return backgroudColor;
    }

    public String getProperty(String anahtar, String varsayilanDeger) {
        deger = prop.getProperty(anahtar);
        if (deger == null || deger.isEmpty()) {
            deger = varsayilanDeger;
            prop.setProperty(anahtar, deger);
        }
        return deger;
    }

    public void setProperty(String anahtar, String deger) {
        prop.setProperty(anahtar, deger);
    }

    @Deprecated
    public Properties getProperties() {
        return prop;
    }

    public void saveProperties() {
        try {
            prop.store(new FileWriter(f), "www.eccsistem.com");
        } catch (IOException ex) {
        }
    }

    public void addListener(SettingListener listener) {
        listeners.add(listener);
    }

    public void removeListener(SettingListener listener) {
        listeners.remove(listener);
    }

    public void notifySettingChanged() {
        for (SettingListener l : listeners) {
            l.notifySettingChanged();
        }
    }

    public int getTableRowHeight() {
        int height = 16;
        try {
            height = Integer.parseInt(Setting.getSettings().getProperty("default.table.row.height", "16"));
        } catch (Exception e) {
            Setting.getSettings().setProperty("default.table.row.height", "16");
            height = 16;
        }
        return height;
    }

    public void tableYuksekligiAl(JTable table, String name) {
        System.out.println(name);
        String colums[] = Setting.getSettings().getProperty(name, "0 0 0 0 0 0 0 0 0").trim().split(" ");
        int count = table.getColumnModel().getColumnCount();
        int son = count > colums.length ? colums.length : count;
        for (int k = 0; k < son; k++) {
            try {
                table.getColumnModel().getColumn(k).setPreferredWidth(Integer.parseInt(colums[k]));
            } catch (Exception e) {
            }
        }
    }

    public void tableYuksekligiKaydet(JTable table, String name) {
        System.out.println(name);
        String colums = "";
        int count = table.getColumnModel().getColumnCount();

        for (int k = 0; k < count; k++) {
            colums += table.getColumnModel().getColumn(k).getPreferredWidth() + " ";
        }
        Setting.getSettings().setProperty(name, colums.trim());

    }

}
