/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yakut.azone.beans;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 *
 * @author yakut
 */
@Entity
public class Kapi implements Serializable {

          @Id
          @GeneratedValue(strategy = GenerationType.SEQUENCE)
          int id;
          @Column(length = 20)
          String ad;
          @ManyToOne
          @JoinColumn(name = "BOLGE_ID")
          Bolge bolge;
          char yon = 'X';
          @ManyToOne
          @JoinColumn(name = "TERMINAL_ID")
          Terminal terminal;
          int okuyucuNo = 0;
          public static char GIRIS = 'G';
          @Transient
          public static char CIKIS = 'C';

          public int getId() {
                    return id;
          }

          public void setId(int id) {
                    this.id = id;
          }

          public String getAd() {
                    return ad;
          }

          public void setAd(String ad) {
                    this.ad = ad;
          }

          public Bolge getBolge() {
                    return bolge;
          }

          public void setBolge(Bolge bolge) {
                    this.bolge = bolge;
          }

          public char getYon() {
                    return yon;
          }

          public void setYon(char yon) {
                    this.yon = yon;
          }

          public Terminal getTerminal() {
                    return terminal;
          }

          public void setTerminal(Terminal terminal) {
                    this.terminal = terminal;
          }

          public int getOkuyucuNo() {
                    return okuyucuNo;
          }

          public void setOkuyucuNo(int okuyucuNo) {
                    this.okuyucuNo = okuyucuNo;
          }
}
