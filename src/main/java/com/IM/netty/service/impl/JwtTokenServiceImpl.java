package com.IM.netty.service.impl;

import com.IM.netty.dao.JwtTokenDao;
import com.IM.netty.entity.JwtToken;
import com.IM.netty.service.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("jwtTokenService")
public class JwtTokenServiceImpl implements JwtTokenService {
    @Autowired
    private JwtTokenDao jwtTokenDao;


    @Override
    public JwtToken findById(Integer id) {
        Optional<JwtToken> jwtToken =jwtTokenDao.findById(id);
        return jwtToken.orElse(null);
    }
}

