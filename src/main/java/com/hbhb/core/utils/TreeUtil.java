package com.hbhb.core.utils;

import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * 集合转树形结构 处理类
 *
 * @author xiaokang
 * @since 2020-06-22
 */
@Slf4j
@SuppressWarnings(value = {"unchecked", "rawtypes"})
public class TreeUtil {

    private static final String GET = "get";
    private static final String SET = "set";
    private static final String ROOT_ID = "0";
    private static final String ID_NAME = "id";
    private static final String PARENT_ID_NAME = "parentId";
    private static final String CHILDREN_NAME = "children";

    /**
     * 集合转树形（id、parentId、children三个字段名不可更改）
     */
    public static <T> List<T> build(List<T> list) {
        try {
            return TreeUtil.build(list, ROOT_ID, ID_NAME, PARENT_ID_NAME, CHILDREN_NAME);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 集合转树形（id、parentId、children三个字段名不可更改,可自定义rootId）
     */
    public static <T> List<T> build(List<T> list, String rootId) {
        try {
            return TreeUtil.build(list, rootId, ID_NAME, PARENT_ID_NAME, CHILDREN_NAME);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 集合转树形（rootId、id、parentId、children三个字段名可自定义）
     */
    public static <T> List<T> build(List<T> list,
                                    String rootId,
                                    String idName,
                                    String parentIdName,
                                    String childrenName)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<T> result = new ArrayList<>();
        // 将首字母转大写
        idName = StringUtils.capitalize(idName);
        parentIdName = StringUtils.capitalize(parentIdName);
        childrenName = StringUtils.capitalize(childrenName);
        // 遍历封装pid=0的
        for (T t : list) {
            // 根据反射，获取父id的值
            String pId = t.getClass().getMethod(GET + parentIdName).invoke(t).toString();
            // 如果pid=0，即是首层
            if (rootId.equals(pId)) {
                // 递归查询并封装子对象
                result.add(findChildren(t, list, idName, parentIdName, childrenName));
            }
        }
        return result;
    }

    /**
     * 递归查找子节点
     */
    public static <T> T findChildren(T bean, List<T> beans,
                                     String idName, String parentIdName, String childrenName)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 反射获取bean的id
        String id = bean.getClass().getMethod(GET + idName).invoke(bean).toString();
        // 反射获取bean的children
        List<T> children = (List) bean.getClass().getMethod(GET + childrenName).invoke(bean);
        // 递归遍历
        for (T it : beans) {
            // 反射获取bean的parentId
            String parentId = it.getClass().getMethod(GET + parentIdName).invoke(it).toString();
            // 如果是其子对象
            if (id.equals(parentId)) {
                // 如果children为空，则创建
                if (children == null) {
                    bean.getClass().getMethod(SET + childrenName, List.class).invoke(bean, new ArrayList<>());
                }
                // 重新获取子对象的list
                children = (List) bean.getClass().getMethod(GET + childrenName).invoke(bean);
                // 继续递归遍历
                children.add(findChildren(it, beans, idName, parentIdName, childrenName));
            }
        }
        return bean;
    }
}