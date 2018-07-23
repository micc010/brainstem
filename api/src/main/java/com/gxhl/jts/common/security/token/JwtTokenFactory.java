package com.gxhl.jts.common.security.token;

import com.gxhl.jts.common.config.JwtProperties;
import com.gxhl.jts.common.security.jti.JtiGenerator;
import com.gxhl.jts.common.security.model.UserContext;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Factory class that should be always used to create {@link JwtToken}.
 *
 * @author vladimir.stankovic
 *         <p>
 *         May 31, 2016
 */
@Component
public class JwtTokenFactory {

    private final JwtProperties jwtProperties;

    @Autowired
    @Qualifier("uuidGenerator")
    private JtiGenerator jtiGenerator;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    public JwtTokenFactory(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    /**
     * Factory method for issuing new JWT Tokens.
     *
     * @param userContext
     *
     * @return
     */
    public AccessJwtToken createAccessJwtToken(UserContext userContext) {
        if (userContext.getUser() == null || StringUtils.isEmpty(userContext.getUser().getUsername()))
            throw new IllegalArgumentException(
                    messageSource.getMessage("jwt.without.user", null, LocaleContextHolder.getLocale())
            );

        Claims claims = Jwts.claims().setSubject(userContext.getUser().getUsername());

        DateTime currentTime = new DateTime();

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuer(jwtProperties.getTokenIssuer())
                .setIssuedAt(currentTime.toDate())
                .setExpiration(currentTime.plusMinutes(jwtProperties.getTokenExpirationTime()).toDate())
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getTokenSigningKey())
                .compact();

        return new AccessJwtToken(token, claims);
    }

    /**
     * @param userContext
     *
     * @return
     */
    public JwtToken createRefreshToken(UserContext userContext) {
        if (userContext.getUser() == null || StringUtils.isEmpty(userContext.getUser().getUsername())) {
            throw new IllegalArgumentException(
                    messageSource.getMessage("jwt.without.user", null, LocaleContextHolder.getLocale())
            );
        }

        DateTime currentTime = new DateTime();

        Claims claims = Jwts.claims().setSubject(userContext.getUser().getUsername());

        // 生成jti
        String jti = jtiGenerator.generateId(currentTime.toDate(),
                currentTime.plusMinutes(jwtProperties.getRefreshTokenExpTime()).toDate());

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuer(jwtProperties.getTokenIssuer())
                .setId(jti)
                .setIssuedAt(currentTime.toDate())
                .setExpiration(currentTime.plusMinutes(jwtProperties.getRefreshTokenExpTime()).toDate())
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getTokenSigningKey())
                .compact();

        return new AccessJwtToken(token, claims);
    }
}
