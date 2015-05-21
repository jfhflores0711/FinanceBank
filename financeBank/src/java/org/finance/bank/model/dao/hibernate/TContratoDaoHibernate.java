/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.finance.bank.model.dao.hibernate;

import java.util.List;
import org.finance.bank.bean.TContrato;
import org.finance.bank.model.dao.TContratoDao;

/**
 *
 * @author oscar
 */
public class TContratoDaoHibernate extends GenericDaoHibernate<TContrato, Long> implements TContratoDao{

    public TContratoDaoHibernate() {
        super(TContrato.class);
    }

    public List<TContrato> findByIdPrestamo(String idPrestamo) {
        return getHibernateTemplate().find("from T_CONTRATO where idPrestamo=?", idPrestamo);
    }

}
