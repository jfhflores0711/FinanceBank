package org.finance.bank.util;

/**
 *
 * @author oscar
 */
import java.math.BigDecimal;
import java.math.BigInteger;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.finance.bank.model.BibliotecaException;

/**
 * This class is converts a Double to a double-digit String
 * (and vise-versa) by BeanUtils when copying properties.
 *
 */
public class CurrencyConverter implements Converter {

    public static final String[] fmtstrings = new String[]{",", " ", "S/.", "%", "€", "/", "$"};
    private final Log log = LogFactory.getLog(CurrencyConverter.class);
    private DecimalFormat formatter = new DecimalFormat("###,###.00");

    public void setDecimalFormatter(DecimalFormat df) {
        this.formatter = df;
    }

    /**
     * Convert a String to a Double and a Double to a String
     *
     * @param type the class type to output
     * @param value the object to convert
     * @return object the converted object (Double or String)
     */
    public final Object convert(final Class type, final Object value) {
        if (value == null) {
            return null;
        } else {
            if (value instanceof String) {
                if (log.isDebugEnabled()) {
                    log.debug("value (" + value + ") instance of String");
                }
                try {
                    if (StringUtils.isBlank(String.valueOf(value))) {
                        return null;
                    }
                    if (log.isDebugEnabled()) {
                        log.debug("converting '" + value + "' to a decimal");
                    }
                    Number num = formatter.parse(String.valueOf(value));
                    return num.doubleValue();
                } catch (ParseException pe) {
                    pe.printStackTrace();
                }
            } else if (value instanceof Double) {
                if (log.isDebugEnabled()) {
                    log.debug("value (" + value + ") instance of Double");
                    log.debug("returning double: " + formatter.format(value));
                }
                return formatter.format(value);
            }
        }
        throw new ConversionException("Could not convert " + value + " to " + type.getName() + "!");
    }

    /**El patrón original era '"¤ #,##0.##"' y se ha obviado el símbolo de la moneda*/
    public static String formatToMoneyUS(double number, int dec) {
        DecimalFormat df;
        if (dec > 0 && dec <= 10) {
            df = new DecimalFormat("#,##0." + "0000000000".substring(0, dec), new DecimalFormatSymbols(Locale.US));
        } else if (dec == 0) {
            df = new DecimalFormat("#,##0", new DecimalFormatSymbols(Locale.US));
        } else {
            df = new DecimalFormat("#,##0.##", new DecimalFormatSymbols(Locale.US));
        }
        return (df.format(number).equals("-0.00")?"0.00":df.format(number));
    }

    public static double formatMoneyToDoubleUS(String money) throws BibliotecaException {
        DecimalFormat df = new DecimalFormat("#,##0.##", new DecimalFormatSymbols(Locale.US));
        Number n = null;
        try {
            n = df.parse(money);
        } catch (ParseException e) {
            throw new BibliotecaException(e);
        }
        return n.doubleValue();
    }

    public static Map<String, BigDecimal> recoveryOnBilletes(BigDecimal monto, ArrayList<Double> orderedValues) {
        Map<String, BigDecimal> mapa = new HashMap<String, BigDecimal>();
        if (monto == null || monto.doubleValue() <= 0 || orderedValues == null || orderedValues.isEmpty()) {
            return null;
        }
        for (int i = 0; i < orderedValues.size(); i++) {
            //if ((monto.doubleValue() % orderedValues.get(i)) < orderedValues.get(i)) {
                mapa.put(String.valueOf(orderedValues.get(i)), new BigDecimal((monto.doubleValue() - monto.doubleValue() % orderedValues.get(i)) / orderedValues.get(i)));
                monto = new BigDecimal(monto.doubleValue() % orderedValues.get(i));
            //}
        }
        return mapa;
    }
}
