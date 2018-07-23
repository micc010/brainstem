package com.gxhl.jts.common.security.token;

public interface JwtToken {

    public static final String JWT_TOKEN_HEADER_PARAM = "token";

    /**
     *
     * @return
     */
    String getToken();

}
