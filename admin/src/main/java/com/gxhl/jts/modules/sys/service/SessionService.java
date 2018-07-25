package com.gxhl.jts.modules.sys.service;

import com.gxhl.jts.modules.sys.entity.SysUser;
import com.gxhl.jts.modules.sys.model.UserOnline;
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
