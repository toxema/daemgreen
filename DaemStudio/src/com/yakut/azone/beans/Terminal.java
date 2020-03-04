/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yakut.azone.beans;

import com.yakut.terminal.Device;
import com.yakut.zksofware.T36;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 *
 * @author yakut
 */
@Entity
public class Terminal implements Serializable {

          @Id
          @GeneratedValue(strategy = GenerationType.SEQUENCE)
          int id;
          @Column(length = 30)
          String ad;
          @Column(length = 50)
          String conString;
          @Transient
          Device device;
          @Transient
          T36 device2;

          public T36 getDevice2() {
                    return device2;
          }

          public void setDevice2(T36 device2) {
                    this.device2 = device2;
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

          public String getConString() {
                    return conString;
          }

          public void setConString(String conString) {
                    this.conString = conString;
          }

          public Device getDevice() {
                    return device;
          }

          public void setDevice(Device device) {
                    this.device = device;
          }

          @Override
          public String toString() {
                    return ad;
          }

          @Override
          public int hashCode() {
                    int hash = 7;
                    hash = 83 * hash + this.id;
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
                    final Terminal other = (Terminal) obj;
                    if (this.id != other.id) {
                              return false;
                    }
                    return true;
          }
}
