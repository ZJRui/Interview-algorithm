package zcy.com.sachin.chapter02;

/**
 * @Author Sachin
 * @Date 2022/3/30
 **/
public class MergeList {


    class ListNode {
        public ListNode() {
        }

        public ListNode(int value) {
            this.val = value;
        }

        public int val;
        public ListNode next;


    }


    /**
     * 合并两个有序单链表
     */

    public ListNode merge(ListNode head1, ListNode head2) {

        if (head1 == null || head2 == null) {
            return head1 != null ? head1 : head2;
        }
        /**
         * 确定head1和head2谁最小作为首节点
         */
        ListNode head = head1.val < head2.val ? head1 : head2;

        ListNode h1Start = head == head1 ? head1.next : head1;
        ListNode h2Start = head == head2 ? head2.next : head2;
        ListNode endNode = head;
        while (h1Start != null && h2Start != null) {

            if (h1Start.val < h2Start.val) {
                endNode.next = h1Start;
                endNode = h1Start;
                h1Start = h1Start.next;
            } else {
                endNode.next = h2Start;
                endNode = h2Start;
                h2Start = h2Start.next;
            }
        }
        if (h1Start != null) {
            endNode.next = h1Start;
        } else {
            endNode.next = h2Start;
        }
        return head;

    }
}
