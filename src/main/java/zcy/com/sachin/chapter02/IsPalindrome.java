package zcy.com.sachin.chapter02;

/**
 * @Author Sachin
 * @Date 2022/3/29
 **/
public class IsPalindrome {

    /**
     * 判断是否是回文结构
     */

    class Node {
        public int value;
        public Node next;
    }

    /**
     * https://leetcode-cn.com/problems/palindrome-linked-list/submissions/
     *
     * @param head
     * @return
     */
    public boolean isPalindrome(Node head) {
        if (head == null || head.next == null) {
            return true;
        }
        boolean res = true;
        /**
         * 确定链表的中间节点 ，从而将链表分为左右两个部分， 然后将又半部分反转
         *  1 2  1  2是中间节点
         *  1 2 3 1   3是中间节点
         */
        /**
         * 使用快慢指针，块指针定位到尾部位置，
         */
        Node fast = head;
        Node slow = head;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        //右半部分的第一个节点
        Node rightStart = slow.next;
        //左半部分尾部清空,截断分成两个链表
        slow.next = null;
        //反转右半部分. 首先定义curNode表示当前被反转的节点， preNode表示被反转节点的前一个节点
        Node curNode = rightStart;
        Node preNode = null;
        while (curNode != null) {
            /**
             * 保存被反转节点的下一个节点，当前节点被反转完之后 就反转这个节点
             */
            Node tmp = curNode.next;
            //反转
            curNode.next = preNode;
            /**
             * curNode=null的时候意味着 preNode是最后一个节点， 同时也是反转之后的首节点
             */
            preNode = curNode;
            curNode = tmp;
        }
        Node leftStart = head;
        rightStart = preNode;

        Node p1 = leftStart;
        Node p2 = rightStart;
        while (p1 != null && p2 != null) {
            if (p1.value != p2.value) {
                res = false;
            }
            p1 = p1.next;
            p2 = p2.next;

        }
        // 恢复列表

        Node afCur = rightStart;
        Node afPre = null;
        while (afCur != null) {

            Node tmp = afCur.next;

            afCur.next = afPre;
            afPre = afCur;
            afCur = tmp;
        }
        slow.next = afPre;

        return res;
    }
}
