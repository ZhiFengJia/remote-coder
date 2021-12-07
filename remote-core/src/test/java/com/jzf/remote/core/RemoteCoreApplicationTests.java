package com.jzf.remote.core;

import org.junit.jupiter.api.Test;

import java.util.Map;

// @SpringBootTest
class RemoteCoreApplicationTests {

    @Test
    void contextLoads() {
        for (Map.Entry<String, String> entry : System.getenv().entrySet()) {
            String k = entry.getKey();
            String value = entry.getValue();
            System.out.println(k + " : " + value);
        }
        for (Map.Entry<Object, Object> entry : System.getProperties().entrySet()) {
            Object key = entry.getKey();
            Object val = entry.getValue();
            System.out.println(key + " : " + val);
        }
    }

}
