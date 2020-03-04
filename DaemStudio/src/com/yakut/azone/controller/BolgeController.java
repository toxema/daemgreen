package com.yakut.azone.controller;

 
import com.yakut.azone.beans.Bolge;
import com.yakut.azone.util.ExceptionHandler;
import java.util.List;

/**
 *
 * @author yakut
 */
public class BolgeController extends AbstractDao {

        public List<Bolge> getBolgeList() {
                return getBolgeList("select b from Bolge b");
        }

        public List<Bolge> getBolgeList(String sql) {
                return super.findAll(sql);
        }

        public void persist(Bolge bolge) {
                super.save(bolge);
        }

        public void merge(Bolge bolge) {
                super.merge(bolge);
        }

        public void delete(Bolge bolge) {
                try {
                        super.startOperation();
                        Bolge b = super.em.find(Bolge.class, bolge.getId());
                        em.remove(b);
                        tx.commit();
                        em.close();
                } catch (Exception ex) {
                        ExceptionHandler.onException("Bölge Silinemedi.\r\n"
                                + "Bu bölgeye bağımlı başka kayıt(lar) olabilir.", ex);
                }
        }
}
