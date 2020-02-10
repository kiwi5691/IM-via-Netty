package com.IM.netty.service;

import com.IM.netty.entity.UserMsg;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author kiwi
 * @date 2019/11/24 11:38
 */
public interface UserMsgService {
    UserMsg findUserMsg(Long id);
    List<UserMsg> listUserMsgs();
    Page<UserMsg> findUserMsg(Pageable pageable);
    int insert(UserMsg userMsg);
    void deletes(List<String> ids);
    void delete(Long id);
}
