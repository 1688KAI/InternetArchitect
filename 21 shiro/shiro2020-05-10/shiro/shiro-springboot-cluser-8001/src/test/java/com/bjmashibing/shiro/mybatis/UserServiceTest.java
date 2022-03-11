package com.bjmashibing.shiro.mybatis;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjmashibing.shiro.moduler.system.entity.User;
import com.bjmashibing.shiro.ApplicationTests;
import com.bjmashibing.shiro.moduler.system.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p></p>
 *
 * @author sunzhiqiang23
 * @date 2020-04-11 23:26
 */
public class UserServiceTest extends ApplicationTests {
    @Autowired
    private UserService userService;
    @Test
    public void query(){
        User user = userService.getById(3);
        System.out.println(user);

    }
    @Test
    public void insert(){
        User user = new User();
        user.setUsername("测试4");
        boolean save = userService.save(user);
        System.out.println(user.getId());
    }

    @Test
    public void update(){
        User user = new User();
        user.setUsername("测试修改");
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", "测试");
        boolean save = userService.update(user,wrapper);
        System.out.println(save);
    }

    @Test
    public void select(){
        User user = new User();
        user.setUsername("测试修改");
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", "测试");
        wrapper.last("and is_delete =0");
        List<User> list = userService.list(wrapper);
        System.out.println(list);

    }

}
