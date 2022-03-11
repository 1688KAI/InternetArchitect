package com.bjmashibing.shiro.sso;

import java.util.*;

/**
 * <p><一旦涉及缓存，就一定要有一种策略，过期时间，回收时机</p>
 *
 * @author sunzhiqiang23
 * @date 2020-04-29 23:42
 */
public class MockDb {

    //数据缓存本地特别好快，唯一问题，不同步，第二，内存溢出
    public static Set<String> TICKET = new HashSet();
    public static Map<String, List<ClientInfo>> CLIENT_TOKEN_SESSIONID = new HashMap<>();
}
