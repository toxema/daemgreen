package com.yakut.azone.lisans;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author yakut
 */
public class Lisans implements Serializable {

    private Date ilkTarih;
    private Date sonTarih;
    private String hdd;

    public Lisans(Date sonTarih, String hdd) {
        this.ilkTarih = new Date();
        this.sonTarih = sonTarih;
        this.hdd = hdd;

    }

    public Lisans() {
    }

    public String getHdd() {
        return hdd;
    }

    public void setHdd(String hdd) {
        this.hdd = hdd;
    }

    public Date getIlkTarih() {
        return ilkTarih;
    }

    public void setIlkTarih(Date ilkTarih) {
        this.ilkTarih = ilkTarih;
    }

    public Date getSonTarih() {
        return sonTarih;
    }

    public void setSonTarih(Date sonTarih) {
        this.sonTarih = sonTarih;
    }

    @Override
    public String toString() {
        return getHdd();
    }
}
