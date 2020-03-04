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

/**
 *
 * @author yakut
 */
@Entity
public class Bolge implements Serializable {

          @Id
          @GeneratedValue(strategy = GenerationType.SEQUENCE)
          int id;
          @Column(length = 30)
          String ad;

          boolean mukerrerGirisSerbest=false;
          boolean mukerrerCikisSerbest=false;

          public Bolge() {
          }

          public Bolge(String ad) {
                     this.ad=ad;
          }
          

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

          public boolean isMukerrerGirisSerbest() {
                    return mukerrerGirisSerbest;
          }

          public void setMukerrerGirisSerbest(boolean mukerrerGirisSerbest) {
                    this.mukerrerGirisSerbest = mukerrerGirisSerbest;
          }

          public boolean isMukerrerCikisSerbest() {
                    return mukerrerCikisSerbest;
          }

          public void setMukerrerCikisSerbest(boolean mukerrerCikisSerbest) {
                    this.mukerrerCikisSerbest = mukerrerCikisSerbest;
          }

          @Override
          public String toString() {
                    return ad;
          }

          @Override
          public int hashCode() {
                    int hash = 7;
                    hash = 37 * hash + this.id;
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
                    final Bolge other = (Bolge) obj;
                    if (this.id != other.id) {
                              return false;
                    }
                    return true;
          }
          
          
}
