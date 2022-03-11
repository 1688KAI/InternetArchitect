package com.bjmashibing.shiro.moduler.system.controller;


import com.bjmashibing.shiro.framework.utils.R;
import com.bjmashibing.shiro.shiro.util.ShiroUtils;
import com.bjmashibing.shiro.moduler.system.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.Serializable;
import java.util.Collection;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author 孙志强
 * @since 2020-04-13
 */
@RestController
public class UserController {

    @Autowired
    private SessionDAO sessionDAO;




    @RequiresPermissions("user:add")
    @RequiresRoles({"000"})
    @RequestMapping("/user")
    public ModelAndView detail(){
        User user = ShiroUtils.getUser();
        ModelAndView mv = new ModelAndView("/user");
        mv.addObject("user", user);
        return mv;
    }

    @RequestMapping("/delete")
    public void clean(String sessionId){
        Session session = SecurityUtils.getSecurityManager().getSession(new DefaultSessionKey(new DefaultSessionKey(sessionId)));
        Collection<Session> sessions = sessionDAO.getActiveSessions();

        sessionDAO.delete(session);

        System.out.println(ShiroUtils.getSession());

    }
    @RequestMapping("/getActivite")
    public Object getActivite(){
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        for(Session session:sessions){
            //id 就是BASIC_REMEMBERME 的value
            Serializable id = session.getId();
            SimplePrincipalCollection simplePrincipalCollection= (SimplePrincipalCollection) session.getAttribute("org.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEY");
            if(simplePrincipalCollection!=null){
                User user = (User) simplePrincipalCollection.getPrimaryPrincipal();
                System.out.println(user);
            }
        }
        return R.ok();
    }

}
