package com.example.demo.controller;

import com.example.demo.dao.IUserDao;
import com.example.demo.domain.Msg;
import com.example.demo.domain.User;
import com.example.demo.utils.FileUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;



@RestController
public class RegisterController {
    @Autowired
    IUserDao mapper;
    @PostMapping("/register")
    public Msg register(User user,HttpServletRequest request, @RequestParam("file") MultipartFile upload) throws IOException {
        if(mapper.countByUsername(user.getUsername())!=1){
            if(upload.isEmpty()){
                return Msg.error("文件不存在");
            }
            SimpleDateFormat sdf=new SimpleDateFormat("YYYY_MM_dd_HH_mm_ss");
            String fileName=user.getUsername()+user.getNickname()+sdf.format(new Date())+upload.getOriginalFilename();

            String s = FileUploadUtils.FileUpload(upload, "/images/user/", fileName);
            Date time=new Date();

            user.setAvatar(s);
            user.setCreateTime(time);
            user.setUpdateTime(time);
            User save = mapper.save(user);
            if (save!=null){
                return Msg.success();
            }else {
                return Msg.error();
            }
        }
        else {
            return Msg.error("用户名重复");
        }
    }
}
