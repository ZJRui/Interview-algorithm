package zcy.com.sachin.chapter04;

import java.util.*;

/**
 * @Author Sachin
 * @Date 2022/3/30
 **/
public class F {


    /**
     * 斐波那契数列
     * 1 1  2 3  5 8
     */
    public int f1(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        return f1(n - 1) + f1(n - 2);
    }

    public int f2(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        int n_1 = 1;
        int n_2 = 1;
        int res = 0;

        for (int i = 3; i <= n; i++) {

            res = n_1 + n_2;
            n_2 = n_1;
            n_1 = res;
        }
        return res;
    }

    /**
     * 跳台阶问题：  如果台阶有N级，最后跳上N级的情况，要么是从N-2级台阶直接跨2级台阶，要么是从N-1级台阶跨1级，因此S(N)=S(N-1)+S(N-2)
     * S(1)=1
     * S(2)=2
     */
    public int s1(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        return s1(n - 1) + s1(n - 2);

    }

    public int s2(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        int n_2 = 1;
        int n_1 = 2;
        int res = 0;
        for (int i = 3; i <= n; i++) {
            res = n_1 + n_2;
            n_2 = n_1;
            n_1 = res;
        }
        return res;

    }


    /**
     * 母牛数量
     */
    public int niuM(int n) {
        if (n < 1) {
            return 0;
        }
        if (n <= 3) {
            return n;
        }
        int n_1 = 3;
        int n_2 = 2;
        int n_3 = 1;

        int res = 0;
        for (int i = 4; i <= n; i++) {
            res = n_1 + n_3;
            n_3 = n_2;
            n_2 = n_1;
            n_1 = res;
        }
        return res;


    }

    /**
     * 数组中最长连续序列
     */
    public int longestConsecutive(int[] nums) {

        /**
         * 生成HashMap<Integer,Integer> map key表示遍历过的某个数字，value表示这个key所在的最长连续序列的长度。
         * 同时map还可以表示arr中的 某一个数字之前是否出现过。
         *
         * 从左到右遍历，假设遍历到arra[i].ruguo arr[i[之前出现过，直接遍历下一个数字。只处理之前没有出现过的arr[i]
         * 首先在map中加入记录（arr[i],1）表示目前arr[i]单独作为一个连续序列。然后看map中是否含有arr[i]-1,如果有，则说明arr[i]-1所在的连续序列可以和arr[i]
         * 合并，合并后 为A序列。利用map可以得到A序列的长度，记为lenA，最小值为leftA，最大值记为rightA，只在map中更新与leftA和rightA有关的记录。
         * 更新成（leftA，lenA）和（rightA，lenA）。接下来看map中是否含有arr[i]+1,如果有则说明Arr[i]+1所在的连续序列可以和A合并，合并后记为B序列。利用map
         * 可以得到B序列的长度为lenB，最小值记为leftB，最大值记为rightB，只在map中更新与leftB和rightB有关的记录。更新为（leftB，lenB）和（rightB，lenB）
         *
         */
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {

            Integer cur = nums[i];
            final Integer lenCur = map.get(nums[i]);

            if (lenCur != null) {
                //nums[i]已经出现过了，也就是说他所在的序列长度已经计算过了，不需要再次计算。

                continue;
            }
            if (lenCur == null) {
                /**
                 * 处理cur 左侧的序列长度
                 */
                final Integer leftLen = map.get(cur - 1);
                if (leftLen == null) {
                    //左边为空，不需要处理

                } else {
                    /**
                     * 左边不为空，则获取左边的长度，计算左边连续序列的最左位置的数字
                     */
                    Integer leftSeqStart = cur - 1 - leftLen + 1;
                    //更新长度
                    map.put(leftSeqStart, leftLen + 1);
                    map.put(nums[i], leftLen + 1);
                }
                /**
                 * 处理右边的序列长度
                 */
                final Integer rightLen = map.get(cur + 1);
                if (rightLen == null) {

                } else {
                    /**
                     * 存在右边 连续序列 ,首先计算 右边连续序列的右边界
                     */
                    Integer rightSeqEnd = cur + 1 + rightLen - 1;
                    //更新右边界的长度。有两种情况：上文是否有左连续序列，
                    // （1）如果有的话，那么cur已经被放置到了左连续序列，此时右连续序列不能再放入cur
                    //（2）如果没有的话，则cur需要放置到右边界
                    //（3）如果左右边界都没有的情况下需要将cur，1放入到map中

                    if (map.get(cur) == null) {
                        //表示没有左连续序列，此时cur需要计算到右边界中
                        map.put(cur, rightLen + 1);
                        map.put(rightSeqEnd, rightLen + 1);
                    } else {
                        //表示左连续序列存在，cur已经计算在了左连续序列，因此这个时候不能再将cur计算到右连续序列中。且需要根据长度
                        //计算出左连续序列的左边界值
                        final Integer leftLenWithCur = map.get(cur);
                        //计算左边界值
                        Integer seqLeft = cur - leftLenWithCur + 1;
                        map.put(seqLeft, leftLenWithCur + rightLen);
                        map.put(rightSeqEnd, leftLenWithCur + rightLen);
                    }
                }
                //判断没有左连续序列和右连续序列的情况
                if (map.get(cur) == null) {
                    map.put(cur, 1);
                }
            }
        }
        /**
         * 从map中找打 value最大的两个 key。
         * 遍历map可以定位哪个value最大，但是如何记录住两个key呢？
         */

        int res = 0;
        Iterator<Integer> it = map.keySet().iterator();
        while (it.hasNext()) {
            final Integer key = it.next();
            final Integer value = map.get(key);
            if (value > res) {
                res = value;
            }

        }
        return res;
    }


    /**
     * 数组中最长连续序列
     */
    public int longestConsecutive2(int[] nums) {

        //先sort
        quickSort(nums, 0, nums.length - 1);
        /**
         * 定义dpTable中的i位置标示 以这个位置的元素结尾的连续序列的长度
         *
         */
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);
        for (int i = 1; i < nums.length; i++) {
            int num = nums[i];
            /**
             * 如果前面的值等于当前的值-1，则序列增加
             */
            if (nums[i - 1] == num - 1) {
                dp[i] = dp[i - 1] + 1;
            }
        }
        int res = -1;
        for (int i = 0; i < dp.length; i++) {
            res = Math.max(res, dp[i]);
        }
        return res;

    }

    /**
     * 快速排序
     */
    public static void quickSort(int array[], int low, int hight) {
        if (low >= hight) {
            return ;
        }
        int i=low,j=hight;
        /**
         * 我们从低位区 将i位置 作为base，因此i位置被空闲出来了，
         *
         * 最终排序后， base位于中间位置，base左边的数据都是小于base的，base右边的数据都是大于等于base的。
         *
         * 因为低位i位置首先被空闲出来，因此我们需要先 从高位遍历找到一个 小于base的数据放置到i位置上。
         *
         * 又因为我们从高位寻找的都是小于base的数据，然后才将其放置到i位置上，因此base的左侧都是小于base的。 base的右侧可以存在等于base的数据。
         *
         *
         * 高位j位置的元素被放置到i位置上之后，j位置被空闲出来，因此我们需要从低位区 i+1位置开始，找到一个大于等于base的 元素，将其放置到j位置上
         *
         */
        int base = array[i];
        while(i<j){
            //如果右边的数字大于等于base则跳过
            while (i < j&&array[j]>=base) {
                j--;
            }
            //找到一个小于base的数字
            if(i<j){
                /**
                 * 这里将高位的 j位置 元素放置到i位置上，因此j位置被空闲出来，下面从低位区找到一个大于等于base的数据将其放置到j位置上
                 */
                array[i] = array[j];
                i++;
            }
            while (i < j&&array[i]<base) {
                i++;
            }
            if (i < j) {
                array[j] = array[i];
                j--;
            }
        }
        array[i] = base;
        quickSort(array, low, i - 1);
        quickSort(array, low, i + 1);

    }


    public void quickSort3(int[] array, int low, int hight) {

        if (low >= hight) {
            return;
        }
        int i = low, j = hight;
        /**
         *  将低位区的low位置作为 base，，然后这个low位置被空闲出来，需要从高位置
         *  寻找一个小于base的数据放置到low位置
         */
        int base = array[low];

        while (i < j) {

            /**
             * 从高位取寻找一个小于base的元素
             */
            while (i < j && array[j] >= base) {
                j-- ;
            }
            if (i < j) {
                //找到了元素
                array[i] = array[j];
                i++;
            }
            /**
             * 从低位区寻找一个大于等于base的元素放置到高位区
             */
            while (i < j && array[i] < base) {
                i++;
            }
            if (i < j) {
                //找到低位区 大于等于base的数据
                array[j] = array[i];
                j--;
            }
        }
        array[j]=base;
        quickSort3(array, low, i - 1);
        quickSort(array, i + 1, hight);

    }

    public void quickSort4(int[] array, int low, int hight) {

        if (low >= hight) {
            return ;
        }
        int base = array[low];
        int i = low, j = hight;
        while (i < j) {

            /**
             * 从高位区寻找一个小于base的数据放置到 低位区的i位置
             */
            while (i < j && array[j] >= base) {
                j--;
            }
            if (i < j) {
                /**
                 * 高位区找到一个小于base的元素
                 */
                array[i] = array[j];
                i++;
                /**
                 * 高位区j位置空闲，从低位区找到一个大于等于base的元素
                 */
            }
            while (i < j && array[i] < base) {
                i++;
            }
            if (i < j) {
                /**
                 * 低位区找到一个大于等于base的元素，将其放置到高位区的j位置
                 */
                array[j] = array[i];
                j--;
            }

        }
        array[i]=base;
        quickSort4(array, low, i - 1);
        quickSort4(array, i + 1, hight);
    }

}
