package com.yakut.azone.controller;

/**
 *
 * @author yakut
 */
import com.yakut.azone.util.DataAccessLayerException;
import com.yakut.azone.util.ExceptionHandler;
import com.yakut.azone.util.JPAUtil;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

public abstract class AbstractDao {

        public EntityManager em;
        public EntityTransaction tx;

        public AbstractDao() {
        }

        protected void saveOrUpdate(Object obj) {
                try {
                        startOperation();
                        em.persist(obj);
                        tx.commit();
                } catch (Exception e) {
                        handleException(e);
                } finally {
                        em.close();
                }
        }

        protected void save(Object obj) {
                try {
                        startOperation();
                        em.persist(obj);
                        tx.commit();
                } catch (Exception e) {
                        handleException(e);
                } finally {
                        em.close();
                }
        }

        protected void delete(Object obj) {
                try {
                        startOperation();

                        em.remove(obj);
                        tx.commit();
                } catch (Exception e) {
                        handleException(e);
                } finally {
                        em.close();
                }
        }

        protected void merge(Object obj) {
                try {
                        startOperation();
                        em.merge(obj);
                        tx.commit();
                } catch (Exception e) {
                        handleException(e);
                } finally {
                        em.close();
                }
        }

        protected Object find(Class clazz, Serializable seri) {
                Object obj = null;
                try {
                        startOperation();
                        obj = em.find(clazz, seri);
                        tx.commit();
                } catch (Exception e) {
                        handleException(e);
                } finally {
                        em.close();
                }
                return obj;
        }

        protected List findAll(String sql) {
                List objects = null;
                try {
                        startOperation();
                        Query query = em.createQuery(sql);
                        objects = query.getResultList();
                        tx.commit();
                } catch (Exception e) {
                        System.out.println("Hata :" + e.getMessage());
                        e.printStackTrace();
                        handleException(e);
                } finally {
                        if (em != null) {
                                em.close();
                        }
                }
                return objects;
        }

        protected Object findUniq(String sql) {
                Object object = null;
                try {
                        startOperation();
                        Query query = em.createQuery(sql);
                        object = query.getSingleResult();
                        tx.commit();
                } catch (Exception e) {
                        handleException(e);
                } finally {
                        em.close();
                }
                return object;
        }

        protected void handleException(Exception e) throws DataAccessLayerException {
                if (e.getMessage().indexOf("open connection") > -1) {
                        ExceptionHandler.handleException(e, 0);
                }
                e.printStackTrace();
                tx.rollback();
                em.close();
        }

        public void startOperation()/*
         * throws Exception
         */ {
                em = JPAUtil.getEntityManagerFactory().createEntityManager();
                tx = em.getTransaction();
                tx.begin();

        }
}
