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
package com.gxhl.jts.handler;

import com.gxhl.jts.common.exception.RestException;
import com.gxhl.jts.common.model.ResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理器
 *
 * @author roger.li
 * @since 2018-03-30
 */
@RestControllerAdvice
public class RestExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(RestException.class)
    public ResponseModel handleRRException(RestException e) {
        ResponseModel responseModel = new ResponseModel();
        responseModel.put("code", e.getCode());
        responseModel.put("msg", e.getMessage());

        return responseModel;
    }

    /**
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseModel handleException(Exception e) {
        logger.error(e.getMessage(), e);
        return ResponseModel.error(e.getMessage());
    }
    
}
