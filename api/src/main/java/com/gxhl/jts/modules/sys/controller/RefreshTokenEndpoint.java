package com.gxhl.jts.modules.sys.controller;

import com.github.rogerli.config.jwt.JwtProperties;
import com.github.rogerli.config.jwt.JwtWebSecurityConfiguration;
import com.github.rogerli.config.jwt.auth.extractor.TokenExtractor;
import com.github.rogerli.config.jwt.auth.verifier.TokenVerifier;
import com.github.rogerli.config.jwt.model.UserContext;
import com.github.rogerli.config.jwt.token.JwtToken;
import com.github.rogerli.config.jwt.token.JwtTokenFactory;
import com.github.rogerli.config.jwt.token.RawAccessJwtToken;
import com.github.rogerli.config.jwt.token.RefreshToken;
import com.github.rogerli.system.login.model.LoginRole;
import com.github.rogerli.system.login.service.LoginService;
import com.github.rogerli.utils.RestfulUtils;
import com.github.rogerli.utils.exception.InvalidJwtTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * RefreshTokenEndpoint
 *
 * @author vladimir.stankovic
 *         <p>
 *         Aug 17, 2016
 */
@RestController
public class RefreshTokenEndpoint {

    @Autowired
    private JwtTokenFactory tokenFactory;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private LoginService loginService;

    @Autowired
    @Qualifier("bloomFilterTokenVerifier")
    private TokenVerifier tokenVerifier;

    @Autowired
    @Qualifier("jwtHeaderTokenExtractor")
    private TokenExtractor tokenExtractor;

    @RequestMapping(value = "/api/auth/token", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public Map<String, Object> refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String tokenPayload = tokenExtractor.extract(request.getHeader(JwtWebSecurityConfiguration.JWT_TOKEN_HEADER_PARAM));
        RawAccessJwtToken rawToken = new RawAccessJwtToken(tokenPayload);
        RefreshToken refreshToken = RefreshToken.create(rawToken, jwtProperties.getTokenSigningKey()).orElseThrow(() -> new InvalidJwtTokenException());

        String jti = refreshToken.getJti();
        if (!tokenVerifier.verify(jti)) {
            throw new InvalidJwtTokenException();
        }

        String subject = refreshToken.getSubject();

        Optional<LoginRole> optional = loginService.findRoleByUsername(subject);

        LoginRole login = optional.orElseThrow(() -> new UsernameNotFoundException("User not found: " + subject));

        if (login.getRoleList() == null) {
            throw new InsufficientAuthenticationException("User has no roles assigned");
        }

        List<GrantedAuthority> authorities = login.getRoleList().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getRole()))
                .collect(Collectors.toList());

        UserContext userContext = UserContext.create(login.getUserName(), login.getOrganId(), authorities);

        JwtToken jwtToken = tokenFactory.createAccessJwtToken(userContext);

        Map<String, Object> jsonMap = new HashMap<String, Object>();
        RestfulUtils.fillOk(jsonMap, HttpStatus.OK);
        jsonMap.put("token", jwtToken.getToken());

        return jsonMap;
    }

}
