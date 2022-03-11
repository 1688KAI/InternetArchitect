package com.bjmashibing.shiro.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * <p></p>
 *
 * @author sunzhiqiang23
 * @date 2020-03-31 13:56
 */
@Component
@Slf4j
public class ApplicationReadyEventListene implements ApplicationListener<ApplicationReadyEvent> {
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("系统启动成功......");
    }
}