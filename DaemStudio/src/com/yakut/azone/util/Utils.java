/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yakut.azone.util;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author yakut
 */
public class Utils {

    public static void stopLogging() {
        Logger.getRootLogger().setLevel(Level.OFF);
    }

    public static void startLogging() {
        startLogging(Level.DEBUG);
    }

    public static void startLogging(Level logLevel) {
        Logger.getRootLogger().setLevel(logLevel);
    }

    public static void startNimbus() {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException ex) {
            System.out.println("Nimbus yüklenemedi.");
        }
    }

    public static void sleep(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException ex) {
         }
    }
}
