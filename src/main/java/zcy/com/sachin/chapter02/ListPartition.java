package zcy.com.sachin.chapter02;

/**
 * @Author Sachin
 * @Date 2022/3/29
 **/
public class ListPartition {
    /**
     *
     * 将单链表按照某个值划分成左边小，中间相等，右边大的形式
     */
    class  Node {
        public int value;
        public Node next;
    }

    public Node partitionList(Node head , int pivot) {

        if (head == null || head.next == null) {
            return head;
        }
        int len = 0;
        Node cur=head;
        while (cur != null) {
            len++;
            cur = cur.next;
        }
        Node[] nodes = new Node[len];
        cur = head;
        len = 0;
        while (cur != null) {
            nodes[len] = cur;
            cur = cur.next;
            len++;
        }
        partition(nodes, pivot);
        for (int i = 0; i < nodes.length - 1; i++) {
            nodes[i].next = nodes[i + 1];
        }
        nodes[nodes.length - 1].next = null;
        return nodes[0];



    }

    /**
     *
     * @param nodes
     * @param privot
     */
    public void partition(Node[] nodes, int privot) {

        /**
         * 当前 较小元素所占用的 索引下标
         */
        int small=-1;
        /**
         * 当前高位元素所占用的下标。
         */
        int big = nodes.length;

        int index = 0;
        while (index != big) {
            if (nodes[index].value > privot) {
                /**
                 * 如果index位置的元素 较大，则将其放置到高位. 其中 big表示已经被占用了的高位位置的索引下标。 --big表示当前可被使用的高位索引。
                 * 注意： 这里我们将index位置的元素 置换到高位，从高位换下来的元素是一个未知数，他可能比privot大， 也可能比privot小。
                 * 因此这里index没有做++， 从而在下次执行while的时候 分析 从高位置换下来的元素 是大于 privot还是小于privot。
                 */
                swap(nodes, index, --big);

            } else if (nodes[index].value < privot) {
                /**
                 * 将index位置元素 置换到  当前 较小值区域内下一个可用位置上。 small表示已经被使用了的位置，因此++samll表示当前可用位置索引。
                 *
                 * 注意： 这个地方index做了++ 运算，这是为什么？ 首先small是小于index的， 其次 small 范围内的元素 一定是小于privot的（原因正是因为
                 * 这里的if， 仅当value<privot 的时候才将 index位置的元素放置到small范围内），因此small++ 到index 范围内的元素是等于 privot的
                 *
                 * 因此我们可以直接将++small位置的元素直接放置到 index位置上。 且index 会++ ，从而处理下一个位置的元素
                 *
                 */
                swap(nodes, ++small, index++);
            }else{
                index++;
            }

        }


    }

    public static void swap(Node[] nodes, int l, int h) {
        if (l ==h) {
            return;
        }
        Node tmp = nodes[l];
        nodes[l] = nodes[h];
        nodes[h] = tmp;
    }


    /**
     * 进阶问题：将单向链表按某值划分成左边小、中间相等、右边大的形式（尉★★☆☆）
     */

    public static Node listPartition2(Node head, int pivot) {
        Node sH = null;//小的头
        Node sT=null;//小的尾

        Node eH=null;//相等头
        Node eT=null;

        Node bH=null;
        Node bT=null;

        /**
         * 当前被处理的节点
         */
        Node cur=head;
        while (cur != null) {
            Node nextCur = cur.next;
            //将当前节点分离
            cur.next = null;
            if (cur.value >pivot) {
                if (bH == null) {
                    bH = cur;
                    bT = cur;
                }else{
                    bT.next = cur;
                    bT = cur;
                }
            } else if (cur.value < pivot) {
                if (sH == null) {
                    sH=cur;
                    sT=cur;
                }else{
                    sT.next=cur;
                    sT = cur;
                }
            }else{
                if (eH == null) {
                    eH=cur;
                    eT=cur;

                }else{
                    eT.next = cur;
                    eT=cur;
                }
            }
            cur = nextCur;
        }
        /**
         * 小的和相等的部分连接起来
         */
        if (sT != null) {
            sT.next = eH;
            if (eT != null) {
                sT = eT;
            }
        }else{
            /**
             * sT=null, 将相等部分赋值给小的部分
             */
            sH = eH;
            sT = eT;

        }

        /**
         * 将大的部分连接起来
         */
        if (sT != null) {
            sT.next = bH;
            if (bT != null) {
                sT = bT;
            }
        }else{
            sH = bH;
            sT = bT;

        }
        return sH;
    }
}
