package com.jzf.algorithm;

import java.util.Arrays;

/**
 * 基数排序(Radix Sort)是桶排序的扩展,
 * 它的基本思想是: 将整数按位数切割成不同的数字,然后按每个位数分别比较(用计数排序).
 */
public class RadixSortTest {
    /**
     * 基数排序
     *
     * @param arr 排序前的数组
     * @return 排序后的数组
     */
    public static int[] sort(int[] arr) {
        int[] result = new int[arr.length];
        int[] count = new int[10];

        for (int i = 0; i < 3; i++) {
            int division = (int) Math.pow(10, i);
            System.out.println("当次位数: " + division);

            // 将数据出现的次数存储在count[]中
            for (int j = 0; j < arr.length; j++) {
                int num = arr[j] / division % 10;
                count[num]++;
            }
            System.out.println("统计次数: " + Arrays.toString(count));

            // 目的是让更改后的count[i]的值,是该数据在result[]中的位置.
            for (int m = 1; m < count.length; m++) {
                count[m] = count[m] + count[m - 1];
            }
            System.out.println("数据位置: " + Arrays.toString(count));

            for (int n = arr.length - 1; n >= 0; n--) {
                int num = arr[n] / division % 10;
                result[--count[num]] = arr[n];
            }

            System.arraycopy(result, 0, arr, 0, arr.length);
            Arrays.fill(count, 0);
        }
        return result;
    }

    public static void main(String[] args) {
        int[] src = {120, 231, 345, 657, 204, 315, 137, 239, 467, 892};
        System.out.println("排序前的数组: " + Arrays.toString(src));

        int[] result = sort(src);
        System.out.println("排序后的数组: " + Arrays.toString(result));
    }
}