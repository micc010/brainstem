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
package com.github.rogerli.common.utils;

import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;

import java.lang.reflect.Field;

/**
 * 代理工具
 *
 * @author roger.li
 * @since 2018-03-30
 */
public class AopTargetUtils {

    /**
     * 获取被代理的接口类
     *
     * @param proxy
     *         代理对象
     * @return
     * @throws Exception
     */
    public static Class<?>[] getInterfaces(Object proxy) throws Exception {
        if (!AopUtils.isAopProxy(proxy)) {
            return new Class[0];// 不是代理对象
        }
        if (AopUtils.isJdkDynamicProxy(proxy)) {
            return getJdkDynamicProxyTargetInterface(proxy);
        } else { // cglib
            return getCglibProxyTargetInterface(proxy);
        }
    }

    /**
     * 获得cglib代理接口
     *
     * @param proxy
     * @return
     * @throws Exception
     */
    private static Class<?>[] getCglibProxyTargetInterface(Object proxy)
            throws Exception {
        Field h = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
        h.setAccessible(true);
        Object dynamicAdvisedInterceptor = h.get(proxy);

        Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField(
                "advised");
        advised.setAccessible(true);
        Class<?>[] proxiedInterfaces = ((AdvisedSupport) advised
                .get(dynamicAdvisedInterceptor)).getProxiedInterfaces();
        return proxiedInterfaces;
    }

    /**
     * 获得jdk代理接口
     *
     * @param proxy
     * @return
     * @throws Exception
     */
    private static Class<?>[] getJdkDynamicProxyTargetInterface(Object proxy)
            throws Exception {
        Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
        h.setAccessible(true);
        AopProxy aopProxy = (AopProxy) h.get(proxy);

        Field advised = aopProxy.getClass().getDeclaredField("advised");
        advised.setAccessible(true);

        Class<?>[] proxiedInterfaces = ((AdvisedSupport) advised.get(aopProxy))
                .getProxiedInterfaces();
        return proxiedInterfaces;
    }

    /**
     * 获取目标对象
     *
     * @param proxy
     *         代理对象
     * @return
     * @throws Exception
     */
    public static Object getTarget(Object proxy) throws Exception {
        if (!AopUtils.isAopProxy(proxy)) {
            return proxy;// 不是代理对象
        }
        if (AopUtils.isJdkDynamicProxy(proxy)) {
            return getJdkDynamicProxyTargetObject(proxy);
        } else { // cglib
            return getCglibProxyTargetObject(proxy);
        }
    }

    /**
     * 获得cglib代理对象
     *
     * @param proxy
     * @return
     * @throws Exception
     */
    private static Object getCglibProxyTargetObject(Object proxy)
            throws Exception {
        Field h = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
        h.setAccessible(true);
        Object dynamicAdvisedInterceptor = h.get(proxy);

        Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField(
                "advised");
        advised.setAccessible(true);
        ((AdvisedSupport) advised.get(dynamicAdvisedInterceptor))
                .getProxiedInterfaces();
        Object target = ((AdvisedSupport) advised
                .get(dynamicAdvisedInterceptor)).getTargetSource().getTarget();
        if (AopUtils.isCglibProxy(target)) {
            target = getCglibProxyTargetObject(target);
        }
        return target;
    }

    /**
     * 获得jdk代理对象
     *
     * @param proxy
     * @return
     * @throws Exception
     */
    private static Object getJdkDynamicProxyTargetObject(Object proxy)
            throws Exception {
        Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
        h.setAccessible(true);
        AopProxy aopProxy = (AopProxy) h.get(proxy);

        Field advised = aopProxy.getClass().getDeclaredField("advised");
        advised.setAccessible(true);

        Object target = ((AdvisedSupport) advised.get(aopProxy))
                .getTargetSource().getTarget();
        if (AopUtils.isJdkDynamicProxy(target)) {
            target = getJdkDynamicProxyTargetObject(target);
        }
        return target;
    }

}