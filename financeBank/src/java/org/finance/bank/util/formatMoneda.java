package org.finance.bank.util;

import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author ronald
 */
public class formatMoneda {

    public static String formatMoneda(Double d) {
        String decimal = "";
        if (d >= 0) {
            Locale locale = Locale.US;
            NumberFormat noDecimals;
            NumberFormat decimals;
            decimals = NumberFormat.getCurrencyInstance(locale);
            noDecimals = NumberFormat.getCurrencyInstance(locale);
            noDecimals.setMaximumFractionDigits(0);
            decimal = decimals.format(d);
            decimal = decimal.substring(1);
        } else {
            d = d * -1;
            Locale locale = Locale.US;
            NumberFormat noDecimals;
            NumberFormat decimals;
            decimals = NumberFormat.getCurrencyInstance(locale);
            noDecimals = NumberFormat.getCurrencyInstance(locale);
            noDecimals.setMaximumFractionDigits(0);
            decimal = decimals.format(d);
            decimal = decimal.substring(1);
            decimal = "-" + decimal;
        }
        return decimal;
    }
}
