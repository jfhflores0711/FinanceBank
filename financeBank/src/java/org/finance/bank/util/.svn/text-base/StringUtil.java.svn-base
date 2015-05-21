package org.finance.bank.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.io.*;

/**
 *
 * @author oscar
 */
public class StringUtil {

    public static final String eetrue = "VERDADERO";
    public static final String eefalse = "FALSO";
    public static final String newline = System.getProperty("line.separator");
    private static Log log = LogFactory.getLog(StringUtil.class);

    public static String readToString(InputStream in) {
        StringBuffer sb = new StringBuffer();
        try {
            BufferedReader inbr = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = inbr.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            log.error("IOException while reading from stream " + in + " to String.", e);
            return null;
        }
        return sb.toString();
    }

    /** Prefixes the string input with enough copies of pad that it
    has length equal to length.  Returns null and logs an error if
    padding with pad cannot produce a string of exactly length.
    Usually, pad should be a single character.*/
    public static String pad(String input, int length, String pad) {
        StringBuffer sb = new StringBuffer();
        int padLength = length - input.length();
        while (sb.length() < padLength) {
            sb.append(pad);
        }
        sb.append(input);
        if (sb.length() != length) {
            log.error("Could not pad \"" + input + "\" with \"" + pad + "\" to get length " + length);
            return null;
        }
        return sb.toString();
    }
}
