package com.IM.netty;

import com.IM.netty.annotation.support.base.ArgsEntity;
import com.IM.netty.controller.api.MsgController;
import com.IM.netty.entity.JwtToken;
import com.IM.netty.entity.UserMsg;
import com.IM.netty.service.JwtTokenService;
import com.IM.netty.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest
@WebAppConfiguration
class MockAPITests {

    public static final String LOGIN_TOKEN_KEY = "F-shareNotes-Token";

    @Autowired
    WebApplicationContext context;
    @Autowired
    private JwtTokenService jwtTokenService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
//        this.mockMvc = MockMvcBuilders.standaloneSetup(new MsgController()).build();
        this.mockMvc=MockMvcBuilders.webAppContextSetup(context).build();

    }

    @Test
    public void getUser() throws Exception {
        String responseString = mockMvc.perform(MockMvcRequestBuilders.post("/wx/getUserList")).andReturn().getResponse().getContentAsString();
        System.out.println("result : "+responseString);
    }

    @Test
    public void saveTag() throws Exception {
        JwtToken token =jwtTokenService.findById(3);
        ArgsEntity argsEntity = new ArgsEntity(3,4);
        String paraJson  = JsonUtils.objectToJson( argsEntity);

//        log.info("token"+token.getToken());
        MvcResult mvcResult = mockMvc.perform(

                MockMvcRequestBuilders.post("/wx/getUserList")
//                        .header(LOGIN_TOKEN_KEY,token.getToken())
                        .header(LOGIN_TOKEN_KEY,token.getToken())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(paraJson)
//                        .param("userId", "3")
//                        .param("fid", "4")
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
        .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        log.info(content);
//        Assertions.assertEquals("recive your param name: shangcg level: 1", content);
    }
    @Test
    void contextLoads() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("HH:mm");
        String strDate2 = dtf2.format(localDateTime);
        System.out.println("template:"+strDate2);
        Stream.of(strDate2).forEach(System.out::println);

    }


    @Test
    void Asserts() {
        UserMsg userMsg = new UserMsg();
        UserMsg userMsg1 = new UserMsg();
        Assert.assertSame(userMsg,userMsg1);
    }
}
