package com.bjmashibing.shiro.moduler.system.controller;


import com.bjmashibing.shiro.shiro.ShiroUtils;
import com.bjmashibing.shiro.moduler.system.entity.User;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author 孙志强
 * @since 2020-04-13
 */
@Controller
public class UserController {

    @RequiresPermissions("user:add")
    @RequiresRoles({"000"})
    @RequestMapping("/user")
    public ModelAndView detail(){
        User user = ShiroUtils.getUser();
        ModelAndView mv = new ModelAndView("/user");
        mv.addObject("user", user);
        return mv;
    }
}
