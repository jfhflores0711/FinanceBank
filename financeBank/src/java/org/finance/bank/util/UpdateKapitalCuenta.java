package org.finance.bank.util;

/**
 *
 * @author ZAMORA
 */
public class UpdateKapitalCuenta {

    /***
     *
     * @param iANUAL Tasa de Interés Anual Nominal en Porcentaje
     * @return double
     */
    public static Double CalcularInteresDiario(double iANUAL) {
        double iD = iANUAL / 100.0D;
        double iTEAD = (StrictMath.pow((iD + 1.00D), (1.00D / 360.00D)));
        return iTEAD - 1.00D;
    }

    public static Double CalcularNuevoSaldo(double K, double iTEAD, int n) {
        return K * (Math.pow((1D + iTEAD), n));
    }
}
