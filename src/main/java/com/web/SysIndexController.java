package com.web;

import com.entity.SysMenu;
import com.service.ISysMenuService;
import com.service.SysMenuServiceImpl;
import com.service.SysRoleServiceImpl;
import com.service.SysUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/a")
public class SysIndexController {
    @Autowired
    SysUserServiceImpl sysUserService;
    @Autowired
    SysMenuServiceImpl sysMenuService;
    @Autowired
    SysRoleServiceImpl sysRoleService;

    @RequestMapping("/index")
    public String index(Model model){
        System.out.println("svvv");
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        System.out.println("sbbb");
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<SysMenu> menuList = sysMenuService.findListByName(userDetails.getUsername());
        menuList.forEach(System.out::println);
        model.addAttribute("menus",menuList);
        return "sys/index";
    }

    @RequestMapping("/console")
    public String console(Model model){
        Integer userCount = sysUserService.count();
        Integer roleCount = sysRoleService.count();
        Integer menuCount = sysMenuService.count();
        model.addAttribute("userCount",userCount);
        model.addAttribute("roleCount",roleCount);
        model.addAttribute("menuCount",menuCount);
        return "sys/console";
    }
}
