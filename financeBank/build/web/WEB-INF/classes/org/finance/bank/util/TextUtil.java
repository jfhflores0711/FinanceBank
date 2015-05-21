package org.finance.bank.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author oscar
 */
public class TextUtil {

    public static final int ALLIGN_CENTER = 0;
    public static final int ALLIGN_LEFT = -1;
    public static final int ALLIGN_RIGHT = 1;

    public static boolean isEmptyString(String txt) {
        return (txt == null || txt.length() == 0);
    }

    public static List split(String text, String token) {
        List list = new ArrayList();
        int start = 0;
        int end = -1;
        while ((end = text.indexOf(token, start)) != -1) {
            list.add(text.substring(start, end));

            start = end + token.length();
        }
        list.add(text.substring(start));
        return list;
    }

    public static String replace(String text, String searchString, String replaceString) {
        StringBuffer buffer = new StringBuffer();
        String line = text;
        int pos = 0;
        while ((pos = line.indexOf(searchString, pos)) != -1) {
            buffer.setLength(0);
            if (pos == 0) {
                buffer.append(replaceString);
                buffer.append(line.substring(pos + searchString.length()));
            } else {
                buffer.append(line.substring(0, pos));
                buffer.append(replaceString);
                buffer.append(line.substring(pos + searchString.length()));
            }
            pos = pos + replaceString.length();
            line = buffer.toString();
        }
        return line;
    }

    public static String replace(String msg, Map parameters) {
        if (parameters.isEmpty() || msg == null) {
            return msg;
        }
        String key = null;
        String text = msg;
        for (Iterator i = parameters.keySet().iterator(); i.hasNext();) {
            key = (String) i.next();
            text = replace(text, key, (String) parameters.get(key));
        }
        return text;
    }

    public static String format(String unformatedText, int size, char filler, int allignType) {
        String text = unformatedText;
        if (text == null) {
            text = "";
        }
        if (text.length() > size) {
            return text.substring(0, size);
        }
        StringBuffer buffer = new StringBuffer();
        int rigth = 0;
        int left = 0;
        switch (allignType) {
            case ALLIGN_LEFT:
                rigth = size - text.length();
                break;
            case ALLIGN_CENTER:
                left = (size - text.length()) / 2;
                rigth = size - (text.length() + left);
                break;
            case ALLIGN_RIGHT:
                left = size - text.length();
                break;
        }
        for (int i = 0; i < left; i++) {
            buffer.append(filler);
        }
        buffer.append(text);
        for (int i = 0; i < rigth; i++) {
            buffer.append(filler);
        }
        return buffer.toString();
    }

    public static String toString(Collection list) {
        if (list == null) {
            return "null";
        }
        StringBuffer buffer = new StringBuffer();

        for (Iterator i = list.iterator(); i.hasNext();) {
            if (buffer.length() != 0) {
                buffer.append(", ");
            }
            buffer.append(toString(i.next()));
        }
        return buffer.toString();
    }

    public static String toString(Map map) {
        if (map == null) {
            return "null";
        }
        StringBuffer buffer = new StringBuffer();
        Object obj = null;
        for (Iterator i = map.keySet().iterator(); i.hasNext();) {
            if (buffer.length() != 0) {
                buffer.append(", ");
            }
            obj = i.next();
            buffer.append(toString(obj));
            buffer.append("=");
            buffer.append(toString(map.get(obj)));
        }
        return buffer.toString();
    }

    public static String toString(Object[] array) {
        if (array == null) {
            return "null";
        }
        if (array.length == 0) {
            return "";
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < array.length; i++) {
            if (i != 0) {
                buffer.append(", ");
            }
            buffer.append(toString(array[i]));
        }
        return buffer.toString();
    }

    public static String toString(Object obj) {
        if (obj == null) {
            return "null";
        }
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj.getClass().isPrimitive()) {
            return obj.toString();
        }
        if (obj.getClass().isArray()) {
            return toString((Object[]) obj);
        }
        String packageName = obj.getClass().getPackage().getName();
        if (packageName.startsWith("java.") || packageName.startsWith("javax.")) {
            return obj.toString();
        }
        BeanUtil beanUtil = new BeanUtil(obj);
        if (beanUtil.extendsClassFromPackageStartingWith("java.") || beanUtil.extendsClassFromPackageStartingWith("javax.")) {
            return obj.toString();
        }
        return beanUtil.toString();
    }

    public static String trim(String text) {
        if (text == null) {
            return null;
        } else {
            return text.trim();
        }
    }

    public static String toLowerCase(String text) {
        if (text == null) {
            return null;
        } else {
            return text.toLowerCase();
        }
    }

    public static String toUpperCase(String text) {
        if (text == null) {
            return null;
        } else {
            return text.toUpperCase();
        }
    }

    public static String trimMaxSize(String text, int maxSize) {
        if (trim(text) == null) {
            return null;
        } else {
            return text.length() > maxSize ? text.substring(0, maxSize) : text;
        }
    }
}
