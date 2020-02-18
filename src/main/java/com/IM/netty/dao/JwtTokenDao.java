package com.IM.netty.dao;

import com.IM.netty.dao.base.BaseRepoInteger;
import com.IM.netty.entity.JwtToken;
import org.springframework.stereotype.Repository;

/**
 * @author kiwi
 * @date 2019/11/24 11:27
 */
@Repository("jwtTokenDao")
public interface JwtTokenDao extends BaseRepoInteger<JwtToken> {
}
