package com.icrzaycode.lcodejava.problems.first;

import com.icrzaycode.lcodejava.util.linkedlist.LinkedListUtil;
import com.icrzaycode.lcodejava.util.linkedlist.ListNode;

/**
 * @author ybh on 2019/5/28
 */
public class AddTwoNumbers {
    /**
     * 两数相加_0002_M
     *
     * 给出两个非空的链表用来表示两个非负的整数。
     * 其中，它们各自的位数是按照逆序的方式存储的，并且它们的每个节点只能存储一位数字。
     * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和
     * 您可以假设除了数字0之外，这两个数都不会以0开头。
     *
     * 示例：
     * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
     * 输出：7 -> 0 -> 8
     * 原因：342 + 465 = 807
     *
     * 思路：
     * 链表的头部位置，就是数字的末尾，所以头部位置可以直接相加，进位也是向前进位
     * 1、第一个while循环解决两个链表相同的位数
     * 2、第二个do while循环处理其中一个链表过长剩下的部分
     * 3、最后处理完高位进位要+1
     *
     * 注意：我的while条件是&&，官方给的答案是||，并且官方的代码比较简洁，是因为它把第一步和第二步合并了
     * 所以官方的答案，循环次数是两个链表中最长的，所以时间复杂度是 O(max(m, n))
     *
     * 我写的代码
     * 第一步while循环的时间复杂度是 O(min(m, n))--------时间1
     * 第二步do while的循环时间复杂度是 O(max(m, n)-min(m, n))--------时间2
     *
     * 所以实际上都差不多，但是，这个和数据量也是有关系的
     * 比如你写两次for循环，实际的时间复杂度是 O(n)+O(n)，但实际算的还是O(n)
     * 所以我的代码实际复杂度是 O(max(时间1,时间2))，比 O(max(m, n)) 要低
     */
    private static ListNode addTwoNumbers(ListNode l1, ListNode l2){
        ListNode returnHead = null;
        if(l1==null || l2==null){
            return null;
        }
        //是否进位
        int carry = 0;
        //定义returnHead的最后一位
        ListNode last = null;
        while (l1!=null && l2!=null){
            int resultNode = (l1.val+l2.val+carry)%10;
            carry = (l1.val+l2.val+carry)/10;
            ListNode thisNode = new ListNode(resultNode);
            if(returnHead == null){
                returnHead = thisNode;
                last = thisNode;
            }else {
                last.next = thisNode;
                last = thisNode;
            }
            l1 = l1.next;
            l2 = l2.next;
        }
        //处理一个链表过长，剩下的部分
        if(l1!=null || l2!=null){
            ListNode tempNode = l1;
            if(l2!=null){
                tempNode = l2;
            }
            do{
                int resultNode = (tempNode.val+carry)%10;
                carry = (tempNode.val+carry)/10;
                ListNode thisNode = new ListNode(resultNode);
                last.next = thisNode;
                last = thisNode;
                tempNode = tempNode.next;
            }while (tempNode!=null);
        }
        if(carry>0){
            //链表处理完了，高位进位了
            last.next = new ListNode(1);
        }

        return returnHead;
    }

    public static void main(String[] args) {
        /**
         * 测试2次
         * 1、一样长度
         * 2、不同长度
         */
        ListNode l1 = LinkedListUtil.getRandomLinkedList(3,9);
        LinkedListUtil.printLinkedList(l1);
        ListNode l2 = LinkedListUtil.getRandomLinkedList(3,9);
        LinkedListUtil.printLinkedList(l2);

        ListNode result12 = addTwoNumbers(l1,l2);
        LinkedListUtil.printLinkedList(result12);

        System.out.println();
        System.out.println("------------");
        System.out.println();

        ListNode l3 = LinkedListUtil.getRandomLinkedList(2,9);
        LinkedListUtil.printLinkedList(l3);
        ListNode l4 = LinkedListUtil.getRandomLinkedList(5,9);
        LinkedListUtil.printLinkedList(l4);

        ListNode result34 = addTwoNumbers(l3,l4);
        LinkedListUtil.printLinkedList(result34);

    }
}
