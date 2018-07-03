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
package com.gxhl.jts.common.exception;

import lombok.Data;

/**
 * Created by lt on 2017/6/20.
 */
@Data
public class RestException extends RuntimeException {

    private String msg;
    private int code = 500;

    /**
     *
     * @param msg
     */
    public RestException(String msg) {
        super(msg);
        this.msg = msg;
    }

    /**
     *
     * @param msg
     * @param e
     */
    public RestException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    /**
     *
     * @param msg
     * @param code
     */
    public RestException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    /**
     *
     * @param msg
     * @param code
     * @param e
     */
    public RestException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

}
