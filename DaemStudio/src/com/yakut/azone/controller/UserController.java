package com.yakut.azone.controller;

import com.yakut.azone.beans.User;
import java.util.List;

/**
 *
 * @author yakut
 */
public class UserController extends AbstractDao {

          public List<User> getUserList() {
                    return getUserList("select b from User b");
          }

          public List<User> getUserList(String sql) {
                    return super.findAll(sql);
          }

          public void persist(User user) {
                    super.save(user);
          }

          public void merge(User user) {
                    super.merge(user);
          }

          public void delete(User user) {
                    super.startOperation();
                    User b = super.em.find(User.class, user.getId());
                    em.remove(b);
                    tx.commit();
                    em.close();
          }

          public User getUser(String text) {
                    super.startOperation();
                    List<User> list;
                    User b = null;
                    list = super.em.createQuery("select u from User u where u.name='" + text + "'").getResultList();
                    if (list.size() > 0) {
                              b = list.get(0);

                    }
                    return b;
          }
}
