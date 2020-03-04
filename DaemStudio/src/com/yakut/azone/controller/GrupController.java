/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yakut.azone.controller;

 
import com.yakut.azone.beans.Grup;
import java.util.List;
 

/**
 *
 * @author yakut
 */


public class GrupController extends AbstractDao {

          public List<Grup> getGrupList() {
                    return getGrupList("select g from Grup g");
          }

          public List<Grup> getGrupList(String sql) {
                    return super.findAll(sql);
          }

          public void persist(Grup grup) {
                    super.save(grup);
          }

          public void merge(Grup grup) {
                    super.merge(grup);
          }

          public void delete(Grup grup) {
                    super.startOperation();
                    Grup g = super.em.find(Grup.class, grup.getId());
                    em.remove(g);
                    tx.commit();
                    em.close();
          }
}
