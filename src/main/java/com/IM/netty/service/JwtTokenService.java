package com.IM.netty.service;


import com.IM.netty.entity.JwtToken;

import java.util.List;

public interface JwtTokenService {
    JwtToken findById(Integer id);
    Iterable<JwtToken> listAll();
}
