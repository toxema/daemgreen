/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yakut.azone.util;

import javax.swing.JOptionPane;
import org.apache.log4j.Logger;

/**
 *
 * @author yakut
 */
public class ExceptionHandler {

    static Logger logger = Logger.getLogger(ExceptionHandler.class);

    public static void handleException(Exception ex, int level) {
        JOptionPane.showMessageDialog(null, "Veritabanına Bağlanılamıyor\r\nBağlantılarınızı Kontrol Edin.", "Bağlantı Hatası", JOptionPane.ERROR_MESSAGE);
        JOptionPane.showMessageDialog(null, "Bağlantı sağlanıncaya  kadar sistem local çalışacak.\r\n Bağlantı kurulunca programı kapatıp açın", "Bilgi", JOptionPane.ERROR_MESSAGE);
        //JPAUtil.localCalis();

    }

    public static void onException(String message, Exception ex) {
        JOptionPane.showMessageDialog(null, message + "\r\n Hata Mesajı:" + ex.getMessage());
        ex.printStackTrace();
    }
}
