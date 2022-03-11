package com.bjmashibing.shiro.sso;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bjmashibing.shiro.framework.utils.HttpUtil;
import com.bjmashibing.shiro.framework.utils.R;
import com.bjmashibing.shiro.framework.utils.SsoClientUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * <p></p>
 *
 * @author sunzhiqiang23
 * @date 2020-04-28 23:33
 */
public class SsoInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1 判断用户是否存在会话
        HttpSession session = request.getSession();
        Boolean isLogin = false;
        Object isLogin1 = session.getAttribute("isLogin");
        if (isLogin1 != null) {
            isLogin = (Boolean) session.getAttribute("isLogin");
        }
        //2-登陆成功
        if (isLogin != null && isLogin) {
            return true;
        }
        //3-判断token
        String ticket = request.getParameter("ticket");
        if (StringUtils.isNotBlank(ticket)) {
            System.out.println("检测到服务器端的token信息");
            //防止伪造，拿到服务器端验证
            // 服务器端的地址
            String httpUrl = SsoClientUtil.SERVER_URL_PREFIX + "/verify";
            HashMap<String, String> params = new HashMap<>();
            params.put("ticket", ticket);
            params.put("logoutUrl", SsoClientUtil.getClientLogOutUrl());
            params.put("jsessionId", session.getId());
            try {
                String result = HttpUtil.sendHttpRequest(httpUrl, params);
                R r = JSON.parseObject(result, R.class);
                if (r.get("code").equals(200)) {
                    System.out.println("服务器端校验通过");
                    session.setAttribute("isLogin", true);
                    return true;
                }
            } catch (Exception e) {
                System.out.println("验证异常");
            }

            //服务器端
        }
        //跳转到统一认证中心，检测系统是否登录
        //http://www.sso.com:9001/checkLogin?redirectUrl=http://www.tb.com:9002
        SsoClientUtil.redirectToSsoUrl(request, response);
        return false;
    }

}
