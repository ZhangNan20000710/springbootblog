package com.example.demo.controller;

import com.example.demo.dao.IBlogDao;
import com.example.demo.dao.ITagDao;
import com.example.demo.dao.ITypeDao;
import com.example.demo.domain.Blog;
import com.example.demo.domain.Tag;
import com.example.demo.domain.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class indexController {

    @Autowired
    IBlogDao blogMapper;
    @Autowired
    ITagDao tagMapper;
    @Autowired
    ITypeDao typeMapper;

    /**
     * 显示博客列表
     * @param model
     * @return
     */
    @GetMapping("/")
    public String ShowAllBlog(Model model, @PageableDefault
            (size = 5,sort = "id",direction = Sort.Direction.DESC) Pageable pageable){
        Page<Blog> all = blogMapper.findAll(pageable);
        model.addAttribute("list",all);
        /**
         * 最新推荐
         */
        Pageable pageable1=new PageRequest(0,10
                ,new Sort(Sort.Direction.DESC,"updateTime"));
        Page<Blog> latest = blogMapper.findAll(pageable1);
        model.addAttribute("latest",latest);

        /**
         * tag，type
         */
        List<Tag> tags = tagMapper.findAll();
        List<Blog> blogAll = blogMapper.findAll();
        for(Tag tag:tags){
            Long count=0l;
            for(Blog blog:blogAll){
                if(blog.getTags().contains(tag)){
                    count++;
                }
            }
            tag.setTagCount(count);
        }
        model.addAttribute("tags",tags);


        List<Type> types = typeMapper.findAll();
        for (Type type:types) {
            type.setTypeCount(blogMapper.countByTypeId(type.getId()));
        }
        model.addAttribute("types",types);
        model.addAttribute("count",blogMapper.count());
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "admin/login";
    }
    @GetMapping("/register")
    public String register(){
        return "admin/register";
    }
    @GetMapping("/blogList")
    public String blogList(){
        return "admin/blogList";
    }
    @GetMapping("/blogPublish")
    public String blogPublish(){
        return "admin/blogPublish";
    }
    @GetMapping("/types")
    public String types(){
        return "admin/types";
    }
    @GetMapping("/tags")
    public String tags(){
        return "admin/tags";
    }
    @GetMapping("/blogAlter")
    public String blogAlter(){
        return "admin/blogAlter";
    }
    @GetMapping("/BlogDetails")
    public String BlogDetails(){
        return "admin/BlogDetails";
    }


}
