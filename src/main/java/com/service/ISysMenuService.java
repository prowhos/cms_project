package com.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.entity.SysMenu;
import com.vo.ResponseResult;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 菜单管理 服务类
 * </p>
 *
 * @author jzhang
 * @since 2020-02-19
 */
public interface ISysMenuService extends IService<SysMenu> {
    /**
     * 根据用户名获取对应菜单
     */
    List<SysMenu> findListByName(String username);

    /**
     * 以树状形式获取菜单
     * @return
     */
    List<Map<String, Object>> queryMenuTree();

    /**
     *
     * @param menuId
     * @return
     */
    SysMenu queryMenuById(Integer menuId);

    /**
     *
     * @param menuId
     * @return
     */
    SysMenu queryParentMenuById(Integer menuId);

    /**
     * 保存菜单
     * @param sysMenu
     */
    void saveMenu(SysMenu sysMenu);

    /**
     * 删除菜单
     * @param menuId
     * @return
     */
    ResponseResult deleteMenu(Integer menuId);
}
