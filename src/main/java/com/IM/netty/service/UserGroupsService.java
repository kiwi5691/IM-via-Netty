package com.IM.netty.service;

import com.IM.netty.entity.User;
import com.IM.netty.entity.UserGroups;

import java.util.List;
import java.util.Set;

public interface UserGroupsService {
    List<UserGroups> finAllFriends(Integer id);
    Set<User> getFriendDetails(List<UserGroups> userGroups);
}
