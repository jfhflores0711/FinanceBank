package org.finance.bank.model.dao;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.finance.bank.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/*
 * @author Administrador
 */
public class DAOGeneral {

    SessionFactory sessFact;
    Session sess;
    Transaction tr;

    public DAOGeneral() {
        this.sessFact = HibernateUtil.getSessionFactory();
        this.sess = sessFact.openSession();
        this.tr = sess.beginTransaction();
    }

    /**
     *  El método nos carga un objeto de la base de datos.
     * @param x parámetro de una clase que va a ser retornado como objeto
     * @param id identificador único de la bases de datos
     * @return Un objeto Serializable o un valor nulo
     */
    public Object load(Class x, Object id) {
        Object p = null;
        try {
            if (sess.get(x, (Serializable) id) != null) {
                p = sess.load(x, (Serializable) id);
            }
        } catch (HibernateException e) {
            Logger.getLogger(DAOGeneral.class.getName()).log(Level.INFO, e.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(DAOGeneral.class.getName()).log(Level.INFO, "ERROR DE LECTURA DE BASE DE DATOS");
        }
        return p;
    }

    /**
     * Método que nos asegura guardar un objeto seriable en la bases de datos
     * @throws none
     * @param obj
     * @return none
     */
    public void persist(Object obj) {
        tr = sess.beginTransaction();
        sess.persist(obj);
        tr.commit();
    }

    /**
     * Problemas con el manejo del log
     * debe tener cuidado con la catidad
     *  de log por semana y el mes
     */
    public List findAll(String Hql) {
        //Logger.getLogger(DAOGeneral.class.getName()).log(Level.INFO, Hql);
        sess.beginTransaction();
        Query query = sess.createQuery(Hql);
        List result = query.list();
        return result;
    }

    public void delete(Object obj) {
        tr = sess.beginTransaction();
        sess.delete(obj);
        tr.commit();
    }

    public void update() {
        tr = sess.beginTransaction();
        tr.commit();
    }

    public void cerrarSession() {
        sess.close();
        if (!sessFact.isClosed()) {
            sessFact.close();
        }
    }

    public void executeUpdate(String execute) {
        tr = sess.beginTransaction();
        Query q = sess.createQuery(execute);
        int i = 0;
        try {
            i = q.executeUpdate();
            tr.commit();
        } catch (Exception e) {
            Logger.getLogger(DAOGeneral.class.getName()).log(Level.WARNING, e.getMessage());
            //return;
        }
        //Logger.getLogger(DAOGeneral.class.getName()).log(Level.FINE, String.valueOf(i));
    }
}
