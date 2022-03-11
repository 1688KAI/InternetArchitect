/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.bjmashibing.shiro.shiro;


import com.bjmashibing.shiro.moduler.system.entity.User;
import com.bjmashibing.shiro.moduler.system.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 认证
 *
 * @author 孙志强

 * @date 2016年11月10日 上午11:55:49
 */
@Component
public class UserRealm extends CasRealm {
    @Value("${shiro.cas} ")
    private String casServerUrlPrefix;
    @Value("${shiro.server}")
    private String shiroServerUrlPrefix;

    @Autowired
    @Lazy
    private UserService userService;

    @PostConstruct
    public void initProperty(){
        //服务器端地址
        setCasServerUrlPrefix("https://localhost:8443/cas");
        // 客户端回调地址
        setCasService(shiroServerUrlPrefix+ShiroConfig.casFilterUrlPattern);
    }
    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User user = (User)principals.getPrimaryPrincipal();
        List<String> permsList = userService.queryAllPerms(user.getId());
        List<String> roles = userService.getRoleCodeList(user.getId());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(permsList);
        info.addRoles(roles);
        return info;
    }


}
