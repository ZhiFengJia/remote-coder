package com.jzf.algorithm.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个整数数组nums和一个目标值target,请你在该数组中找出和为目标值的那两个整数,并返回他们的数组下标.
 * 你可以假设每种输入只会对应一个答案.但是,你不能重复利用这个数组中同样的元素.
 * <p>
 * 示例:
 * 给定 nums=[2, 7, 11, 15], target=9
 * 因为 nums[0] + nums[1] = 2 + 7 = 9
 * 所以返回 [0, 1]
 *
 * @author Jia ZhiFeng <312290710@qq.com>
 * @date 2019/5/31 11:15:29
 */
public class Solution_2 {
    //52ms(暴力法)
    public int[] twoSum1(int[] nums, int target) {
        //遍历数组中每个元素
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if ((nums[i] + nums[j]) == target) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    //9ms(Hash查找)
    public int[] twoSum2(int[] nums, int target) {
        Map map = new HashMap(nums.length);
        for (int i = 0; i < nums.length; i++) {
            //将数组存到HashMap中,以利用key的hash特性.
            map.put(nums[i], i);
        }
        for (int i = 0; i < nums.length; i++) {
            //在key的hash表中查找是否有目标值,并且目标值对应的索引不等于自身.
            if (map.containsKey(target - nums[i]) &&
                    ((int) map.get(target - nums[i]) != i)) {
                return new int[]{i, (int) map.get(target - nums[i])};
            }
        }
        return null;
    }

    //8ms
    public int[] twoSum3(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[]{map.get(complement), i};
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("No two sum solution");
    }

    //2ms
    public int[] twoSum4(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap(16);
        for (int i = 0; i < nums.length; i++) {
            Integer first = map.get(target - nums[i]);
            if (first != null) {
                return new int[]{first, i};
            }
            map.putIfAbsent(nums[i], i);
        }
        return null;
    }

    public static void main(String[] args) {
        Solution_2 solution_2 = new Solution_2();

        int[] nums = {2, 7, 11, 15};
        int target = 9;
//        int[] nums = {3, 2, 4};
//        int target = 6;
        System.out.println(Arrays.toString(solution_2.twoSum1(nums, target)));
        System.out.println(Arrays.toString(solution_2.twoSum2(nums, target)));
        System.out.println(Arrays.toString(solution_2.twoSum3(nums, target)));
        System.out.println(Arrays.toString(solution_2.twoSum4(nums, target)));
    }
}
