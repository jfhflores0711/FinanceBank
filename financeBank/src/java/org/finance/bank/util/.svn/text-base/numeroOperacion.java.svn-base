package org.finance.bank.util;

import java.util.Date;
import java.util.List;
import org.finance.bank.model.dao.DAOGeneral;

/**
 *
 * @author ronald
 */
public final class numeroOperacion {

    public static String getNumber(String codFilial, String codCaja) {
        String nuevonumero = "";
        DAOGeneral d = new DAOGeneral();
        String cadena = DateUtil.getDateTime("yyyyMMdd", new Date());
        cadena += codFilial + codCaja;
        int numero = 0;
        String hql = "select max(op.numeroOperacion) from TOperacion op where op.numeroOperacion like '" + cadena + "%' "
                + " AND op.TPersonaCaja.TCaja.TFilial.codFilial ='" + codFilial + "' AND op.TPersonaCaja.TCaja.codCaja='" + codCaja + "'";
        List result2 = d.findAll(hql);
        String numeroOp = (String) result2.get(0);
        if (numeroOp == null) {
            nuevonumero = "0001";
        } else {
            numeroOp = numeroOp.replaceAll(cadena, "");
            numero = Integer.parseInt(numeroOp);
            numero = numero + 1;
            nuevonumero = Integer.toString(numero);
        }
        if (nuevonumero.length() < 4) {
            nuevonumero = "0000".substring(nuevonumero.length()) + nuevonumero;
        }
        nuevonumero = cadena + nuevonumero;
        d.cerrarSession();
        return nuevonumero;
    }
}
