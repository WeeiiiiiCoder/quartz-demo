package com.lazyboy;

import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * {@code ApplicationPath} 指定servletMapping路径
 *
 * 写一个类继承Application,然后交给spring管理
 *
 * @auther: zhouwei
 * @date: 2020/12/4 15:17
 */
@Component
@ApplicationPath("/jaxrs-service")
public class JaxrsApplication extends Application {
}