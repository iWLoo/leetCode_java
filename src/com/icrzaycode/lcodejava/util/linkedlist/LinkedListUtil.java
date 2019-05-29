package com.icrzaycode.lcodejava.util.linkedlist;

/**
 * @author ybh on 2019/5/29.
 */
public class LinkedListUtil {

    /**
     * 获取一个随机的单向链表
     * @param length        链表的长度
     * @param randomMax     链表中的最大值，范围：[0,randomMax]
     * @return
     */
    public static ListNode getRandomLinkedList(int length,int randomMax){
        ListNode returnNode = null;
        //定义一个尾部指针
        ListNode last = null;

        for(int i=0; i<length ;i++){
            int random = (int)(Math.random()*(randomMax+1));
            while (random==0 && i==length-1){
                //保证最后一位不是0
                random = (int)(Math.random()*(randomMax+1));
            }
            ListNode node = new ListNode(random);
            if(returnNode==null){
                returnNode = node;
                last = node;
            }else {
                last.next = node;
                last = node;
            }
        }
        return returnNode;
    }

    /**
     * 打印链表
     * @param listNode
     */
    public static void printLinkedList(ListNode listNode){
        StringBuilder sb = new StringBuilder("");
        if(listNode==null){
            throw new RuntimeException("listNode is null");
        }
        do{
            sb.append(listNode.val);
            sb.append("->");
            listNode = listNode.next;
        }while (listNode!=null);
        sb.append("null");
        System.out.println(sb.toString());
    }

    public static void main(String[] args) {
        int length01 = 8;
        int randomMax01 = 9;
        ListNode listNode01 = LinkedListUtil.getRandomLinkedList(length01,randomMax01);
        LinkedListUtil.printLinkedList(listNode01);

        System.out.println();
        System.out.println("--------------------");
        System.out.println();

        int length02 = 10;
        int randomMax02 = 9;
        ListNode listNode02 = LinkedListUtil.getRandomLinkedList(length02,randomMax02);
        LinkedListUtil.printLinkedList(listNode02);
    }
}
