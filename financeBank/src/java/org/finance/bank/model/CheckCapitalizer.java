package org.finance.bank.model;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.finance.bank.bean.TTransaccionC;
import org.finance.bank.model.dao.DAOGeneral;
import org.finance.bank.util.DateUtil;

/**
 *
 * @author admin
 */
public class CheckCapitalizer {

    String d1;
    String d2;

    public CheckCapitalizer() {
    }

    /***
     * Checqueo de capitalización en cada inicio de sesión
     * cuando uno se loguea por primera vez en el día, esto se va a ejecutar
     * No se reciben ningún parámetro para esta operacion
     * Se Utilizan datos Internos como:
     * fc= fecha hasta que se procesó la actualización y/o capitalización de datos de las cuentas.
     * fu= fecha última o fecha próxima para realiozar inicio de capitalización de datos.
     * days= diferencia de días entre fc y fu numéricamente
     * days2= diferencia de dias entre fc y hoy numéricamente.
     * Se procesa todo cuando days==0 o hoy está después de fu.
     * INSERT INTO "public".t_transaccion_c (idtranscapita, tipo_operacion, fecha, fechaultima) VALUES ('20110502171839074473', 'CHECK', 'n', 'n')
     */
    public static boolean capitalizarHastaHoy(TTransaccionC sacc) {
        if (true) {
            return false;
        }
        if (sacc == null) {
            DAOGeneral dao = new DAOGeneral();
            Date now = new Date();
            try {
                sacc = (TTransaccionC) dao.load(TTransaccionC.class, "20110502171839074473");
            } catch (Exception e) {
                Logger.getLogger(CheckCapitalizer.class.getName()).log(Level.WARNING, "ERROR GENERAL:" + e.getMessage());
            }
            if (sacc == null) {
                try {
                    TTransaccionC newTT = new TTransaccionC();
                    newTT.setIdtranscapita("20110502171839074473");
                    newTT.setFecha(DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", now));
                    newTT.setFechaultima(DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", now));
                    newTT.setTipoOperacion("CHECK");
                    dao.persist(newTT);
                    sacc = newTT;
                } catch (Exception e) {
                    Logger.getLogger(CheckCapitalizer.class.getName()).log(Level.WARNING, "ERROR GENERAL:" + e.getMessage());
                }
            }
            Date capitalDate = null;
            try {
                capitalDate = DateUtil.convertStringToDate("yyyy/MM/dd HH:mm:ss", sacc.getFecha());
            } catch (Exception ex) {
                sacc.setFechaultima(sacc.getFecha());
                sacc.setFecha(DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", now));
                dao.update();
                Logger.getLogger(CheckCapitalizer.class.getName()).log(Level.WARNING, "ERROR DE LECTURA/VUELVA A INTENTARLO.");
                return false;
            }
            if (capitalDate == null) {
                sacc.setFechaultima(DateUtil.getNOW_S());
                sacc.setFecha(sacc.getFechaultima());
                dao.update();
                Logger.getLogger(CheckCapitalizer.class.getName()).log(Level.INFO, "ERROR DEL PASO DE VALORES");
                return false;
            } else {
                if (capitalDate.after(now)) {
                    Logger.getLogger(CheckCapitalizer.class.getName()).log(Level.WARNING, "ERROR EN LA FECHA DEL SISTEMA.");
                    return false;
                }
            }
        }
        DAOGeneral dao = new DAOGeneral();
        boolean capitalizado = false;
        Date now = new Date();
        if (sacc.getFecha().startsWith(DateUtil.getDateTime("yyyy/MM/dd", now))) {
            return capitalizado;
        } else {
            Date capitalDate;
            try {
                capitalDate = DateUtil.convertStringToDate("yyyy/MM/dd HH:mm:ss", sacc.getFecha());
            } catch (Exception ex) {
                Logger.getLogger(CheckCapitalizer.class.getName()).log(Level.SEVERE, null, ex);
                sacc.setFechaultima(sacc.getFecha());
                sacc.setFecha(DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", now));
                dao.update();
                return false;
            }
            if (capitalDate == null) {
                Logger.getLogger(CheckCapitalizer.class.getName()).log(Level.WARNING, "capitalDate = ERROR");
                return false;
            } else {
                if (capitalDate.before(now)) {
                    TTransaccionC saccS = (TTransaccionC) dao.load(TTransaccionC.class, "20110502171839074473");
                    saccS.setFecha(DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", now));
                    saccS.setFechaultima(DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", now));
                    dao.update();
                    Logger.getLogger(CheckCapitalizer.class.getName()).log(Level.INFO, "saccS CAPITALIZAR= " + saccS.getFecha());
                    return true;
                } else {
                    Logger.getLogger(CheckCapitalizer.class.getName()).log(Level.WARNING, "capitalDate = ERROR EN TIEMPO");
                    return false;
                }
            }
        }
    }
}
