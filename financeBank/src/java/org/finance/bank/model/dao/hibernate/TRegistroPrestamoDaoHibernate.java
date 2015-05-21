/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.finance.bank.model.dao.hibernate;

import java.util.List;
import org.finance.bank.bean.TRegistroPrestamo;

/**
 *
 * @author oscar
 */
public class TRegistroPrestamoDaoHibernate extends GenericDaoHibernate<TRegistroPrestamo, String> {

    public TRegistroPrestamoDaoHibernate() {
        super(TRegistroPrestamo.class);
    }

    /**
     * Gets TRegistroPrestamo information based on idTRP
     * @param idTRP the idTRP
     * @return populated TRegistroPrestamo object
     */
    public TRegistroPrestamo getRegistroPrestamoById(String idTRP) {
        List trp = getHibernateTemplate().find("from T_REGISTRO_PRESTAMO where IDPRESTAMO=?", idTRP);
        if (trp.isEmpty()) {
            return null;
        } else {
            return (TRegistroPrestamo) trp.get(0);
        }
    }

    /**
     * Removes a TRegistroPrestamos from the database by idTRP
     * @param idTRP the TRegistroPrestamos's idTRP
     */
    public void removeRegistroPrestamo(String idTRP) {
        Object to = getRegistroPrestamoById(idTRP);
        getHibernateTemplate().delete(to);
    }

    /**
     * Gets a list of TRegistroPrestamo ordered by the uppercase version of their idTRP.
     *
     * @return List populated list of TRegistroPrestamos
     */
    public List<TRegistroPrestamo> getRegistroPrestamo(){
        return getAll();
    }

    /**
     * Saves a TRegistroPrestamo's information.
     * @param user the object to be saved
     * @return the persisted User object
     */
    public TRegistroPrestamo saveTRegistroPrestamo(TRegistroPrestamo trp){
        return save(trp);
    }
}
