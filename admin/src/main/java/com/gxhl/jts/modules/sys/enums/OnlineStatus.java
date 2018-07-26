package com.gxhl.jts.modules.sys.enums;

/**
 * @author roger.li
 * @since 2018/7/26
 */
public enum OnlineStatus {

    online("在线"), offline("离线");

    private final String statusName;

    OnlineStatus(String statusName) {
        this.statusName = statusName;
    }


}
