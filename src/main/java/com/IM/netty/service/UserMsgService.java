package com.IM.netty.service;

import com.IM.netty.entity.User;
import com.IM.netty.entity.UserMsg;
import com.IM.netty.model.dto.ChatMsg;
import com.IM.netty.model.dto.UserDTO;
import com.IM.netty.model.dto.UserInfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author kiwi
 * @date 2019/11/24 11:38
 */
public interface UserMsgService {
    UserMsg findUserMsg(Long id);
    List<UserMsg> listUserMsgs();
    Page<UserMsg> findUserMsg(Pageable pageable);
    Long insert(ChatMsg chatMsg);
    void deletes(List<String> ids);
    void delete(Long id);
    List<UserDTO> getUserMsgDTO(Set<User> users, Integer userId, Optional<Set<User>> userFriends);
    List<UserMsg> getUserMsgById(Integer id);
    List<UserInfoDTO> listUserInfo(List<UserDTO> userDTOList);
    List<UserMsg> listUserMsgByFid(Integer userId,Integer fid);
}
