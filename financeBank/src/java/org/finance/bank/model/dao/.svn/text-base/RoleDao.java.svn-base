package org.finance.bank.model.dao;

import org.finance.bank.bean.TTipoPersona;

/**
 *
 * @author oscar
 */
/**
 * Role Data Access Object (DAO) interface.
 *
 */
public interface RoleDao extends GenericDao<TTipoPersona, String> {

    /**
     * Gets role information based on rolename
     * @param rolename the rolename
     * @return populated role object
     */
    TTipoPersona getRoleByName(String rolename);

    /**
     * Removes a role from the database by name
     * @param rolename the role's rolename
     */
    void removeRole(String rolename);
}
