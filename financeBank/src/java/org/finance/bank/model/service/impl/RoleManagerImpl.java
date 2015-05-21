package org.finance.bank.model.service.impl;

/**
 *
 * @author oscar
 */
import java.util.List;
import org.finance.bank.bean.TTipoPersona;
import org.finance.bank.model.dao.RoleDao;
import org.finance.bank.model.service.RoleManager;

/**
 * Implementation of RoleManager interface.
 *
 */
public class RoleManagerImpl extends UniversalManagerImpl implements RoleManager {

    private RoleDao dao1;

    public void setRoleDao(RoleDao dao) {
        this.dao1 = dao;
    }

    /**
     * {@inheritDoc}
     */
    public List<TTipoPersona> getRoles(TTipoPersona role) {
        return dao1.getAll();
    }

    /**
     * {@inheritDoc}
     */
    public TTipoPersona getRole(String rolename) {
        return dao1.getRoleByName(rolename);
    }

    /**
     * {@inheritDoc}
     */
    public TTipoPersona saveRole(TTipoPersona role) {
        return dao1.save(role);
    }

    /**
     * {@inheritDoc}
     */
    public void removeRole(String rolename) {
        dao1.removeRole(rolename);
    }
}
