package com.jzf.algorithm.leetcode;

/**
 * 给出两个非空的链表用来表示两个非负的整数.其中,它们各自的位数是按照逆序的方式存储的,并且它们的每个节点只能存储一位数字.
 * 如果,我们将这两个数相加起来,则会返回一个新的链表来表示它们的和.
 * 您可以假设除了数字0之外,这两个数都不会以0开头。
 * <p>
 * 示例:
 * 输入:(2 -> 4 -> 3) + (5 -> 6 -> 4)
 * 输出:7 -> 0 -> 8
 * 原因:342 + 465 = 807
 *
 * @author Jia ZhiFeng <312290710@qq.com>
 * @date 2019/5/31 14:30:15
 */
public class Solution_3 {

    //10ms
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        //创建一个哑节点
        ListNode listNode = new ListNode(0);
        ListNode temp = listNode;
        //进位
        int prevTens = 0;
        while (l1 != null || l2 != null) {
            int val1 = l1 == null ? 0 : l1.val;
            int val2 = l2 == null ? 0 : l2.val;

            //计算当前位的和
            int val = val1 + val2 + prevTens;
            //进位赋值,供下个节点计算和
            prevTens = val / 10;

            //创建一个节点赋值给当前节点的下个节点
            temp.next = new ListNode(val % 10);
            //将当前节点指向下个节点
            temp = temp.next;

            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
        }
        //防止最后位相加产生进位
        if (prevTens > 0) {
            temp.next = new ListNode(prevTens);
        }
        //返回哑节点的下个节点
        return listNode.next;
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public static void main(String[] args) {
        Solution_3 solution_3 = new Solution_3();

        ListNode listNodel1 = solution_3.new ListNode(2);
        listNodel1.next = solution_3.new ListNode(4);
        listNodel1.next.next = solution_3.new ListNode(3);

        ListNode listNodel2 = solution_3.new ListNode(5);
        listNodel2.next = solution_3.new ListNode(6);
        listNodel2.next.next = solution_3.new ListNode(4);

        ListNode listNode = solution_3.addTwoNumbers(listNodel1, listNodel2);

        System.out.print(listNode.val + "-->");
        while ((listNode = listNode.next) != null) {
            System.out.print(listNode.val);

            if (listNode.next != null) {
                System.out.print("-->");
            }
        }
    }
}
