package com.IM.netty.controller.view;

import com.IM.netty.cache.UserStatusCacheMap;
import com.IM.netty.entity.User;
import com.IM.netty.entity.UserGroups;
import com.IM.netty.model.dto.ChatMsg;
import com.IM.netty.service.UserGroupsService;
import com.IM.netty.service.UserMsgService;
import com.IM.netty.service.UserService;
import com.IM.netty.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequestMapping("msg/")
public class LoginController {

    @Autowired
    private HttpSession session;

    @Autowired
    private UserService userService;
    @Autowired
    private UserGroupsService userGroupsService;

    @RequestMapping("/chat")
    public String chat(HttpServletRequest req) {
        return "chat";
    }

    @GetMapping("/login")
    public String getLogin(Model model) {
        model.addAttribute("hide",false);
        return "login";
    }
    @PostMapping("/login")
    public String postLogin(String name, Model model,HttpServletRequest request) {

        List<User> userList =userService.listUsers();
        List<User> checkUsers = userList.stream().filter(user -> user.getNickname().equals(name)).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(checkUsers)){
            User user = checkUsers.get(0);

            Optional<List<UserGroups>> userGroups = Optional.ofNullable(userGroupsService.finAllFriends(user.getId()));
            Set<User> users = new HashSet<>();
            if(userGroups.isPresent()){
                users = userGroupsService.getFriendDetails(userGroups.get());
            }
            Map<String,Object> map = getFirstOne(users);
            //存放好友记录
            UserStatusCacheMap.saveFriendLists(user.getId(),users);
            //存放当前session
            session.setAttribute("currentId",user.getId());
            model.addAttribute("users",users);
            model.addAttribute("fNickName", map.get("fNickName"));
            model.addAttribute("fId", map.get("fId"));

        }else{
            model.addAttribute("hide",true);
            return "login";
        }
        return "friendList";
    }


    public Map<String,Object> getFirstOne(Set<User> userSet){
        Map<String,Object> map = new HashMap<>();
        map.put("fId",userSet.iterator().next().getId());
        map.put("fNickName",userSet.iterator().next().getNickname());
        return map;
    }
}
