package test;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.junit.jupiter.api.Test;

/**
 * <p>入门程序--登录，session存储角色，权限验证，退出</p>
 *
 * @author sunzhiqiang23
 * @date 2020-04-07 11:45
 */
@Slf4j
public class QuickStartShiro {

    @Test
    public void test(){
        //1-初始化环境
        DefaultSecurityManager securityManager=new DefaultSecurityManager();
        IniRealm iniRealm=new IniRealm("classpath:shiro-quick-start.ini");
        securityManager.setRealm(iniRealm);
        //绑定当前 securityManager
        SecurityUtils.setSecurityManager(securityManager);
        // 2--获取当前用户信息
        Subject subject = SecurityUtils.getSubject();
        // 3-当前用户信息 操作一些事情--不需要web环境和其他容器支持
        Session session = subject.getSession();
        session.setAttribute("someKey", "value");
        String value = (String) session.getAttribute("someKey");
        if (value.equals("value")) {
            log.info("检测到 正确的 value 值! [" + value + "]");
        }
        // 4-当前用户登陆，检查角色和权限
        if (!subject.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken("system", "system");
            token.setRememberMe(true);
            try {
                subject.login(token);
            } catch (UnknownAccountException uae) {
                log.info("无此用户，用户名： " + token.getPrincipal());
            } catch (IncorrectCredentialsException ice) {
                log.info("用户 " + token.getPrincipal() + " 登录密码不正确!");
            } catch (LockedAccountException lae) {
                log.info("用户名 " + token.getPrincipal() + " 被锁定.请联系管理员已解锁.");
            }
            // ... 系统自己定义的异常,系统提供了很多预制的异常，均 继承自 ShiroException或者起子类
            catch (AuthenticationException ae) {
                //unexpected condition?  error?
            }
        }
        //5 -验证
        log.info("用户 [" + subject.getPrincipal() + "] 登陆成功.");
        //6-测试角色
        if (subject.hasRole("system")) {
            log.info("拥有【system】角色!");
        } else {
            log.info("未拥有【system】角色!");
        }
        //5-测试权限码
        if (subject.isPermitted("user:update")) {
            log.info("拥有【user:update】权限.");
        } else {
            log.info("未拥有【user:update】权限..");
        }
        //6-退出
        subject.logout();
        System.exit(0);
    }
}
