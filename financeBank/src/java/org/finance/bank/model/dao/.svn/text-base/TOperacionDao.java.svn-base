package org.finance.bank.model.dao;

import java.util.List;
import org.finance.bank.bean.TOperacion;

/**
 *
 * @author oscar
 */
public interface TOperacionDao extends GenericDao<TOperacion, String> {

    String create(TOperacion to);

    /**
     * Gets TOperacion information based on idTO
     * @param idTO the idTO
     * @return populated TOperacion object
     */
    TOperacion getTOperacionById(String idTO);

    /**
     * Removes a TOperacions from the database by idTO
     * @param idTO the TOperacions's idTO
     */
    void removeRegistroPrestamo(String idTO);

    /**
     * Gets a list of TOperacion ordered by the uppercase version of their idTO.
     *
     * @return List populated list of TOperacions
     */
    List<TOperacion> getTOperacion();

    /**
     * Saves a user's information.
     * @param user the object to be saved
     * @return the persisted User object
     */
    TOperacion saveTOperacion(TOperacion to);
}
