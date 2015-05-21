package org.finance.bank.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

/**
 *
 * @author oscar
 */
public class NumberUtil {

    private static final String MAX_INTEGER = String.valueOf(Integer.MAX_VALUE);
    private static final String MIN_INTEGER = String.valueOf(Integer.MIN_VALUE);
    private static final int LONG_MIN_VALUE_DIGITS = 19;
    private static final long[] DIGITS = {
        0L,
        10L,
        100L,
        1000L,
        10000L,
        100000L,
        1000000L,
        10000000L,
        100000000L,
        1000000000L,
        10000000000L,
        100000000000L,
        1000000000000L,
        10000000000000L,
        100000000000000L,
        1000000000000000L,
        10000000000000000L,
        100000000000000000L,
        1000000000000000000L
    };
    private static final char[] ZEROS = "000000000000000000".toCharArray();

    /*Tener cuidado*/
    public static Double format(String number) {
        Double ret = null;
        String work = number.replace(',', '.');
        ret = new Double(work);
        return ret;
    }

    public static boolean isValid(String number) {
        if (TextUtil.isEmptyString(number)) {
            return false;
        }
        String work = number.trim();
        char ch = '\0';
        for (int i = 0; i < work.length(); i++) {
            ch = work.charAt(i);
            if (!Character.isDigit(ch)) {
                if (ch == '-' && i == 0) {
                    continue;
                }
                if ((ch == ',' || ch == '.') && (i > 0) && (i < (work.length() - 1))) {
                    continue;
                }
                return false;
            }
        }
        return true;
    }

    public static boolean isValidInteger(String number) {
        if (TextUtil.isEmptyString(number)) {
            return false;
        }
        String work = number.trim();
        char ch = '\0';
        for (int i = 0; i < work.length(); i++) {
            ch = work.charAt(i);
            if (!Character.isDigit(ch)) {
                if (!(ch == '-' && i == 0)) {
                    return false;
                }
            }
        }
        if (work.charAt(0) == '-') {
            if (work.length() > MIN_INTEGER.length()) {
                return false;
            }
            if (work.length() == MIN_INTEGER.length() && work.compareTo(MIN_INTEGER) > 0) {
                return false;
            }
        } else {
            if (work.length() > MAX_INTEGER.length()) {
                return false;
            }

            if (work.length() == MAX_INTEGER.length() && work.compareTo(MAX_INTEGER) > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>
     * Tests whether a given number can be safely coerced to <tt>Double</tt> or
     * <tt>Integer</tt> without changing the value of the number.  Safe means
     * that coercing a number to <tt>Double</tt> or <tt>Integer</tt> and then
     * coercing it back to the original type will result in the same value (no
     * loss of precision).  This is trivial for types, which have a smaller
     * domain then <tt>Integer</tt> or <tt>Double</tt>, but for
     * example a <tt>Long</tt> number may not be safely coerced to
     * <tt>Integer</tt>.
     * </p><p>
     * If the coercion is safe, the number will be returned as either
     * <tt>Double</tt> or <tt>Integer</tt>, as appropriate to the original
     * precision.  Otherwise the original number is returned.
     * </p>
     *
     * @param number a number to coerce to <tt>Integer</tt> or <tt>Double</tt>
     * @return the coerced number, or the original number, if coercion was not safe
     */
    public static Number coerceNumber(Number number) {
        Number result;
        if ((number instanceof Integer) || (number instanceof Double)) {
            result = number;
        } else if ((number instanceof Byte) || (number instanceof Short)) {
            result = number.intValue();
        } else if (number instanceof Long) {
            if (canConform((Long) number)) {
                result = number.intValue();
            } else {
                result = number;
            }
        } else if (number instanceof BigInteger) {
            if (isInteger((BigInteger) number)) {
                result = number.intValue();
            } else {
                result = number;
            }
        } else if (number instanceof Float) {
            result = number.doubleValue();
        } else if (number instanceof BigDecimal) {
            if (isDouble((BigDecimal) number)) {
                result = number.doubleValue();
            } else {
                result = number;
            }
        } else {
            result = number;
        }
        return result;
    }

    private static boolean canConform(Long number) {
        long l = number;
        long i = number.intValue();
        return l == i;
    }

    private static boolean isInteger(BigInteger number) {
        int i = number.intValue();
        BigInteger b = new BigInteger(String.valueOf(i));
        return number.equals(b);
    }

    private static boolean isLong(BigInteger number) {
        long i = number.longValue();
        BigInteger b = new BigInteger(String.valueOf(i));
        return number.equals(b);
    }

    private static boolean isDouble(BigDecimal number) {
        double doubleValue = number.doubleValue();
        return (doubleValue != Double.NEGATIVE_INFINITY) && (doubleValue != Double.POSITIVE_INFINITY);
    }

    /**
     * <p>
     * Coerces the given number to <tt>Double</tt> or <tt>Long</tt> precision,
     * if possible.  Note that this is only impossible for <tt>BigDecimal</tt>
     * or <tt>BigInteger</tt> values, respectively, that are out of range of
     * their primitive counterparts.
     * </p>
     *
     * @param number a number to coerce to <tt>Long</tt> or <tt>Double</tt>
     * @return the coerced number, or the original number, in case of overflow
     */
    public static Number higherPrecisionNumber(Number number) {
        Number result;
        if ((number instanceof Integer) || (number instanceof Byte)
                || (number instanceof Short)) {
            result = number.longValue();
        } else if (number instanceof Long) {
            result = number;
        } else if (number instanceof BigInteger) {
            if (isLong((BigInteger) number)) {
                result = number.longValue();
            } else {
                result = number;
            }
        } else if (number instanceof Float) {
            result = number.doubleValue();
        } else if (number instanceof BigDecimal) {
            if (isDouble((BigDecimal) number)) {
                result = number.doubleValue();
            } else {
                result = number;
            }
        } else {
            result = number;
        }
        return result;
    }

    public static boolean isEmptyWithZero(Long longValue) {
        if (longValue == null) {
            return true;
        } else if (longValue == 0) {
            return true;
        }
        return false;
    }

    static public int getMaxProductSimplest(int[] param) {
        int minNum = param[0];
        int product = 1;
        for (int num1 : param) {
            product *= num1;
            if (num1 < minNum) {
                minNum = num1;
            }
        }
        return product / minNum;
    }

    static public int getMaxProductFull(int[] param) {
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        int maxProduct = 1;
        for (int i = 0; i < param.length; i++) {
            if (-1 == numbers.indexOf(param[i])) {
                int product = 1;
                for (int j = 0; j < param.length; j++) {
                    if (j != i) {
                        product *= param[j];
                    }
                }
                if (0 == i || product > maxProduct) {
                    maxProduct = product;
                }
                numbers.add(param[i]);
            }
        }
        return maxProduct;
    }

    /**
     * Counts the number of decimal digits needed to represent
     * the given value.
     * @param value the value to count for.
     * @return the number of decimal digits needed to represent
     * the given value.
     */
    public static int countDigits(long value) {
        int result;
        if (value == Long.MIN_VALUE) {
            result = LONG_MIN_VALUE_DIGITS;
        } else {
            final long test = Math.abs(value);
            for (result = 0; result < DIGITS.length; result++) {
                if (test < DIGITS[result]) {
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Convert the given unscaled long with the given scale to
     * it's string representation.
     * @param unscaledValue the scaled long.
     * @param scale the scale to be applied.
     * @return string representation of the scaled value using a dot
     *    '.' as decimal separator.
     */
    public static String toString(long unscaledValue, int scale) {
        final StringBuffer sb = new StringBuffer();
        sb.append(Math.abs(unscaledValue));
        final int missingDigits = 1 + scale - sb.length();
        if (missingDigits > 0) {
            sb.insert(0, ZEROS, 0, 1 + scale - sb.length());
        }
        if (scale > 0) {
            sb.insert(sb.length() - scale, '.');
        }
        if (unscaledValue < 0) {
            sb.insert(0, '-');
        }
        return sb.toString();
    }

    /**
     * Returns a random long number between the value <tt>min</tt>
     * and the value <tt>max</tt>.
     * @param min the minimum value.
     * @param max the maximum value.
     * @return a random long number between the value <tt>min</tt>
     *       and the value <tt>max</tt>.
     * @throws ArgumentMalformedException if the value <tt>min</tt>
     *       is greater than the value <tt>max</tt>
     */
    public static long random(long min, long max) {
        if (min > max) {
            throw new ArithmeticException("min" + String.valueOf(min)
                    + "Value min " + min + " must be lesser than value max " + max);
        }
        return (long) (Math.random() * (max - min) + min);
    }

    public static String convertToPercentLocaleUS(double number) {
        DecimalFormat df = new DecimalFormat("#,##0.##", new DecimalFormatSymbols(Locale.US));
        return df.format(number * 100) + "%";
    }
    private static int flag = 0;
    private static String num;
    private static String num_letra;
    private static String num_letras;
    private static String num_letram;
    private static String num_letradm;
    private static String num_letracm;
    private static String num_letramm;
    private static String num_letradmm;

    private static String unidad(int numero) {
        switch (numero) {
            case 9:
                num = "nueve";
                break;
            case 8:
                num = "ocho";
                break;
            case 7:
                num = "siete";
                break;
            case 6:
                num = "seis";
                break;
            case 5:
                num = "cinco";
                break;
            case 4:
                num = "cuatro";
                break;
            case 3:
                num = "tres";
                break;
            case 2:
                num = "dos";
                break;
            case 1:
                if (flag == 0) {
                    num = "uno";
                } else {
                    num = "un";
                }
                break;
            case 0:
                num = "";
                break;
        }
        return num;
    }

    private static String decena(int numero) {
        if (numero >= 90 && numero <= 99) {
            num_letra = "noventa ";
            if (numero > 90) {
                num_letra = num_letra.concat("y ").concat(unidad(numero - 90));
            }
        } else if (numero >= 80 && numero <= 89) {
            num_letra = "ochenta ";
            if (numero > 80) {
                num_letra = num_letra.concat("y ").concat(unidad(numero - 80));
            }
        } else if (numero >= 70 && numero <= 79) {
            num_letra = "setenta ";
            if (numero > 70) {
                num_letra = num_letra.concat("y ").concat(unidad(numero - 70));
            }
        } else if (numero >= 60 && numero <= 69) {
            num_letra = "sesenta ";
            if (numero > 60) {
                num_letra = num_letra.concat("y ").concat(unidad(numero - 60));
            }
        } else if (numero >= 50 && numero <= 59) {
            num_letra = "cincuenta ";
            if (numero > 50) {
                num_letra = num_letra.concat("y ").concat(unidad(numero - 50));
            }
        } else if (numero >= 40 && numero <= 49) {
            num_letra = "cuarenta ";
            if (numero > 40) {
                num_letra = num_letra.concat("y ").concat(unidad(numero - 40));
            }
        } else if (numero >= 30 && numero <= 39) {
            num_letra = "treinta ";
            if (numero > 30) {
                num_letra = num_letra.concat("y ").concat(unidad(numero - 30));
            }
        } else if (numero >= 20 && numero <= 29) {
            if (numero == 20) {
                num_letra = "veinte ";
            } else {
                num_letra = "veinti".concat(unidad(numero - 20));
            }
        } else if (numero >= 10 && numero <= 19) {
            switch (numero) {
                case 10:
                    num_letra = "diez ";
                    break;
                case 11:
                    num_letra = "once ";
                    break;
                case 12:
                    num_letra = "doce ";
                    break;
                case 13:
                    num_letra = "trece ";
                    break;
                case 14:
                    num_letra = "catorce ";
                    break;
                case 15:
                    num_letra = "quince ";
                    break;
                case 16:
                    num_letra = "dieciseis ";
                    break;
                case 17:
                    num_letra = "diecisiete ";
                    break;
                case 18:
                    num_letra = "dieciocho ";
                    break;
                case 19:
                    num_letra = "diecinueve ";
                    break;
            }
        } else {
            num_letra = unidad(numero);
        }
        return num_letra;
    }

    private static String centena(int numero) {
        flag = 0;
        if (numero >= 100) {
            if (numero >= 900 && numero <= 999) {
                num_letra = "novecientos ";
                if (numero > 900) {
                    num_letra = num_letra.concat(decena(numero - 900));
                }
            } else if (numero >= 800 && numero <= 899) {
                num_letra = "ochocientos ";
                if (numero > 800) {
                    num_letra = num_letra.concat(decena(numero - 800));
                }
            } else if (numero >= 700 && numero <= 799) {
                num_letra = "setecientos ";
                if (numero > 700) {
                    num_letra = num_letra.concat(decena(numero - 700));
                }
            } else if (numero >= 600 && numero <= 699) {
                num_letra = "seiscientos ";
                if (numero > 600) {
                    num_letra = num_letra.concat(decena(numero - 600));
                }
            } else if (numero >= 500 && numero <= 599) {
                num_letra = "quinientos ";
                if (numero > 500) {
                    num_letra = num_letra.concat(decena(numero - 500));
                }
            } else if (numero >= 400 && numero <= 499) {
                num_letra = "cuatrocientos ";
                if (numero > 400) {
                    num_letra = num_letra.concat(decena(numero - 400));
                }
            } else if (numero >= 300 && numero <= 399) {
                num_letra = "trescientos ";
                if (numero > 300) {
                    num_letra = num_letra.concat(decena(numero - 300));
                }
            } else if (numero >= 200 && numero <= 299) {
                num_letra = "doscientos ";
                if (numero > 200) {
                    num_letra = num_letra.concat(decena(numero - 200));
                }
            } else if (numero >= 100 && numero <= 199) {
                if (numero == 100) {
                    num_letra = "cien ";
                } else {
                    num_letra = "ciento ".concat(decena(numero - 100));
                }
            }
        } else {
            num_letra = decena(numero);
        }
        return num_letra;
    }

    private static String miles(int numero) {
        if (numero >= 1000 && numero < 2000) {
            num_letram = ("mil ").concat(centena(numero % 1000));
        }
        if (numero >= 2000 && numero < 10000) {
            flag = 1;
            num_letram = unidad(numero / 1000).concat(" mil ").concat(centena(numero % 1000));
        }
        if (numero < 1000) {
            num_letram = centena(numero);
        }

        return num_letram;
    }

    private static String decmiles(int numero) {
        if (numero == 10000) {
            num_letradm = "diez mil";
        }
        if (numero > 10000 && numero < 20000) {
            flag = 1;
            num_letradm = decena(numero / 1000).concat("mil ").concat(centena(numero % 1000));
        }
        if (numero >= 20000 && numero < 100000) {
            flag = 1;
            num_letradm = decena(numero / 1000).concat(" mil ").concat(miles(numero % 1000));
        }
        if (numero < 10000) {
            num_letradm = miles(numero);
        }

        return num_letradm;
    }

    private static String cienmiles(int numero) {
        if (numero == 100000) {
            num_letracm = "cien mil";
        }
        if (numero >= 100000 && numero < 1000000) {
            flag = 1;
            num_letracm = centena(numero / 1000).concat(" mil ").concat(centena(numero % 1000));
        }
        if (numero < 100000) {
            num_letracm = decmiles(numero);
        }
        return num_letracm;
    }

    private static String millon(int numero) {
        if (numero >= 1000000 && numero < 2000000) {
            flag = 1;
            num_letramm = ("Un millon ").concat(cienmiles(numero % 1000000));
        }
        if (numero >= 2000000 && numero < 10000000) {
            flag = 1;
            num_letramm = unidad(numero / 1000000).concat(" millones ").concat(cienmiles(numero % 1000000));
        }
        if (numero < 1000000) {
            num_letramm = cienmiles(numero);
        }

        return num_letramm;
    }

    private static String decmillon(int numero) {
        if (numero == 10000000) {
            num_letradmm = "diez millones";
        }
        if (numero > 10000000 && numero < 20000000) {
            flag = 1;
            num_letradmm = decena(numero / 1000000).concat("millones ").concat(cienmiles(numero % 1000000));
        }
        if (numero >= 20000000 && numero < 100000000) {
            flag = 1;
            num_letradmm = decena(numero / 1000000).concat(" millones ").concat(millon(numero % 1000000));
        }
        if (numero < 10000000) {
            num_letradmm = millon(numero);
        }
        return num_letradmm;
    }

    public static String convertirLetras(int numero) {
        if (numero > 99999999) {
            return "El Número está fuera de rango";
        }
        num_letras = decmillon(numero);
        return num_letras;
    }

    public static String convertirLetrasDecimal(double numero) {
        String dec = DateUtil.eedisplayFloatND(numero, 2);
        num_letras = convertirLetras((int) numero);
        return num_letras + " con " + dec.substring(dec.indexOf(".") + 1) + "/100";
    }

    public static String converseN2S(String n) {
        String s = "";
        if (n != null) {
            n = n.trim();
            n = n.replace("0", "Z");
            n = n.replace("1", "O");
            n = n.replace("2", "W");
            n = n.replace("3", "T");
            n = n.replace("4", "F");
            n = n.replace("5", "V");
            n = n.replace("6", "X");
            n = n.replace("7", "S");
            n = n.replace("8", "H");
            n = n.replace("9", "N");
            n = n.replace(".", "P");
            for (int x = n.length() - 1; x >= 0; x--) {
                s += n.charAt(x);
            }
        }
        return s;
    }

    public static String converseS2N(String s) {
        String n = "";
        if (s != null) {
            s = s.trim();
            s = s.replace("Z", "0");
            s = s.replace("O", "1");
            s = s.replace("W", "2");
            s = s.replace("T", "3");
            s = s.replace("F", "4");
            s = s.replace("V", "5");
            s = s.replace("X", "6");
            s = s.replace("S", "7");
            s = s.replace("H", "8");
            s = s.replace("N", "9");
            s = s.replace("P", ".");
            for (int x = s.length() - 1; x >= 0; x--) {
                n += s.charAt(x);
            }
        }
        return n;
    }
}
