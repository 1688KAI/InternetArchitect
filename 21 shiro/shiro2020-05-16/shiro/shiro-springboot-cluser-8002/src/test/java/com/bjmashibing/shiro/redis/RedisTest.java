package com.bjmashibing.shiro.redis;

import com.bjmashibing.shiro.ApplicationTests;
import com.bjmashibing.shiro.moduler.system.entity.User;
import com.bjmashibing.shiro.moduler.system.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * <p></p>
 *
 * @author sunzhiqiang23
 * @date 2020-04-27 12:24
 */
public class RedisTest extends ApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testRedis() {
        long id = 1;
        User user = (User) redisTemplate.opsForValue().get(id);
        if (user == null) {
            user = userService.getById(1);
            redisTemplate.opsForValue().set(id, user);
        }
        System.out.println(user);

    }


}
