package com.IM.netty.service.impl;

import com.IM.netty.dao.UserRepository;
import com.IM.netty.entity.User;
import com.IM.netty.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("userService")
public class UserServiceImpl  implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public List<User> listUsers() {
        Iterable<User> userIterable = userRepository.findAll();
        List<User> users = new ArrayList<>();
        userIterable.forEach(users::add);
        return users;
    }
}
