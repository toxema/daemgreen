package com.yakut.azone.controller;

import com.yakut.azone.beans.Bolge;
import com.yakut.azone.beans.Grup;
import com.yakut.azone.beans.Hareket;
 
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author yakut
 */
public class HareketController extends AbstractDao {

            public List<Hareket> getHareketList() {
                        return getHareketList("select b from Hareket b");
            }

            public List<Hareket> getHareketList(String sql) {
                        return super.findAll(sql);
            }

//    public int getMaxId(){      
//        startOperation();
//        List l = em.createNativeQuery("select max(Id) from  Ogs_Hareket ").getResultList();
//        em.close();
//        if (l.size() > 0) {
//            Integer i = (Integer) l.get(0);
//            return i;
//        }
//        return 0;
//    
//    }
            public void persist(Hareket hareket) {
  
                        super.save(hareket);
    
            }

            public void delete(Hareket hareket) {
                        super.delete(hareket);
            }

            public void merge(Hareket hareket) {
                        super.startOperation();
                        Hareket b = super.em.find(Hareket.class, hareket.getId());
                        em.remove(b);
                        tx.commit();
                        em.close();
            }

            public List<Hareket> getHareketListBolgeAndTarih(Bolge bolge, Date bas, Date bit) {
                        List<Hareket> list = null;
                        startOperation();
                        if (bolge != null) {
                                    list = em.createQuery("select h from Hareket h "
                                            + "left join h.kapi k "
                                            + "left join k.bolge b "
                                            + " where tur=1 and h.yon='G' and (h.tarih between :tarih1 and :tarih2) and b=:bolge").setParameter("tarih1", bas).setParameter("tarih2", bit).setParameter("bolge", bolge).getResultList();
                        } else {
                                    list = em.createQuery("select h from Hareket h "
                                            + "left join h.kapi k "
                                            + "left join k.bolge b "
                                            + "where tur=1 and  h.yon='G' and (h.tarih between :tarih1 and :tarih2) ").setParameter("tarih1", bas).setParameter("tarih2", bit).getResultList();
                        }
                        tx.commit();
                        em.close();

                        return list;
            }

            public List<Hareket> getHareketList(String enrollBas, String enrollBit, Date bas, Date bit, Bolge bolge, Grup grup, int yon, int tur) {
                        List<Hareket> list = null;
                        String sql = "select h from Hareket h "
                                + " left join h.personel p "
                                + " left join p.grup grup "
                                + " left join h.kapi kapi "
                                + " left join kapi.bolge bolge ";

                        String hareketTuru = (tur == 0 ? "" : " and h.hareketTuru=" + (tur - 1));
                        String sql2 = "where 1=1 " + hareketTuru + " and (h.tarih between :bas and :bit)  " + (yon == 1 ? " and h.yon='G' " : yon == 2 ? " and h.yon='C' " : "");

                        sql2 += grup != null ? " and p.grup=:grup" : "";
                        sql2 += bolge != null ? " and kapi.bolge=:bolge" : "";


                        //            sql2 += " and (p.cihazNo between :enrollBas and :enrollBit) ";

                        startOperation();
                        Query q = em.createQuery(sql + sql2);
                        //     q.setParameter("enrollBas", enrollBas).setParameter("enrollBit", enrollBit);
                        //     q.setParameter("bas", new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(bas)).setParameter("bit", new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(bit));

                   

                        q.setParameter("bas", bas).setParameter("bit", bit);

                        if (grup != null) {
                                    q.setParameter("grup", grup);
                        }
                        if (bolge != null) {
                                    q.setParameter("bolge", bolge);
                        }


                        list = q.getResultList();
                        tx.commit();
                        em.close();
                        return list;
            }
}
