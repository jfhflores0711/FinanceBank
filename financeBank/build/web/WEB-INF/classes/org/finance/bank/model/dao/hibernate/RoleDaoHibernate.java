/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.finance.bank.model.dao.hibernate;

/**
 *
 * @author oscar
 */
import java.util.List;
import org.finance.bank.bean.TTipoPersona;
import org.finance.bank.model.dao.RoleDao;

/**
 * This class interacts with Spring's HibernateTemplate to save/delete and
 * retrieve Role objects.
 *
 */
public class RoleDaoHibernate extends GenericDaoHibernate<TTipoPersona, String> implements RoleDao {

    /**
     * Constructor to create a Generics-based version using Role as the entity
     */
    public RoleDaoHibernate() {
        super(TTipoPersona.class);
    }

    /**
     * {@inheritDoc}
     */
    public TTipoPersona getRoleByName(String rolename) {
        List roles = getHibernateTemplate().find("from T_Tipo_Persona where descripcion=?", rolename);
        if (roles.isEmpty()) {
            return null;
        } else {
            return (TTipoPersona) roles.get(0);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void removeRole(String rolename) {
        Object role = getRoleByName(rolename);
        getHibernateTemplate().delete(role);
    }
}
