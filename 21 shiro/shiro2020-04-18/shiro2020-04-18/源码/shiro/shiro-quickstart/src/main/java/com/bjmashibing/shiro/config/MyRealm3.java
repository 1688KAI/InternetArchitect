package com.bjmashibing.shiro.config;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

/**
 * <p></p>
 *
 * @author sunzhiqiang23
 * @date 2020-04-08 21:09
 */
public class MyRealm3 implements Realm {
    @Override
    public String getName() {
        return "myRealm3";
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //如果身份认证验证成功，返回一个AuthenticationInfo实现；
        return new SimpleAuthenticationInfo("zhangsan", "password", getName());
    }
}
