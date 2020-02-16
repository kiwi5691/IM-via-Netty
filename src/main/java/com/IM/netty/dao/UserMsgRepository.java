package com.IM.netty.dao;

import com.IM.netty.dao.base.BaseRepo;
import com.IM.netty.entity.UserMsg;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import static javafx.scene.input.KeyCode.T;

/**
 * @author kiwi
 * @date 2019/11/24 11:27
 */
@Repository("userMsgDao")
public interface UserMsgRepository extends BaseRepo<UserMsg> {
    @Modifying
    @Transactional
    @Query(value = "update UserMsg u set u.isSign=1 where u.id in (:collection)")
    int update(@Param("collection") Collection<Long> collection);

}
