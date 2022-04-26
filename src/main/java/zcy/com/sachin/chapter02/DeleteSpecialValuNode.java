package zcy.com.sachin.chapter02;

/**
 * @Author Sachin
 * @Date 2022/3/29
 **/
public class DeleteSpecialValuNode {

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
     * 删除指定 值的node
     */
    public ListNode remoteSpecialNode(ListNode head,int num) {
        if (head == null) {
            return head;
        }

        /**
         * 首先找到第一个不为num的节点作为首节点
         */
        ListNode cur=head;
        ListNode newHead;
        /**
         * 考虑 所有节点 值都是7 ，而num=7的情况 会删除所有的节点
         */
        while (cur!=null&&cur.val == num) {
            cur = cur.next;
        }
        newHead=cur;
        if (newHead == null) {
            return newHead;
        }
        /**
         * 从newHead的next节点开始判断 节点的val是否为num，如果为num则删除。 删除 next需要保存前屈pre，
         */
        ListNode preNode = newHead;
        ListNode fNode = newHead.next;
        while (fNode != null) {
            if (fNode.val == num) {
                preNode.next = fNode.next;
                fNode = preNode.next;
            }else{
                preNode=fNode;
                fNode = fNode.next;
            }
        }
        return newHead;


    }


}
