package com.jzf.remote.core.util;

/**
 * @author jiazhifeng
 * @date 2021/12/11 17:47
 */
public class DecompiledUtilsTest {

    public static void main(String[] args) {
        String result = DecompiledUtils.exec("com/jzf/algorithm/CountSortTest");
        System.out.println(result);
    }
}