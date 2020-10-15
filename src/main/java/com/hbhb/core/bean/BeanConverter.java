package com.hbhb.core.bean;

import com.hbhb.core.utils.CamelUnderlineUtil;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiaokang
 * @since 2020-06-25
 */
@SuppressWarnings(value = {"rawtypes"})
public class BeanConverter {

    public static void copyProp(Object from, Object to) {
        BeanUtils.copyProperties(from, to);
    }

    public static <K, T> List<T> copyBeanList(List<K> srcList, Class<T> destClass) {
        List<T> destList = new ArrayList<>();
        if (destClass == null || CollectionUtils.isEmpty(srcList)) {
            return destList;
        }
        try {
            for (Object src : srcList) {
                T obj = destClass.newInstance();
                copyBean(obj, src);
                destList.add(obj);
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return destList;
    }

    public static <T> T convert(Object object, Class<T> destClass) {
        if (destClass == null || object == null) {
            return null;
        }
        T destObj = null;
        try {
            destObj = destClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return copyBean(destObj, object);
    }

    public static <T> T copyBean(T destObj, Object... srcArray) {
        for (Object srcObj : srcArray) {
            if (srcObj == null) {
                continue;
            }
            // 反射获取字段
            Field[] fields = destObj.getClass().getDeclaredFields();
            // 遍历目标对象字段
            for (Field destField : fields) {
                // 获取源对象值
                try {
                    Field srcField = srcObj.getClass().getDeclaredField(destField.getName());
                    // 跳过final的设值
                    if (destField.getType().equals(srcField.getType())) {
                        if (Modifier.isFinal(srcField.getModifiers())) {
                            continue;
                        }
                        srcField.setAccessible(true);
                        destField.setAccessible(true);
                        destField.set(destObj, srcField.get(srcObj));
                    }
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return destObj;
    }

    public static Map<String, String> convert2Map(Object obj, Class clazz) {
        Map<String, String> dstMap = new HashMap<>();
        Field[] fields = obj.getClass().getDeclaredFields();

        try {
            for (Field field : fields) {
                if ("serialVersionUID".equals(field.getName())) {
                    continue;
                }
                String dstName = CamelUnderlineUtil.camel2Underline(field.getName());
                PropertyDescriptor pd;
                pd = new PropertyDescriptor(field.getName(), clazz);
                Method method = pd.getReadMethod();
                Object dstObject = null;
                dstObject = method.invoke(obj);
                if (dstObject instanceof ArrayList) {
                    dstObject = "";
                }
                dstMap.put(dstName, StringUtils.isEmpty(dstObject) ? "" : dstObject.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dstMap;
    }

    private static boolean isAllNull(Object... srcArray) {
        if (srcArray == null || srcArray.length == 0) {
            return true;
        }
        boolean isAllSrcNull = true;
        for (Object src : srcArray) {
            if (src != null) {
                isAllSrcNull = false;
                break;
            }
        }
        return isAllSrcNull;
    }
}