package org.finance.bank.model.dao;

import java.util.List;
import org.finance.bank.bean.TDetallePrestamo;

/**
 *
 * @author oscar
 */
public interface TDetallePrestamoDao extends GenericDao<TDetallePrestamo, String> {

    String create(TDetallePrestamo tdp);

    /**
     * Gets TDetallePrestamo information based on idTDP
     * @param idTDP the idTDP
     * @return populated TDetallePrestamo object
     */
    TDetallePrestamo getTDetallePrestamoById(String idTDP);

    /**
     * Removes a TDetallePrestamos from the database by idTDP
     * @param idTDP the TDetallePrestamos's idTDP
     */
    void removeRegistroPrestamo(String idTDP);

    /**
     * Gets a list of TDetallePrestamo ordered by the uppercase version of their idTDP.
     *
     * @return List populated list of TDetallePrestamos
     */
    List<TDetallePrestamo> getTDetallePrestamo();

    /**
     * Saves a user's information.
     * @param user the object to be saved
     * @return the persisted User object
     */
    TDetallePrestamo saveTDetallePrestamo(TDetallePrestamo tdp);
}
