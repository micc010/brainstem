package com.gxhl.jts.modules.sys.controller;

import com.github.rogerli.config.jwt.auth.JwtAuthenticationToken;
import com.github.rogerli.config.jwt.model.UserContext;
import com.github.rogerli.system.login.entity.Login;
import com.github.rogerli.system.login.service.LoginService;
import com.github.rogerli.system.purview.entity.Purview;
import com.github.rogerli.system.user.entity.UserMeModel;
import com.github.rogerli.system.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * End-point for retrieving logged-in user details.
 *
 * @author vladimir.stankovic
 *         <p>
 *         Aug 4, 2016
 */
@RestController
public class ProfileEndpoint {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/api/me", method = RequestMethod.GET)
    @ResponseBody
    public UserMeModel get(JwtAuthenticationToken token) {
        UserContext userContext = (UserContext) token.getPrincipal();
        userContext.setStatus(HttpStatus.OK.value());

        UserMeModel user = userService.findByUsername(userContext.getUsername());
        user.setStatus(userContext.getStatus());
        user.setOrganId(userContext.getOrganId());
        user.setUsername(userContext.getUsername());
        user.setAuthorities(userContext.getAuthorities());

        Login login = loginService.findByUsername(userContext.getUsername());
        login.setId(login.getId());

        List<Purview> list = loginService.findUserPurview(login);
//        List<String> urls = new ArrayList<>(list.size());
        for (Purview p:
                list) {
            p.setType(null);
            p.setParentId(null);
        }
        user.setUrls(list);

        return user;
    }

}
