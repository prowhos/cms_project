package com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @Auther: jzhang
 * @Date: 2018/10/15 15:38
 * @Description: 系统用户接口
 */
@Mapper
@Component
public interface SysUserMapper extends BaseMapper<SysUser> {
    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    SysUser findUserByName(String username);

    /**
     * 根据主键获取用户并获取用户角色
     */
    SysUser findUserById(Integer id);

    /**
     * 更新角色
     * @param sysUser
     */
    void insertRoles(SysUser sysUser);

    /**
     * 删除角色
     * @param sysUser
     */
    void deleteRoles(SysUser sysUser);
}
