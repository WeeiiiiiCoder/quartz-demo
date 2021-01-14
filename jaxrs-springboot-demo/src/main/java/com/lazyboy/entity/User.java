package com.lazyboy.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试用户
 *
 * @auther: zhouwei
 * @date: 2020/12/4 15:13
 */
@Data
@AllArgsConstructor
public class User {

    private Long userId;

    private String userName;

    private String gender;

    private Map<String, String> list;

    public Map<String, String> getList() {
        return list;
    }

    public void setList(Map<String, String> list) {
        this.list = list;
    }

    public static void main(String[] args) throws JsonProcessingException {
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        stringStringHashMap.put("aa", "aa");
        String s = objectMapper.writeValueAsString(stringStringHashMap);
        System.out.println(s);


    }
}