package zcy.com.sachin.chapter02;

import java.util.Stack;

/**
 * @Author Sachin
 * @Date 2022/3/29
 **/

public class ReverseKListNode {

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
     * 将单链表的每k个节点之间逆序
     */
    public static ListNode reversKNode(ListNode head, int k) {

        if (k < 2) {
            return head;
        }

        Stack<ListNode> stack = new Stack<>();
        ListNode cur = head;
        ListNode newHead = null;
        /**
         *    1 2 3 4 5 6  7  8  9
         *    1 2 3 —> 3 2 1
         *    4 5 6 -> 6 5 4
         *
         *    preNode 表示上一个分段的尾部节点 ,比如上面preNode表示1，下一个分段的
         *    首节点 将会作为preNode的next
         *    nextNode 表示当前节点的下一个节点，比如 对于 456 这个分段
         *    来说， curNode=6， preNode=1， nextNode=7，nextNode尚未 push到stack，
         *    456这个分段 反转之后 的尾节点的next就是nextNode，也就是
         *    节点4 的next就是 7. 同时  节点4会作为新的preNode用于下一个789分段
         */
        ListNode preNode = null;
        ListNode nextNode = null;
        while (cur != null) {
            stack.push(cur);
            nextNode = cur.next;
            if (stack.size() == k) {
                preNode= resign1(stack, preNode, nextNode);
                /**
                 * 对于第一次reverseStack返回的节点将会作为整个链表的首节点。
                 * 第一次reverse的时候 最后被放入的当前节点 位于栈顶，反转之后会作为整个链表的首节点
                 */
                if (newHead==null) {
                    newHead=cur;
                }
            }
            cur = nextNode;
        }
        /**
         * 如果从来没有进行过reverse
         */
        if (newHead == null) {
            newHead = head;
        }
        return newHead;

    }

    /**
     * 1 2 3 4 5 6
     * <p>
     * 1 2 3
     * <p>
     * 3- 2 -1
     *
     * @param stack
     */
    public static ListNode resign1(Stack<ListNode> stack, ListNode left, ListNode right) {
        /**
         * left 表示上一个 分段的 最后一个节点，这个节点的next  就是 stack 的首节点.
         * right 表示  当前cur的next，  stack 分段的最后一个节点的next 应该设置为right
         *
         */
        ListNode headSeg = stack.peek();
        if (left != null) {
            left.next = headSeg;
        }
        /**
         * end 节点表示最后一个节点， 对于弹出来的节点 都是end的next节点。
         * 比如栈中  3， 2,1   end开始为null，然后end=3， 然后end=2， 然后end=1，
         */
        ListNode end = null;
        while (!stack.isEmpty()) {
            final ListNode pop = stack.pop();
            if (end != null) {
                end.next = pop;
            }
            end = pop;
        }
        end.next = right;
      return end;
    }


}
