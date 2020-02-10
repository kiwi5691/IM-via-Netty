package com.IM.netty.controller.view;

import com.IM.netty.cache.UserStatusCacheMap;
import com.IM.netty.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class ChatController {

    @Autowired
    private HttpSession session;

    @GetMapping({"/friends","/friends.html"})
    public String getFriends(Model model) {
        Object id = session.getAttribute("currentId");
        if(id==null){
            model.addAttribute("hide",true);
            return "redirect:/login";
        }
        Optional<Set<User>> optionalUsers = Optional.ofNullable(UserStatusCacheMap.getFriendLists(Integer.parseInt(id.toString())));
        if(optionalUsers.isPresent()) {
            Map<String,Object> map = getFirstOne(optionalUsers.get());
            model.addAttribute("users", optionalUsers.get());
            model.addAttribute("fNickName", map.get("fNickName"));
            model.addAttribute("fId", map.get("fId"));
            return "friendList";
        }else {
            model.addAttribute("hide",true);
            return "redirect:/login";
        }

    }


    @GetMapping({"/chatting","/chatting.html"})
    public String getChatting(Model model) {
        Object id = session.getAttribute("currentId");
        if(id==null){
            model.addAttribute("hide",true);
            return "redirect:/login";
        }
        Optional<Set<User>> optionalUsers = Optional.ofNullable(UserStatusCacheMap.getFriendLists(Integer.parseInt(id.toString())));
        if(optionalUsers.isPresent()) {
            Map<String,Object> map = getFirstOne(optionalUsers.get());
            model.addAttribute("users", optionalUsers.get());
            model.addAttribute("fNickName", map.get("fNickName"));
            model.addAttribute("fId", map.get("fId"));


            return "chatting";
        }

        return "redirect:/login";
    }

    public Map<String,Object> getFirstOne(Set<User> userSet){
        Map<String,Object> map = new HashMap<>();
        map.put("fId",userSet.iterator().next().getId());
        map.put("fNickName",userSet.iterator().next().getNickname());
        return map;
    }
}
