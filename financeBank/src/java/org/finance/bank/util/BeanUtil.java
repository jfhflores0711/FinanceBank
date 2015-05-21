package org.finance.bank.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *
 * @author oscar
 */
public class BeanUtil {

    private Object obj = null;

    public BeanUtil(Class clazz) throws InstantiationException, IllegalAccessException {
        obj = clazz.newInstance();
    }

    public BeanUtil(Object obj) {
        if (obj == null) {
            throw new NullPointerException("Can't create BeanUtil instance with null object");
        }
        this.obj = obj;
    }

    public Class[] getExtendsList() {
        List ret = new ArrayList();
        Class father = obj.getClass().getSuperclass();
        while (father != null && !father.equals(Object.class)) {
            ret.add(father);
            father = father.getSuperclass();
        }
        return (Class[]) ret.toArray(new Class[ret.size()]);
    }

    public boolean extendsClass(Class clazz) {
        Class[] fathers = getExtendsList();
        for (int i = 0; i < fathers.length; i++) {
            if (fathers[i].getName().equals(clazz.getName())) {
                return true;
            }
        }
        return false;
    }

    public boolean extendsClassFromPackageStartingWith(String packageName) {
        Class[] fathers = getExtendsList();
        for (int i = 0; i < fathers.length; i++) {
            if (fathers[i].getPackage().getName().startsWith(packageName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        if (implementsMethod("toString", new Class[0])) {
            return obj.toString();
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append(getClassName());
        buffer.append("(");
        Class clazz = obj.getClass();
        Method[] methods = clazz.getMethods();
        Method method = null;
        Object value = null;
        int count = 0;
        List listedFields = new ArrayList();
        String fieldName = null;
        for (int i = 0; i < methods.length; i++) {
            method = methods[i];

            if (!(method.getName().startsWith("get") || method.getName().startsWith("is"))) {
                continue;
            }
            if (method.getDeclaringClass().equals(Object.class)) {
                continue;
            }
            int modifiers = method.getModifiers();
            if (Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers) || Modifier.isAbstract(modifiers)
                    || Modifier.isProtected(modifiers) || Modifier.isPrivate(modifiers)) {
                continue;
            }
            if (method.getParameterTypes().length != 0) {
                continue;
            }
            try {
                value = method.invoke(obj, new Object[0]);
                fieldName = getPropertyName(method.getName());
                listedFields.add(fieldName);
                if (value != null && value.equals(obj)) {
                    continue;
                }
                if (count != 0) {
                    buffer.append(", ");
                }
                buffer.append(fieldName);
                buffer.append("=");
                appendObject(buffer, value);
                count++;
            } catch (Exception e) {
                throw new RuntimeException("Unable to invoke method " + method.getName() + ".");
            }
        }
        Field[] fields = clazz.getFields();

        for (int i = 0; i < fields.length; i++) {

            if (listedFields.contains(fields[i].getName())) {
                continue;
            }

            if (count != 0) {
                buffer.append(", ");
            }

            try {
                value = fields[i].get(obj);

                buffer.append(fields[i].getName());
                buffer.append("=");
                appendObject(buffer, value);

                count++;
            } catch (Exception e) {
                throw new RuntimeException("Unable to get the value from field " + fields[i].getName() + ".");
            }
        }

        buffer.append(")");

        return buffer.toString();
    }

    private void appendObject(StringBuffer buffer, Object value) {
        if (value != null) {
            if (value.getClass().isArray()) {
                buffer.append("[");

                for (int j = 0; j < Array.getLength(value); j++) {
                    if (j != 0) {
                        buffer.append(", ");
                    }

                    buffer.append(TextUtil.toString(Array.get(value, j)));
                }

                buffer.append("]");
            } else if (value instanceof Collection) {
                buffer.append("[");
                buffer.append(TextUtil.toString((Collection) value));
                buffer.append("]");
            } else if (value instanceof Map) {
                buffer.append("{");
                buffer.append(TextUtil.toString((Map) value));
                buffer.append("}");
            } else {
                buffer.append(TextUtil.toString(value));
            }
        } else {
            buffer.append("null");
        }

    }

    public boolean implementsMethod(String methodName, Class[] args) {
        try {
            obj.getClass().getDeclaredMethod(methodName, args);
        } catch (NoSuchMethodException e) {
            return false;
        }

        return true;
    }

    public Object invokeMethod(String methodName, Object[] args) throws SecurityException, NoSuchMethodException,
            IllegalArgumentException, IllegalAccessException, InvocationTargetException {

        List methods = getMethods(methodName);
        Method method = null;

        if (methods.size() == 1) {
            method = (Method) methods.get(0);
        } else {
            Class[] clazzs = new Class[args.length];

            for (int i = 0; i < args.length; i++) {
                clazzs[i] = args[i].getClass();
            }

            method = obj.getClass().getMethod(methodName, clazzs);
        }

        return method.invoke(obj, args);
    }

    @Override
    public int hashCode() {
        if (implementsMethod("hashCode", new Class[0])) {
            return obj.hashCode();
        }

        return toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BeanUtil other = (BeanUtil) obj;
        if (this.obj != other.obj && (this.obj == null || !this.obj.equals(other.obj))) {
            return false;
        }
        return true;
    }

    public String getClassName() {
        return obj.getClass().getName();
    }

    public Object get(String propertyName) throws SecurityException, NoSuchMethodException, IllegalArgumentException,
            IllegalAccessException, InvocationTargetException {
        return invokeMethod(getGetMethodName(propertyName), new Object[0]);
    }

    public String getPropertyName(String methodName) {
        String name = null;
        if (methodName.startsWith("get") || methodName.startsWith("set")) {
            name = methodName.substring(3);
        } else if (methodName.startsWith("is")) {
            name = methodName.substring(2);
        } else {
            name = methodName;
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append(name.substring(0, 1).toLowerCase());
        if (name.length() > 1) {
            buffer.append(name.substring(1));
        }
        return buffer.toString();
    }

    public String getGetMethodName(String propertyName) {
        String name = getMethodName("get", propertyName);
        if (getMethods(name).isEmpty()) {
            String booleanName = getMethodName("is", propertyName);
            if (!getMethods(booleanName).isEmpty()) {
                name = booleanName;
            }
        }
        return name;
    }

    public List getMethods(String methodName) {
        List ret = new ArrayList();
        Method[] methods = obj.getClass().getMethods();
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].getName().equals(methodName)) {
                ret.add(methods[i]);
            }
        }
        return ret;
    }

    public String getMethodName(String prefix, String propertyName) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(prefix);
        buffer.append(propertyName.substring(0, 1).toUpperCase());
        if (propertyName.length() > 1) {
            buffer.append(propertyName.substring(1));
        }
        return buffer.toString();
    }

    public void set(String propertyName, Object value) throws NoSuchMethodException, IllegalArgumentException,
            IllegalAccessException, InvocationTargetException {
        String name = getMethodName("set", propertyName);
        List list = getMethods(name);
        Method method = null;
        if (list.isEmpty()) {
            throw new NoSuchMethodException();
        }
        if (list.size() == 1) {
            method = (Method) list.get(0);
        } else {
            method = getSetMethod(name, value.getClass());

            if (method == null) {
                throw new NoSuchMethodException(name);
            }
        }
        Object[] args = new Object[1];
        args[0] = value;
        method.invoke(obj, args);
    }

    public Method getSetMethod(String methodName, Class clazz) throws NoSuchMethodException {
        Class[] classes = new Class[1];
        Class[] classesI = new Class[1];
        Class[] interfaces = null;
        Method method = null;
        classes[0] = clazz;
        while (method == null && classes[0] != null) {
            try {
                method = obj.getClass().getMethod(methodName, classes);
            } catch (NoSuchMethodException e) {
            }
            if (method == null) {
                interfaces = classes[0].getInterfaces();
                for (int i = 0; i < interfaces.length; i++) {
                    classesI[0] = interfaces[i];
                    try {
                        method = obj.getClass().getMethod(methodName, classesI);
                    } catch (NoSuchMethodException e) {
                    }
                }
            }
            classes[0] = clazz.getSuperclass();
        }
        if (method == null) {
            throw new NoSuchMethodException(methodName);
        }
        return method;
    }

    public static Object getPropertyValue(BeanUtil beanUtil, String propertyName) throws Exception {
        BeanUtil bu = beanUtil;
        List list = TextUtil.split(propertyName, ".");
        String pn = (String) list.get(list.size() - 1);
        Object obj = null;
        for (int i = 0; i < list.size() - 1; i++) {
            obj = bu.get((String) list.get(i));
            bu = new BeanUtil(obj);
        }
        return bu.get(pn);
    }

    public static Object getPropertyValue(Object obj, String propertyName) throws Exception {
        return getPropertyValue(new BeanUtil(obj), propertyName);
    }
}
