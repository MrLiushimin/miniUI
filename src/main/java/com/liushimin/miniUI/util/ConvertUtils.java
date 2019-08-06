package com.liushimin.miniUI.util;

import org.apache.commons.lang3.StringUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

public class ConvertUtils {

    /**
     * 将一个 Map 对象转化为一个 JavaBean
     *
     *
     * @param type
     * @param map  包含属性值的 map
     * @return 转化出来的 JavaBean 对象
     * @throws IntrospectionException    如果分析类属性失败
     * @throws IllegalAccessException    如果实例化 JavaBean 失败
     * @throws InstantiationException    如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
    public static <T> T convertToBean(Class<T> type, Map map)
            throws IntrospectionException, IllegalAccessException,
            InstantiationException, InvocationTargetException {

//        Collections.addAll(Arrays.asList(1,2,3));

        BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
        T obj = type.newInstance(); // 创建 JavaBean 对象

        // 给 JavaBean 对象的属性赋值
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();

            if (map.containsKey(propertyName)) {
                // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
                Object value = map.get(propertyName);

                Object[] args = new Object[1];

                if (value == null || (value instanceof String && StringUtils.isBlank((String)value))) {
                    value = defaultValue(descriptor.getPropertyType());
                } else if (descriptor.getPropertyType() == Date.class && value instanceof String && StringUtils.isNotBlank((String)value)) {
                    List<String> datePatters = DateKit.getDatePatters();
                    for (String datePatter : datePatters) {
                        try {
                            value = DateKit.toDate(value.toString(), datePatter);
                            if (value instanceof Date) {
                                break;
                            }
                        } catch (Exception e) {

                        }
                    }
                } else if (descriptor.getPropertyType() == int.class && value instanceof BigDecimal) {
                    value = ((BigDecimal) value).intValue();
                }

                args[0] = value;

                descriptor.getWriteMethod().invoke(obj, args);
            }
        }
        return obj;
    }

    /**
     * 将一个 JavaBean 对象转化为一个  Map,包括null值和空字符串
     *
     * @param bean 要转化的JavaBean 对象
     * @return 转化出来的  Map 对象
     * @throws IntrospectionException    如果分析类属性失败
     * @throws IllegalAccessException    如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
    public static <T> Map convertToMap(T bean)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        return convertToMapNoNullValue(bean, false, false);
    }

    /**
     * 将一个 JavaBean 对象转化为一个  Map,不包括null值
     * noBlankStr 用来控制是否转换空字符串，true为不转换
     *
     * @param bean
     * @param noBlankStr
     * @param <T>
     * @return
     * @throws IntrospectionException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static <T> Map convertToMapNoNullValue(T bean, boolean noBlankStr)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        return convertToMapNoNullValue(bean, true, noBlankStr);
    }

    /**
     * 将一个 JavaBean 对象转化为 Map的基础方法
     * @param bean
     * @param noNullValue
     * @param noBlankStr
     * @param <T>
     * @return
     * @throws IntrospectionException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private static <T> Map convertToMapNoNullValue(T bean, boolean noNullValue,boolean noBlankStr)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        Class type = bean.getClass();
        Map returnMap = new HashMap<String, Object>();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);

        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                if (noNullValue && (result == null)) {

                } else if (noBlankStr && result instanceof String && StringUtils.isBlank((String)result)) {

                } else {
                    returnMap.put(propertyName, result);
                }
            }
        }
        return returnMap;
    }

    public static <T> List<Map> convertToMapList(Class type, List<T> list) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        List<Map> result = new ArrayList<Map>();
        for (T o : list) {
            result.add(convertToMap(o));
        }
        return result;
    }

    public static <T> List<T> convertToBeanList(Class<T> type, List<Map> list) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        List<T> result = new ArrayList<T>();
        for (Map o : list) {
            result.add(convertToBean(type, o));
        }
        return result;
    }

    private static Object defaultValue(Class<?> propertyType) {
        if ((propertyType == int.class) || (propertyType == int.class) || (propertyType == int.class) || (propertyType == int.class)) {
            return 0;
        } else if ((propertyType == double.class) || (propertyType == float.class)) {
            return 0.0;
        } else if (propertyType == boolean.class) {
            return false;
        } else if (propertyType == char.class) {
            return ' ';
        }
        return null;
    }

}
