package org.finance.bank.model.dao;

import java.util.List;
import org.finance.bank.bean.TCobranza;

/**
 *
 * @author oscar
 */
public interface TCobranzaDao extends GenericDao<TCobranza, String> {

    String create(TCobranza tc);

    /**
     * Gets TCobranza information based on idTC
     * @param idTC the idTC
     * @return populated TCobranza object
     */
    TCobranza getTCobranzaById(String idTC);

    /**
     * Removes a TCobranzas from the database by idTC
     * @param idTC the TCobranzas's idTC
     */
    void removeTCobranza(String idTC);

    /**
     * Gets a list of TCobranza ordered by the uppercase version of their idTC.
     *
     * @return List populated list of TCobranzas
     */
    List<TCobranza> getTCobranzas();

    /**
     * Saves a user's information.
     * @param user the object to be saved
     * @return the persisted User object
     */
    TCobranza saveTCobranza(TCobranza tc);
}
