package org.finance.bank.util;

/**
 *
 * @author oscar
 */
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.finance.bank.model.LabelValue;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Utility class to convert one object to another.
 *
 */
public final class ConvertUtil {

    private static final Log log = LogFactory.getLog(ConvertUtil.class);

    /**
     * Checkstyle rule: utility classes should not have public constructor
     */
    private ConvertUtil() {
    }

    /**
     * Method to convert a ResourceBundle to a Map object.
     * @param rb a given resource bundle
     * @return Map a populated map
     */
    public static Map<String, String> convertBundleToMap(ResourceBundle rb) {
        Map<String, String> map = new HashMap<String, String>();
        Enumeration<String> keys = rb.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            map.put(key, rb.getString(key));
        }
        return map;
    }

    /**
     * Convert a java.util.List of LabelValue objects to a LinkedHashMap.
     * @param list the list to convert
     * @return the populated map with the label as the key
     */
    public static Map<String, String> convertListToMap(List<LabelValue> list) {
        Map<String, String> map = new LinkedHashMap<String, String>();
        for (LabelValue option : list) {
            map.put(option.getLabel(), option.getValue());
        }
        return map;
    }

    /**
     * Method to convert a ResourceBundle to a Properties object.
     * @param rb a given resource bundle
     * @return Properties a populated properties object
     */
    public static Properties convertBundleToProperties(ResourceBundle rb) {
        Properties props = new Properties();
        for (Enumeration<String> keys = rb.getKeys(); keys.hasMoreElements();) {
            String key = keys.nextElement();
            props.put(key, rb.getString(key));
        }
        return props;
    }

    /**
     * Convenience method used by tests to populate an object from a
     * ResourceBundle
     * @param obj an initialized object
     * @param rb a resource bundle
     * @return a populated object
     */
    public static Object populateObject(Object obj, ResourceBundle rb) {
        try {
            Map<String, String> map = convertBundleToMap(rb);
            BeanUtils.copyProperties(obj, map);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Exception occurred populating object: " + e.getMessage());
        }
        return obj;
    }

    public static String prepareStringParameter(String str) {
        if (str == null) {
            return "";
        }
        str = str.trim();
        str = str.toUpperCase();
        str = str.replace("=", "");
        str = str.replace("'", "");
        str = str.replace("\"\"\"\"", "");
        str = str.replace(" or ", "");
        str = str.replace(" and ", "");
        str = str.replace("(", "");
        str = str.replace(")", "");
        str = str.replace("<", "[");
        str = str.replace(">", "]");
        str = str.replace("--", "");
        str = str.replace("SELECT ", "");
        str = str.replace("INSERT ", "");
        str = str.replace("UPDATE ", "");
        str = str.replace("DELETE ", "");
        str = str.replace("DROP ", "");
        str = str.replace("-SHUTDOWN", "");
        str = str.replace("--", "");
        return str;
    }

    public static String prepareStringForQuery(String str) {
        str = str.trim();
        str = str.replace("'", "''");
        str = str.replace("<", "[");
        str = str.replace(">", "]");
        str = str.replace("=", "");
        return str;
    }

    public static boolean isNumeric(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) < '0' || s.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }

    public static String isDefaultNumeric(String str) {
        if (str == null || str.length() < 1) {
            return "";
        }
        str.replace(",", ".");
        String[] strs = str.split(".");
        if (strs.length > 2) {
            return "";
        }
        if (isNumeric(strs[0]) && isNumeric(strs[1])) {
            return str;
        }
        return "";
    }
}
