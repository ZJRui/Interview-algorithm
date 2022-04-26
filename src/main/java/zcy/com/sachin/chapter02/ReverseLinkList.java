package zcy.com.sachin.chapter02;

/**
 * @Author Sachin
 * @Date 2022/3/29
 **/
public class ReverseLinkList {

    public static void main(String[] args) {

        ReverseLinkList list = new ReverseLinkList();
        list.test();
    }

    public void test() {
        Node node1 = new ReverseLinkList.Node(1);
        Node node2 = new ReverseLinkList.Node(2);
        Node node3 = new ReverseLinkList.Node(3);
        Node node4 = new ReverseLinkList.Node(4);
        Node node5 = new ReverseLinkList.Node(5);

        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        reverse(node1, 2, 4);
    }


    /**
     * 单链表反转
     */

    class Node {
        public int value;
        public Node next;

        public Node(int value) {
            this.value = value;
        }
    }


    public Node reverseNode(Node head) {

        /**
         * 被反转节点的前一个节点
         */
        Node pre = null;
        /**
         * 表示当前被反转的节点
         */
        Node cur = head;

        while (cur != null) {

            /**
             * 反转当前节点， 反转之前记录当前 节点的next,作为下一个被反转的节点
             */
            Node tmp = cur.next;
            //反转当前节点
            cur.next = pre;
            /**
             * 记录下一个被反转节点的上一个节点. 如果cur=null,则意味着pre是最后一个节点，也就是反转之后的首节点
             */
            pre = cur;
            /**
             * 反转下一个节点
             */
            cur = tmp;

        }

        return pre;
    }


    /**
     * https://leetcode-cn.com/problems/reverse-linked-list/submissions/
     *  普通单链表反转 自己的思璐
     */
    public static Node reverseLinkListMySelf(Node head) {

        if (head == null || head.next == null) {
            return head;
        }
        Node newHead = null;
        if (head.next.next == null) {
            newHead = head.next;
            head.next.next = head;
            head.next = null;
            return newHead;
        }
        /**
         * 三个及以上节点     1 ，2 ，3
         *
         * P2定义为 当前被反转的节点
         * p1定义为当前被反转节点的前一个节点
         * p3定义为当前被反转节点的后一个节点
         */
        Node p1 = head;
        Node p2 = head.next;
        Node p3 = head.next.next;
        p1.next = null;//避免死循环
        while (p2 != null) {
            //反转当前节点
            p2.next = p1;
            //用p2记录下一个被反转的节点，但是在记录之前 需要保存 p2位 下一个被反转节点的 前一个节点
            p1 = p2;
            //记录下一个被反转的节点
            p2 = p3;
            if (p3 != null) {
                p3 = p3.next;
            }
        }
        return p1;
    }


    /**
     * 反转部分单链表
     *
     * 进阶： https://leetcode-cn.com/problems/reverse-linked-list-ii/
     * 给你单链表的头指针 head 和两个整数 left 和 right ，其中 left <= right 。请你反转从位置 left 到位置 right 的链表节点，返回 反转后的链表 。
     */

    public static Node reverse(Node head, int left, int right) {

        if (left >= right) {
            return  head;
        }

        //找到left所在位置节点
        Node newHead = head;
        Node preNewHead = head;
        //确定反转节点的数量
        int count = right - left ;
        while (left != 1) {
            left--;
            preNewHead = newHead;
            newHead = newHead.next;
        }


        final Node restHead = reverseNode(newHead, count);
        if (newHead == preNewHead) {
            return restHead;
        }
        preNewHead.next = restHead;

        return head;

    }


    public static Node reverseNode(Node head, int count) {


        if (head == null || head.next == null) {
            return head;
        }
        Node newHead = null;
        /**
         *
         *   1 2 3 4 5 6  7
         *
         *     left=2  right =3
         */
        if (count==1) {
            /**
             *
             */
            newHead = head.next;
            head.next = newHead.next;
            newHead.next=head;
            return newHead;
        }
        /**
         * 三个及以上节点     1 ，2 ，3
         *
         * P2定义为 当前被反转的节点
         * p1定义为当前被反转节点的前一个节点
         * p3定义为当前被反转节点的后一个节点
         *
         *   1 2 3 4 5 6  7
         *
         *     left=2  right =4  count=2
         *   1 2  5 4 3 6 7
         */
        Node p1 = head;
        Node p2 = head.next;
        Node p3 = head.next.next;
        while (count!= 0) {
            //反转当前节点
            p2.next = p1;
            count--;
            //用p2记录下一个被反转的节点，但是在记录之前 需要保存 p2位 下一个被反转节点的 前一个节点
            p1 = p2;
            //记录下一个被反转的节点
            p2 = p3;
            if (p3 != null) {
                p3 = p3.next;
            }
        }
        head.next = p2;
        return p1;

    }

}
