package com.web;

import com.entity.SysMenu;
import com.mapper.SysMenuMapper;
import com.service.SysMenuServiceImpl;
import com.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/a/menu")
public class SysMenuController {
    @Autowired
    SysMenuServiceImpl sysMenuService;
    @RequestMapping("/list")
    public String list(){
        return "sys/menuList";
    }
    @RequestMapping("/queryMenuTree")
    @ResponseBody
    public List<Map<String,Object>> queryMenuTree(){
        List<Map<String,Object>> map = sysMenuService.queryMenuTree();
        for (Map<String,Object>item:map
             ) {
            System.out.println(item);
        }
        return map;
    }

    @RequestMapping("/getMenuById")
    @ResponseBody
    public SysMenu getMenuById(Integer menuId) {
        SysMenu sysMenu = sysMenuService.queryMenuById(menuId);
        return sysMenu;
    }

    @RequestMapping("/getParentMenu")
    @ResponseBody
    public SysMenu getParentMenu(Integer menuId) {
        SysMenu sysMenu = sysMenuService.queryParentMenuById(menuId);
        return sysMenu;
    }

    @RequestMapping("/form")
    public String form() {
        return "menuForm";
    }

    @RequestMapping("/save")
    @ResponseBody
    public String save(SysMenu sysMenu) {
        sysMenuService.saveMenu(sysMenu);
        return "success";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public ResponseResult delete(Integer menuId) {
        return sysMenuService.deleteMenu(menuId);
    }
}
