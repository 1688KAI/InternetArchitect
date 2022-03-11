package test;

import com.bjmashibing.shiro.CryptoRealm;
import com.bjmashibing.shiro.MyRealm1;
import com.bjmashibing.shiro.MyRealm2;
import com.bjmashibing.shiro.MyRealm3;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * <p></p>
 *
 * @author sunzhiqiang23
 * @date 2020-04-07 11:45
 */
@Slf4j
public class LoginShiro {
    /**
     * 基本登录操作
     */
    @Test
    public void testBasicLogin() throws InterruptedException {
        DefaultSecurityManager securityManager=new DefaultSecurityManager();
        IniRealm iniRealm=new IniRealm("classpath:shiro-quick-start.ini");
        securityManager.setRealm(iniRealm);
        SecurityUtils.setSecurityManager(securityManager);
        // 1-获取当前用户信息
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        session.setAttribute("key", "value");

        String key = (String) session.getAttribute("key");
        System.out.println("key 值：" + key);

        // 2-当前用户登陆
        if (!currentUser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken("system", "system");
            try {
                currentUser.login(token);
                new Thread(() -> {
                    System.out.println("登陆成功，登录用户"+SecurityUtils.getSubject().getPrincipals());
                }).start();
            } catch (UnknownAccountException uae) {
                log.info("无此用户，用户名： " + token.getPrincipal());
            } catch (IncorrectCredentialsException ice) {
                log.info("密码不正确 " + token.getPrincipal() + " was incorrect!");
            }
        }
        new CountDownLatch(1).await();
        //3-退出
//        currentUser.logout();
//        System.exit(0);
    }


    /**
     * 测试单一一个 realm
     */
    @Test
    public void testOneRealms(){
        DefaultSecurityManager securityManager=new DefaultSecurityManager();
        securityManager.setRealm(new MyRealm1());
        SecurityUtils.setSecurityManager(securityManager);
        // 1-获取当前用户信息
        Subject currentUser = SecurityUtils.getSubject();
        // 2-当前用户登陆
        if (!currentUser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken("system", "system");
            try {
                currentUser.login(token);
                log.info("登陆成功");
            } catch (UnknownAccountException uae) {
                log.info("无此用户，用户名： " + token.getPrincipal());
            } catch (IncorrectCredentialsException ice) {
                log.info("密码不正确 " + token.getPrincipal() + " was incorrect!");
            }
        }
        //3-退出
        currentUser.logout();
        System.exit(0);
    }
    /**
     * 测试多个 realm
     */
    @Test
    public void testMultiRealms(){
        DefaultSecurityManager securityManager=new DefaultSecurityManager();

        List<Realm> list = new ArrayList<>();
        MyRealm1 myRealm1 = new MyRealm1();
        MyRealm2 myRealm2 = new MyRealm2();
        list.add(myRealm1);
        list.add(myRealm2);
        securityManager.setRealms(list);
        SecurityUtils.setSecurityManager(securityManager);
        // 1-获取当前用户信息
        Subject currentUser = SecurityUtils.getSubject();
        // 2-当前用户登陆
        if (!currentUser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken("system", "system");
            try {
                currentUser.login(token);
                log.info("登陆成功");
            } catch (UnknownAccountException uae) {
                log.info("无此用户，用户名： " + token.getPrincipal());
            } catch (IncorrectCredentialsException ice) {
                log.info("密码不正确 " + token.getPrincipal() + " was incorrect!");
            }
        }
        //3-退出
        currentUser.logout();
        System.exit(0);
    }
    /**
     * 测试多个 realm ，认证策略
     */
    @Test
    public void testMultiRealmsStrate(){
        DefaultSecurityManager securityManager=new DefaultSecurityManager();

        List<Realm> list = new ArrayList<>();
        list.add(new MyRealm1());
        list.add(new MyRealm2());
        list.add(new MyRealm3());

        // 设置策略
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();

        authenticator.setAuthenticationStrategy(new FirstSuccessfulStrategy());
//        authenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
//        authenticator.setAuthenticationStrategy(new AllSuccessfulStrategy());

        authenticator.setRealms(list);
        securityManager.setAuthenticator(authenticator);

        SecurityUtils.setSecurityManager(securityManager);
        // 1-获取当前用户信息
        Subject currentUser = SecurityUtils.getSubject();
        // 2-当前用户登陆
        if (!currentUser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken("system", "system1");
            try {
                currentUser.login(token);
                PrincipalCollection principals = currentUser.getPrincipals();
                log.info("登陆成功:"+principals);
            } catch (UnknownAccountException uae) {
                log.info("无此用户，用户名： " + token.getPrincipal());
            } catch (IncorrectCredentialsException ice) {
                log.info("密码不正确 " + token.getPrincipal() + " was incorrect!");
            }
        }
        //3-退出
        currentUser.logout();
        System.exit(0);
    }

    /**
     * 测试加密
     */
    @Test
    public void testCrypto() {
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setRealm(new CryptoRealm());
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("admin", "admin1");
        subject.login(token);
        boolean authenticated = subject.isAuthenticated();
        if (authenticated) {
            System.out.println("登陆成功");
        }
    }
}
