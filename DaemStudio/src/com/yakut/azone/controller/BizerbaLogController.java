package com.yakut.azone.controller;

import com.yakut.azone.beans.BizerbaLog;
import com.yakut.azone.util.ExceptionHandler;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author yakut
 */
public class BizerbaLogController extends AbstractDao {

        public List<BizerbaLog> getBizerbaLogList() {
                return getBizerbaLogList("select b from BizerbaLog b order by b.urunKodu asc");
        }

        public List<BizerbaLog> getBizerbaLogList(String urunBas, String urunBit, Date bas, Date bit) {
                List<BizerbaLog> list = null;
                startOperation();

                list = em.createQuery("select h from BizerbaLog h "
                        + "where  (h.tarih between :tarih1 and :tarih2)  "
                        + " and (h.urunKodu between :urunBas and :urunBit) "
                        + ""
                        + "order by h.urunKodu asc").setParameter("tarih1", bas).setParameter("tarih2", bit).setParameter("urunBas", urunBas).setParameter("urunBit", urunBit).
                        getResultList();

                tx.commit();
                em.close();

                return list;
        }

        public List<BizerbaLog> getBizerbaLogList(String sql) {
                return super.findAll(sql);
        }

        public void persist(BizerbaLog bizerbaLog) {
                super.save(bizerbaLog);
        }

        public void merge(BizerbaLog bizerbaLog) {
                super.merge(bizerbaLog);
        }

        public void delete(BizerbaLog bizerbaLog) {
                try {
                        super.startOperation();
                        BizerbaLog b = super.em.find(BizerbaLog.class, bizerbaLog.getId());
                        em.remove(b);
                        tx.commit();
                        em.close();
                } catch (Exception ex) {
                        ExceptionHandler.onException("Bölge Silinemedi.\r\n"
                                + "Bu bölgeye bağımlı başka kayıt(lar) olabilir.", ex);
                }
        }

        public List getBizerbaOzetList(String urunBas, String urunBit, Date bas, Date bit) {
                List list;
                startOperation();
                String sql;

                sql = "select CAST (tarih AS date) ,MAKINA_ADI,URUN_KODU,URUN_ADI,sum(GERCEK_AGIRLIK) \"AS\" ,sum(BARKOD_AGIRLIK)  \"ASR\"  ,  sum(FARK_AGIRLIK) \"ASE\" , 1 \"ASC\"   from BizerbaLog  "
                          + "where  (tarih between :tar1 and :tar2) "
                         + " and (urun_Kodu between :urunBas and :urunBit) "
                        + " group by 1,2,3 ,4";

                System.out.println(sql);
                Query q = em.createNativeQuery(sql);
                      q.setParameter("tar1", bas).setParameter("tar2", bit).setParameter("urunBas", urunBas).setParameter("urunBit", urunBit);

                list = q.getResultList();
                return list;
        }
}
