package zcy.com.sachin.chapter02;

/**
 * @Author Sachin
 * @Date 2022/3/30
 **/
public class CheckListHasLoop {
    /**
     * 如何检测一个链表是否 有环
     */

    class ListNode {
        public ListNode() {
        }

        public ListNode(int value) {
            this.val = value;
        }

        public int val;
        public ListNode next;


    }

    public boolean isLoop(ListNode head) {
        ListNode slow=head;
        ListNode fast = head;
        if (head == null) {
            return false;
        }
        /**
         *   1 2 3 4 ：fast开始是1， 然后是 3，然后是null
         *
         *   1 2 3   fast开始是1，然后是3， 3的next是null 。 也就是说 有环的情况下 fast=fast.next.next 一定不为null
         *   无环的情况下，可能是fast=null ，或者是fast.next==null
         *
         */
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                return true;
            }
        }
        return false;

    }
}
