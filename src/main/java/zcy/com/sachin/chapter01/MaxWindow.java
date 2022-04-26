package zcy.com.sachin.chapter01;

import java.util.LinkedList;

/**
 * @Author Sachin
 * @Date 2022/3/29
 **/
public class MaxWindow {
    /**
     * 生成窗口最大值数组
     */

    public int[] getMaxWindow(int[] arr, int w) {

        if (arr == null || w < 1 || arr.length < w) {
            return null;
        }
        LinkedList<Integer> qmax=new LinkedList<>();

        /**
         * 如果w=arr.length 则最大值数组中只有一个元素。 因此元素个数是 arr.length-w+1
         */

        int[] res = new int[arr.length - w + 1];
        int index = 0;
        for (int i = 0; i < arr.length; i++) {

            /**
             * 将当前值放入队列中，并剔除队列中比当前值小的元素，因为当前值比较大，因此可以 维持一段窗口.
             * 剔除的顺序是从队列尾部向前踢，直到找到第一个大于当前值的数据
             */
            while (!qmax.isEmpty() && arr[qmax.peekLast()]<= arr[i]) {
                qmax.pollLast();
            }
            qmax.addLast(i);
            /**
             * 判断是否越界 ，队列： 2...5, 则 窗口中包含2，3,4,5， 此时需要剔除队列first
             */
            if (qmax.peekLast()  - qmax.peekFirst() == w) {
                qmax.pollFirst();
            }
            if (i >= w - 1) {
                /**
                 * 开始记录，前w-1个不记录
                 */
                res[index++] = arr[qmax.peekFirst()];
            }


        }
        return res;

    }
}
