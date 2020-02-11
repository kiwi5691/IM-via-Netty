package com.IM.netty.service.impl;

import com.IM.netty.cache.UserStatusCacheMap;
import com.IM.netty.dao.UserMsgRepository;
import com.IM.netty.entity.User;
import com.IM.netty.entity.UserGroups;
import com.IM.netty.entity.UserMsg;
import com.IM.netty.model.dto.UserDTO;
import com.IM.netty.service.UserMsgService;
import com.IM.netty.utils.DtoUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

    @Override
    public List<UserDTO> getUserMsgDTO(Set<User> users, Integer userId,Optional<Set<User>> userFriends) {
        List<UserDTO> userDTOList = copyUserDTOList(users);
        //拷贝到dto

        users.stream().forEach(user -> DtoUtils.copyProperties( users,userDTOList));

        //匿名构造specification
        Specification<UserMsg> specification =( root,  criteriaQuery,  criteriaBuilder)-> {
                List<Predicate> predicates = new ArrayList<>();
                if(userId!=null){
                    predicates.add(criteriaBuilder.equal(root.get("sendId"),userId));
                    predicates.add(criteriaBuilder.equal(root.get("acceptId"),userId));
                }
                return criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
        };

        Optional<List<UserMsg>> userMsgs  = Optional.of(userMsgRepository.findAll(specification));
        if(userMsgs.isPresent()&&userFriends.isPresent()){
            //朋友的IDs
            List<Integer> fIds =userFriends.get().stream().map(User::getId).collect(Collectors.toList());
            Map<Integer,Date> lastTimes = new HashMap<>();
            //fid,UserMsg
            Map<Integer,List<UserMsg>> msgByFidMaps = new HashMap<>();
            for (Integer id: fIds) {
                List<UserMsg> finalUserMsgsList = new ArrayList<>();
                userMsgs.get().stream().forEach(userMsg -> {
                    if(userMsg.getAcceptId().equals(id)||userMsg.getSendId().equals(id)){
                        finalUserMsgsList.add(userMsg);
                    }
                });
                msgByFidMaps.put(id,finalUserMsgsList);
            }
            for (Integer id: fIds) {
                List<UserMsg> userMsgs1 = new ArrayList<>();
                userMsgs1 =msgByFidMaps.get(id);
                Optional<UserMsg> date = userMsgs1.stream().filter(userMsg->userMsg.getCreateTime()!=null).max(Comparator.comparing(UserMsg::getCreateTime));
                date.ifPresent(value -> lastTimes.put(id, value.getCreateTime()));
            }
            userDTOList.forEach(userDTO ->{
                userDTO.setUserMsgs(msgByFidMaps.get(userDTO.getId()));
                userDTO.setLastChatTime(lastTimes.get(userDTO.getId()));
            });
            userDTOList.forEach(userDTO -> log.info(userDTO.toString()));
            return userDTOList;
        }else {
            userDTOList.forEach(userDTO ->{
                userDTO.setUserMsgs(null);
                userDTO.setLastChatTime(null);
            });
            return userDTOList;
        }
    }

    @Override
    public List<UserMsg> getUserMsgById(Integer id) {
        return null;
    }
    public List<UserDTO> copyUserDTOList(Set<User> users){
        List<UserDTO> userDTOList = new ArrayList<>();
        if(users.size()==0){
            return  null;
        }
        for(User user:users){
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setAvatar(user.getAvatar());
            userDTO.setGender(user.getGender());
            userDTO.setUsername(user.getUsername());
            userDTO.setNickname(user.getNickname());
            userDTO.setLastLoginTime(user.getLastLoginTime());
            userDTO.setMobile(user.getMobile());
            userDTO.setLastLoginIp(user.getLastLoginIp());
            userDTO.setPassword(user.getPassword());
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }
}
