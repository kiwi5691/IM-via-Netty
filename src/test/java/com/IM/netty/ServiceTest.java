package com.IM.netty;

import com.IM.netty.dao.JwtTokenDao;
import com.IM.netty.entity.JwtToken;
import com.IM.netty.service.JwtTokenService;
import com.IM.netty.service.impl.JwtTokenServiceImpl;
import com.IM.netty.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ReflectionUtils;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import java.lang.reflect.Field;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
//@SpringBootTest(classes ={IMApplication.class})
public class ServiceTest {


    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
     JwtTokenService jwtTokenService = new JwtTokenServiceImpl();

    @Mock
    private JwtTokenDao jwtTokenDao;

    @Test
    public void testFindId(){

        JwtToken jwtToken =jwtTokenService.findById(3);
        log.info("jwtToken"+jwtToken.toString());
//        Mockito.doReturn("我是模拟的返回值").when(em)( any());

    }



}
