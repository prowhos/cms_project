package com.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.entity.SysRole;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jzhang
 * @since 2020-02-19
 */
public interface ISysRoleService extends IService<SysRole> {

    /**
     * 保存角色
     */
    void saveRole(SysRole sysRole, int[] roleMenus);

    /**
     * 获取角色所管理的菜单
     * @param role
     * @return
     */
    List<Map<String, Object>> getRoleMenuTree(SysRole role);
}
