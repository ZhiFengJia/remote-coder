package com.jzf.compiler;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;
import java.util.Properties;

/**
 * 为class劫持java.lang.System提供支持 除了out和err外，其余的都直接转发给System处理
 *
 * @author jiazhifeng
 * @date 2021/11/24 12:42
 */
public class JZFSystem {
    private ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    public PrintStream out = new PrintStream(buffer);
    public PrintStream err = out;

    public String getBufferString() {
        return buffer.toString();
    }

    public static long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    public static long nanoTime() {
        return System.nanoTime();
    }

    public static void arraycopy(Object src, int srcPos, Object dest, int destPos, int length) {
        System.arraycopy(src, srcPos, dest, destPos, length);
    }

    public static Properties getProperties() {
        return System.getProperties();
    }

    public static String getProperty(String key) {
        return System.getProperty(key);
    }

    public static Map<String,String> getenv() {
        return System.getenv();
    }

    public static String getenv(String name) {
        return System.getenv(name);
    }
}
