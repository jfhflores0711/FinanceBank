package org.finance.bank.model.dao;

import org.finance.bank.model.BibliotecaException;

/**
 * Convertir todas las excepciones a traves de una 
 * llamada a una excepcion general en el sistema y tener un 
 * manejo más eficaz de los errores.
 */
public class DAOException extends BibliotecaException {

    private static final long serialVersionUID = 55455829291955849L;

    public DAOException() {
    }

    public DAOException(String e) {
        super(e);
    }

    public DAOException(Throwable e) {
        super(e);
        e.printStackTrace();
    }

    public DAOException(String s, Throwable e) {
        super(s, e);
        e.printStackTrace();
    }
}
