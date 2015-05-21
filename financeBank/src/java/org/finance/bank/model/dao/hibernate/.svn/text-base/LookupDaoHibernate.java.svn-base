package org.finance.bank.model.dao.hibernate;

/**
 *
 * @author oscar
 */
import java.util.List;
import org.finance.bank.bean.TTipoPersona;
import org.finance.bank.model.dao.LookupDao;

/**
 * Hibernate implementation of LookupDao.
 *
 */
public class LookupDaoHibernate extends UniversalDaoHibernate implements LookupDao {

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<TTipoPersona> getRoles() {
        log.debug("Retrieving all role names...");
        return getHibernateTemplate().find("from T_Tipo_Persona order by descripcion");
    }
}
