package zcy.com.sachin.chapter04;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

import java.util.HashMap;

/**
 * @Author Sachin
 * @Date 2022/3/30
 **/
public class MinimumCurrentyToExchangeMoney {
    /**
     * 换钱最少货币数
     * 给定 钱币 5,2,3 求20的最少换钱数量
     * 要求20的最小换钱数量，只需要 先求出 （20-5） （20-2） （20-3）的最小货币数量，然后取最小值
     * <p>
     * 因此 对于给定的 k，需要求k-5，此时需要手动计算 初始值 0-5，从而能保证后续的使用
     * 《算法小炒 凑零钱问题
     */

    public int exchange(int[] money, int k) {
        if (k == 0) {
            return 0;
        }
        if (k < 0) {
            return -1;
        }
        int res = Integer.MAX_VALUE;
        for (int i = 0; i < money.length; i++) {
            int subOpt = exchange(money, k - money[i]);
            if (subOpt == -1) {
                continue;
            }
            res = Math.min(res, 1 + subOpt);
        }
        if (res != Integer.MAX_VALUE) {
            return res;

        }
        return -1;
    }


    /**
     * 带备忘录的 递归 《算法小炒 凑零钱问题
     */
    public int exchange2(int[] money, int k) {
        int[] memory = new int[k];
        /**
         * 创建备忘录
         */
        for (int i = 0; i < memory.length; i++) {
            memory[i] = -2;
        }
        return exchange3(money, k, memory);
    }

    public int exchange3(int[] money, int k, int[] memory) {

        if (k == 0) {
            return 0;
        }
        if (k < 0) {
            return -1;
        }
        int res = Integer.MAX_VALUE;
        for (int i = 0; i < money.length; i++) {
            int subOpt;
            /**
             * k是金额， money[i] 是纸币金额，  有可能金额 小于纸币金额，这个时候 不需要在 备忘表中查找
             */
            if ((k - money[i]) >= 0 && memory[k - money[i]] != -2) {
                subOpt = memory[k - money[i]];
            } else {
                subOpt = exchange3(money, k - money[i], memory);
                //放入备忘录， 只有 当金额大于 纸币金额的时候才需要放入， 比如 金额是3，纸币金额是5，这个时候不需要放入备忘表
                if ((k - money[i]) >= 0) {
                    memory[k - money[i]] = subOpt;
                }
            }
            if (subOpt == -1) {
                continue;
            }
            res = Math.min(res, 1 + subOpt);
        }
        if (res != Integer.MAX_VALUE) {
            return res;

        }
        return -1;
    }

    /**
     * 《算法小炒 凑零钱问题》
     *
     * @param coins
     * @param amount
     * @return
     */
    public int exchange4(int[] coins, int amount) {

        /**
         * 定义dp数组，dp[i] 表示 当目标金额为i，时至少需要x个硬币
         * 数组的大小为amount+1，因为金额是amount，但是 金额从0开始。因此数组长度是amount+1
         */
        int[] dp = new int[amount + 1];
        /**
         * 初始值，为什么设置为初始较大的值，而不是-1 参考下文解释
         */
        for (int i = 1; i < dp.length; i++) {
            dp[i] = Integer.MAX_VALUE;
        }
        /**
         * base case
         */
        dp[0] = 0;
        for (int i = 0; i < dp.length; i++) {

            /**
             * 对于当前金额i，求所有子问题 +1的最小值
             */
            for (int j = 0; j < coins.length; j++) {

                if (i - coins[j] < 0) {
                    /**
                     * 金额小于纸币的面值，因此无解，也就意味着 当前 纸币方案不合适
                     */
                    continue;
                }
                //当前方案的值 dp[i - coins[j]]+1,  j不断变化，在这些值中取最小值。
                //判断 dp[i-coins[j]]是否是一个 有解的 值，也就是（i-coins[j]）能否使用 纸币 组成,在这种情况下 （i-coins[j]）+1是一个解决方案
                //那么我们如何判断  金额 （i-coins[j]）是否有解呢？ 只需判断dp[i-coins[j]]是否是一个有效值。 而且因为我们 需要将 当前的解决方案
                //存放在dp[i]这个位置上，以便下次 forj循环能够和dp[i]位置上 做比较，取数量小的， 因此dp[i]的初始值不应该是 小值，因为小值 会影响比较
                if (dp[i - coins[j]] != Integer.MAX_VALUE) {
                    //此时 金额（i-coins[j]） 存在解; 如果dp[i]的初始值设置为比较小的值-1，这就会影响下面的min取值
                    dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
                }
            }

        }
        /**
         * return -1 是题目要求，无解的时候返回-1
         */
        if (dp[amount] == Integer.MAX_VALUE) {
            return -1;
        }
        return dp[amount];

    }


    /**
     * 换钱最少方法数
     */

    public int coins1(int[] arra, int aim) {
        if (arra == null || arra.length == 0 || aim < 0) {
            return 0;
        }
        return process1(arra, 0, aim);

    }

    public int process1(int[] arr, int index, int aim) {
        int res = 0;
        if (index == arr.length) {
            if (aim == 0) {
                return 1;
            } else {
                return 0;
            }
        } else {
            for (int i = 0; arr[index] * i <= aim; i++) {
                res += process1(arr, index + 1, aim - arr[index] * i);
            }
        }
        return res;
    }


    public int coins2(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        /**
         * mpa[i][j]  表示
         */
        int[][] map = new int[arr.length + 1][aim + 1];
        return process2(arr, 0, aim, map);

    }

    public int process2(int[] arr, int index, int aim, int[][] map) {
        int res = 0;
        if (index == arr.length) {
            if (aim == 0) {
                res = 1;
            }
        } else {

            int mapValue = 0;
            for (int i = 0; arr[index] * i <= aim; i++) {

                /**
                 * map ij  表示  仅使用数组arr中 i位置及其之后的元素 组成 j的方法数
                 *
                 * map[i][j]=-1 表示无法组成
                 */
                mapValue = map[index + 1][aim - arr[index] * i];
                if (mapValue == 0) {
                    //map的初始值就是0，表示当前 位置 没有计算过
                    res += process2(arr, index + 1, aim - arr[index] * i, map);
                } else {

                    // (mapValue != 0) 则 mapValue=-1 表示  无法使用 arr中从 i位置及其之后的元素组成 j。 或者mapValue=3 表示方案数
                    res += mapValue == -1 ? 0 : mapValue;
                }
            }

        }
        if (res == 0) {
            //表示没有方案
            map[index][aim] = -1;
        } else {
            map[index][aim] = res;

        }
        return res;
    }


    /**
     * 最长递增子序列
     */

    public int[] getDp1(int[] arr) {
        int[] dp = new int[arr.length];
        /**
         * 求每一个位置的最长递增子序列的长度
         */
        for (int i = 0; i < arr.length; i++) {
            /**
             * 默认 当前自身长度为1 构成子序列
             */
            dp[i] = 1;
            /**
             * 求该位置前面的 所有小于当前元素的  位置处的 最长递增子序列
             */
            for (int j = 0; j < i; j++) {
                if (arr[j] < arr[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        return dp;
    }

    public int[] generateLts(int[] arr, int[] dp) {

        /**
         * 最长子序列 的位置
         */
        int pos = 0;
        int maxSubLen = 0;
        for (int i = 0; i < dp.length; i++) {
            if (dp[i] > maxSubLen) {
                maxSubLen = dp[i];
                pos = i;
            }
        }
        /**
         * 定义最长子序列数组
         */
        int[] subSeq = new int[maxSubLen];
        /**
         * 最长子序列的最后一个位置的元素
         */
        subSeq[maxSubLen - 1] = arr[pos];
        /**
         * 将maxSubLen 作为下标索引使用
         */
        maxSubLen = maxSubLen - 1;

        /**
         * 从 arr中 最长子序列 最后一个元素所在的位置 向前 确定 第二个元素的位置
         *
         */
        for (int i = pos - 1; i >= 0; i--) {

            if (arr[i] < arr[pos] && dp[i] == dp[pos] - 1) {
                subSeq[maxSubLen] = arr[i];
                pos = i;
            }

        }
        return subSeq;
    }

    /**
     * 最长公共子序列
     */

    public int[][] getDp(char[] str1, char[] str2) {
        str1 = "hofubmnylkra".toCharArray();
        str2 = "pqhgxgdofcvmr".toCharArray();
        int len1 = str1.length;
        int len2 = str2.length;
        int[][] dp = new int[len1][len2];
        /**
         * 初始化第一列
         */
        for (int i = 0; i < len1; i++) {
            if (str1[i] == str2[0]) {
                dp[i][0] = 1;
            }
            if (i >= 1 && dp[i - 1][0] == 1) {
                dp[i][0] = 1;
            }
        }
        /**
         * 初始化第一行
         */
        for (int i = 0; i < len2; i++) {
            if (str1[0] == str2[i]) {
                dp[0][i] = 1;
            }
            if (i >= 1 && dp[0][i - 1] == 1) {
                dp[0][i] = 1;
            }
        }

        /**
         * dp[i][j]表示 字符串str1[i]  和  str2[j]的最长公共序列
         */
        for (int i = 1; i < len1; i++) {

            for (int j = 1; j < len2; j++) {

                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);

                if (str1[i] == str2[j]) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - 1] + 1);
                }
            }
        }


        return dp;

    }

    /**
     * 最长公共子串
     */
    public int[][] getDp4(char[] str1, char[] str2) {

        int[][] dp = new int[str1.length][str2.length];

        /**
         * 初始化第一列
         */
        for (int i = 0; i < str1.length; i++) {
            if (str1[i] == str2[0]) {
                dp[i][0] = 1;
            }
        }
        for (int i = 0; i < str2.length; i++) {
            if (str2[i] == str1[0]) {
                dp[0][i] = 1;
            }
        }

        /**
         * dp i j 表示  以 str1 的i位置和 str2的j位置结尾的 公共子串
         */
        for (int i = 1; i < str1.length; i++) {
            for (int j = 1; j < str2.length; j++) {
                if (str1[i] != str2[j]) {
                    dp[i][j] = 0;
                    continue;
                }
                dp[i][j] = dp[i - 1][j - 1] + 1;
            }
        }
        return dp;
    }


    public static void main(String[] args) {

        MinimumCurrentyToExchangeMoney minimumCurrentyToExchangeMoney = new MinimumCurrentyToExchangeMoney();
        minimumCurrentyToExchangeMoney.skip(null);
    }


    /**
     *
     *  跳跃游戏
     *
     * @param arra
     * @return
     */
    public int skip(int[] arra) {

        int[] test = {2, 3, 1, 1, 4};
        int[] dp = new int[arra.length];
        int res = skipTwo(test, 0,dp);
        return res;
    }

    /**
     *跳跃游戏
     * @param arr
     * @param index
     * @param dp
     * @return
     */
    public int skipTwo(int[] arr, int index,int[] dp) {

        /**
         * 如果 当前位置index 等于数组的最后一个元素的位置 ，则不需要跳就到达了末尾。 且index大于数组最后一个
         * 元素的位置实际上也是不需要跳的
         */
        if (index >= arr.length - 1) {
            return 0;
        }
        /**
         * 如果inde 位于倒数第二个元素位置，只要倒数第二个元素不等于0 则只需要跳一步就到了末尾
         */
        if (index == arr.length - 2) {
            if (arr[index] == 0) {
                return -1;
            }
            return 1;
        }
        /**
         * 当前位置 可以跳 几步。 注意如果当前位置num=0，则意味着 永远跳不到末尾，也就是说不会执行for循环
         * for循环内是 考虑跳动 1步，2步的情况，那么也就是说 在没有执行for的时候 stepNum可能仍然是Integer maxValue，这个时候
         * 意味着当前位置跳不到末尾，应该返回-1表示。
         *
         *
         */
        int num = arr[index];
        int stepNum = Integer.MAX_VALUE;
        for (int i = 1; i <= num; i++) {
            /**
             * 如果当前位置 + 跳动的步数，超过了数组最后一个元素的位置，则意味着只需要跳一步
             */
            if (index + i > arr.length-1) {
                stepNum = 1;
                break;
            }
            /**
             * 判断dp数组中 是否有 存储 从 index+i位置 开始往后跳的 最小步数。dp初始值=0
             */
            if (dp[index + i] != 0) {
                /**
                 * 如果有存储，但是存储了-1，表示 index+i位置 到末尾不可达。 我们考虑可达的情况下 取最小值。
                 */
                if (dp[index + i] != -1) {
                    stepNum = Math.min(stepNum, dp[index + i] + 1);
                }
            }else{
                /**
                 * 如果dp中没有存储 则 递归调用。
                 *
                 */
                int subSkip = skipTwo(arr, index + i,dp);
                //记录  index+i 到达末尾的跳跃步数
                dp[index + i] = subSkip;
                /**
                 * 返回-1 表示 index+i不可到达最后位置，比如index+i位置是0，则index+i位置无法跳到末尾。
                 * 考虑返回不是-1的情况， index+i的跳跃步数 加上1 （这里的这个1  表示从index跳到 index+i 这一个步骤）
                 */
                if (subSkip != -1) {
                    stepNum = Math.min(stepNum, subSkip + 1);
                }
            }
        }
        /**
         * 两种情况： （1）index所在位置为0，导致不会 执行for循环跳跃1步、2步，也就是index无法到达 末尾，这个时候需要返回-1
         * （2）index所在位置不是0，但是index 能跳到的位置都是0，比如 index位置是2，表示能跳动1或者2步，每一步跳到的位置 都是0，也就是说上面的
         * for中 得到的index+1 =-1 index+2=-1,  因为在for循环中 对于 index+1和index+2 返回-1 的时候 不会更新当前的stepNum、毕
         * 竟一旦在for循环中将 stepNum设置为-1，则就无法做Math.min操作了
         *
         */
        if (stepNum == Integer.MAX_VALUE) {
            return -1;
        }
        return stepNum;

    }

    /**
     * 数组中最长连续序列
     */

    public int maxSeq(int[] arr) {

        int[] mm = new int[arr.length];

        int res=0;
        for (int i = 0; i < arr.length; i++) {
            if (mm[i] != 0) {

            }else{
                maxSeq2(arr, i, mm);
            }
            res = Math.max(mm[i] , res);

        }//end for

        return res;
    }

    /**
     * 在 数组arr中 的index位置 所在元素 为末尾，寻找他的最长连续序列的长度
     * @param arr
     * @param index
     * @param mm
     */
    public int  maxSeq2(int[] arr, int index, int[] mm) {

        if(index>=arr.length){
            return 0;
        }
        int num=arr[index];

        /**
         * 确定num-1 的最长连续序列长度
         * 首先判断num-1是否在arr中
         */
        int fIndex=-1;
        for (int j = 0; j < arr.length; j++) {
            if (arr[j] == num - 1) {
                fIndex = j;
            }//if
        }//end
        if (fIndex == -1) {
            mm[index] = 1;
            return 1;
        }else{
            //找到了 num-1
            /**
             * 判断内存中是否存在
             */
            if (mm[fIndex] != 0) {
                //表示已经存在num-1的最长连续序列 长度
                mm[index] = mm[fIndex] + 1;
            }else{
                //表示尚不存在 ，则计算num-1的长度
                mm[fIndex]=  maxSeq2(arr, fIndex,mm);
                mm[index] = mm[fIndex] + 1;
            }
        }
        return mm[index];
    }


    public int longestConsecutive(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int max=1;
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            if (!map.containsKey(arr[i])) {
                map.put(arr[i], 1);
                if (map.containsKey(arr[i] - 1)) {
                    final int merge = merge(map, arr[i] - 1, arr[i]);
                    max = Math.max(max, merge);
                }
                if (map.containsKey(arr[i] + 1)) {
                    final int merge = merge(map, arr[i], arr[i] + 1);
                    max = Math.max(max, merge);
                }
            }
        }
        return max;
    }

    private int merge(HashMap<Integer, Integer> map, int less, int more) {

        int left = less - map.get(less) + 1;
        int right = more + map.get(more) - 1;
        int len = right - left + 1;
        map.put(left, len);
        map.put(right, len);
        return len;



    }
}
