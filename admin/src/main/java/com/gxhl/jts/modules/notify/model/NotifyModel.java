package com.gxhl.jts.modules.notify.model;

import com.gxhl.jts.modules.notify.entity.Notify;
import lombok.Data;

@Data
public class NotifyModel extends Notify {

    private static final long serialVersionUID = 1L;

    private String isRead;

    private String before;

    private String sender;

    @Override
    public String toString() {
        return "NotifyDTO{" +
                "isRead='" + isRead + '\'' +
                ", before='" + before + '\'' +
                ", sender='" + sender + '\'' +
                '}';
    }
}
