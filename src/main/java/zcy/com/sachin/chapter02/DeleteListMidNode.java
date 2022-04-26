package zcy.com.sachin.chapter02;

/**
 * @Author Sachin
 * @Date 2022/3/29
 **/
public class DeleteListMidNode {
    /**
     * 删除链表中间节点
     *
     *  1 2 3 4
     *  四个节点的情况 书中删除中间节点是 删除 2，
     *  leetcode中删除中间节点是删除 3
     *
     */


    class Node{
        public int value;
        public Node next;

    }

    public static Node remoteMidNode(Node head) {

        /**
         * 如果只有一个节点或者 head为空，则返回
         */
        if (head == null || head.next == null) {
            return head;
        }
        /**
         * 两个节点则删除第一个节点
         */
        if (head.next.next == null) {
            return head.next;
        }
        /**
         * 三个或以上节点
         */

        //三个节点的情况下 删除第二个节点需要保存对第一个节点的引用
        Node preDel=head;
        Node cur = head.next.next;
        while (cur.next != null && cur.next.next != null) {
            preDel = preDel.next;
            cur = cur.next.next;
        }
        //退出while是因为 cur.next=null || cut.next.next==null
        /**
         * 对于 cur.next=null, 则意味着 只有 1,2,3 三个节点， 因此删除2节点，preDel不需要变动。
         * 对于cur.next.next==null ,则意味着 1,2,3,4 四个节点， 删除2节点，predel也不需要懂
         */
        preDel = preDel.next.next;
        return head;

    }

    /**
     * https://leetcode-cn.com/problems/delete-the-middle-node-of-a-linked-list/
     * @param head
     * @return
     */
    public static Node leetCodeRemoveMidNode(Node head) {
        /**
         * 如果只有一个节点，删除这个节点
         */
        if (head == null || head.next == null) {
            return null;
        }
        //两个节点
        if (head.next.next == null) {
            //删除第二个节点
            head.next = null;
            return head;
        }
        /**
         * 三个及以上节点
         */
        Node preDel = head;
        Node cur = head.next.next;
        while (cur.next != null && cur.next.next != null) {
            preDel = preDel.next;
            cur = cur.next.next;
        }
        /**
         *  对应于4个节点的情况
         *  1 ，2 ，3， 4
         *  删除3 ， pre是2
         */
        if (cur.next != null && cur.next.next == null) {
            preDel = preDel.next;
        }
        preDel.next = preDel.next.next;
        return head;

    }
}
