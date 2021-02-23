package com.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.SysDict;
import com.mapper.SysDictMapper;
import org.springframework.stereotype.Service;

@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper,SysDict> implements ISysDictService {
}
