package com.harshit.journalApp.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Disabled
    @Test
    void sendMail(){

        redisTemplate.opsForValue().set("email", "anandmehtaa339@gmail.com");
        Object email = redisTemplate.opsForValue().get("name");
        int a =1;


    }
}
