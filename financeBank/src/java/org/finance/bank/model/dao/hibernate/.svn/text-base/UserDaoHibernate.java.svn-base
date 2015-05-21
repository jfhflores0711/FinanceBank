/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.finance.bank.model.dao.hibernate;

/**
 *
 * @author oscar
 */
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;

import org.springframework.core.annotation.AnnotationUtils;
//import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import javax.persistence.Table;
import java.util.List;
import org.finance.bank.bean.TCuentaAcceso;
//import org.finance.bank.bean.TPersona;
import org.finance.bank.model.dao.UserDao;

/**
 * This class interacts with Spring's HibernateTemplate to save/delete and
 * retrieve User objects.
 *
 */
public class UserDaoHibernate extends GenericDaoHibernate<TCuentaAcceso, String> implements UserDao, UserDetailsService {

    /**
     * Constructor that sets the entity to User.class.
     */
    public UserDaoHibernate() {
        super(TCuentaAcceso.class);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<TCuentaAcceso> getUsers() {
        return getHibernateTemplate().find("from T_Cuenta_Acceso u order by upper(u.Login)");
    }

    /**
     * {@inheritDoc}
     */
    public TCuentaAcceso saveUser(TCuentaAcceso user) {
        log.debug("user's id: " + user.getIdAcceso());
        getHibernateTemplate().saveOrUpdate(user);
        // necessary to throw a DataIntegrityViolation and catch it in UserManager
        getHibernateTemplate().flush();
        return user;
    }

    /**
     * Overridden simply to call the saveUser method. This is happenening
     * because saveUser flushes the session and saveObject of BaseDaoHibernate
     * does not.
     *
     * @param user the user to save
     * @return the modified user (with a primary key set if they're new)
     */
    @Override
    public TCuentaAcceso save(TCuentaAcceso user) {
        return this.saveUser(user);
    }

    /**
     * {@inheritDoc}
     */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List users = getHibernateTemplate().find("from T_Cuenta_Acceso where login=?", username);
        if (users == null || users.isEmpty()) {
            throw new UsernameNotFoundException("user '" + username + "' not found...");
        } else {
            return (UserDetails) users.get(0);
        }
    }

    /**
     * {@inheritDoc}
     */
    public String getUserPassword(String username) {
        SimpleJdbcTemplate jdbcTemplate =
                new SimpleJdbcTemplate(SessionFactoryUtils.getDataSource(getSessionFactory()));
        Table table = AnnotationUtils.findAnnotation(TCuentaAcceso.class, Table.class);
        return jdbcTemplate.queryForObject(
                "select contrasenia from " + table.name() + " where login=?", String.class, username);

    }
}
