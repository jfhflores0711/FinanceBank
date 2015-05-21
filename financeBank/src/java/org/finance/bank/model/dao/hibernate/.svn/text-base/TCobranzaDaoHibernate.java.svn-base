/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.finance.bank.model.dao.hibernate;

import java.util.List;
import org.finance.bank.bean.TCobranza;
import org.finance.bank.model.dao.TCobranzaDao;

/**
 *
 * @author oscar
 */
public class TCobranzaDaoHibernate extends GenericDaoHibernate<TCobranza, String> implements TCobranzaDao {

    /**
     * Constructor to create a Generics-based version using Role as the entity
     */
    public TCobranzaDaoHibernate() {
        super(TCobranza.class);
    }

    /**
     * {@inheritDoc}
     */
//    String create(TCobranza tc) {
//
//        return (String)getSession().save(tc);
//    }

    /**
     * Gets TCobranza information based on idTC
     * @param idTC the idTC
     * @return populated TCobranza object
     */
    /**
     * {@inheritDoc}
     */
    public TCobranza getTCobranzaById(String idTC) {
         List tc = getHibernateTemplate().find("from T_Cobranza where IDCOBRANZA=?", idTC);
        if (tc.isEmpty()) {
            return null;
        } else {
            return (TCobranza) tc.get(0);
        }
    }

    /**
     * Removes a TCobranzas from the database by idTC
     * @param idTC the TCobranzas's idTC
     */
    /**
     * {@inheritDoc}
     */
    public void removeTCobranza(String idTC) {
        Object tc = getTCobranzaById(idTC);
        getHibernateTemplate().delete(tc);
    }

    /**
     * Gets a list of TCobranza ordered by the uppercase version of their idTC.
     *
     * @return List populated list of TCobranzas
     */
    /**
     * {@inheritDoc}
     */
    public List<TCobranza> getTCobranzas() {
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
    public TCobranza saveTCobranza(TCobranza tc) {
        return save(tc);
    }
}
