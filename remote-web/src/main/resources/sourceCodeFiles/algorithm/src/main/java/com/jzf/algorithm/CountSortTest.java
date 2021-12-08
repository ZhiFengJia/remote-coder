package com.jzf.algorithm;

import java.util.Arrays;

/**
 * 计数排序的基本思想: 为一组数在排序之前先统计这组数中其他数小于这个数的个数,则可以确定这个数的位置.
 * 1.首先对每个元素进行频率统计,得到元素的频率表.
 * 2.然后将频率表转换为该元素在数组中的最后位置.
 * <p>
 * 注意点:
 * 1.数组中的元素对应频率表/位置表的索引
 *
 * @author Jia ZhiFeng <312290710@qq.com>
 * @date 2019/5/30 18:00:20
 */
public class CountSortTest {
    /**
     * 计数排序
     *
     * @param arr 排序前的数组
     * @return 排序后的数组
     */
    public static int[] sort(int[] arr) {
        int[] result = new int[arr.length];
        int[] count = new int[max(arr) + 1];

        //首先对每个元素进行频率统计,得到元素的频率表count[];
        for (int i = 0; i < arr.length; i++) {
            count[arr[i]]++;
        }
        System.out.println("统计次数: " + Arrays.toString(count));

        //然后将频率表转换为该元素在数组中的最后位置.
        for (int i = 1; i < count.length; i++) {
            count[i] = count[i] + count[i - 1];
        }
        System.out.println("数据位置: " + Arrays.toString(count));

        for (int i = 0; i < arr.length; i++) {
            result[--count[arr[i]]] = arr[i];
        }
        return result;
    }

    private static int max(int[] arr) {
        return Arrays.stream(arr).max().getAsInt();
    }

    public static void main(String[] args) {
        int[] src = {12, 23, 34, 54, 20, 31, 13, 23, 46, 53};
        System.out.println("排序前的数组: " + Arrays.toString(src));

        int[] result = sort(src);
        System.out.println("排序后的数组: " + Arrays.toString(result));
    }
}
