/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yakut.azone.test;

import com.yakut.azone.beans.Hareket;
import com.yakut.azone.controller.HareketController;
import com.yakut.azone.controller.PersonelController;

/**
 *
 * @author yakut
 */
public class Test {
          public static void main(String[] args) {
                   HareketController hc=new HareketController();
                   Hareket h=new Hareket();
                   
                   h.setAciklama("deneme");
                   
                   
                   hc.persist(h);
          }
}
