package zcy.com.sachin.chapter04;

/**
 * @Author Sachin
 * @Date 2022/3/30
 **/
public class MatrixMinimumPathSum {
    /**
     * 矩阵最小路径之和
     */


    public int sum(int[][] m) {
        if (m == null || m.length == 0 || m[0] == null || m[0].length == 0) {
            return 0;
        }
        /**
         * 创建dp数组
         */
        int row=m.length;
        int col = m[0].length;
        int[][] dp = new int[row][col];
        /**
         * 定义 dp[i][j]表示从（0,0）开始走到 ij位置的最小距离之和
         * dp[i][j]=dp[i-1][j]和dp[i][j-1] 中最小值 加上ij本身的值
         */
        dp[0][0] = m[0][0];
        for (int i = 1; i < row; i++) {
            dp[i][0] = dp[i - 1][0] + m[i][0];
        }
        for (int i = 1; i < col; i++) {
            dp[0][i] = dp[0][i - 1] + m[0][i];
        }
        /**
         * 确定 行号 为指定的值，然后对当前行从左到右（也就是列）分析
         */
        for (int i = 1; i < row; i++) {

            for (int j = 1; j < col; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + m[i][j];
            }
        }
        return dp[row - 1][col - 1];
    }

}
