package com.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.SysMenu;
import com.mapper.SysMenuMapper;
import com.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenu> findListByName(String username) {
        return sysMenuMapper.findByUserName(username);
    }

    @Override
    public List<Map<String, Object>> queryMenuTree() {
        List<SysMenu> sysMenus = sysMenuMapper.selectList(new QueryWrapper<>());
        List<Map<String,Object>> rootMenu = new ArrayList<>();
        //取出pid为0的节点
        //把根节点放在Mpa里面，并去调用递归算法去查询子树
        if(!CollectionUtils.isEmpty(sysMenus)){
            List<SysMenu> parentList =sysMenus.stream().filter(item->0==(item.getPid())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(parentList)){
                for (SysMenu item:parentList
                     ) {
                    Map<String,Object> map = new HashMap<>();
                    packageData(item,map);
                    this.querySubMenuTree(map,sysMenus);
                    rootMenu.add(map);
                }
            }
        }
        return rootMenu;
    }
    public Map<String, Object> querySubMenuTree(Map<String, Object> rootMap, List<SysMenu> sysMenus){
        List<Map<String,Object>> subMenu = new ArrayList<>();
        //选出某一个根节点下面的子树，递归。
        List<SysMenu> sub = sysMenus.stream().filter(item->rootMap.get("id")==(item.getPid())).collect(Collectors.toList());
        if(!sub.isEmpty()){
            for (SysMenu item:sub
                 ) {
                Map<String,Object> subMap = new HashMap<>();
                packageData(item,subMap);
                subMenu.add(subMap);
                this.querySubMenuTree(subMap,sysMenus);
            }
        }
        if(!CollectionUtils.isEmpty(subMenu)){
            rootMap.put("children",subMenu);
        }
        return rootMap;
    }

    @Override
    public SysMenu queryMenuById(Integer menuId) {
        return sysMenuMapper.selectById(menuId);
    }

    @Override
    public SysMenu queryParentMenuById(Integer menuId) {
        SysMenu sysMenu = sysMenuMapper.selectById(menuId);
        return sysMenuMapper.selectList(new QueryWrapper<SysMenu>()
                .eq("id",sysMenu.getPid()==0?menuId:sysMenu.getPid())).get(0);
    }

    @Override
    public void saveMenu(SysMenu sysMenu) {
        if (sysMenu.getId() != null) {
            sysMenuMapper.updateById(sysMenu);
        } else {
            if (sysMenu.getPid() == null) {
                sysMenu.setPid(0);
            }
            sysMenuMapper.insert(sysMenu);
        }
    }

    @Override
    public ResponseResult deleteMenu(Integer menuId) {
        List<SysMenu> subCatalog = sysMenuMapper.selectList(new QueryWrapper<SysMenu>().eq("pid",menuId));
        if(!subCatalog.isEmpty()){
            return new ResponseResult("201", "请先删除子菜单！");
        }
        sysMenuMapper.deleteById(menuId);
        return new ResponseResult("200", "删除成功！");
    }

    private void packageData(SysMenu sysMenu, Map<String, Object> map) {
        map.put("id", sysMenu.getId());
        map.put("name", sysMenu.getName());
        map.put("href", sysMenu.getUrl());
//        map.put("sort", sysMenu.getSort());
    }
}
