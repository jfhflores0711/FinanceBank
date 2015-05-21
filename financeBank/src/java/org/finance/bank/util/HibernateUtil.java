package org.finance.bank.util;

/**
 *
 * @author admin
 */
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
        } catch (Throwable th) {
            Logger.getLogger(HibernateUtil.class.getName()).log(Level.SEVERE, "Initial SessionFactory creation failed" + th);
            throw new ExceptionInInitializerError(th);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /*
     * @dbTestId Método para retornar los valores de bloqueo
     * @param a Id que se requiere boquear
     * @param b idpersonacaja
     * @param p prioridad
     */
    public static synchronized boolean dbTestId(String a, String b, String c, String p) {
        boolean locked = false;
        try {
            Session s = getSessionFactory().openSession();
            Transaction t = s.beginTransaction();
            SQLQuery q = s.createSQLQuery("INSERT INTO t_bloqueo " +
                    "(idvitory, bloquear_id, fecha, idpersonacaja, nombretabla, prioridad) VALUES" +
                    " ('"
                    + a + "','" + a + "','" + formatFecha.get() + "','" + b + "','" + c + "','" + p
                    + "')");
            q.executeUpdate();
            t.commit();
            s.close();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(HibernateUtil.class.getName()).log(Level.WARNING, "ERROR GENERAL:" + ex.getMessage());
        }
        return locked;
    }

    /***
     * Este metodo trunca datos de la tabla t_bloqueo
     */
    public static synchronized void truncarBloqueo() {
        try {
            Session s = getSessionFactory().openSession();
            Transaction t = s.beginTransaction();
            s.createSQLQuery("DELETE FROM t_bloqueo").executeUpdate();
            t.commit();
            s.close();
        } catch (Exception ex) {
            Logger.getLogger(HibernateUtil.class.getName()).log(Level.WARNING, "ERROR GENERAL:" + ex.getMessage());
        }
    }

    public static synchronized boolean firstLogin(Date a, String c) {
        boolean b = false;
        try {
            Session s = getSessionFactory().openSession();
            Transaction t = s.beginTransaction();
            SQLQuery q = s.createSQLQuery("INSERT INTO t_transaccion_c (idtranscapita, tipo_operacion, fecha, fechaultima) VALUES"
                    + "('" + DateUtil.convertDateIdH(a) + "', 'CHECK', '" + c + "', '" + c + "')");
            q.executeUpdate();
            t.commit();
            s.close();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(HibernateUtil.class.getName()).log(Level.WARNING, "ERROR GENERAL:" + ex.getCause());
        }
        return b;
    }

    public static synchronized boolean dbBlocked(String FECHAB){
        boolean b = false;
        try {
            Session s = getSessionFactory().openSession();
            Transaction t = s.beginTransaction();
            SQLQuery q = s.createSQLQuery("INSERT INTO t_transaccion_c (idtranscapita, tipo_operacion, fecha, fechaultima) VALUES"
                    + "('2011050217183907447300', 'BLOCK', '" + FECHAB + "', '" + FECHAB + "')");
            q.executeUpdate();
            t.commit();
            s.close();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(HibernateUtil.class.getName()).log(Level.WARNING, "ERROR GENERAL:" + ex.getMessage());
        }
        return b;
    }

    public static synchronized boolean dbUnBlocker(){
        boolean b = false;
        try {
            Session s = getSessionFactory().openSession();
            Transaction t = s.beginTransaction();
            SQLQuery q = s.createSQLQuery("DELETE FROM t_transaccion_c Where idtranscapita='"
                    + "2011050217183907447300'");
            q.executeUpdate();
            t.commit();
            s.close();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(HibernateUtil.class.getName()).log(Level.WARNING, "ERROR GENERAL:" + ex.getMessage());
        }
        return b;
    }
}
