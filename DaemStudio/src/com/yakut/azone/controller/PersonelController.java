package com.yakut.azone.controller;

import com.yakut.azone.beans.Personel;
import com.yakut.azone.util.ExceptionHandler;
import java.util.List;

/**
 *
 * @author yakut
 */
public class PersonelController extends AbstractDao {

          public List<Personel> getPersonelList() {
                    return getPersonelList("select b from Personel b order by  b.sicil asc");
          }

          public List<Personel> getPersonelList(String sql) {
                    return super.findAll(sql);
          }

          public void persist(Personel personel) {
                    super.save(personel);
          }

          public void merge(Personel personel) {
                    super.merge(personel);
          }

          public void delete(Personel personel) {
                    try {
                              super.startOperation();
                              Personel b = super.em.find(Personel.class, personel.getId());
                              em.remove(b);
                              tx.commit();
                              em.close();
                    } catch (Exception ex) {
                              ExceptionHandler.onException("Personel Silinemedi.\r\n"
                                      + "Bu personele bağımlı başka kayıt(lar) olabilir.", ex);
                    }
          }

          public List<Personel> getPersonelListByText(String text) {
                    List<Personel> list;
                    startOperation();
                    String sql = "";
                    text=text.trim();
                    if (text.contains(" ")) {
                              String r[] = text.split(" ");   
                              sql = ("select a from Personel a   "
                                      + " where "
                                      + " a.blok like '%_text1%' " 
                                      + " and  a.daire like '%_text2%' "
                                      + "  order by a.sicil asc ").replaceAll("_text1", r[0]).replaceAll("_text2", r[1]);
                    } else {
                              sql = ("select a from Personel a  "
                                      + " left join a.grup g  "
                                      + " where  a.ad like '%_text_%' "
                                      + " or a.soyad like '%_text_%' "
                                      + " or a.sicil like '%_text_%' "
                                       + " or a.kartNo like '%_text_%' "
                                      + " or g.ad  like '%_text_%' "
                                      
//                                      + " or a.daire like '%_text_%' "
//                                      + " or a.blok like '%_text_%' "
                                      + "  order by  a.sicil asc").replaceAll("_text_", text);
                    }


                    System.out.println(sql);
                    list = em.createQuery(sql).getResultList();
                    em.close();
                    return list;
          }

          public List<Personel> getPersonelList(String sicilBas, String sicilson) {
                    List<Personel> list;
                    startOperation();
                    String sql = "select a from Personel a   "
                            + " where  a.sicil between  '" + sicilBas + "' and  '" + sicilson + "'   order by  a.sicil asc";
                    System.out.println(sql);
                    list = em.createQuery(sql).getResultList();
                    em.close();
                    return list;
          }

          public Personel getPersonelByEnroll(String enroll) {
                    Personel p = null;
                    startOperation();
                    List<Personel> list = em.createQuery("select p from Personel p where p.cihazNo=:enroll   order by p.sicil asc").setParameter("enroll", enroll).getResultList();
                    if (list.size() > 0) {
                              p = list.get(0);
                    }
                    return p;
          }

          public static void main(String[] args) {
                    PersonelController pc = new PersonelController();
                    List<Personel> list = pc.getPersonelListByText("");
                    for (Personel p : list) {
                              System.out.println(p);
                    }
          }

          public Personel getPersonelByAdSoyad(String ad, String soyad) {
                    Personel p = null;
                    startOperation();
                    List<Personel> list = em.createQuery("select p from Personel p where p.ad=:ad and p.soyad=:soyad    order by  p.sicil asc").setParameter("ad", ad).setParameter("soyad", soyad).getResultList();
                    if (list.size() > 0) {
                              p = list.get(0);
                    }
                    return p;
          }
}
