/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yakut.azone.util;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author yakut
 */
public class JPAUtil {

    private static EntityManagerFactory fak;//=Persistence.createEntityManagerFactory("OtobusPU");

    public static EntityManagerFactory getEntityManagerFactory() {
        if (fak == null) {

            Map properties = new HashMap();
            String dataBasePath = Setting.getSettings().getProperty("database.path", "c:\\data\\Daemstudio\\data.fdb");
            System.out.println("database Path:" + dataBasePath);
            String host = Setting.getSettings().getProperty("database.server.ip", "localhost");
            System.out.println("host:" + host);
            String port = Setting.getSettings().getProperty("database.server.port", "3050");
            System.out.println("port:" + port);
            properties.put("hibernate.connection.username", "SYSDBA");
            properties.put("hibernate.connection.password", "masterkey");
            properties.put("hibernate.connection.url", "jdbc:firebirdsql:" + host + "/" + port + ":" + dataBasePath + "?lc_ctype=WIN1254");

            try {
                fak = Persistence.createEntityManagerFactory("aZonePU", properties);
            } catch (Exception ex) {
                ex.printStackTrace();
                fak = null;
            }

        }

        return fak;
    }

    public static void reloadAgain() {
        fak = null;
    }

}
