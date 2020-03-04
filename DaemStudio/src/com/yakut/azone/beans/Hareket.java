/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yakut.azone.beans;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author yakut
 */
@Entity
public class Hareket implements Serializable {

          @Id
          @GeneratedValue(strategy = GenerationType.SEQUENCE)
          int id;
          @Temporal(TemporalType.TIMESTAMP)
          Date tarih;
          @ManyToOne
          @JoinColumn(name = "PERSONEL_ID")
          Personel personel;
          @ManyToOne
          @JoinColumn(name = "KAPI_ID")
          Kapi kapi;
          char yon = 'X';
          int hareketTuru = HATA;//1 onay 0 hata
          @Column(length = 50)
          String aciklama;
          @Transient
          public static int ONAY = 1;
          @Transient
          public static int HATA = 0;

          public int getId() {
                    return id;
          }

          public void setId(int id) {
                    this.id = id;
          }

          public Date getTarih() {
                    return tarih;
          }

          public void setTarih(Date tarih) {
                    this.tarih = tarih;
          }

          public Personel getPersonel() {
                    return personel;
          }

          public void setPersonel(Personel personel) {
                    this.personel = personel;
          }

          public Kapi getKapi() {
                    return kapi;
          }

          public void setKapi(Kapi kapi) {
                    this.kapi = kapi;
          }

          public char getYon() {
                    return yon;
          }

          public void setYon(char yon) {
                    this.yon = yon;
          }

          public int getHareketTuru() {
                    return hareketTuru;
          }

          public void setHareketTuru(int hareketTuru) {
                    this.hareketTuru = hareketTuru;
          }

          public String getAciklama() {
                    return aciklama;
          }

          public void setAciklama(String aciklama) {
                    this.aciklama = aciklama;
          }

          @Override
          public String toString() {
                    return "Hareket{" + "id=" + id + ", tarih=" + tarih + ", personel=" +( personel!=null?personel.getAd():"") + ", kapi=" + (kapi!=null?kapi.getAd():"") + ", yon=" + yon + ", hareketTuru=" + hareketTuru + ", aciklama=" + aciklama + '}';
          }
          
          
}
