package com.IM.netty.dao;

import com.IM.netty.dao.base.BaseRepo;
import com.IM.netty.entity.UserMsg;
import org.springframework.stereotype.Repository;

/**
 * @author kiwi
 * @date 2019/11/24 11:27
 */
@Repository("userMsgDao")
public interface UserMsgRepository extends BaseRepo<UserMsg> {
}
