package org.finance.bank.ds;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;

public class DBAfinancebank {

    private String driver;
    private String url;
    private String username;
    private String pwname;

    public DBAfinancebank(ServletContext ctx) {
        this.driver = ctx.getInitParameter("driver");
        this.url = ctx.getInitParameter("url");
        this.username = ctx.getInitParameter("username");
        this.pwname = ctx.getInitParameter("password");
    }

    public Connection getConnection() {
        try {
            Class.forName(driver).newInstance();
            Connection conn =
                    DriverManager.getConnection(this.url, this.username, this.pwname);
            return conn;
        } catch (SQLException sqle) {
            Logger.getLogger(DBAfinancebank.class.getName()).log(Level.INFO, "Error getConn=" + sqle.getMessage());
            return null;
        } catch (Exception e) {
            Logger.getLogger(DBAfinancebank.class.getName()).log(Level.INFO, "Error getConn=" + e.getMessage());
            return null;
        }
    }

    public void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
                conn = null;
            }
        } catch (SQLException sqle) {
            Logger.getLogger(DBAfinancebank.class.getName()).log(Level.INFO, "Error sqle=" + sqle.getMessage());
        }
    }
}
