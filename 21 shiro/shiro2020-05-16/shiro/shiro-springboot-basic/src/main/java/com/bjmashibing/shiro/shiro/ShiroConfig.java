package com.bjmashibing.shiro.shiro;


import com.bjmashibing.shiro.shiro.session.MySessionListener1;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * ShiroConfig
 *
 * @author sunzhiqiang
 * @version 1.0
 **/
@Configuration
public class ShiroConfig {

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setLoginUrl("/login");
        shiroFilter.setSecurityManager(securityManager);
        shiroFilter.setFilterChainDefinitionMap(getFilterChainMap());
        return shiroFilter;
    }
    public Map<String, String> getFilterChainMap(){
        Map<String, String> filterMap = new LinkedHashMap<>();
        //过滤静态资源
        filterMap.put("/static/**", "anon");
        filterMap.put("/captcha.jpg", "anon");
        filterMap.put("/favicon.ico", "anon");

        filterMap.put("/login", "anon");
        filterMap.put("/logout", "logout");
        filterMap.put("/login_o", "anon");
        filterMap.put("/index", "user");
        filterMap.put("/**", "authc");
        return filterMap;
    }
    @Bean("securityManager")
    public SecurityManager securityManager(UserRealm userRealm, SessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        securityManager.setSessionManager(sessionManager);
        securityManager.setRememberMeManager(cookieRememberMeManager());
        securityManager.setCacheManager(ehCacheManager());

        securityManager.setRealm(userRealm);
        return securityManager;
    }
    @Bean("sessionManager")
    public SessionManager sessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();



        //设置session过期时间为1小时(单位：毫秒)，默认为30分钟
        sessionManager.setGlobalSessionTimeout(30 * 60 * 1000);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        sessionManager.setDeleteInvalidSessions(true);

        //会话监听器
        sessionManager.setSessionListeners(Collections.singleton(new MySessionListener1()));

        //可以不配置，默认有实现
		SimpleCookie simpleCookie = new SimpleCookie();
		simpleCookie.setName("BASIC_WEBSID");
        simpleCookie.setHttpOnly(true);
        sessionManager.setSessionIdCookie(simpleCookie);
        sessionManager.setSessionDAO(getSessionDAO());

        return sessionManager;
    }
    @Bean("sessionDAO")
    public SessionDAO getSessionDAO() {
        MemorySessionDAO memorySessionDAO = new MemorySessionDAO();
        return memorySessionDAO;
    }
    @Bean("cookieRememberMeManager")
    public CookieRememberMeManager cookieRememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        SimpleCookie simpleCookie = new SimpleCookie("BASIC_REMEMBERME");
        simpleCookie.setHttpOnly(true);
        simpleCookie.setMaxAge(7 * 24 * 60 * 60);
		cookieRememberMeManager.setCookie(simpleCookie);
		//rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
		cookieRememberMeManager.setCipherKey(Base64.decode("2AvVhdsgUs0FSA3SDFAdag=="));
        return cookieRememberMeManager;
    }
    @Bean
    public EhCacheManager ehCacheManager(){
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:shiro-ehcache.xml");
        return cacheManager;
    }

    /**
     * 开启shiro权限注解生效
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

}
