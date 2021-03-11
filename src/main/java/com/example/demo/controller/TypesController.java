package com.example.demo.controller;

import com.example.demo.dao.ITypeDao;
import com.example.demo.domain.Msg;
import com.example.demo.domain.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TypesController {

    @Autowired
    ITypeDao mapper;
    @PostMapping("/addTypes")
    public Msg addTypes(Type type){
        if(mapper.countByName(type.getName())<1){
            Type save = mapper.save(type);
            return Msg.success();
        }else {
            return Msg.error("分类名称重复");
        }

    }
    @GetMapping("/ShowTypes/{i}")
    public Msg showTypes(@PathVariable int i){
        if(i==-1){
            List<Type> all = mapper.findAll();
            return Msg.success().add("list",all);
        }else {
            Pageable pageable=new PageRequest(i,5);
            Page<Type> all = mapper.findAll(pageable);
            return Msg.success().add("list",all);
        }
    }
    @DeleteMapping("/DelType/{i}")
    public Msg DelType(@PathVariable Long i){
        mapper.delete(i);
        return Msg.success();
    }

    @PutMapping("/AlterType/{i}")
    public Msg AlterType(@PathVariable Long i,String name){
        if(mapper.countByName(name)<1){
            Type type = new Type();
            type.setId(i);
            type.setName(name);
            mapper.save(type);
            return Msg.success();
        }else {
            return Msg.error("分类名称重复");
        }
    }
}