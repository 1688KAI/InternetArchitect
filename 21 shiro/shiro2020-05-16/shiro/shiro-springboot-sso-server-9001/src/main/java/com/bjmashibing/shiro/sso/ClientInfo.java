package com.bjmashibing.shiro.sso;

import lombok.Data;

/**
 * <p></p>
 *
 * @author sunzhiqiang23
 * @date 2020-05-02 13:51
 */
@Data
public class ClientInfo {
    //用户登出地址
    private String logoutUrl;
    //用户sessionId
    private String jsessionId;

}
