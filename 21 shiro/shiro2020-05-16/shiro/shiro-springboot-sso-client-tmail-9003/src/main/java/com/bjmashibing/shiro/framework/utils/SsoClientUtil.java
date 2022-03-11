package com.bjmashibing.shiro.framework.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

/**
 * <p></p>
 *
 * @author sunzhiqiang23
 * @date 2020-04-29 22:53
 */
public class SsoClientUtil {
    public static Properties ssoProperties = new Properties();
    public static String SERVER_URL_PREFIX ;
    public static String CLIENT_HOST_URL ;
    static {
        try {
            ssoProperties.load(SsoClientUtil.class.getClassLoader().getResourceAsStream("sso.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        SERVER_URL_PREFIX = ssoProperties.getProperty("server-url-prefix");
        CLIENT_HOST_URL = ssoProperties.getProperty("client-host-prefix");
    }

    public static String getRedirectUrl(HttpServletRequest request) {
        return CLIENT_HOST_URL + request.getServletPath();
    }

    public static void redirectToSsoUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String redirectUrl = getRedirectUrl(request);
        StringBuilder url = new StringBuilder(50);
        url.append(SERVER_URL_PREFIX)
                .append("/checkLogin?redirectUrl=")
                .append(redirectUrl);
        response.sendRedirect(url.toString());
    }

    /**
     * 获取客户端的完整退出地址
     * @return
     */
    public static String getClientLogOutUrl(){
        return CLIENT_HOST_URL + "/logout";
    }
    /**
     * 获取认证中心的完整退出地址
     * @return
     */
    public static String getServerLogOutUrl(){
        return SERVER_URL_PREFIX + "/logout";
    }

}
