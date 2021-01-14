package com.lazyboy.controller;

import com.lazyboy.entity.User;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Map;

/**
 * rest接口
 *
 * {@code @Path}注解知名
 *
 * @auther: zhouwei
 * @date: 2020/12/4 15:17
 */
@Component
@Path("/restservice")
public class JaxrsDemoController {


    @POST
    @Path("/getMap")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getUser(User user) {
        Map<String, String> list = user.getList();
        return list.toString();
    }
}