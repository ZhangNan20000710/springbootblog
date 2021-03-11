package com.example.demo;

import com.example.demo.dao.IBlogDao;
import com.example.demo.dao.IUserDao;
import com.example.demo.domain.Blog;
import com.example.demo.domain.User;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DemoApplicationTests {

    @Autowired
    IUserDao mapper;
    @Test
    public void contextLoads() {
        User user = new User();
        user.setUpdateTime(new Date());
        User save = mapper.save(user);
        System.out.println(save);
    }
    @Test
    public void test() {
        SimpleDateFormat sdf=new SimpleDateFormat("YYYY_MM_DD_HH_mm_ss");

        System.out.println(sdf.format(new Date()));
    }
    @Test
    public void test1() throws IOException {
        String path="D:\\ideaproject\\springboot_08blog\\target\\classes\\static\\images\\user\\";
        String path2="D:\\ideaproject\\springboot_08blog\\src\\main\\resources\\static\\images\\user\\";

//        FileUtils.copyFile(new File(path,"阿斯蒂芬撒地方第三个合法的2020_03_73_19_57_15193727.jpg"),new File(path2,"阿斯蒂芬撒地方第三个合法的2020_03_73_19_57_15193727.jpg"));
    }
    @Autowired
    IBlogDao mapper1;
    @Test
    public void test2() {
//        List<Blog> blogList = mapper1.findByUserUsername("123");
//        System.out.println(blogList);
    }
    @Test
    public void test3() {
        String str1="sdasfd";
        String str2=str1.intern();
        str2="sdfa";
        System.out.println(str1);
        StringBuilder stringBuilder=new StringBuilder("sadf");
    }
}
