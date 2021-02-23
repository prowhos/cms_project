package com.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.entity.SysDict;
import com.service.ISysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DictUtils {
    @Autowired
    private ISysDictService service;
    private static ISysDictService sysDictService;
    @PostConstruct
    public void init(){
        sysDictService = service;
    }
    public String getDictLabel(String type,Integer value){
        QueryWrapper<SysDict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type_code",type);
        queryWrapper.eq("value",value);
        SysDict sysDict = sysDictService.getOne(queryWrapper);
        return sysDict.getLabel();
    }
}
