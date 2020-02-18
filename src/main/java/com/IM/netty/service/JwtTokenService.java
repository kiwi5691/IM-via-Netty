package com.IM.netty.service;


import com.IM.netty.entity.JwtToken;

public interface JwtTokenService {
    JwtToken findById(Integer id);
}
