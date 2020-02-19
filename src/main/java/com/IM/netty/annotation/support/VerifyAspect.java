package com.IM.netty.annotation.support;

import com.IM.netty.annotation.support.base.ArgsEntity;
import com.IM.netty.cache.JwtLocalCache;
import com.IM.netty.entity.JwtToken;
import com.IM.netty.exception.SellerAuthorizeException;
import com.IM.netty.service.JwtTokenService;
import com.IM.netty.utils.HttpContextUtil;
import com.IM.netty.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author kiwi
 */
@Slf4j
@Aspect
@Component
public class VerifyAspect {

    public static final String LOGIN_TOKEN_KEY = "F-shareNotes-Token";


    @Autowired
    private JwtTokenService jwtTokenService;

    @Pointcut("@annotation(com.IM.netty.annotation.SecurityVerify)")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void before(JoinPoint point) throws Throwable {
        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
        String token = request.getHeader(LOGIN_TOKEN_KEY);
        if (token == null || token.isEmpty()) {
            throw new SellerAuthorizeException();
        }
        Integer userId =this.argsConstructToUserId(point.getArgs());
        if(userId!=null){
            if(!checkToken(userId,token)){
                throw new SellerAuthorizeException();
            }
        }else {
            throw new SellerAuthorizeException();
        }
    }
    public Integer argsConstructToUserId(Object[] args ){
        List<ArgsEntity> argsList = new ArrayList<>();
        for (Object arg : args) {
            ArgsEntity argsEntity =JsonUtils.jsonToPojo(arg.toString(), ArgsEntity.class);
            argsList.add(argsEntity);
        }
        if(!CollectionUtils.isEmpty(argsList)){
            return argsList.get(0).getUserId();
        }
        else {
            return null;
        }
    }

    public Boolean checkToken(Integer userId,String token){
        Optional<String> JwtToken = Optional.ofNullable(JwtLocalCache.get(userId));
        if(JwtToken.isPresent()) {
            if(JwtToken.get().equals(token)){
                return true;
            }else {
                //再次遍历数据库
                com.IM.netty.entity.JwtToken  jwtTokenEntity= jwtTokenService.findById(userId);
                if(jwtTokenEntity!=null&&jwtTokenEntity.getToken().equals(token)){
                    //重新放入缓存
                    JwtLocalCache.set(userId,jwtTokenEntity.getToken());
                    return true;
                }else {
                    return false;
                }
            }
        }else {
            //再次遍历数据库
            com.IM.netty.entity.JwtToken  jwtTokenEntity= jwtTokenService.findById(userId);
            if(jwtTokenEntity!=null&&jwtTokenEntity.getToken().equals(token)){
                //重新放入缓存
                JwtLocalCache.set(userId,jwtTokenEntity.getToken());
                return true;
            }else {
                return false;
            }
        }
    }
}
