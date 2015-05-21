package org.finance.bank.model.service;

/**
 *
 * @author oscar
 */
import java.util.List;
import org.finance.bank.bean.TTipoPersona;

/**
 * Business Service Interface to handle communication between web and
 * persistence layer.
 *
 */
public interface RoleManager extends UniversalManager {

    /**
     * {@inheritDoc}
     */
    List getRoles(TTipoPersona role);

    /**
     * {@inheritDoc}
     */
    TTipoPersona getRole(String rolename);

    /**
     * {@inheritDoc}
     */
    TTipoPersona saveRole(TTipoPersona role);

    /**
     * {@inheritDoc}
     */
    void removeRole(String rolename);
}
