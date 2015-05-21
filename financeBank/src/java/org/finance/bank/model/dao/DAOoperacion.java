package org.finance.bank.model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.finance.bank.model.BOperacion;

/**
 *
 * @author roger
 */
public class DAOoperacion {

    private Connection conn;

    public DAOoperacion(Connection conn) {
        this.conn = conn;
    }
    /*
    public Vector listarOperacion(String idCuenta) {
    ResultSet rs = null;
    String sql = "SELECT T_OPERACION.ID_OPERACION, T_OPERACION.DESCRIPCION, T_OPERACION.FECHA, T_TIPO_OPERACION.NOMBRE FROM T_OPERACION,T_TIPO_OPERACION"
    + " WHERE T_OPERACION.CODIGO_TIPO_OPERACION=T_TIPO_OPERACION.CODIGO_TIPO_OPERACION"
    + " ORDER BY T_OPERACION.FECHA ASC";
    Vector listaOperacion = new Vector();
    try {
    int n = 0;
    Statement stmt = conn.createStatement();
    rs = stmt.executeQuery(sql);
    while (rs.next()) {
    BOperacion bop = new BOperacion();
    bop.setIdOperacion(rs.getString("ID_OPERACION"));
    bop.setDescripcion(rs.getString("DESCRIPCION"));
    bop.setFecha(rs.getString("FECHA"));
    bop.setTipoOperacion(rs.getString("NOMBRE"));
    listaOperacion.add(n++, bop);
    }
    rs.close();
    } catch (SQLException sqle) {
    Logger.getLogger(DAOoperacion.class.getName()).log(Level.SEVERE, "sqle en meta=" + sqle.getMessage());
    } catch (Exception e) {
    Logger.getLogger(DAOoperacion.class.getName()).log(Level.WARNING, "e=" + e.getMessage());
    }
    return listaOperacion;
    }*/
}
