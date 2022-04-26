package zcy.com.sachin.chapter02;

/**
 * @Author Sachin
 * @Date 2022/3/29
 **/
public class DeleteLastK {
    /**
     * 单链表中删除倒数第k个节点
     */
    class Node {
        public int value;
        public Node next;
    }

    public static Node deleteLastKthNode(Node head, int lastKth) {

        if (head == null || lastKth < 1) {
            return head;
        }
        /**
         * 链表      1, 2 , 3 , 4, 5， 6， null
         * lastkth  3, 2,  1,  0, -1, -2, -3
         * lastKth -3 -2 ,-1   0 , 1, 2,  3
         */
        Node cur=head;
        while (cur != null) {
            lastKth--;
            cur = cur.next;
        }
        if (lastKth > 0) {
            //则说明  链表中 节点数量小于lastKth
        }
        if (lastKth == 0) {
            head = head.next;
            return head;
        }
        if (lastKth < 0) {
            cur = head;
            while (lastKth != -1) {
                lastKth++;
                cur = cur.next;
            }
            cur.next = cur.next.next;

        }

        return head;

    }
}
