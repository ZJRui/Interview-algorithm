package zcy.com.sachin.chapter02;

import org.w3c.dom.Node;

import java.util.Stack;

/**
 * @Author Sachin
 * @Date 2022/3/29
 **/
public class ListSum {

    public static void main(String[] args) {
        ListSum listSum = new ListSum();
        listSum.test();
        
    }

    private void test() {
        Node node1 = new Node(9);
        Node node2 = new Node(9);
        Node node3 = new Node(9);
        Node node4 = new Node(9);
        Node node5 = new Node(9);
        Node node6 = new Node(9);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node6;


        Node node7 = new Node(9);
        Node node8 = new Node(9);
        Node node9 = new Node(9);
        Node node10 = new Node(9);
        node7.next = node8;
        node8.next = node9;
        node9.next = node10;
        final Node res = addList1(node1, node7);
    }

    /**
     * 两个单链表生成相加链表
     */

    class Node {
        public Node() {
        }
        public Node(int value) {
            this.value = value;
        }

        public int value;
        public Node next;
    }

    public Node addList1(Node head1, Node head2) {
        Stack<Integer> s1 = new Stack<>();
        Stack<Integer> s2 = new Stack<>();
        while (head1 != null) {
            s1.push(head1.value);

            head1 = head1.next;
        }
        while (head2 != null) {
            s2.push(head2.value);
            head2 = head2.next;
        }
        /**
         * 表示进位
         */
        int cas = 0;
        int param1;
        int param2;
        int tmpSum = 0;
        Node head = null;
        while (!s1.empty() || !s2.empty()) {
            param1 = s1.empty() ? 0 : s1.pop();
            param2 = s2.empty() ? 0 : s2.pop();
            tmpSum = param2 + param1 + cas;
            cas = tmpSum / 10;

            int val = tmpSum % 10;
            Node cur = new Node();
            cur.value = val;
            /**
             * cur作为首节点
             */
            cur.next = head;
            head = cur;

        }
        if (cas == 1) {
            Node node = new Node();
            node.value = 1;
            node.next = head;
            head= node;
        }
        return head;
    }

}
