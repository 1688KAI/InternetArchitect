package test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.subject.Subject;
import org.junit.jupiter.api.Test;

/**
 * <p></p>
 *
 * @author sunzhiqiang23
 * @date 2020-04-25 20:10
 */
public class TestShiro {
    @Test
    public void testLogin() {
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        IniRealm realm = new IniRealm("classpath:shiro-quick-start.ini");
        securityManager.setRealm(realm);
        //绑定securityManager
        SecurityUtils.setSecurityManager(securityManager);

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("system1", "system");
        //密码不对 IncorrectCredentialsException 异常
        //用户名不对UnknownAccountException 异常
        subject.login(token);

        if (subject.isAuthenticated()) {
            System.out.println("登陆成功，登录用户" + subject.getPrincipal());
        }else{
            System.out.println("未登录");
        }

        subject.logout();

        if (subject.isAuthenticated()) {
            System.out.println("登陆成功，登录用户" + subject.getPrincipal());
        }else{
            System.out.println("未登录");
        }



    }
}
