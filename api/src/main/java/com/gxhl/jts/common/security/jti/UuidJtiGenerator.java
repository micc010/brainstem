/**
 * @文件名称： DefaultJtiGenerator.java
 * @文件描述：
 * @版权所有：(C)2017-2028
 * @公司：
 * @完成日期: 2017/1/6
 * @作者 ： Roger
 */
package com.gxhl.jts.common.security.jti;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/**
 * @author Roger
 * @create 2017/1/6 10:42
 */
@Component
@Qualifier("uuidGenerator")
public class UuidJtiGenerator implements JtiGenerator {

    @Override
    public String generateId(Date issuedAt, Date expiration) {
        String jti = UUID.randomUUID().toString();
        return jti;
    }
}
