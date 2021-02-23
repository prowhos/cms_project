package com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.entity.SysRole;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jzhang
 * @since 2020-02-19
 */
@Repository
public interface SysRoleMapper extends BaseMapper<SysRole> {
    //查询角色关联查询角色菜单
    SysRole findRoleAndMenu(Integer id);

    //根据id删除所有角色菜单
    void deleteRoleMenus(SysRole sysRole);

    //批量插入角色菜单
    void insertRoleMenus(SysRole sysRole);
}
