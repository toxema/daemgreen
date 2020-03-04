package com.yakut.azone.controller;

import com.yakut.azone.beans.Kapi;
import com.yakut.azone.beans.Terminal;
import com.yakut.azone.util.ExceptionHandler;
import java.util.List;

/**
 *
 * @author yakut
 */
public class KapiController extends AbstractDao {

          public List<Kapi> getKapiList() {
                    return getKapiList("select b from Kapi b");
          }

          public List<Kapi> getKapiList(String sql) {
                    return super.findAll(sql);
          }

          public void persist(Kapi kapi) {
                    super.save(kapi);
          }

          public void merge(Kapi kapi) {
                    super.merge(kapi);
          }

          public void delete(Kapi kapi) {
                    try {
                              super.startOperation();
                              Kapi b = super.em.find(Kapi.class, kapi.getId());
                              em.remove(b);
                              tx.commit();
                              em.close();
                    } catch (Exception ex) {
                              ExceptionHandler.onException("Bölge Silinemedi.\r\n"
                                      + "Bu bölgeye bağımlı başka kayıt(lar) olabilir.", ex);
                    }
          }

          public Kapi getKapiByTerminal(Terminal term) {
                    Kapi kapi = null;
                    try {
                              startOperation();
                              List<Kapi> list = em.createQuery("select k from Kapi k where k.terminal=:term ").setParameter("term", term).getResultList();
                              if(list.size()>0){
                                        kapi=list.get(0);
                              }
                              tx.commit();
                              em.close();
                    } catch (Exception ex) {
                    }
                    return kapi;
          }
}
