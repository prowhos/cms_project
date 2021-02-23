package com.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.SysMenu;
import com.entity.SysRole;
import com.mapper.SysMenuMapper;
import com.mapper.SysRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService{
    @Autowired
    private SysRoleMapper roleMapper;
    @Autowired
    private SysMenuMapper menuMapper;
    //查询当前角色所拥有的菜单
    List<SysMenu> roleMenu = null;
    @Override
    public void saveRole(SysRole sysRole, int[] roleMenus) {
        if(sysRole!=null){
            roleMapper.updateById(sysRole);
        }else {
            roleMapper.insert(sysRole);
        }
        roleMapper.deleteRoleMenus(sysRole);
        List<SysMenu> roleMenuList = new ArrayList<>();
        for (int item:roleMenus
             ) {
            SysMenu menu = new SysMenu();
            menu.setId(item);
            roleMenuList.add(menu);
        }
        sysRole.setMenus(roleMenuList);
        roleMapper.insertRoleMenus(sysRole);
    }

    @Override
    public List<Map<String, Object>> getRoleMenuTree(SysRole role) {
        //查询出所有的菜单
        List<SysMenu> sysMenus = menuMapper.selectList(new QueryWrapper<>());
        //查询当前角色所拥有的菜单
        if(role!=null&&role.getId()!=null){
            SysRole currRole = roleMapper.findRoleAndMenu(role.getId());
            roleMenu = currRole.getMenus();
        }else{
            roleMenu = new ArrayList<>();
        }
        //rootMenu存放根菜单
        List<Map<String,Object>> rootMenu = new ArrayList<>();
        //取出pid为0的节点
        if(!CollectionUtils.isEmpty(sysMenus)){
            List<SysMenu> parentList = sysMenus.stream().filter(item -> 0 == (item.getPid())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(parentList)){
                for (SysMenu item:parentList
                     ) {
                    Map<String,Object>map = new HashMap<>();
                    map.put("title",item.getName());
                    map.put("value",item.getId());
                    if(!roleMenu.isEmpty()){
                        for (SysMenu rMenu:roleMenu
                             ) {
                            if(rMenu.getId()==item.getId()){
                                map.put("checked",true);
                            }
                        }
                    }
                    querySubMenuTree(map,sysMenus,roleMenu);
                    rootMenu.add(map);
                }
            }
        }
        System.out.println(rootMenu);
        return rootMenu;
    }
    public Map<String, Object> querySubMenuTree(Map<String, Object> rootMap, List<SysMenu> sysMenus, List<SysMenu> roleMenu){
        //subMenu
        List<Map<String,Object>> subMenu = new ArrayList<>();
        //选出某一个根节点下面的子树，递归。
        List<SysMenu> sub = sysMenus.stream().filter(item -> rootMap.get("value") == (item.getPid())).collect(Collectors.toList());
        //取出pid为0的节点
        if(!sub.isEmpty()){
            for (SysMenu item:sub
                 ) {
                Map<String,Object> subMap = new HashMap<>();
                subMap.put("title", item.getName());
                subMap.put("value", item.getId());
                subMap.put("data", new ArrayList<>());
                if(!roleMenu.isEmpty()){
                    for (SysMenu rMenu:roleMenu
                         ) {
                        if(rMenu.getId()==item.getId()){
                            subMap.put("checked",true);
                            break;
                        }
                    }
                }
                subMenu.add(subMap);
                this.querySubMenuTree(subMap,sysMenus,roleMenu);
            }
        }
        if (!CollectionUtils.isEmpty(subMenu)) {
            rootMap.put("data", subMenu);
        } else {
            rootMap.put("data", new ArrayList<>());
        }
        return rootMap;
    }
}
