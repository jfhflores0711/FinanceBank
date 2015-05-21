/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.finance.bank.model.dao.hibernate;

import java.util.List;
import org.finance.bank.bean.TDetallePrestamo;

/**
 *
 * @author oscar
 */
public class TDetallePrestamoDaoHibernate extends GenericDaoHibernate<TDetallePrestamo, String>{

    public TDetallePrestamoDaoHibernate(){
        super(TDetallePrestamo.class);
    }


        /**
     * Gets TDetallePrestamo information based on idTDP
     * @param idTDP the idTDP
     * @return populated TDetallePrestamo object
     */
    /**
     * {@inheritDoc}
     */
    public TDetallePrestamo getTDetallePrestamoById(String idTDP){
        List tdp = getHibernateTemplate().find("from T_DETALLE_PRESTAMO where IDDETALLEPRESTAMO=?", idTDP);
        if (tdp.isEmpty()) {
            return null;
        } else {
            return (TDetallePrestamo) tdp.get(0);
        }
    }

    /**
     * Removes a TDetallePrestamos from the database by idTDP
     * @param idTDP the TDetallePrestamos's idTDP
     */
    /**
     * {@inheritDoc}
     */
    public void removeRegistroPrestamo(String idTDP){
        Object tdp = getTDetallePrestamoById(idTDP);
        getHibernateTemplate().delete(tdp);
    }

    /**
     * Gets a list of TDetallePrestamo ordered by the uppercase version of their idTDP.
     *
     * @return List populated list of TDetallePrestamos
     */
    /**
     * {@inheritDoc}
     */
    public List<TDetallePrestamo> getTDetallePrestamo(){
        return getAll();
    }

    /**
     * Saves a user's information.
     * @param user the object to be saved
     * @return the persisted User object
     */
    /**
     * {@inheritDoc}
     */
    public TDetallePrestamo saveTDetallePrestamo(TDetallePrestamo tdp){
        return save(tdp);
    }
}
