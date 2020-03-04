package com.yakut.azone.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;

/**
 *
 * @author yakut
 */
public class Database {

    Logger logger = Logger.getLogger(Database.class);
    String database = "c:\\UzmanPdks\\data\\imbat\\data.fdb";
    String ip = "127.0.0.1";
    Connection connection;
    boolean showSql = false;
    Exception exception;

    public Database() {
        //    this(Setting.getSettings().getProperty("database.path", "c:\\UzmanPdks\\data\\simsek\\data.fdb"), Setting.getSettings().getProperty("database.ip", "127.0.0.1"));
    }

    public Database(String path) {
        this(path, "127.0.0.1");
    }

    public Database(String path, boolean showSql) {
        this(path, "127.0.0.1", showSql);
    }

    public Database(String path, String ip) {
        this(path, ip, false);
    }

    public Database(String path, String ip, boolean showSql) {
        this.database = path;
        this.ip = ip;
        this.showSql = showSql;
    }

    public boolean begin() throws Exception {
        boolean value = false;
        Class.forName("org.firebirdsql.jdbc.FBDriver");
        if (getConnection() != null) {
            value = true;
        }
        return value;
    }

    public Exception getException() {
        return exception;
    }

    // begin() metodunun hata fırlatmayanı
    public boolean connect() {
        boolean value = false;
        try {
            value = begin();
        } catch (Exception ex) {
            exception = ex;
            logger.error(ex);
        }
        return value;
    }

    public void end() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                exception = ex;
                logger.error(ex);
            }
        }
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection("jdbc:firebirdsql:" + ip + "/3050:" + database + "?lc_ctype=WIN1254", "SYSDBA", "masterkey");
            }
        } catch (SQLException ex) {
            connection = null;
            exception = ex;
            logger.error(ex);
        }
        return connection;
    }

    public Statement getSession() {
        Statement s = null;
        try {
            s = getConnection().createStatement();
        } catch (SQLException ex) {
            exception = ex;
            logger.error(ex);
        }
        return s;
    }

    public ResultSet sql(String sql) {
        ResultSet r = null;
        try {
            logger.info("SQL:" + sql);
            r = getSession().executeQuery(sql);
        } catch (SQLException ex) {
            exception = ex;
            logger.error(ex);
            ex.printStackTrace();
        }
        return r;
    }

    public boolean execute(String sql) {
        boolean b = false;
        try {
            logger.info("SQL:" + sql);
            b = getSession().execute(sql);
            b = true;
        } catch (SQLException ex) {
            exception = ex;
            logger.error(ex);
        }

        return b;
    }

    public int getMax(String sql) {
        int max = 0;
        ResultSet r = sql(sql);
        try {
            if (r.next()) {
                max = r.getInt(1);
            }
        } catch (SQLException ex) {
            logger.error(max);
        }

        return max;
    }

    public String t(int i) {
        return t(i, 4);
    }

    public String t(int i, int adet) {
        String s = "0000000000000" + i;
        s = s.substring(s.length() - adet);
        return s;
    }

    public String getDatabase() {
        return database;
    }

    public Database setDatabase(String database) {
        this.database = database;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public Database setIp(String ip) {
        this.ip = ip;
        return this;
    }
}
