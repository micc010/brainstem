package com.gxhl.jts.modules.sys.model;

import com.gxhl.jts.modules.sys.enums.OnlineStatus;
import lombok.Data;

import java.util.Date;

/**
 *
 *
 */
@Data
public class UserOnline {

    private String id;
    private String userId;
    private String username;
    /**
     * 用户主机地址
     */
    private String host;
    /**
     * 用户登录时系统IP
     */
    private String systemHost;
    /**
     * 用户浏览器类型
     */
    private String userAgent;
    /**
    * 在线状态
    */
    private String status = OnlineStatus.online.name();
    /**
     * session创建时间
     */
    private Date startTimestamp;
    /**
     * session最后访问时间
     */
    private Date lastAccessTime;
    /**
     * 超时时间
     */
    private Long timeout;

}
