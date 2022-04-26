package zcy.com.sachin.chapter02;

/**
 * @Author Sachin
 * @Date 2022/3/30
 **/
public class OrderListInserNode {

    public static void main(String[] args) {
        OrderListInserNode test = new OrderListInserNode();
        test.test();

    }
    class ListNode {
        public ListNode() {
        }

        public ListNode(int value) {
            this.val = value;
        }

        public int val;
        public ListNode next;


    }

    public void test() {
        ListNode node1 = new ListNode(3);
        ListNode node2 = new ListNode(4);
        ListNode node3 = new ListNode(1);
        node1.next = node2;
        node2.next = node3;
        node3.next = node1;
        insert(node1, 2);

    }


    public  ListNode insert(ListNode head, int num) {
        ListNode node = new ListNode(num);
        if (head == null) {
            node.next = node;
            return node;
        }

         /*
    3种插入情况：
        1) cur.val < x < cur.next.val 顺序插入
        2) 插入点为为序列的边界跳跃点：
            如 3->4->1 插入5，这样 1(cur->next)<4(cur)<5(x) 4为插入点的前驱节点；这种情况表示x比最大值都大
            如 3->4->1 插入0，这样 0(x)<1(cur->next)<4(cur) 4为插入点的前驱节点；这种情况表示x比最小值都小
    */

        ListNode cur=head;
        /**
         * 为什么要用exe，主要是cur从head开始循环，需要循环到cur再次到head的位置时结束
         */
        boolean exe = true;
        while (cur != head || exe) {
            exe = false;
            /**
             * 确定是否可以顺序插入
             */
            if (cur.val <= num && num <= cur.next.val) {
                node.next = cur.next;
                cur.next = node;
                return head;
            }
            /**
             * 意味着   3,6,8,9,12,0   最开始cur=9,curnext=12, 正常情况下 cur<curNext, 如果出现了cur> curNext 则意味着
             * cur是尾部节点，curNext是头节点， 然后这个时候 判断插入的值 num 是否大于最大值 ，是否小于最小值， 因为这两种情况下 新节点插入的位置相同，都是在
             * 12之后，0之前。
             *
             * cur从某一个位置开始移动，当 插入的值大于等于cur，但是小于等于curNext的时候 就需要将这个插入的值插入到cur之后。
             * 如果cur向后移动的过程中发现了 cur小于curNext，则意味着cur是最后的节点，curNext是最初的节点，这个时候 就需要判断当前插入的值是否有必要插入在 0，12之间，当num>=12 或者num<=0的时候 都需要将 num插入在0-12之间
             *
             */
            if (cur.val > cur.next.val) {
                if (cur.val <= num) {
                    /**
                     * cur是最大值，插入的值比最大值还大
                     */
                    node.next = cur.next;
                    cur.next = node;
                    return head;
                } else if (cur.next.val >= num) {
                    /**
                     * cur。next 是最小值， 插入的值比最小值还小
                     */
                    node.next = cur.next;
                    cur.next = node;
                    return head;
                }

            }

            cur = cur.next;
        }
        //当cur.next=head 时意味着 cur是最后一个节点
        node.next = cur.next;
        cur.next = node;

        return head;
    }
}
