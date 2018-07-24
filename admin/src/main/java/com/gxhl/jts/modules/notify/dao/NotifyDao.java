package com.gxhl.jts.modules.notify.dao;

import com.gxhl.jts.modules.notify.entity.Notify;
import com.gxhl.jts.modules.notify.model.NotifyModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 通知通告
 *
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-10-05 17:11:16
 */
@Mapper
public interface NotifyDao {

    Notify get(Long id);

    List<Notify> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(Notify notify);

    int update(Notify notify);

    int remove(Long id);

    int batchRemove(Long[] ids);

    List<Notify> listByIds(Long[] ids);

    int countDTO(Map<String, Object> map);

    List<NotifyModel> listDTO(Map<String, Object> map);
}
