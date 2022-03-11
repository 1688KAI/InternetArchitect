package com.bjmashibing.shiro.sso;

import com.bjmashibing.shiro.framework.utils.HttpUtil;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.List;

/**
 * <p></p>
 *
 * @author sunzhiqiang23
 * @date 2020-05-02 14:24
 */
@WebListener
public class MySessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        String ticket = (String) session.getAttribute("ticket");
        //销毁用户信息
        MockDb.TICKET.remove(ticket);
        //这里应该有啥？sessionId用于维护各个应用的session，logoutUrl用于各个应用的退出
        List<ClientInfo> clientInfos = MockDb.CLIENT_TOKEN_SESSIONID.remove(ticket);

        for (ClientInfo clientInfo : clientInfos) {
            try {
                //遍历通知客户端销毁
                HttpUtil.sendHttpRequest(clientInfo.getLogoutUrl(), clientInfo.getJsessionId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }



    }
}
