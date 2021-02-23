package com.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.entity.SysRole;
import com.entity.SysUser;
import com.service.SysRoleServiceImpl;
import com.service.SysUserServiceImpl;
import com.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping(value="/a")
public class SysUserController {
    @Autowired
    SysUserServiceImpl sysUserService;
    @Autowired
    SysRoleServiceImpl sysRoleService;
    @RequestMapping("/user/login")
    public String login(){
        return "sys/login";
    }

    @RequestMapping("/user/logout")
    public String loginPage(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth!=null){
            new SecurityContextLogoutHandler().logout(request,response,auth);
        }
        return "redirect:/a/user/login";
    }

    @RequestMapping("/user/list")
    public String list(Integer curr,Integer size,SysUser sysUser,Model model){
        Integer current = 1;
        Integer pageSize = 2;
        //判断前台传递的页码和每页记录数
        if (curr != null) current = curr;
        if (size != null) pageSize = size;
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        if (sysUser != null && sysUser.getUsername() != null && sysUser.getUsername() != "") {
            queryWrapper.eq("username",sysUser.getUsername());
        }
        if (sysUser != null && sysUser.getEmail() != null && sysUser.getEmail() != "") {
            queryWrapper.eq("abc",sysUser.getEmail());
        }
        IPage<SysUser> page = sysUserService.page(new Page<>(current,pageSize),queryWrapper);
        model.addAttribute("userList",page.getRecords());//获取某一页的记录数
        model.addAttribute("total",page.getTotal());    //获取记录总数
        model.addAttribute("current",page.getCurrent());//当前页
        return "sys/userList";
    }
    @RequestMapping("/user/form")
    public String form(Model model,Integer id,SysUser sysUser){
        List<SysRole> roleList = sysRoleService.list();
        if(id!=null){
            sysUser = sysUserService.findUserById(id);
        }
        model.addAttribute("roleList",roleList);
        System.out.println(sysUser);
        model.addAttribute("sysUser",sysUser);
        return "sys/userForm";
    }

    @RequestMapping("/user/save")
    public String save(SysUser sysUser){
        sysUserService.saveSysUser(sysUser);
        return "redirect:list";
    }

    @RequestMapping("/user/modify")
    public String modify(Model model){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userDetails.getUsername());
        SysUser sysUser = sysUserService.getOne(queryWrapper);
        model.addAttribute("sysUser",sysUser);
        return "sys/modifyUserPwd";
    }

    @ResponseBody
    @RequestMapping("/user/modifySubmit")
    public String modifySubmit(SysUser sysUser){
        String pwd = new BCryptPasswordEncoder().encode(sysUser.getPassword());
        sysUser.setPassword(pwd);
        sysUserService.saveOrUpdate(sysUser);
        return "success";
    }

    @ResponseBody
    @RequestMapping("/user/changeStatus")
    public ResponseResult changeStatus(SysUser sysUser){
        sysUserService.updateById(sysUser);
        ResponseResult result = null;
        if (sysUser.getStatus() == 2) {
            result = new ResponseResult("200","账户已锁定");
        } else {
            result = new ResponseResult("200","账户已启动");
        }
        return result;
    }
}
