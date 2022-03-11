package com.bjmashibing.shiro.framework.utils;

import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * <p></p>
 *
 * @author sunzhiqiang23
 * @date 2020-04-28 22:43
 */
public class HttpUtil {

    public static String sendHttpRequest(String httpUrl, Map<String, String> params) throws IOException {

        URL url = new URL(httpUrl);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");

        connection.setDoOutput(true);
        StringBuilder builder = new StringBuilder();
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.append("&").append(entry.getKey()).append("=").append(entry.getValue());
            }
            String substring = builder.substring(1);
            connection.getOutputStream().write(substring.getBytes("UTF-8"));
        }
        connection.connect();
        connection.getInputStream();
        String result = StreamUtils.copyToString(connection.getInputStream(), Charset.forName("UTF-8"));
        return result;
    }
}
