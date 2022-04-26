package labuladong.sachin.chapter01;

import java.util.Arrays;

/**
 * @Author Sachin
 * @Date 2022/4/24
 **/
public class Alrgs {

    /**
     * DP table 处理斐波那契额
     */
    public int fib(int N) {
        int[] dp = new int[N + 1];
        dp[1] = 1;
        dp[2] = 2;
        for (int i = 3; i <= N; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[N];
    }


    /**
     * 给定金额和 硬币值，求最少硬币数量
     */
    public int coinChnage(int amount, int[] coins) {

        return dpCoinChange(amount, coins);
    }

    public int dpCoinChange(int amount, int[] coins) {
        if (amount == 0) {
            return 0;
        }
        if (amount < 0) {
            return -1;
        }
        int res = Integer.MAX_VALUE;

        for (int i = 0; i < coins.length; i++) {
            int sub = dpCoinChange(amount - coins[i], coins);
            if (sub < 0) {
                continue;
            }
            res = Math.min(res, sub + 1);
        }
        return res == Integer.MAX_VALUE ? -1 : res;
    }

    /**
     * 带备忘录的递归
     */

    public int memDpCoinChange(int amount, int[] coins, int[] mem) {

        if (mem[amount] != Integer.MAX_VALUE) {
            return mem[amount];
        }
        if (amount < 0) {
            return -1;
        }
        if (amount == 0) {
            return 0;
        }

        int res = Integer.MAX_VALUE;
        for (int i = 0; i < coins.length; i++) {
            int sub = memDpCoinChange(amount - coins[i], coins, mem);
            if (sub == -1) {//表示子问题没有解， 这个时候子问题返回-1，此时不能执行Math.min
                continue;
            }
            res = Math.min(sub, res + 1);//res+1表示子问题加当前的硬币
        }
        mem[amount] = res == Integer.MAX_VALUE ? -1 : res;
        return mem[amount];

    }

    /**
     * 使用自底向上的方式计算最少货币数
     */
    public int coinChanges(int amount, int[] coins) {

        /**
         *
         *
         */
        int[] dp = new int[amount + 1];
        /**
         * 设置每一个位置的初始值是 amount+1，表示无法兑换.
         * 为什么不可以直接初始化正无穷？
         * （1）如果直接初始化正无穷，那么在Match.min 比较dp[i] 和dp[i-coins[k]]+1的时候需要 先判断 子问题 dp[i-coins[k]]是否是正无穷Integer.maxValue ，如果是，则dp[i-coins[k]]+1会出现溢出的问题。 相加得到负数，然后math.min就会出错。
         * （2） 如果初始化amount+1，当子问题无解时， dp[i]=amount+1, dp[i-coins[k]]+1=amont+1+1,因此Math.min 仍然是amount+1。
         * 对于无解的情况下最终结果需要返回-1
         */
        for (int i = 0; i < amount + 1; i++) {
            dp[i] = amount + 1;
        }
        //base case
        dp[0] = 0;

        for (int i = 0; i <= amount; i++) {
            for (int k = 0; k < coins.length; k++) {
                if (i - coins[k] < 0) {
                    continue;
                }
                //子问题无解
                if (i - coins[k] == amount + 1) {
                    continue;
                }
                dp[i] = Math.min(dp[i], dp[i - coins[k]] + 1);
            }
        }
        if (dp[amount] == amount + 1) {
            return -1;

        }
        return dp[amount];
    }


    public int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length];
        /**
         * dp全部初始化为1
         */
        Arrays.fill(dp, 1);

        for (int i = 0; i < nums.length; i++) {

            for (int k = 0; k <= i; k++) {
                if (nums[i] > nums[k]) {
                    dp[i] = Math.max(dp[i], dp[k] + 1);
                }
            }

        }
        int res = 0;
        for (int i = 0; i < dp.length; i++) {
            res = Math.max(res, dp[i]);
        }
        return res;
    }

    /**
     * 最长回文子序列
     * 给你一个字符串 s ，找出其中最长的回文子序列，并返回该序列的长度。
     * <p>
     * 子序列定义为：不改变剩余字符顺序的情况下，删除某些字符或者不删除任何字符形成的一个序列。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/longest-palindromic-subsequence
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */

    public int longesPalindromeSubSeq(String s) {

        char[] array = s.toCharArray();

        /**
         * 假设字符s长度是13，也就是index为0-12
         * 定义DP数组（i，j）表示  子串i，j的最长回文子序列。 我们的目标是求i=0 j=12这个范围内的最长回文子序列
         *
         * 他的子问题：1-12,0-11,1-11 范围内的最大值。
         *  如果dp[i]=dp[j] 则dp[i,j]=dp[i+1][j-1]+2
         *  否则dp[i,j]=max(dp[i+1,j],dp[i,j-1])
         *
         */
        int[][] dp = new int[array.length][array.length];
        for (int i = 0; i < array.length; i++) {
            for (int k = 0; k <= i; k++) {
                if (k < i) {
                    dp[i][k]=0;//dp(2,0) 子串2,0没有含义
                }
                if (k ==i) {
                    dp[i][k]=1;
                }
            }
        }

        /**
         * 我们最终求的结果是 dp(0,12)
         *
         * 对角线上的位置都是1，dp[12,12]这个位置=1
         * 我们要求dp[11][12]
         */
        for (int i = array.length - 1; i >= 0; i--) {// array.length-1 就是i=12,相当于对角线位置


            for (int j = i + 1; j < array.length; j++) {
                if (array[i] == array[j]) {
                    dp[i][j] = dp[i + 1][j - 1] + 2;
                }else{
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
            }


        }

        return dp[0][array.length - 1];

    }
}
