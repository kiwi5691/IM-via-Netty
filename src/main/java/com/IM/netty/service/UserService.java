package com.IM.netty.service;

import com.IM.netty.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {
    List<User> listUsers();
}
