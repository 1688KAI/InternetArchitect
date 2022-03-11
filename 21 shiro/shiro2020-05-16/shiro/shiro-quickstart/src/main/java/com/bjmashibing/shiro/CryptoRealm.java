package com.bjmashibing.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

/**
 * <p></p>
 *
 * @author sunzhiqiang23
 * @date 2020-04-08 21:09
 */
public class CryptoRealm extends AuthorizingRealm {


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //连接数据库
        String username = "admin";
        String password = "c657540d5b315892f950ff30e1394480";
        String salt = "salt";
        return new SimpleAuthenticationInfo(username, password, ByteSource.Util.bytes(salt), getName());
    }



    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        HashedCredentialsMatcher shaCredentialsMatcher = new HashedCredentialsMatcher();

        shaCredentialsMatcher.setHashAlgorithmName("MD5");
        shaCredentialsMatcher.setHashIterations(1);
        shaCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        super.setCredentialsMatcher(shaCredentialsMatcher);
    }
}
