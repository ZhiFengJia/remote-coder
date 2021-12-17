package com.jzf.remote.web;

import com.jzf.remote.core.util.HexUtils;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Map;

// @SpringBootTest
class RemoteWebApplicationTests {

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

    @Test
    public void bytesToBeautiful() {
        byte[] bytes = ("qwertyuiopasdfghjklzxcvbnmfdgdgdfgasdfsdfdsfdsfsdfsdfxcvxcvr222222222222222222222222" +
                "2222222222222222222222222222222222222222222222222222222222222222222222222222222ffffffffffffff" +
                "fffffffffffffffffffffffffffffffggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg" +
                "ggggggggggggdfffffffffffffffffffffffffffffffgdfgdfgdffffffffffffffffffffffffffffffffffewwwwwdfgdgdfgd").getBytes(StandardCharsets.UTF_8);
        String bytesToBeautiful = HexUtils.bytesToBeautiful(bytes);
        System.out.println(bytesToBeautiful);
    }

}
