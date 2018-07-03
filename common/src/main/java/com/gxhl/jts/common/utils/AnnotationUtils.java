/**
 * Copyright 2018 http://github.com/micc010
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.gxhl.jts.common.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 注解工具
 *
 * @author roger.li
 * @since 2018-03-30
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class AnnotationUtils {

    /**
     * 读取method注解值
     *
     * @param annotationClasss
     *         注解类
     * @param targetClass
     *         使用注解的类
     * @param annotationFields
     *         注解属性的名称
     * @throws Exception
     */
    public static Map<String, Map<String, Object>> getMethodValue(
            Class annotationClasss, Class targetClass,
            String... annotationFields) throws Exception {
        Map<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>(
                20);
        Method[] methods = targetClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(annotationClasss)) {
                Map<String, Object> methodMap = new HashMap<String, Object>(10);
                Annotation p = method.getAnnotation(annotationClasss);
                for (String annotationField : annotationFields) {
                    Method m = p.getClass().getDeclaredMethod(annotationField);
                    if (m == null) {
                        continue;
                    }
                    Object value = m.invoke(p);
                    methodMap.put(annotationField, value);
                }
            }
        }
        return map;
    }

    /**
     * 读取field注解值
     *
     * @param annotationClasss
     *         注解类
     * @param targetClass
     *         使用注解的类
     * @param annotationFields
     *         注解属性的名称
     * @return
     * @throws Exception
     */
    public static Map<String, Map<String, Object>> getFieldValue(
            Class annotationClasss, Class targetClass,
            String... annotationFields) {
        Map<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>(
                20);
        Field[] fields = targetClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(annotationClasss)) {
                Map<String, Object> fieldMap = new HashMap<String, Object>(10);
                map.put(field.getName(), fieldMap);
                Annotation p = field.getAnnotation(annotationClasss);
                for (String annotationField : annotationFields) {
                    try {
                        Method m = p.getClass().getDeclaredMethod(
                                annotationField);
                        if (m == null) {
                            continue;
                        }
                        Object value = m.invoke(p);
                        fieldMap.put(annotationField, value);
                    } catch (Exception e) {
                        continue;
                    }
                }
            }
        }
        return map;
    }

    /**
     * 读取class注解值
     *
     * @param annotationClasss
     *         注解类
     * @param targetClass
     *         使用注解的类
     * @param annotationFields
     *         注解属性的名称
     * @return
     * @throws Exception
     */
    public static Map<String, Object> getClassValue(Class annotationClasss,
                                                    Class targetClass, String... annotationFields) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>(10);
        if (targetClass.isAnnotationPresent(annotationClasss)) {
            Annotation p = targetClass.getAnnotation(annotationClasss);
            for (String annotationField : annotationFields) {
                Method m = p.getClass().getMethod(annotationField);
                if (m == null) {
                    continue;
                }
                Object value = m.invoke(p);
                map.put(annotationField, value);
            }
        }
        return map;
    }
}