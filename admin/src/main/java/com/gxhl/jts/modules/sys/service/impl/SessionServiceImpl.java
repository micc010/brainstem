package com.gxhl.jts.modules.sys.service.impl;

import com.gxhl.jts.modules.sys.entity.SysUser;
import com.gxhl.jts.modules.sys.model.UserOnline;
import com.gxhl.jts.modules.sys.service.SessionService;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 待完善
 *
 * @author bootdo
 */
@Service
public class SessionServiceImpl implements SessionService {

    @Resource(name = "sessionRepository")
    private SessionRepository sessionRepository;

    @Resource
    private SessionRegistry sessionRegistry;


    @Override
    public List<UserOnline> list() {
        List<UserOnline> list = new ArrayList<>();
        List<Object> principals = sessionRegistry.getAllPrincipals();
        for (Object principal : principals) {
            SysUser user = (SysUser) principal;
            List<SessionInformation> informationList = sessionRegistry.getAllSessions(principal, true);
            for (SessionInformation info :
                    informationList) {
                UserOnline userOnline = new UserOnline();
                userOnline.setId(user.getUserId().toString());
                userOnline.setUsername(user.getUsername());
                userOnline.setHost(info.get());
                userOnline.setStartTimestamp(.getStartTimestamp());
                userOnline.setLastAccessTime(info.getLastRequest());
                userOnline.setTimeout(info.);
                list.add(userOnline);
            }
        }
        return list;
    }

    @Override
    public List<SysUser> listOnlineUser() {
        List<SysUser> list = new ArrayList<>();
        SysUser userDO;
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        for (Session session : sessions) {
            SimplePrincipalCollection principalCollection = new SimplePrincipalCollection();
            if (session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) == null) {
                continue;
            } else {
                principalCollection = (SimplePrincipalCollection) session
                        .getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
                userDO = (UserDO) principalCollection.getPrimaryPrincipal();
                list.add(userDO);
            }
        }
        return list;
    }

    @Override
    public Collection<Session> sessionList() {
        return sessionDAO.getActiveSessions();
    }

    @Override
    public boolean forceLogout(String sessionId) {
        Session session = sessionDAO.readSession(sessionId);
        session.setTimeout(0);
        return true;
    }
}
