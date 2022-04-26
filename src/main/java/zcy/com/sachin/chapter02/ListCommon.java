package zcy.com.sachin.chapter02;

/**
 * @Author Sachin
 * @Date 2022/3/29
 **/
public class ListCommon {
    /**
     * 打印两个有序链表的公共部分
     */
    public void printCommonPart(Node head1, Node head2) {
        while (head1 != null && head2 != null) {
            if (head1.value == head2.value) {
                System.out.printf(head1.value + "");
                head1 = head1.next;
                head2 = head2.next;
                continue;

            }
            if (head1.value > head2.value) {
                head2 = head2.next;
            } else {
                head1 = head1.next;
            }
        }
    }


    class Node {
        public int value;
        public Node next;

        public Node(int data) {
            this.value = data;
        }
    }

}
