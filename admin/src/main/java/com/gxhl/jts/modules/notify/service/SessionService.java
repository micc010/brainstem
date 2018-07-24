package com.gxhl.jts.modules.notify.service;

import com.gxhl.jts.modules.notify.model.UserOnline;
import com.gxhl.jts.modules.sys.entity.SysUser;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public interface SessionService {
    List<UserOnline> list();

    List<SysUser> listOnlineUser();

    Collection<Session> sessionList();

    boolean forceLogout(String sessionId);
}
