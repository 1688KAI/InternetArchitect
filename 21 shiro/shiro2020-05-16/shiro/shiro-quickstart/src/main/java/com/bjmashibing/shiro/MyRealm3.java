package com.bjmashibing.shiro;

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
        //得到用户名
        String username = (String)token.getPrincipal();
        //得到密码
        String password = new String((char[])token.getCredentials());
        //如果用户名错误
        if(!"zhangsan".equals(username)) {
            throw new UnknownAccountException();
        }
        //如果密码错误
        if(!"zhangsan".equals(password)) {
            throw new IncorrectCredentialsException();
        }
        //如果身份认证验证成功，返回一个AuthenticationInfo实现；
        return new SimpleAuthenticationInfo(username, password, getName());
    }
}
