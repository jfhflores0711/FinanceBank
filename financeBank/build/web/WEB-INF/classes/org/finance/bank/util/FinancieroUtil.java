package org.finance.bank.util;

import java.math.BigDecimal;

/**
 *
 * @author admin
 */
public class FinancieroUtil {
    /*
    VARIABLES Y ELEMENTOS DE LA MATEMATICA FINANCIERA
    ============================================================================
    Variable                                    Símbolo             Unidades
    ============================================================================
    Capital Monetario                           C (ó c)      Unidades monetarias
    Disponibilidad (Vencimiento)                t            Unidades de tiempo
    Capital Financiero                          (C; t) ó C_t
    Momento inicial (ó actual)                  t=0
    Momento final (ó n-ésimo)                   t=n
    Tipo de Interés (efectivo anual)            i
    Fraccionamiento                             m            Períodos de tiempo no anuales
    Tipo de Interés nominal con capitalización_ j(m)
    fraccionada m    
    Tipo de Interés efectivo fraccionado m     i^(m)
    Nominal letra de cambio                    N            Unidades monetarias
    Efectivo letra de cambio                   E            Unidades monetarias
    Descuento letra de cambio                  D            Unidades monetarias
     */

    public BigDecimal C = BigDecimal.ZERO;
    public BigDecimal t = BigDecimal.ZERO;
    public BigDecimal C_t = BigDecimal.ZERO;
    public BigDecimal t0;
    public BigDecimal tn;
    public BigDecimal i;
    public BigDecimal m;
    public BigDecimal j_m;
    public BigDecimal i__m;
    public BigDecimal N;
    public BigDecimal E;
    public BigDecimal D;

    /**
     * MAGNITUDES DERIVADAS
     * =================================================================
     *            Magnitud                         Fórmula
     * =================================================================
     *                                C_n          C_t+n
     * Factor                    f =  ---    ó f = -----
     *                                C_0           C_t
     *
     *                             ⎧1 − f si f ≤ 1
     * Rédito                    r=⎨
     *                             ⎩ f − 1 si f ≥ 1
     *
     *                               r            r
     *  Tanto                    i= ---    ó  i= -----
     *                              n−0          t+n−t
     *
     *
     */
    public static BigDecimal factor(BigDecimal C_n, BigDecimal C_0) {
        return C_n.divide(C_0);
    }

    public static BigDecimal redito(BigDecimal f) {
        return (f.doubleValue() <= 1D ? (BigDecimal.ONE.subtract(f)) : (f.subtract(BigDecimal.ONE)));
    }

    public static BigDecimal tanto(BigDecimal r, BigDecimal n) {
        return r.divide(n);
    }

    /***
     * REGIMEN DE INTERES SIMPLE
     * =========================================================================
     *           Concepto                             Fórmula
     * =========================================================================
     * Intereses Totales                             I = C *n *i
     * 
     * Capital acumulado                             C_n = C * (1 + n * i)
     * 
     * Efectivo con Descuento Comercial              E = N * (1 − n * i)  ó C' = C * (1 − n * i )
     * 
     *                                                      N                    C
     * Efectivo con Descuento Racional               E= ----------    ó C' = ----------
     *                                                  (1 + n * i)          (1 + n * i)
     * 
     * Descuento Comercial                           D = N * n * i  ó  D = C * n *i
     * 
     *                                                  N * n * i           C * n * i
     * Descuento Racional                            D= ---------   ó  D = ----------
     *                                                  1 + n * i           1 + n * i
     * 
     *                                                        i               j (m)
     * Tipo de interés fraccionado (m)               i^(m) = ---    ó i (m) = -----
     *                                                        m                m
     * 
     */
    public static BigDecimal Simple_Interes_Tot(BigDecimal C, BigDecimal n, BigDecimal i) {
        return C.multiply(n).multiply(i);
    }

    public static BigDecimal Simple_Capital_Ac(BigDecimal C, BigDecimal n, BigDecimal i) {
        return C.multiply(BigDecimal.ONE.add(n.multiply(i)));
    }



}
