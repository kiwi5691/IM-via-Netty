package com.IM.netty.service.impl;

import com.IM.netty.dao.UserMsgRepository;
import com.IM.netty.entity.UserMsg;
import com.IM.netty.service.UserMsgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author kiwi
 * @date 2019/11/24 11:39
 */
@Slf4j
@Service("userMsgService")
public class UserMsgServiceImpl implements UserMsgService {
    @Autowired
    private UserMsgRepository userMsgRepository;

    @Override
    @Cacheable(value = "user-Msg", key = "#id", unless = "#result eq null")
    public UserMsg findUserMsg(Long id) {
        return userMsgRepository.findById(id).orElse(null);
    }

    @Override
    public List<UserMsg> listUserMsgs() {
        return null;
    }

    @Override
    public Page<UserMsg> findUserMsg(Pageable pageable) {
        return userMsgRepository.findAll(pageable);
    }

    @Override
    @Cacheable(value = "user-Msg",key = "#result.id", unless = "#result eq null")
    public int insert(UserMsg userMsg) {
        //TODO VO
        userMsgRepository.save(userMsg);
        return 0;
    }


    @Override
    public void deletes(List<String> ids) {

    }

    @Override
    @CacheEvict(value = "user-Msg", key = "#id")
    public void delete(Long id) {
        userMsgRepository.findById(id).ifPresent(userMsgRepository::delete);
    }
}
