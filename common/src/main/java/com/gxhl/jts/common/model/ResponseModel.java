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
package com.gxhl.jts.common.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 *
 * @author roger.li
 * @since 2018-03-30
 */
public class ResponseModel extends HashMap<String, Object> {

    @Autowired
    private MessageSource messageSource;

    /**
     *
     */
    public ResponseModel() {
        put("code", 0);
        put("msg", "success");
    }

    /**
     *
     * @return
     */
    public static ResponseModel error() {
        return error(500, "未知异常，请联系管理员");
    }

    /**
     *
     * @param msg
     * @return
     */
    public static ResponseModel error(String msg) {
        return error(500, msg);
    }

    /**
     *
     * @param code
     * @param msg
     * @return
     */
    public static ResponseModel error(int code, String msg) {
        ResponseModel responseModel = new ResponseModel();
        responseModel.put("code", code);
        responseModel.put("msg", msg);
        return responseModel;
    }

    /**
     *
     * @param msg
     * @return
     */
    public static ResponseModel ok(String msg) {
        ResponseModel responseModel = new ResponseModel();
        responseModel.put("msg", msg);
        return responseModel;
    }

    /**
     *
     * @param map
     * @return
     */
    public static ResponseModel ok(Map<String, Object> map) {
        ResponseModel responseModel = new ResponseModel();
        responseModel.putAll(map);
        return responseModel;
    }

    /**
     *
     * @return
     */
    public static ResponseModel ok() {
        return new ResponseModel();
    }

    /**
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public ResponseModel put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
