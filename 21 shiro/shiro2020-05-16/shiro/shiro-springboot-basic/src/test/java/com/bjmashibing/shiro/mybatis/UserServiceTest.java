package com.bjmashibing.shiro.mybatis;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
        User user = userService.getById(2);
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
        user.setId(1L);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getId, user.getId())
                .eq(User::getIsDelete, (byte) 0);

        User user1 = new User();
        user1.setIsDelete(1);
        user1.setUsername("system1");
        boolean save = userService.update(user1,wrapper);
        System.out.println(save);
    }

    @Test
    public void delete(){
        boolean save = userService.removeById(1L);
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
