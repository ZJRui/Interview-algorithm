package zcy.com.sachin.chapter02;

/**
 * @Author Sachin
 * @Date 2022/3/29
 **/
public class DeleteRepeatNode {

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
     * 删除无序链表中重复出现的节点
     * @param head
     * @return
     */
    public static ListNode deleteRepeatNode(ListNode head) {

        ListNode cur = head;
        while (cur != null) {
            /**
             * 从当前节点的下一个节点开始判断是否 重复
             */
            ListNode fCur = cur.next;
            /**
             * fCur的值可能与当前cur的值相同，需要保存fCur的前一个节点用于删除fCur
             */
            ListNode preNode = cur;
            while (fCur != null) {
                if (fCur.val == cur.val) {
                    //删除fCur
                    preNode.next = fCur.next;
                    /**
                     * 删除之后 更新 fCur，重新判断fCur是否和当前cur的值是否相同
                     */
                    fCur = preNode.next;
                    continue;
                }
                preNode = fCur;
                fCur = fCur.next;
            }
            cur = cur.next;
        }
        return head;

    }
}
