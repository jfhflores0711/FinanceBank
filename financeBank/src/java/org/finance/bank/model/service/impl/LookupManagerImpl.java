package org.finance.bank.model.service.impl;

/**
 *
 * @author oscar
 */
import java.util.ArrayList;
import java.util.List;
import org.finance.bank.bean.TTipoPersona;
import org.finance.bank.model.LabelValue;
import org.finance.bank.model.dao.LookupDao;
import org.finance.bank.model.service.LookupManager;


/**
 * Implementation of LookupManager interface to talk to the persistence layer.
 *
 */
public class LookupManagerImpl extends UniversalManagerImpl implements LookupManager {
    private LookupDao dao1;

    /**
     * Method that allows setting the DAO to talk to the data store with.
     * @param dao the dao implementation
     */
    public void setLookupDao(LookupDao dao) {
        super.dao = dao;
        this.dao1 = dao;
    }

    /**
     * {@inheritDoc}
     */
    public List<LabelValue> getAllRoles() {
        List<TTipoPersona> roles = dao1.getRoles();
        List<LabelValue> list = new ArrayList<LabelValue>();
        for (TTipoPersona role1 : roles) {
            list.add(new LabelValue(role1.getDescripcion(), role1.getDescripcion()));
        }
        return list;
    }
}
