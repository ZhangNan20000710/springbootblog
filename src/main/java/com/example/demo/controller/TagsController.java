package com.example.demo.controller;

import com.example.demo.dao.ITagDao;
import com.example.demo.domain.Msg;
import com.example.demo.domain.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TagsController {
    @Autowired
    ITagDao mapper;

    @GetMapping("/showTags/{id}")
    public Msg ShowTags(@PathVariable int id){
        if(id==-1){
            List<Tag> all = mapper.findAll();
            return Msg.success().add("list",all);
        }else {
            Pageable pageable=new PageRequest(id,5);
            Page<Tag> all = mapper.findAll(pageable);
            return Msg.success().add("list",all);
        }
    }
    @PostMapping("/addTag")
    public Msg SaveTag(Tag tag){
        if(mapper.countByName(tag.getName())<1){
            mapper.save(tag);
            return Msg.success();
        }else {
            return Msg.error("标签名重复");
        }
    }
    @DeleteMapping("/delTag/{id}")
    public Msg DelTag(@PathVariable Long id){
        mapper.delete(id);
        return Msg.success();
    }
}
