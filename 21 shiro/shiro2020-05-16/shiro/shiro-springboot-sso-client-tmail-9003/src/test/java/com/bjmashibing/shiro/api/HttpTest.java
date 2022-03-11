package com.bjmashibing.shiro.api;

import com.bjmashibing.shiro.framework.utils.HttpUtil;
import org.junit.jupiter.api.Test;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.HashMap;

/**
 * <p></p>
 *
 * @author sunzhiqiang23
 * @date 2020-04-28 22:32
 */
public class HttpTest {

    @Test
    public void testUrl() throws IOException {
        HashMap<String, String> params = new HashMap<>();
        params.put("shouji", "15111111111");
        params.put("appkey", "0fe586214383870ac785c3d8c4c05dc0");
        String result = HttpUtil.sendHttpRequest("https://way.jd.com/jisuapi/query4", params);
        System.out.println(result);
    }
}
