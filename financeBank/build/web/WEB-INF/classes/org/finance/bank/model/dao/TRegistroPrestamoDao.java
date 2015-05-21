package org.finance.bank.model.dao;

import java.util.List;
import org.finance.bank.bean.TRegistroPrestamo;

/**
 *
 * @author oscar
 */
public interface TRegistroPrestamoDao extends GenericDao<TRegistroPrestamo, String> {

    String create(TRegistroPrestamo trp);

    /**
     * Gets TRegistroPrestamo information based on idTRP
     * @param idTRP the idTRP
     * @return populated TRegistroPrestamo object
     */
    TRegistroPrestamo getTRegistroPrestamoById(String idTRP);

    /**
     * Removes a TRegistroPrestamos from the database by idTRP
     * @param idTRP the TRegistroPrestamos's idTRP
     */
    void removeTRegistroPrestamo(String idTRP);

    /**
     * Gets a list of TRegistroPrestamo ordered by the uppercase version of their idTRP.
     *
     * @return List populated list of TRegistroPrestamos
     */
    List<TRegistroPrestamo> getTRegistroPrestamo();

    /**
     * Saves a user's information.
     * @param user the object to be saved
     * @return the persisted User object
     */
    TRegistroPrestamo saveTRegistroPrestamo(TRegistroPrestamo trp);
}
