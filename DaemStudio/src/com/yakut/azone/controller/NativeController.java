/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yakut.azone.controller;

import java.sql.ResultSet;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author yakut
 */
public class NativeController extends AbstractDao {

    public void test() {
        startOperation();
        class Sonuc {

            Date tarih;
            int count;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public Date getTarih() {
                return tarih;
            }

            public void setTarih(Date tarih) {
                this.tarih = tarih;
            }

            @Override
            public String toString() {
                return "Sonuc{" + "tarih=" + tarih + ", count=" + count + '}';
            }
        }
        List q = em.createQuery("select y.daire from Arac y  group by y.daire").getResultList();

        Iterator i = q.iterator();
        while (i.hasNext()) {
            String result = (String) i.next();
            System.out.println(result);

        }
    }

    public List<String> getDaireList() {
        startOperation();
        List<String> list = em.createQuery("select a.daire from Arac a  group by a.daire").getResultList();
        em.close();
        return list;
    }

    public List<Integer> getKatList() {

        startOperation();
        List<Integer> list = em.createQuery("select a.kat from Arac a  group by a.kat").getResultList();
        em.close();
        return list;

    }

    public List<String> getBlokList() {
        startOperation();
        List<String> list = em.createQuery("select a.blok from Arac a  group by a.blok").getResultList();
        em.close();
        return list;
    }

    public static void main(String[] as) {
      NativeController n=new NativeController();
      List<Integer> list=n.getKatList();
      for(int k:list){
          System.out.println("k:"+k);
      }
    }
}
