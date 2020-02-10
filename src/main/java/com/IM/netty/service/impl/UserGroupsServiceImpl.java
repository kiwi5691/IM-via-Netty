package com.IM.netty.service.impl;

import com.IM.netty.dao.UserGroupsRepository;
import com.IM.netty.dao.UserRepository;
import com.IM.netty.entity.User;
import com.IM.netty.entity.UserGroups;
import com.IM.netty.service.UserGroupsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.*;

@Service("userGroupsService")
public class UserGroupsServiceImpl implements UserGroupsService {
    @Autowired
    private UserGroupsRepository userGroupsRepository;

    @Autowired
    private UserRepository userRepository;
    @Override
    public List<UserGroups> finAllFriends(Integer id) {
        Specification<UserGroups> specification = new Specification<UserGroups>() {
            @Override
            public Predicate toPredicate(Root<UserGroups> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Object> userId = root.get("userId");
                Predicate predicate = criteriaBuilder.equal(userId, id);
                return predicate;
            }
        };
        List<UserGroups> userGroups = userGroupsRepository.findAll(specification);
        return userGroups;
    }

    @Override
    public Set<User> getFriendDetails(List<UserGroups> userGroups) {
        List<Integer> friendsId = new ArrayList<>();
        Set<User> userSets =  new HashSet<>();
        userGroups.forEach(userGroups1 -> friendsId.add(userGroups1.getFriendId()));
        for (Integer tIds: friendsId) {
            Specification<User> specification = new Specification<User>() {
                @Override
                public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    Path<Object> id = root.get("id");
                    Predicate predicate = criteriaBuilder.equal(id, tIds);
                    return predicate;
                }
            };
            Optional<User> user;
            user= userRepository.findOne(specification);
            user.ifPresent(userSets::add);
        }


        return userSets;
    }
}
