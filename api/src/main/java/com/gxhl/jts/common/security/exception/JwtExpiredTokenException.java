package com.gxhl.jts.common.security.exception;

import com.gxhl.jts.common.security.token.JwtToken;
import org.springframework.security.core.AuthenticationException;

/**
 * @author roger.li
 * @since 2018-03-30
 */
public class JwtExpiredTokenException extends AuthenticationException {

    private static final long serialVersionUID = -5959543783324224864L;
    
    private JwtToken token;

    /**
     *
     * @param msg
     */
    public JwtExpiredTokenException(String msg) {
        super(msg);
    }

    /**
     *
     * @param token
     * @param msg
     * @param t
     */
    public JwtExpiredTokenException(JwtToken token, String msg, Throwable t) {
        super(msg, t);
        this.token = token;
    }

    /**
     *
     * @return
     */
    public String token() {
        return this.token.getToken();
    }

}
