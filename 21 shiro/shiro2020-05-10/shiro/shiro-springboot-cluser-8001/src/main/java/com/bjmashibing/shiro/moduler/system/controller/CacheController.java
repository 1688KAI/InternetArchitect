package com.bjmashibing.shiro.moduler.system.controller;

import com.bjmashibing.shiro.framework.utils.R;
import com.bjmashibing.shiro.shiro.UserRealm;
import com.bjmashibing.shiro.shiro.util.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.Subject;

/**
 * <p></p>
 *
 * @author sunzhiqiang23
 * @date 2020-04-27 14:12
 */
@RestController
public class CacheController {
    @Autowired
    private UserRealm userRealm;

    @RequestMapping("/getNoCacheUserInfo")
    public Object getNoCacheUserInfo(){
        PrincipalCollection principals = ShiroUtils.getSubject().getPrincipals();
        userRealm.clearCache(principals);
        return R.ok("清除缓存成功");
    }
}
