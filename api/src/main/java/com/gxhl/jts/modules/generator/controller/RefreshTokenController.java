package com.gxhl.jts.modules.generator.controller;

import com.gxhl.jts.common.config.JwtProperties;
import com.gxhl.jts.common.model.ResponseModel;
import com.gxhl.jts.common.security.exception.InvalidJwtTokenException;
import com.gxhl.jts.common.security.extractor.TokenExtractor;
import com.gxhl.jts.common.security.model.UserContext;
import com.gxhl.jts.common.security.token.JwtToken;
import com.gxhl.jts.common.security.token.JwtTokenFactory;
import com.gxhl.jts.common.security.token.RawAccessJwtToken;
import com.gxhl.jts.common.security.token.RefreshToken;
import com.gxhl.jts.common.security.verifier.TokenVerifier;
import com.gxhl.jts.modules.generator.entity.User;
import com.gxhl.jts.modules.generator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author roger.li
 * @since 2018-03-30
 */
@RestController
public class RefreshTokenController {

    @Autowired
    private JwtTokenFactory tokenFactory;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier("bloomFilterTokenVerifier")
    private TokenVerifier tokenVerifier;

    @Autowired
    @Qualifier("jwtHeaderTokenExtractor")
    private TokenExtractor tokenExtractor;

    /**
     * @param request
     * @param response
     *
     * @return
     *
     * @throws IOException
     * @throws ServletException
     */
    @RequestMapping(value = "/auth/token", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public Map<String, Object> refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String tokenPayload = tokenExtractor.extract(request.getHeader(JwtToken.JWT_TOKEN_HEADER_PARAM));
        RawAccessJwtToken rawToken = new RawAccessJwtToken(tokenPayload);
        RefreshToken refreshToken = RefreshToken.create(rawToken, jwtProperties.getTokenSigningKey()).orElseThrow(() -> new InvalidJwtTokenException());
        String jti = refreshToken.getJti();
        if (!tokenVerifier.verify(jti)) {
            throw new InvalidJwtTokenException("Jwt is invalid");
        }
        String subject = refreshToken.getSubject();
        User user = userService.queryByUsername(subject);
        if (user == null) {
            throw new InsufficientAuthenticationException("User has no roles assigned");
        }
        UserContext userContext = UserContext.create(user);
        JwtToken jwtToken = tokenFactory.createAccessJwtToken(userContext);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("token", jwtToken.getToken());
        return ResponseModel.ok(jsonMap);
    }

}
