/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.finance.bank.model.dao.hibernate;

import java.util.List;
import org.finance.bank.bean.TOperacion;

/**
 *
 * @author oscar
 */
public class TOperacionDaoHibernate extends GenericDaoHibernate<TOperacion, String> {

    public TOperacionDaoHibernate() {
        super(TOperacion.class);
    }

//    public String create(TOperacion to){
//        return create(to);
//    }
    /**
     * Gets TOperacion information based on idTO
     * @param idTO the idTO
     * @return populated TOperacion object
     */
    /**
     * {@inheritDoc}
     */
    public TOperacion getTOperacionById(String idTO) {
        List to = getHibernateTemplate().find("from T_OPERACION where ID_OPERACION=?", idTO);
        if (to.isEmpty()) {
            return null;
        } else {
            return (TOperacion) to.get(0);
        }
    }

    /**
     * Removes a TOperacions from the database by idTO
     * @param idTO the TOperacions's idTO
     */
    /**
     * {@inheritDoc}
     */
    public void removeRegistroPrestamo(String idTO) {
        Object to = getTOperacionById(idTO);
        getHibernateTemplate().delete(to);
    }

    /**
     * Gets a list of TOperacion ordered by the uppercase version of their idTO.
     *
     * @return List populated list of TOperacions
     */
    /**
     * {@inheritDoc}
     */
    public List<TOperacion> getTOperacion() {
        return getAll();
    }

    /**
     * Saves a TOperacion's information.
     * @param user the object to be saved
     * @return the persisted User object
     */
    /**
     * {@inheritDoc}
     */
    public TOperacion saveTOperacion(TOperacion to) {
        return save(to);
    }
}
