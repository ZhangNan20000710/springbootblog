package com.example.demo.controller;

import com.example.demo.dao.IUserDao;
import com.example.demo.domain.Msg;
import com.example.demo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    IUserDao mapper;

    @ResponseBody
    @PostMapping("/sign")
    public Msg sign(String username, String password, HttpSession session){
        Long i = mapper.countByUsernameAndPassword(username, password);
        if(i!=0){
            session.setAttribute("username",username);
            return Msg.success();
        }else {
            return Msg.error();
        }
    }
    @ResponseBody
    @GetMapping("/showAll")
    public Msg showAll(String username){
        User user = mapper.findByUsername(username);
        return Msg.success().add("user",user);
    }

    @GetMapping("/Logout")
    public String Logout(HttpSession session, HttpServletRequest request){
        session.invalidate();
        return "redirect:"+request.getHeader("referer");
    }
}
