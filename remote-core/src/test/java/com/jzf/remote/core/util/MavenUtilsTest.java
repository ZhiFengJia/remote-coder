package com.jzf.remote.core.util;

import java.io.IOException;
import java.util.Arrays;

public class MavenUtilsTest {

    public static void main(String[] args) {
        test1();
    }

    public static void test1() {
        MavenUtils.goals("algorithm", Arrays.asList("clean", "package"),
                consumer -> {
                    try {
                        do {
                            String line = consumer.readLine();
                            if (line == null) {
                                break;
                            }
                            System.out.println(line);
                        } while (true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }
}