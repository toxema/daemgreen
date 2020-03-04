/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yakut.azone.beans;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author yakut
 */
@Entity
public class Personel implements Serializable {

          @Id
          @GeneratedValue(strategy = GenerationType.SEQUENCE)
          int id;
          @Column(length = 24)
          String sicil;
          @Column(length = 24)
          String kartNo;
          @Column(length = 12)
          String cihazNo;
          @Column(length = 25)
          String ad;
          @Column(length = 30)
          String soyad;
          @ManyToOne
          @JoinColumn(name = "GRUP_ID")
          Grup grup;
          @Temporal(TemporalType.DATE)
          Date gecerlilikBaslangicTarihi;
          @Temporal(TemporalType.DATE)
          Date gecerlilikBitisTarihi;
          int durum = 0;// 0 gecerli ,1 gecersiz,2 kayıp,3 ...
          char yon = 'X';
          @Temporal(TemporalType.TIMESTAMP)
          Date sonHareket;
          @ManyToOne
          @JoinColumn(name = "BOLGE_ID")
          Bolge bolge;
          @Column(length = 12)
          String blok;
          @Column(length = 12)
          String daire;
          @Column(length = 100)
          String aciklama;
          @Column(length = 30)
          String email;
          @Column(length=12)
          String telefon;


          public int getId() {
                    return id;
          }

          public void setId(int id) {
                    this.id = id;
          }

          public String getSicil() {
                    return sicil;
          }

          public void setSicil(String sicil) {
                    this.sicil = sicil;
          }

          public String getKartNo() {
                    return kartNo;
          }

          public void setKartNo(String kartNo) {
                    this.kartNo = kartNo;
          }

          public String getCihazNo() {
                    return cihazNo;
          }

          public void setCihazNo(String cihazNo) {
                    this.cihazNo = cihazNo;
          }

          public String getAd() {
                    return ad;
          }

          public void setAd(String ad) {
                    this.ad = ad;
          }

          public String getSoyad() {
                    return soyad;
          }

          public void setSoyad(String soyad) {
                    this.soyad = soyad;
          }

          public Grup getGrup() {
                    return grup;
          }

          public void setGrup(Grup grup) {
                    this.grup = grup;
          }

          public Date getGecerlilikBaslangicTarihi() {
                    return gecerlilikBaslangicTarihi;
          }

          public void setGecerlilikBaslangicTarihi(Date gecerlilikBaslangicTarihi) {
                    this.gecerlilikBaslangicTarihi = gecerlilikBaslangicTarihi;
          }

          public Date getGecerlilikBitisTarihi() {
                    return gecerlilikBitisTarihi;
          }

          public void setGecerlilikBitisTarihi(Date gecerlilikBitisTarihi) {
                    this.gecerlilikBitisTarihi = gecerlilikBitisTarihi;
          }

          public int getDurum() {
                    return durum;
          }

          public void setDurum(int durum) {
                    this.durum = durum;
          }

          public char getYon() {
                    return yon;
          }

          public void setYon(char yon) {
                    this.yon = yon;
          }

          public Date getSonHareket() {
                    return sonHareket;
          }

          public void setSonHareket(Date sonHareket) {
                    this.sonHareket = sonHareket;
          }

          public Bolge getBolge() {
                    return bolge;
          }

          public void setBolge(Bolge bolge) {
                    this.bolge = bolge;
          }

          public String getBlok() {
                    return blok;
          }

          public void setBlok(String blok) {
                    this.blok = blok;
          }

          public String getDaire() {
                    return daire;
          }

          public void setDaire(String daire) {
                    this.daire = daire;
          }

          public String getAciklama() {
                    return aciklama;
          }

          public void setAciklama(String aciklama) {
                    this.aciklama = aciklama;
          }

          public String getEmail() {
                    return email;
          }

          public void setEmail(String email) {
                    this.email = email;
          }

       

          public String getTelefon() {
                    return telefon;
          }

          public void setTelefon(String telefon) {
                    this.telefon = telefon;
          }

          @Override
          public int hashCode() {
                    int hash = 5;
                    hash = 89 * hash + this.id;
                    hash = 89 * hash + (this.sicil != null ? this.sicil.hashCode() : 0);
                    return hash;
          }

          @Override
          public boolean equals(Object obj) {
                    if (obj == null) {
                              return false;
                    }
                    if (getClass() != obj.getClass()) {
                              return false;
                    }
                    final Personel other = (Personel) obj;
                    if (this.id != other.id) {
                              return false;
                    }
                    if ((this.sicil == null) ? (other.sicil != null) : !this.sicil.equals(other.sicil)) {
                              return false;
                    }
                    return true;
          }

          @Override
          public String toString() {
                    return "Personel{" + "id=" + id + ", sicil=" + sicil + ", kartNo=" + kartNo + ", cihazNo=" + cihazNo + ", ad=" + ad + ", soyad=" + soyad + '}';
          }
}
