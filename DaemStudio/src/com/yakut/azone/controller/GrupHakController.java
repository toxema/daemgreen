/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yakut.azone.controller;

import com.yakut.azone.beans.Bolge;
import com.yakut.azone.beans.Grup;
import com.yakut.azone.beans.GrupHak;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author yakut
 */
public class GrupHakController extends AbstractDao {

          public List<GrupHak> getGrupHakList() {
                    return getGrupHakList("select b from GrupHak b");
          }

          public List<GrupHak> getGrupHakList(String sql) {
                    return super.findAll(sql);
          }

          public void persist(GrupHak grupHak) {
                    super.save(grupHak);
          }

          public void merge(GrupHak grupHak) {
                    super.merge(grupHak);
          }

          public void delete(GrupHak grupHak) {
                    super.startOperation();
                    GrupHak b = super.em.find(GrupHak.class, grupHak.getId());
                    em.remove(b);
                    tx.commit();
                    em.close();
          }

          public GrupHak getGrupHakByKapi(Grup grup, Bolge bolge) {
                    Calendar calendar = Calendar.getInstance();
                    int weekday = calendar.get(Calendar.DAY_OF_WEEK);
                    startOperation();
                    GrupHak gHak = null;
                    System.out.println("Haftanın günü :" + weekday);
                    try {
                              List<GrupHak> list = em.createQuery("select gr from GrupHak gr where  gr.bolge=:bolge and gr.grup=:grup and  gunler like '%" + weekday + "%' ").setParameter("bolge", bolge).setParameter("grup", grup).getResultList();
                              if (!list.isEmpty()) {
                                        gHak = list.get(0);
                              } else {
                                        gHak = null;
                              }

                              tx.commit();
                              em.close();
                    } catch (Exception ex) {
                    }

                    return gHak;
          }

          public GrupHak getHak(Grup grup, Bolge bolge) {

                    startOperation();
                    GrupHak gHak = null;

                    try {
                              List<GrupHak> list = em.createQuery("select gr from GrupHak gr where  gr.bolge=:bolge and gr.grup=:grup ").setParameter("bolge", bolge).setParameter("grup", grup).getResultList();
                              if (!list.isEmpty()) {
                                        gHak = list.get(0);
                              } else {
                                        gHak = null;
                              }

                              tx.commit();
                              em.close();
                    } catch (Exception ex) {
                    }

                    return gHak;
          }
}
