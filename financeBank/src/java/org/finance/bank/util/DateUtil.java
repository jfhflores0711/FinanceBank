package org.finance.bank.util;

/**
 *
 * @author oscar
 */
import java.text.DateFormat;
import java.text.NumberFormat;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Date Utility Class used to convert Strings to Dates and Timestamps
 * Minutes should be mm not MM (MM is month).
 */
public class DateUtil {

    private static Log log = LogFactory.getLog(DateUtil.class);
    private static final String TIME_PATTERN = "HH:mm:ss";
    public static final int eeisus = 0;
    public static final char eedec = '.';
    public static final char eeth = ' ';
    public static final String[] fmtdaynamesshort = new String[]{"dom", "lun", "mar", "mié", "jue", "vie", "sáb"};
    public static final String[] fmtdaynameslong = new String[]{"domingo", "lunes", "martes", "miércoles", "jueves", "viernes", "sábado"};
    public static final String[] fmtmonthnamesshort = new String[]{"ene", "feb", "mar", "abr", "may", "jun", "jul", "ago", "sep", "oct", "nov", "dic"};
    public static final String[] fmtmonthnameslong = new String[]{"enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"};
    private static final String[] fmtstrings = new String[]{",", " ", "$", "\\", "/", "€", "S/.", "pta", "￥", " de ", "¥", "-", ":", " ", "de", "₩"};
    public static final int[] fmtdate6 = new int[]{34, 25, 2}; //--> $# ##0.##
    public static final int[] fmtdate7 = new int[]{7, 35, 36, 3, 35, 36, 11}; //-->  06\/07\/2009
    public static final int[] fmtdate8 = new int[]{25, 2, 33, 37}; //--> # ##0.## €
    public static final int[] fmtdate9 = new int[]{38, 33, 25, 2}; //--> S/. # ##0.##
    public static final int[] fmtdate10 = new int[]{9, 32, 33, 5, 33, 7, 32, 33, 11}; //--> lunes, julio 06, 2009
    public static final int[] fmtdate11 = new int[]{25, 2, 33, 39}; //--> # ##0.## pta
    public static final int[] fmtdate12 = new int[]{40, 25, 2}; //--> ￥# ##0.##
    public static final int[] fmtdate13 = new int[]{9, 32, 33, 7, 41, 5, 41, 11}; //--> lunes, 06 de julio de 2009
    public static final int[] fmtdate14 = new int[]{9}; //--> lunes
    public static final int[] fmtdate15 = new int[]{42, 25, 2}; //--> ¥40 000.00
    public static final int[] fmtdate16 = new int[]{6, 43, 2, 43, 10, 33, 14, 44, 17, 33, 21}; //-->  6-7-09 12:00 AM
    public static final int[] fmtdate17 = new int[]{45, 33, 25, 2}; //-->  # ##0.##
    public static final int[] fmtdate18 = new int[]{6, 33, 46, 33, 5, 33, 46, 33, 11}; //--> 6 de julio de 2009
    public static final int[] fmtdate19 = new int[]{34, 25, 2}; //-->  $# ##0.##
    public static final int[] fmtdate20 = new int[]{37, 33, 25, 2}; //--> € # ##0.##
    public static final int[] fmtdate21 = new int[]{6, 43, 4, 43, 11}; //--> 6-jul-2009
    public static final int[] fmtdate22 = new int[]{25, 2, 33, 37}; //--> # ##0.## €
    public static final int[] fmtdate23 = new int[]{24, 0, 43}; //--> # ##0.##-
    public static final int[] fmtdate24 = new int[]{7, 36, 3, 36, 11}; //--> 06/07/2009
    public static final int[] fmtdate25 = new int[]{47, 25, 2}; //--> ₩# ##0.##
    public static final int[] fmtdate26 = new int[]{9, 32, 33, 5, 33, 7, 32, 33, 11}; //--> lunes, julio 06, 2009

    public static String getDateAnt(Date date) {
        SimpleDateFormat df;
        String returnValue = "";
        if (date != null) {
            df = new SimpleDateFormat(getDatePattern());
            Calendar a = Calendar.getInstance();
            a.setTime(date);
            a.add(Calendar.DATE, - 1);
            date = a.getTime();
            returnValue = df.format(date);
        }
        return (returnValue);
    }

    public static Date getDateDownDay(Date date) {
        if (date != null) {
            Calendar a = Calendar.getInstance();
            a.setTime(date);
            a.add(Calendar.DATE, - 1);
            date = a.getTime();
        }
        return (date);
    }

    public static Date getDateUpDay(Date d) {
        if (d != null) {
            Calendar a = Calendar.getInstance();
            a.setTime(d);
            a.add(Calendar.DATE, +1);
            d = a.getTime();
        }
        return (d);
    }

    public static String getDateAntFull(Date date) {
        SimpleDateFormat df;
        String returnValue = "";
        if (date != null) {
            df = new SimpleDateFormat(getDatePattern() + " HH:mm:ss");
            Calendar a = Calendar.getInstance();
            a.setTime(date);
            a.add(Calendar.DATE, - 1);
            date = a.getTime();
            returnValue = df.format(date);
        }
        return (returnValue);
    }

    /**
     * Checkstyle rule: utility classes should not have public constructor
     */
    private DateUtil() {
    }

    /**
     * Return default datePattern (yyyy/MM/dd)
     * @return a string representing the date pattern on the UI
     */
    public static String getDatePattern() {
        Locale locale = LocaleContextHolder.getLocale();
        String defaultDatePattern;
        try {
            defaultDatePattern = ResourceBundle.getBundle(Constants.BUNDLE_KEY, locale).getString("date.format");
        } catch (MissingResourceException mse) {
            defaultDatePattern = "yyyy/MM/dd";
        }
        return defaultDatePattern;
    }

    public static String getDateTimePattern() {
        return DateUtil.getDatePattern() + " HH:mm:ss.SSS";
    }

    public static String getNOW_S() {
        return DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", new Date());
    }

    /**
     * This method attempts to yyyy/MM/dd.
     *
     * @param aDate date from database as a string
     * @return formatted string for the ui
     */
    public static String getDate(Date aDate) {
        SimpleDateFormat df;
        String returnValue = "";
        if (aDate != null) {
            df = new SimpleDateFormat(getDatePattern());
            returnValue = df.format(aDate);
        }
        return (returnValue);
    }

    /**
     * This method generates a string representation of a date/time
     * in the format you specify on input
     *
     * @param aMask the date pattern the string is in
     * @param strDate a string representation of a date
     * @return a converted Date object
     * @see java.text.SimpleDateFormat
     * @throws ParseException when String doesn't match the expected format
     */
    public static Date convertStringToDate(String aMask, String strDate) {
        SimpleDateFormat df;
        Date date = new Date();
        df = new SimpleDateFormat(aMask);
        if (log.isDebugEnabled()) {
            log.debug("converting '" + strDate + "' to date with mask '" + aMask + "'");
        }
        try {
            if (strDate != null) {
                date = df.parse(strDate);
            }
        } catch (ParseException pe) {
            date = new Date();
            log.error("ParseException: " + pe);
        }
        return (date);
    }

    /**
     * This method returns the current date time in the format:
     * yyyy/MM/dd HH:mm:ss a
     *
     * @param theTime the current time
     * @return the current date/time
     */
    public static String getTimeNow(Date theTime) {
        return getDateTime(TIME_PATTERN, theTime);
    }

    /**
     * This method returns the current date in the format: yyyy/MM/dd
     *
     * @return the current date
     * @throws ParseException when String doesn't match the expected format
     */
    public static Calendar getToday() throws ParseException {
        Date today = new Date();
        SimpleDateFormat df = new SimpleDateFormat(getDatePattern());
        String todayAsString = df.format(today);
        Calendar cal = new GregorianCalendar();
        cal.setTime(convertStringToDate(todayAsString));
        return cal;
    }

    public static final double today() {
        java.util.Calendar dte = java.util.Calendar.getInstance();
        dte.setTime(new java.util.Date());
        return date(dte.get(java.util.Calendar.YEAR),
                dte.get(java.util.Calendar.MONTH) + 1,
                dte.get(java.util.Calendar.DATE));
    }

    /**
     * This method generates a string representation of a date's date/time
     * in the format you specify on input
     *
     * @param aMask the date pattern the string is in
     * @param aDate a date object
     * @return a formatted string representation of the date
     *
     * @see java.text.SimpleDateFormat
     */
    public static String getDateTime(String aMask, Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";
        if (aDate == null) {
            log.error("aDate is null!");
        } else {
            df = new SimpleDateFormat(aMask);
            returnValue = df.format(aDate);
        }
        return (returnValue);
    }

    /**
     * This method generates a string representation of a date based
     * on the System Property 'dateFormat'
     * in the format you specify on input
     *
     * @param aDate A date to convert
     * @return a string representation of the date
     */
    public static String convertDateToString(Date aDate) {
        return getDateTime(getDateTimePattern(), aDate);
    }

    /**
     * This method converts a String to a date using the datePattern
     *
     * @param strDate the date to convert (in format yyyy/MM/dd)
     * @return a date object
     * @throws ParseException when String doesn't match the expected format
     */
    public static Date convertStringToDate(String strDate)
            throws ParseException {
        Date aDate = null;
        try {
            if (log.isDebugEnabled()) {
                log.debug("converting date with pattern: " + getDatePattern());
            }
            aDate = convertStringToDate(getDatePattern(), strDate);
        } catch (Exception pe) {
            log.error("Could not convert '" + strDate + "' to a date, throwing exception");
            pe.printStackTrace();
            aDate = new Date();
        }
        return aDate;
    }

    /**
     * This method converts a String to a date using the datePattern (in format yyyymmddHHmmssSSSSSS)
     *
     * @param none
     * @return a date String yyyymmddHHmmssSSSSSS
     * @throws none
     */
    public static String convertDateId() {
        String everId = "";
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        everId = df.format(new Date()) + Double.toString(System.nanoTime() * 100000L / Math.random()).replace(".", "").replace("-", "0");
        return everId.substring(0, 21);
    }

    /*
     * este metodo convertDateId recibe parametros y los bloquea en la base de datos
     * @param a id de algún usuario, usuariocaja, ect
     * @param b nombre de la clase que bloquea
     * @return Un String único y bloqueado
     * @throws none
     */
    public static synchronized String convertDateId(String a, String b) {
        String idForInitNewestTable = "";
        boolean ne = false;
        while (!ne) {
            idForInitNewestTable = DateUtil.convertDateId();
            ne = HibernateUtil.dbTestId(idForInitNewestTable, a, b, "1");
        }
        return idForInitNewestTable;
    }

    public static String convertDateIdH(Date a) {
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        return df.format(a) + "0000000000000";
    }

    /* Run-time Library */
    private static final String zeroes = "00000000000000000000";
    //private static final Double Double_NaN = new Double(Double.NaN);
    private static final String String_NaN = "";
    //private static final boolean Boolean_NaN = false;

    public static final String String_valueOf(double x) {
        NumberFormat nf = NumberFormat.getInstance(Locale.US);
        String str = nf.format(x);
        if (str.endsWith(".0")) {
            return str.substring(0, str.length() - 2);
        } else {
            return str;
        }
    }

    private static final String[] js_string_split_char(String str, char chr) {
        int counter = 0;
        for (int ii = 0; ii < str.length(); ii++) {
            if (str.charAt(ii) == chr) {
                counter++;
            }
        }
        String[] res = new String[counter + 1];
        if (counter == 0) {
            res[0] = str;
        } else {
            int nextFree = 0;
            int prevMatch = -1;
            for (int ii = 0; ii < str.length(); ii++) {
                if (str.charAt(ii) == chr) {
                    res[nextFree++] = str.substring(prevMatch + 1, ii);
                    prevMatch = ii;
                }
            }
            if (prevMatch == str.length() - 1) {
                res[nextFree] = "";
            } else {
                res[nextFree] = str.substring(prevMatch + 1, str.length());
            }
        }
        return res;
    }

    public static final boolean myIsNaN(Object x) {
        if (x instanceof Double) {
            return !isFinite(((Double) x).doubleValue());
        } else if (x instanceof String) {
            return true;
        }
        return false;
    }

    private static final boolean myIsNaNDouble(double x) {
        return !isFinite(x);
    }

    public static final double mod(double n, double d) {
        return n - d * Math.floor(n / d);
    }

    public static final double round(double n, double nd) {
        if (isFinite(n) && isFinite(nd)) {
            double sign_n = (n < 0) ? -1 : 1;
            double abs_n = Math.abs(n);
            double factor = Math.pow(10, nd);
            return sign_n * Math.round(abs_n * factor) / factor;
        } else {
            return Double.NaN;
        }
    }

    private static final String n2s(double x) {
        return String_valueOf(x);
    }

    private static final double s2n(String str) {
        str = str.replace(eedec, '.');
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return Double.NaN;
        }
    }

    public static final double eeparseFloat(String str) {
        String str2 = str.replace(eedec, '.');
        try {
            return Double.parseDouble(str2);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static final double eeparsePercent(String str) {
        String[] parts = js_string_split_char(str.replace(",", ""), '%');
        String tmp = parts[0].replace(eedec, '.');
        try {
            return Double.parseDouble(tmp) / 100;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static final String v2s(Object v) {
        if (v instanceof Double) {
            return n2s(((Double) v).doubleValue());
        }
        if (v instanceof Boolean) {
            return b2s(((Boolean) v).booleanValue());
        }
        if (v instanceof String) {
            return (String) v;
        }
        return String_NaN;
    }

    public static final double v2n(Object v) {
        if (v instanceof Double) {
            return ((Double) v).doubleValue();
        }
        if (v instanceof Boolean) {
            return ((Boolean) v).booleanValue() ? 1 : 0;
        }
        if (v instanceof String) {
            return s2n((String) v);
        }
        return Double.NaN;
    }

    public static final String eedisplayFloat(double x) {
        if (myIsNaNDouble(x)) {
            return String.valueOf(Double.NaN);
        } else {
            return (String_valueOf(x)).replace('.', eedec);
        }
    }

    public static final String eedisplayScientific(double x, int nd) {
        if (myIsNaNDouble(x)) {
            return String.valueOf(Double.NaN);
        } else {
            java.text.NumberFormat formatter = new java.text.DecimalFormat();
            formatter = new java.text.DecimalFormat("0." + "00000000000000000000".substring(0, nd) + "E0");
            return (formatter.format(x)).replace('.', eedec);
        }
    }

    public static final String eedisplayFloatND(double x, int nd) {
        if (myIsNaNDouble(x)) {
            return String.valueOf(Double.NaN);
        } else {
            double res = round(x, nd);
            String str = String_valueOf(res);
            if (str.indexOf('e') != -1) {
                return str;
            }
            if (str.indexOf('E') != -1) {
                return str;
            }
            if (nd > 0) {
                String[] parts = js_string_split_char(str.replace(",", ""), '.');
                if (parts.length < 2) {
                    String decimals = (zeroes).substring(0, nd);
                    return parts[0] + eedec + decimals;
                } else {
                    String decimals = (parts[1] + zeroes).substring(0, nd);
                    return parts[0] + eedec + decimals;
                }
            } else {
                if (str.endsWith(".0")) {
                    str = str.substring(0, str.length() - 2);
                }
                return (str);
            }
        }
    }

    public static final String eedisplayPercent(double x) {
        if (myIsNaNDouble(x)) {
            return String.valueOf(Double.NaN);
        } else {
            String tmp = String_valueOf(x * 100) + '%';
            return tmp.replace('.', eedec);
        }
    }

    public static final String eedisplayPercentND(double x, int nd) {
        if (myIsNaNDouble(x)) {
            return String.valueOf(Double.NaN);
        } else {
            return eedisplayFloatND(x * 100, nd) + '%';
        }
    }

    public static final String eedisplayFloatNDTh(double x, int nd) {
        if (myIsNaNDouble(x)) {
            return String.valueOf(Double.NaN);
        } else {
            double res = round(x, nd);
            String str = String_valueOf(res);
            if (str.indexOf('e') != -1) {
                return str;
            }
            if (str.indexOf('E') != -1) {
                return str;
            }
            if (nd > 0) {

                String[] parts = js_string_split_char(str.replace(",", ""), '.');
                String res2 = eeinsertThousand(parts[0]);
                if (parts.length < 2) {
                    String decimals = (zeroes).substring(0, nd);
                    return (res2 + eedec + decimals);
                } else {
                    String decimals = (parts[1] + zeroes).substring(0, nd);
                    return (res2 + eedec + decimals);
                }
            } else {
                if (str.endsWith(".0")) {
                    str = str.substring(0, str.length() - 2);
                }
                return (eeinsertThousand(str));
            }
        }
    }

    public static final String eedisplayPercentNDTh(double x, int nd) {
        if (myIsNaNDouble(x)) {
            return String.valueOf(Double.NaN);
        } else {
            return eedisplayFloatNDTh(x * 100, nd) + '%';
        }
    }

    public static final double eeparseFloatTh(String str) {
        String str1 = js_string_delete_char(str, eeth);
        String str2 = str1.replace(eedec, '.');
        try {
            return Double.parseDouble(str2);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static final Object eeparseFloatV(String str) {
        if (str.compareTo("") == 0) {
            return str;
        }
        String str2 = str.replace(eedec, '.');
        try {
            return new Double(Double.parseDouble(str2));
        } catch (NumberFormatException e) {
            return str;
        }
    }

    private static final String eeinsertThousand(String whole) {
        if (whole.compareTo("") == 0 || whole.indexOf("e") >= 0) {
            return whole;
        } else {
            String minus_sign = "";
            if (whole.charAt(0) == '-') {
                minus_sign = "-";
                whole = whole.substring(1);
            }
            String res = "";
            int str_length = whole.length() - 1;
            for (int ii = 0; ii <= str_length; ii++) {
                if (ii > 0 && ii % 3 == 0) {
                    res = eeth + res;
                }
                res = whole.charAt(str_length - ii) + res;
            }
            return minus_sign + res;
        }
    }

    private static final String js_string_delete_char(String str, char chr) {
        StringBuffer res = new StringBuffer(str.length());
        for (int ii = 0; ii < str.length(); ii++) {
            char current = str.charAt(ii);
            if (current != chr) {
                res.append(current);
            }
        }
        return res.toString();
    }

    private static final int mytypeof(Object v) {
        if (v instanceof Double) {
            if (myIsNaNDouble(((Double) v).doubleValue())) {
                return 4;
            }
            return 1;
        } else if (v instanceof String) {
            return 2;
        } else if (v instanceof Boolean) {
            return 3;
        }
        return 4;
    }

    public static final boolean isFinite(double x) {
        return !(Double.isInfinite(x) || Double.isNaN(x));
    }

    private static final long mod_long(long n, long d) {
        return n - d * (n / d);
    }

    public static final boolean eeisnumber(Object v) {
        if (v instanceof Double) {
            return isFinite(((Double) v).doubleValue());
        }
        return false;
    }

    public static final double js_parseFloat(String str) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return Double.NaN;
        }
    }

    public static final boolean leap_gregorian(long year) {
        return ((year % 4) == 0)
                && (!(((year % 100) == 0) && ((year % 400) != 0)));
    }
    public static final long GREGORIAN_EPOCH = 1721425;

    public static final long gregorian_to_jd(long year, long month, long day) {
        return GREGORIAN_EPOCH
                + (365 * (year - 1))
                + ((year - 1) / 4)
                + (-((year - 1) / 100))
                + ((year - 1) / 400)
                + ((((367 * month) - 362) / 12)
                + ((month <= 2) ? 0 : (leap_gregorian(year) ? -1 : -2))
                + day);
    }

    public static final long[] jd_to_gregorian(long jd) {
        long wjd, depoch, quadricent, dqc, cent, dcent, quad, dquad,
                yindex, year, yearday, leapadj;
        wjd = jd;
        depoch = wjd - GREGORIAN_EPOCH - 1;
        quadricent = (depoch / 146097);
        dqc = mod_long(depoch, 146097);
        cent = (dqc / 36524);
        dcent = mod_long(dqc, 36524);
        quad = (dcent / 1461);
        dquad = mod_long(dcent, 1461);
        yindex = (dquad / 365);
        year = (quadricent * 400) + (cent * 100) + (quad * 4) + yindex;
        if (!((cent == 4) || (yindex == 4))) {
            year++;
        }
        yearday = wjd - gregorian_to_jd(year, 1, 1);
        leapadj = ((wjd < gregorian_to_jd(year, 3, 1)) ? 0 : (leap_gregorian(year) ? 1 : 2));
        long month = ((((yearday + leapadj) * 12) + 373) / 367);
        long day = (wjd - gregorian_to_jd(year, month, 1)) + 1;
        return new long[]{year, month, day};
    }

    private static final String[] split_date_keep_only_colon(String str) {
        int parts = 0;
        String[] res = null;
        int str_length = str.length();
        for (int pass = 1; pass <= 2; pass++) {
            int start = 0;
            int collecting_now = 0;
            if (pass == 2) {
                res = new String[parts];
                parts = 0;
            }
            for (int ii = 0; ii < str_length; ii++) {
                if (Character.isLetter(str.charAt(ii))) {
                    switch (collecting_now) {
                        case 0:
                            start = ii;
                            collecting_now = 1;
                            break;
                        case 1:
                            break;
                        case 2:
                            if (pass == 2) {
                                res[parts] = str.substring(start, ii);
                            }
                            parts++;
                            start = ii;
                            collecting_now = 2;
                    }
                } else if (Character.isDigit(str.charAt(ii))) {
                    switch (collecting_now) {
                        case 0:
                            start = ii;
                            collecting_now = 2;
                            break;
                        case 1:
                            if (pass == 2) {
                                res[parts] = str.substring(start, ii);
                            }
                            parts++;
                            start = ii;
                            collecting_now = 1;
                            break;
                        case 2:
                            break;
                    }
                } else if (':' == str.charAt(ii)) {
                    switch (collecting_now) {
                        case 0:
                            if (pass == 2) {
                                res[parts] = str.substring(ii, ii + 1);
                            }
                            parts++;
                            break;
                        case 1:
                        case 2:
                            if (pass == 2) {
                                res[parts] = str.substring(start, ii);
                            }
                            parts++;
                            if (pass == 2) {
                                res[parts] = str.substring(ii, ii + 1);
                            }
                            parts++;
                            start = ii + 1;
                            collecting_now = 0;
                            break;
                    }
                } else {
                    switch (collecting_now) {
                        case 0:
                            break;
                        case 1:
                        case 2:
                            if (pass == 2) {
                                res[parts] = str.substring(start, ii);
                            }
                            parts++;
                            collecting_now = 0;
                            break;
                    }
                }
            }
            if (start < str_length) {

                if (pass == 2) {
                    res[parts] = str.substring(start, str_length);
                }
                parts++;
            }
        }
        return res;
    }

    public static final double eeparsedate_keep_all(String str) {
        double res = eeparsedate(str, 3);
        if (Double.isNaN(res)) {
            return 1;
        } else {
            return res;
        }
    }

    public static final Object eeparsedate_keep_allV(String str) {
        if (str.compareTo("") == 0) {
            return str;
        }
        double res = eeparsedate(str, 3);
        if (Double.isNaN(res)) {
            return str;
        } else {
            return new Double(res);
        }
    }

    public static final double eeparsedate_keep_time(String str) {
        double res = eeparsedate(str, 2);
        if (Double.isNaN(res)) {
            return 0;
        } else {
            return res;
        }
    }

    public static final Object eeparsedate_keep_timeV(String str) {
        if (str.compareTo("") == 0) {
            return str;
        }
        double res = eeparsedate(str, 2);
        if (Double.isNaN(res)) {
            return str;
        } else {
            return new Double(res);
        }
    }

    private static final double eeparsedate(String str, int keep) {
        double year = 1900;
        double month = 1;
        double day = 1;
        double hour = 0;
        double minutes = 0;
        double seconds = 0;
        int ptr = 0;
        double current = 0;
        String current_str = "";
        String lookahead;
        String[] parts = split_date_keep_only_colon(str);
        int len = 0;
        if (str.length() > 0) {
            len = parts.length;
        }
        boolean time_parsed = false;
        try {
            if (len < 1) {
                return Double.NaN;
            } else {
                int cmd = 1;
                if (len > 1) {
                    lookahead = parts[1];
                    if (lookahead.compareTo(":") == 0) {
                        cmd = 2;
                    }
                }
                while (cmd > 0 && ptr < len) {
                    if (cmd == 1) {
                        current = js_parseFloat(parts[ptr]);
                        if (Double.isNaN(current)) {
                            return Double.NaN;
                        }
                        lookahead = parts[ptr + 1];
                        if (lookahead.compareTo(":") == 0) {
                            cmd = 2;
                        } else {
                            if (current > 1899) {
                                year = current;
                                ptr++;
                                current = js_parseFloat(parts[ptr++]);
                                if (Double.isNaN(current)) {
                                    return Double.NaN;
                                }
                                month = current;
                                current = js_parseFloat(parts[ptr++]);
                                if (Double.isNaN(current)) {
                                    return Double.NaN;
                                }
                                day = current;
                                cmd = 3;
                            } else if (current < 32) {
                                if (eeisus != 0) {
                                    month = current;
                                    ptr++;
                                    current = js_parseFloat(parts[ptr++]);
                                    if (Double.isNaN(current)) {
                                        return Double.NaN;
                                    }
                                    if (current > 1899) {
                                        year = current;
                                    } else {
                                        day = current;
                                        current = js_parseFloat(parts[ptr++]);
                                        if (Double.isNaN(current)) {
                                            return Double.NaN;
                                        }
                                        year = current;
                                        if (year < 30) {
                                            year += 2000;
                                        }
                                    }
                                } else {
                                    day = current;
                                    ptr++;
                                    current = js_parseFloat(parts[ptr++]);
                                    if (Double.isNaN(current)) {
                                        return Double.NaN;
                                    }
                                    if (current > 1899) {
                                        year = current;
                                        month = day;
                                        day = 1;
                                    } else {
                                        month = current;
                                        current = js_parseFloat(parts[ptr++]);
                                        if (Double.isNaN(current)) {
                                            return Double.NaN;
                                        }
                                        year = current;
                                        if (year < 30) {
                                            year += 2000;
                                        }
                                    }
                                }
                                cmd = 3;
                            } else {
                                return Double.NaN;
                            }
                        }
                    } else if (cmd == 2 || cmd == 3) {
                        if (cmd == 3 && time_parsed) {
                            return Double.NaN;
                        }
                        time_parsed = true;
                        current = js_parseFloat(parts[ptr++]);
                        if (Double.isNaN(current)) {
                            return Double.NaN;
                        }
                        hour = current;
                        lookahead = parts[ptr++];
                        if (lookahead.compareTo(":") == 0) {
                            if (ptr >= len) {
                                minutes = 0;
                                seconds = 0;
                                ptr = len;
                            } else {
                                current = js_parseFloat(parts[ptr++]);
                                if (Double.isNaN(current)) {
                                    return Double.NaN;
                                }
                                minutes = current;
                                if (ptr + 1 >= len) {
                                    seconds = 0;
                                    ptr = len;
                                } else {
                                    current_str = parts[ptr];
                                    if (current_str.compareTo(":") == 0) {
                                        ptr++;
                                        current = js_parseFloat(parts[ptr++]);
                                        if (Double.isNaN(current)) {
                                            return Double.NaN;
                                        }
                                        seconds = current;
                                    }
                                }
                            }
                        }
                        cmd = 1;
                    }
                }
            }
        } catch (java.lang.ArrayIndexOutOfBoundsException e1) {
        }
        switch (keep) {
            case 1:
                return date(year, month, day);

            case 2:
                return time(hour, minutes, seconds);

            case 3:
                return date(year, month, day) + time(hour, minutes, seconds);

            default:
                return Double.NaN;
        }
    }

    public static final double date(double year, double month, double day) {
        if (!isFinite(day) || !isFinite(month) || !isFinite(year)) {
            return Double.NaN;
        }
        if (year < 1900) {
            year += 1900;
        }
        if (year > 9999) {
            return Double.NaN;
        }
        double adj_year = year;
        double adj_month = month;
        if (month > 0) {
            adj_year = year + Math.floor((month - 1) / 12);
            adj_month = ((month - 1) % 12) + 1;
        } else if (month < 0) {
            double tmp = Math.ceil((-month) / 12);
            adj_year = year - tmp;
            adj_month = adj_month + tmp * 12;
        }
        double res = (gregorian_to_jd((long) adj_year, (long) adj_month, (long) day) - 2415020);
        if (res > 59) {
            return res + 1;
        }
        return res;
    }

    public static final double datevalue(String date_text) {
        return eeparsedate(date_text, 1);
    }

    public static final double eeday(double serial_number) {
        if (!isFinite(serial_number)) {
            return Double.NaN;
        }
        if (serial_number < 1) {
            return 0;
        }
        if (serial_number > 60) {
            serial_number--;
        }
        long[] res = jd_to_gregorian((long) serial_number + 2415020);
        return res[2];
    }

    public static final double hour(double serial_number) {
        if (!isFinite(serial_number)) {
            return Double.NaN;
        }
        double res = Math.floor((serial_number - Math.floor(serial_number)) * 86400 + 0.5);
        return Math.floor(res / 3600);
    }

    public static final double minute(double serial_number) {
        if (!isFinite(serial_number)) {
            return Double.NaN;
        }
        double res = Math.floor((serial_number - Math.floor(serial_number)) * 86400 + 0.5);
        return Math.floor(res / 60) % 60;

    }

    public static final double eemonth(double serial_number) {
        if (!isFinite(serial_number)) {
            return Double.NaN;
        }
        if (serial_number < 1) {
            return 1;
        }
        if (serial_number > 60) {
            serial_number--;
        }
        long[] res = jd_to_gregorian((long) serial_number + 2415020);
        return res[1];
    }

    public static final double second(double serial_number) {
        if (!isFinite(serial_number)) {
            return Double.NaN;
        }
        double res = Math.floor((serial_number - Math.floor(serial_number)) * 86400 + 0.5);
        return res % 60;
    }

    public static final double time(double hour, double minute, double second) {
        if (!isFinite(second) || !isFinite(minute) || !isFinite(hour)) {
            return Double.NaN;
        }
        return ((second + minute * 60 + hour * 3600) % 86400) / 86400;
    }

    public static final double weekday(double serial_number, double return_type) {
        if (!isFinite(return_type) || !isFinite(serial_number)) {
            return Double.NaN;
        }
        if (return_type < 1 || return_type > 3) {
            return Double.NaN;
        }
        double res = Math.floor(serial_number + 6) % 7;
        switch ((int) (Math.floor(return_type))) {
            case 1:
                return res + 1;
            case 2:
                return (res + 6) % 7 + 1;
            case 3:
            default:
                return (res + 6) % 7;
        }
    }

    public static final double year(double serial_number) {
        if (!isFinite(serial_number)) {
            return Double.NaN;
        }
        if (serial_number < 1) {
            return 1900;
        }
        if (serial_number > 60) {
            serial_number--;
        }
        long[] res = jd_to_gregorian((long) serial_number + 2415020);
        return res[0];
    }

    public static final String eedisplayFloatV(Object x) {
        if (x instanceof Double) {
            return eedisplayFloat(((Double) x).doubleValue());
        } else if (x instanceof String) {
            return (String) x;
        } else if (x instanceof Boolean) {
            return b2s(((Boolean) x).booleanValue());
        }
        return "";
    }

    /**
     * Retorna o n&uacute;mero de dias entre duas datas
     * @param firstDate Data inicial
     * @param lastDate Data final
     * @return N&uacute;mero de dias
     */
    public static int daysDifBetween(Date firstDate, Date lastDate) {
        firstDate = convertStringToDate("yyyyMMdd", getDateTime("yyyyMMdd", firstDate));
        lastDate = convertStringToDate("yyyyMMdd", getDateTime("yyyyMMdd", lastDate));
        return (int) (millisBetween(firstDate, lastDate) / TimeConst.DAY);
    }

    /**
     * Retorna o n&uacute;mero de dias entre duas datas
     * @param firstDate Data inicial
     * @param lastDate Data final
     * @return N&uacute;mero de dias
     */
    public static int daysBetween(Date firstDate, Date lastDate) {
        return (int) (millisBetween(firstDate, lastDate) / TimeConst.DAY);
    }

    /**
     * Retorna o n&uacute;mero de horas entre duas datas
     * @param firstDate Data inicial
     * @param lastDate Data final
     * @return N&uacute;mero de horas
     */
    public static int hoursBetween(Date firstDate, Date lastDate) {
        return (int) (millisBetween(firstDate, lastDate) / TimeConst.HOUR);
    }

    /**
     * Retorna o n&uacute;mero de minutos entre duas datas
     * @param firstDate Data inicial
     * @param lastDate Data final
     * @return N&uacute;mero de minutos
     */
    public static int minutesBetween(Date firstDate, Date lastDate) {
        return (int) (millisBetween(firstDate, lastDate) / TimeConst.MINUTE);
    }

    /**
     * Retorna o n&uacute;mero de segundos entre duas datas
     * @param firstDate Data inicial
     * @param lastDate Data final
     * @return N&uacute;mero de segundos
     */
    public static int secondsBetween(Date firstDate, Date lastDate) {
        return (int) (millisBetween(firstDate, lastDate) / TimeConst.SECOND);
    }

    /**
     * Retorna o n&uacute;mero de milissegundos entre dias dadas
     * @param firstDate Data inicial
     * @param lastDate Data final
     * @return N&uacute;mero de milissegundos
     */
    public static long millisBetween(Date firstDate, Date lastDate) {
        long t1 = firstDate.getTime();
        long t2 = lastDate.getTime();
        long dif = t1 - t2;

        if (dif < 0) {
            dif = dif * (-1);
        }
        return dif;
    }

    public static final String b2s(boolean b) {
        return b ? StringUtil.eetrue : StringUtil.eefalse;
    }

    private static final boolean str_ne(String x, String y) {
        return (x.compareToIgnoreCase(y) != 0);
    }

    private static final boolean str_gr(String x, String y) {
        return (x.compareToIgnoreCase(y) > 0);
    }

    private static final boolean str_ls(String x, String y) {
        return (x.compareToIgnoreCase(y) < 0);
    }

    public static final boolean var_ne(Object x, Object y) {
        int xt = mytypeof(x);
        int yt = mytypeof(y);
        if (xt != yt) {
            return true;
        }
        switch (xt) {
            case 1:
                return double_ne((Double) x, (Double) y);
            case 3:
                return boolean_ne((Boolean) x, (Boolean) y);
            case 2:
                return str_ne((String) x, (String) y);
            default:
                return true;
        }
    }

    public static final boolean var_gr(Object x, Object y) {
        int xt = mytypeof(x);
        int yt = mytypeof(y);
        if (xt != yt) {
            return (xt > yt);
        }
        switch (xt) {
            case 1:
                return double_gr((Double) x, (Double) y);
            case 3:
                return boolean_gr((Boolean) x, (Boolean) y);
            case 2:
                return str_gr((String) x, (String) y);
            default:
                return true;
        }
    }

    public static final boolean var_ls(Object x, Object y) {
        int xt = mytypeof(x);
        int yt = mytypeof(y);
        if (xt != yt) {
            return (xt < yt);
        }
        switch (xt) {
            case 1:
                return double_ls((Double) x, (Double) y);
            case 3:
                return boolean_ls((Boolean) x, (Boolean) y);
            case 2:
                return str_ls((String) x, (String) y);
            default:
                return false;
        }
    }

    private static final boolean boolean_ls(Boolean x, Boolean y) {
        return (x.booleanValue() == false && true == y.booleanValue());
    }

    private static final boolean boolean_gr(Boolean x, Boolean y) {
        return (x.booleanValue() == true && false == y.booleanValue());
    }

    private static final boolean boolean_ne(Boolean x, Boolean y) {
        return (x.booleanValue() != y.booleanValue());
    }

    private static final boolean double_ls(Double x, Double y) {
        return (x.compareTo(y) < 0);
    }

    private static final boolean double_gr(Double x, Double y) {
        return (x.compareTo(y) > 0);
    }

    private static final boolean double_ne(Double x, Double y) {
        return (x.compareTo(y) != 0);
    }

    public static final String eedatefmt(int[] fmt, double x) {
        if (!DateUtil.isFinite(x)) {
            return String_NaN;
        }
        double tmp = 0;
        String res = "";
        int len = fmt.length;
        for (int ii = 0; ii < len; ii++) {
            if (fmt[ii] > 31) {
                res += fmtstrings[fmt[ii] - 32];
            } else {
                switch (fmt[ii]) {
                    case 2:
                        res += DateUtil.String_valueOf(DateUtil.eemonth(x));
                        break;
                    case 3:
                        tmp = DateUtil.eemonth(x);
                        if (tmp < 10) {
                            res += "0";
                        }
                        res += DateUtil.String_valueOf(tmp);
                        break;
                    case 4:
                        res += DateUtil.fmtmonthnamesshort[(int) DateUtil.eemonth(x) - 1];
                        break;
                    case 5:
                        res += DateUtil.fmtmonthnameslong[(int) DateUtil.eemonth(x) - 1];
                        break;
                    case 6:
                        res += DateUtil.String_valueOf(DateUtil.eeday(x));
                        break;
                    case 7:
                        tmp = DateUtil.eeday(x);
                        if (tmp < 10) {
                            res += "0";
                        }
                        res += DateUtil.String_valueOf(tmp);
                        break;
                    case 8:
                        res += DateUtil.fmtdaynamesshort[(int) DateUtil.weekday(x, 1) - 1];
                        break;
                    case 9:
                        res += DateUtil.fmtdaynameslong[(int) DateUtil.weekday(x, 1) - 1];
                        break;
                    case 10:
                        tmp = DateUtil.year(x) % 100;
                        if (tmp < 10) {
                            res += "0";
                        }
                        res += DateUtil.String_valueOf(tmp).replace(",", "");
                        break;
                    case 11:
                        res += DateUtil.String_valueOf(DateUtil.year(x)).replace(",", "");
                        break;
                    case 12:
                        res += DateUtil.String_valueOf(DateUtil.hour(x));
                        break;
                    case 13:
                        tmp = DateUtil.hour(x);
                        if (tmp < 10) {
                            res += "0";
                        }
                        res += DateUtil.String_valueOf(tmp);
                        break;
                    case 14:
                        tmp = DateUtil.hour(x) % 12;

                        if (tmp == 0) {
                            res += "12";
                        } else {
                            res += DateUtil.String_valueOf(tmp % 12);
                        }
                        break;
                    case 15:
                        tmp = DateUtil.hour(x) % 12;
                        if (tmp == 0) {
                            res += "12";
                        } else {
                            if (tmp < 10) {
                                res += "0";
                            }
                            res += DateUtil.String_valueOf(tmp);
                        }
                        break;
                    case 16:
                        res += DateUtil.String_valueOf(DateUtil.minute(x));
                        break;
                    case 17:
                        tmp = DateUtil.minute(x);
                        if (tmp < 10) {
                            res += "0";
                        }
                        res += DateUtil.String_valueOf(tmp);
                        break;
                    case 18:
                        res += DateUtil.String_valueOf(DateUtil.second(x));
                        break;
                    case 19:
                        tmp = DateUtil.second(x);
                        if (tmp < 10) {
                            res += "0";
                        }
                        res += DateUtil.String_valueOf(tmp);
                        break;
                    case 21:
                    case 22:
                        if (DateUtil.hour(x) < 12) {
                            res += "AM";
                        } else {
                            res += "PM";
                        }
                        break;
                    case 23:
                        res += DateUtil.eedisplayFloat(x);
                        break;
                    case 24:
                        tmp = fmt[++ii];
                        res += DateUtil.eedisplayFloatND(x, (int) tmp);
                        break;
                    case 25:
                        tmp = fmt[++ii];
                        res += DateUtil.eedisplayFloatNDTh(x, (int) tmp);
                        break;
                    case 26:
                        res += DateUtil.eedisplayPercent(x);
                        break;
                    case 27:
                        tmp = fmt[++ii];
                        res += DateUtil.eedisplayPercentND(x, (int) tmp);
                        break;
                    case 28:
                        tmp = fmt[++ii];
                        res += DateUtil.eedisplayPercentNDTh(x, (int) tmp);
                        break;
                    case 29:
                        tmp = fmt[++ii];
                        res += DateUtil.eedisplayFloatNDTh(x, (int) tmp);
                        break;
                    case 30:
                        int padding = fmt[++ii];
                        tmp = DateUtil.hour(x) + Math.floor(x) * 24;
                        String tmpstr = DateUtil.String_valueOf(tmp);
                        if (tmpstr.length() < padding) {
                            res += ("00000000000000000000").substring(0, padding - tmpstr.length());
                        }
                        res += tmpstr;
                        break;
                }
            }
        }
        return res;

    }

    public static final String incDateFull(String sFec0) {
        int nDia = Integer.parseInt(sFec0.substring(0, 2));
        int nMes = Integer.parseInt(sFec0.substring(3, 5));
        int nAno = Integer.parseInt(sFec0.substring(6, 10));
        nDia += 1;
        if (nDia > finMesFull(nMes, nAno)) {
            nDia = 1;
            nMes += 1;
            if (nMes == 13) {
                nMes = 1;
                nAno += 1;
            }
        }
        return makeDateFormatFull(nDia, nMes, nAno);
    }

    public static final String makeDateFormatFull(int nDay, int nMonth, int nYear) {
        String sRes = padNmbFull(nDay, 2, "0") + "/" + padNmbFull(nMonth, 2, "0") + "/" + padNmbFull(nYear, 4, "0");
        return sRes;
    }

    public static final String padNmbFull(int nStr, int nLen, String sChr) {
        String sRes = String.valueOf(nStr);
        if (String.valueOf(nStr).length() < nLen) {
            for (int rm = 0; rm < (nLen - String.valueOf(nStr).length()); rm++) {
                sRes = sChr + sRes;
            }
        }
        return sRes;
    }

    public static final String padNmbOnlyDecFull(double nStr, int nLen, String sChr) {
        String sRes = String.valueOf(nStr);
        String[] a;
        if (sRes.contains(".")) {
            a = sRes.split("\\.");
        } else {
            return sRes + ".00";
        }
        if (a[1].length() < nLen) {
            for (int rm = 0; rm < (nLen - a[1].length()); rm++) {
                a[1] = a[1] + sChr;
            }
        }
        return a[0] + "." + a[1];
    }

    public static final String addToDateFull(String sFec0, int sInc) {
        int nInc = Math.abs(sInc);
        String sRes = sFec0;
        if (nInc >= 0) {
            for (int k = 0; k < nInc; k++) {
                sRes = incDateFull(sRes);
            }
        }
        return sRes;

    }
    static int[] aFinMesFull = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public static final int finMesFull(int nMes, int nAno) {
        return aFinMesFull[nMes - 1] + (((nMes == 2) && (nAno % 4) == 0) ? 1 : 0);
    }

    public static final String sumaMesFull(String sF, int n) {
        int n0 = Math.abs(n);
        String sF0 = sF;
        for (int i = 0; i < n0; i++) {
            sF0 = incMesFull(sF0);
        }
        return sF0;
    }

    public static final String incMesFull(String sFec0) {
        int nDia = Integer.parseInt(sFec0.substring(0, 2));
        int nMes = Integer.parseInt(sFec0.substring(3, 5));
        int nAno = Integer.parseInt(sFec0.substring(6, 10));
        //
        if (nDia == 31) {
            nDia = 30;
        }
        nMes += 1;
        if (nMes == 13) {
            nMes = 1;
            nAno += 1;
        }
        return makeDateFormatFull(nDia, nMes, nAno);
    }

    public static final int difDiasFull(String f0, String f1) {
        Date d0 = DateUtil.convertStringToDate("dd/MM/yyyy", f0);
        Date d1 = DateUtil.convertStringToDate("dd/MM/yyyy", f1);
        return daysBetween(d0, d1);
    }

    public static final int difDiasFullS(String f0, String f1, String p0, String p1) {
        Date d0 = DateUtil.convertStringToDate(p0, f0);
        Date d1 = DateUtil.convertStringToDate(p1, f1);
        return daysBetween(d0, d1);
    }

    public class TimeConst {

        public static final long SECOND = 1000;
        public static final long MINUTE = 60 * SECOND;
        public static final long HOUR = 60 * MINUTE;
        public static final long DAY = 24 * HOUR;
    }
}
