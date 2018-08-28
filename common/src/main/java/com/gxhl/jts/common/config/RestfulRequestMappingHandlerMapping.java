/**
 * @文件名称： RestfulRequestMappingHandlerMapping.java
 * @文件描述：
 * @版权所有：(C)2016-2028
 * @公司：
 * @完成日期: 2016/12/5
 * @作者 ： Roger
 */
package com.gxhl.jts.common.config;

import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Roger
 * @description
 * @create 2016/12/5 3:00
 */
public class RestfulRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    public static final String SERVICE_NAME = "service-name";
    private final Map<HandlerMethod, RequestMappingInfo> mappingLookup = new LinkedHashMap<HandlerMethod, RequestMappingInfo>();

    /**
     * @param lookupPath
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    protected HandlerMethod lookupHandlerMethod(String lookupPath, HttpServletRequest request) throws Exception {
        //自己的查找逻辑，如果找不到，再执行原有的逻辑，以免出现错误情况
        HandlerMethod handlerMethod = lookupHandlerMethodHere(lookupPath, request);
        if (handlerMethod == null)
            handlerMethod = super.lookupHandlerMethod(lookupPath, request);
        return handlerMethod;
    }

    /**
     * @param lookupPath
     * @param request
     * @return
     */
    private HandlerMethod lookupHandlerMethodHere(String lookupPath, HttpServletRequest request) {
        String servicename = request.getHeader(SERVICE_NAME);
        if (!StringUtils.isEmpty(servicename)) {
            List<HandlerMethod> methodList = this.getHandlerMethodsForMappingName(servicename);
            if (methodList.size() > 0) {
                HandlerMethod handlerMethod = methodList.get(0);
                RequestMappingInfo requestMappingInfo = mappingLookup.get(handlerMethod);
                handleMatch(requestMappingInfo, lookupPath, request);
                return handlerMethod;
            }
        }
        return null;
    }

    /**
     * @param handler
     * @param method
     * @param mapping
     */
    @Override
    protected void registerHandlerMethod(Object handler, Method method, RequestMappingInfo mapping) {
        HandlerMethod handlerMethod = createHandlerMethod(handler, method);
        mappingLookup.put(handlerMethod, mapping);
        super.registerHandlerMethod(handler, method, mapping);
    }
}
