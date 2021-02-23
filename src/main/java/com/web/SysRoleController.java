package com.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.entity.SysRole;
import com.entity.SysUser;
import com.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/a/role")
public class SysRoleController {
    @Autowired
    private ISysRoleService sysRoleService;

    @RequestMapping("/list")
    public String list(Model model,Integer curr,Integer size){
        Integer current = 1;
        Integer pageSize = 5;
        if (curr != null) current = curr;
        if (size != null) pageSize = size;
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        IPage<SysRole> pageList = sysRoleService.page(new Page<>(current,pageSize),queryWrapper);
        model.addAttribute("roles",pageList.getRecords());
        model.addAttribute("count",pageList.getTotal());
        model.addAttribute("curr",pageList.getCurrent());
        return "sys/roleList";
    }

    @RequestMapping("/form")
    public String form(Model model,SysRole role){
        if(role!=null&&role.getId()!=null){
            role = sysRoleService.getById(role.getId());
        }
        model.addAttribute("sysRole",role);
        return "sys/roleForm";
    }

    @ResponseBody
    @RequestMapping("/getRoleMenuTree")
    public List<Map<String,Object>> getRoleMenuTree(SysRole sysRole){
        List<Map<String,Object>> list = sysRoleService.getRoleMenuTree(sysRole);
        return list;
    }

    @ResponseBody
    @RequestMapping("/save")
    public String save(SysRole sysRole,int[] roleMenus){
        sysRoleService.saveRole(sysRole,roleMenus);
        return "success";
    }

    @ModelAttribute("role")
    public SysRole get(Integer id) {
        if (id != null) {
            return sysRoleService.getById(id);
        } else {
            return new SysRole();
        }
    }
}

