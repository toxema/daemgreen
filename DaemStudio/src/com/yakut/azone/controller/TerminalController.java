package com.yakut.azone.controller;

 
import com.yakut.azone.beans.Terminal;
import com.yakut.azone.util.ExceptionHandler;
import java.util.List;

/**
 *
 * @author yakut
 */
public class TerminalController extends AbstractDao {

        public List<Terminal> getTerminalList() {
                return getTerminalList("select b from Terminal b");
        }

        public List<Terminal> getTerminalList(String sql) {
                return super.findAll(sql);
        }

        public void persist(Terminal terminal) {
                super.save(terminal);
        }

        public void merge(Terminal terminal) {
                super.merge(terminal);
        }

        public void delete(Terminal terminal) {
                try {
                        super.startOperation();
                        Terminal b = super.em.find(Terminal.class, terminal.getId());
                        em.remove(b);
                        tx.commit();
                        em.close();
                } catch (Exception ex) {
                        ExceptionHandler.onException("terminal Silinemedi.\r\n"
                                + "Bu terminale bağımlı başka kayıt(lar) olabilir.", ex);
                }
        }
}
