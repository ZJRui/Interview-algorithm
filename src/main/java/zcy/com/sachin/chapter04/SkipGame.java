package zcy.com.sachin.chapter04;

import com.sun.xml.internal.ws.util.StringUtils;

import java.util.*;

/**
 * @Author Sachin
 * @Date 2022/4/25
 **/
public class SkipGame {


    /**
     * 跳跃游戏
     */
    public int skipGame(int[] array) {

        /**
         * 定义dp数组，i位置表示从开始的位置跳到这个位置跳了多少步。
         * 因此dp[array.length-1]就表示从0位置跳到array.length-1位置跳了多少步。
         */
        int[] dp = new int[array.length];
        Arrays.fill(dp, Integer.MAX_VALUE);
        return skipStep(array, 0, 0, dp);
    }

    public int skipStep(int[] array, int cur, int steps, int[] dp) {

        /**
         *
         * steps表示当前已经跳了多少步，也就是从0位置开始跳到cur位置，已经跳了多少步
         *
         * 如果当前的位置已经是数组最后一个元素的位置，则不需要继续跳，这个时候只需要返回steps跳跃的次数
         */
        if (cur == array.length - 1) {
            return steps;
        }
        /**
         * cur> array.length-1 则表示上一步跳到的位置已经超出了数组，则表示这个跳跃组合是一个无效的跳跃组合，因此返回-1
         */
        if (cur > array.length - 1) {
            return -1;
        }

        int res = Integer.MAX_VALUE;
        for (int i = 1; i <= array[cur]; i++) {
            /**
             * i表示每次跳跃几步
             * 首先求出子问题的跳跃步数
             */
            int sub = skipStep(array, cur + i, steps + 1, dp);
            if (sub == -1) {
                continue;
            }
            res = Math.min(res, sub + 1);
        }
        if (res == Integer.MAX_VALUE) {
            return -1;
        }
        return res;
    }


    public int skipGame2(int[] array) {

        /**
         *
         * 跳跃游戏  3,2,3,2,1,4
         * 对于0位置的3，他可以选择跳跃1步，2步，3步到不同的位置，然后在不同的位置再次进行跳跃，也就是说要求0位置的最少跳跃次数，实际上
         * 就是求这三个子问题的最少跳跃次数。从这三个子问题来看，他们的区别就是当前所在的位置，以及已经跳跃了多少步。
         *
         * 但是我们注意 到 位置3的元素是2， 可以从3位置跳跃1次到这个位置2，或者从3位置跳跃一次到1位置，然后从1位置跳跃2个位置到3位置。
         * 这两种方案中他们的区别是跳跃到2位置的步数是不同的。 但是 这两种方案中，都需要 解决 从3位置跳跃到最末未位置 如何进行跳跃的问题
         *
         * 也就是说这两个方案存在重叠子问题。 这个子问题就是 从位置3 如何最优跳到位置5，如果知道了这个子问题的解，那么我们就能求出这两种方案
         * 哪个方案更优。 为了避免重叠子问题，需要使用备忘录 dp，,因此dp的含义就是 从当前位置到末尾的最少跳跃步数。
         *
         * 那么对于位置i我们如何求出dp[i]呢，主要看i位置的数字是几，然后 根据min(dp[i+1],dp[i+2]…)因此整个过程就是从dp的末尾开始求解
         *
         */
        int[] dp = new int[array.length];
        /**
         * 最后位置不需要跳跃
         */
        dp[array.length - 1] = 0;
        for (int i = array.length - 1 - 1; i >= 0; i--) {

            int res = Integer.MAX_VALUE;
            for (int k = 1; k <= array[i]; k++) {

                /**
                 * 定义从当前位置 i，跳跃k具体 然后到了nextPos位置
                 */
                int nextPos = i + k;
                if (nextPos > array.length - 1) {
                    //则说明跳出了array
                    continue;
                }
                /**
                 * 表示直接从 i位置 跳到了 末尾位置，因此只需要一步
                 */
                if (nextPos == array.length - 1) {
                    res = 1;
                    break;
                }
                if (dp[nextPos] == -1) {
                    //表示从nextPos无法跳跃到 末尾，因此这种方案不可用
                    continue;
                }
                res = Math.min(res, dp[nextPos] + 1);
            }
            if (res == Integer.MAX_VALUE) {
                //表示i位置到末尾位置不可达
                dp[i] = -1;
                continue;
            }
            dp[i] = res;
        }

        return dp[0];
    }


    /**
     * leetcode 折成一条线的纸牌博弈问题
     *
     * @param nums
     * @return
     */
    public boolean PredictTheWinner(int[] nums) {


        final int res = chooseNum(nums, 0, nums.length - 1, 0, 0, true);
        return res > 0;


    }

    public int chooseNum(int[] nums, int start, int end, int resA, int resB, boolean targetA) {

        /**
         * 1,5,2
         *
         * 如果可选的范围start>end 表示此时已经没有可选的 数字了，也就说这个时候已经形成了一种组合方案，需要计算出
         * 在这种组合方案下 A和B的得分差
         */
        if (start > end) {
            return resA - resB;
        }
        /**
         * 在所有的选择方案中，我们要选择返回的差值最大的， 最大的差值大于0则意味着存在某种组合方案，在这种组合方案下 resA赢得比赛。
         */
        int res = -1;
        int temp = -1;
        /**
         * 第一种选择： 选择左侧的数字
         */
        int toAdd = nums[start];
        if (targetA) {
            temp = chooseNum(nums, start + 1, end, resA + toAdd, resB, !targetA);
            res = Math.max(res, temp);
        } else {
            temp = chooseNum(nums, start + 1, end, resA, resB + toAdd, !targetA);
            res = Math.max(res, temp);
        }
        /**
         * 第二种选择 选择右侧的数字
         */
        toAdd = nums[end];
        if (targetA) {
            temp = chooseNum(nums, start, end - 1, resA + toAdd, resB, !targetA);
            res = Math.max(res, temp);
        } else {
            temp = chooseNum(nums, start, end - 1, resA, resB + toAdd, !targetA);
            res = Math.max(res, temp);
        }
        return res;
    }


    public int PredictTheWinner(int[] nums, int y) {


        if (nums == null || nums.length == 0) {
            return 0;
        }
        return Math.max(f(nums, 0, nums.length - 1), s(nums, 0, nums.length - 1));
    }

    /**
     * 定义递归函数f（i，j）表示如果arr(i,j)这个排列上的纸牌被先拿，最终能获得什么分数。
     * 如果i=j，只剩下一张纸牌，当然会被先拿纸牌的人拿走，所以返回arr[i]
     * <p>
     * 如果i!=j,当前拿纸牌的人有两种选择，要么拿走arr[i] 要么拿走arr[j] . 如果拿走arr[i]
     * 那么排列将剩下arr(i+1,j)对当前玩家来说，面对arr{i+1,j}排列的纸牌，他成了后拿的人，所以后续他能获得的分数为s(i+1,j)
     *
     * @param array
     * @param i
     * @param j
     * @return
     */
    public int f(int[] array, int i, int j) {
        if (i == j) {
            return array[i];

        }
        return Math.max(array[i] + s(array, i + 1, j), array[j] + s(array, i, j - 1));
    }

    public int s(int[] array, int i, int j) {
        if (i == j) {
            return 0;
        }
        return Math.min(f(array, i + 1, j), f(array, i, j - 1));
    }

    public static void maind(String[] args) {


        SkipGame skipGame = new SkipGame();

        char[] str = "21220121".toCharArray();
        converToStr("21220121");
        char[] chars = str;
        int[] array = new int[chars.length];
        for (int i = 0; i < chars.length; i++) {
            array[i] = Integer.valueOf(chars[i] + "");
        }

        final int i = SkipGame.converToStr2(array);
        System.out.println(i);
    }

    public static int converToStr(String str) {


        final char[] chars = str.toCharArray();
        int[] array = new int[chars.length];
        for (int i = 0; i < chars.length; i++) {
            array[i] = Integer.valueOf(chars[i] + "");
        }
        List<String> res = new ArrayList<>();
        converToStr1(array, 0, "", res);
        for (int i = 0; i < res.size(); i++) {
            System.out.println(res.get(i));
        }
        return res.size();
    }

    public static int converToStr1(int[] array, int i, String res, List<String> resList) {

        //a:97, A:65
        if (i > array.length - 1) {
            //已经超了
            resList.add(res);
            return 1;
        }
        int cur = array[i];

        if (cur == 0) {
            //自身无法用字母表示，且他和后面的元素组合在一起也无法用0表示，因此return
            return -1;//标志这是一种无效的组合方案
        }

        //自身组成一个字母
        //不为0的情况下才可以用字母表示
        char t = (char) (97 - 32 + cur - 1);
        int nums = 0;
        int retV = -1;
        retV = converToStr1(array, i + 1, res + t, resList);
        if (retV != -1) {
            nums += retV;
        }
        //和后面的字母一起组合成一个字符
        if (i + 1 <= array.length - 1) {
            int value = cur * 10 + array[i + 1];
            if (value < 26) {
                t = (char) (97 - 32 + value - 1);
                retV = converToStr1(array, i + 2, res + t, resList);
                if (retV != -1) {
                    nums += retV;
                }
            }
        }
        return nums;
    }


    public static int converToStr2(int[] array) {

        int[] dp = new int[array.length];
        /**
         * dp(i)表示i到-length-1这些元素的组合方案
         * 因此dp(lenght-1)需要看 这个位置是否是0
         */
        dp[array.length - 1] = array[array.length - 1] == 0 ? 0 : 1;

        if (array.length == 1) {
            return dp[0];
        }
        for (int i = array.length - 1 - 1; i >= 0; i--) {
            int num = array[i];
            if (num == 0) {
                dp[i] = 0;
                continue;
            }
            //自身变成字母的时候
            dp[i] = dp[i] + dp[i + 1];
            //和后面的字母一起变成字母的时候
            int value = num * 10 + array[i + 1];
            if (value <= 26) {
                //dp(i+2)!=0表示剩余的数字可以被转换成字母，这个时候才需要将 dp(i+2)累积进去。
                //实际上即使不判断dp(i+2)是否为0， 当dp(i+2)=0累积进去也不会对dp(i)产生影响。
                if (i + 2 <= array.length - 1 && dp[i + 2] != 0) {
                    dp[i] = dp[i] + dp[i + 2];
                } else {
                    //这种情况下表示 i+1是最后一个字母，i和i+1转为一个字符只有一种解决方案。
                    if (i + 2 > array.length - 1) {
                        dp[i] = dp[i] + 1;
                    }
                }
            }
        }
        StringBuilder builder = new StringBuilder();
        for (int q = 0; q < dp.length; q++) {
            builder.append(dp[q] + " ");
        }
        System.out.println(builder.toString());
        return dp[0];
    }


    public static void main2(String[] args) {
        //[[-2,-3,3],[-5,-10,1],[10,30,-5]]
        //int a[][] = {{-2, -3, 3}, {-5, -10, 1}, {10,30,-5}};
        int a[][] = {{100}};

        longWithCity(a);
    }

    ;

    public static int longWithCity(int[][] data) {

        int i = data.length;
        int j = data[0].length;
        int[][] dp = new int[i][j];


        //初始化dp
        if (data[i - 1][j - 1] >= 1) {
            //此时不需要初始血量
            dp[i - 1][j - 1] = 1;
        } else {
            dp[i - 1][j - 1] = 1 - data[i - 1][j - 1];
        }
        /**
         * 最后一行
         */
        for (int k = j - 1 - 1; k >= 0; k--) {
            int incres = data[i - 1][k];
            int res = dp[i - 1][k + 1] - incres;
            if (res > 0) {
                dp[i - 1][k] = res;
            } else {
                dp[i - 1][k] = 1;
            }
        }
        /**
         * 最后一列
         */
        for (int k = i - 1 - 1; k >= 0; k--) {
            int incres = data[k][j - 1];
            int res = dp[k + 1][j - 1] - incres;
            if (res > 0) {
                dp[k][j - 1] = res;
            } else {
                dp[k][j - 1] = 1;
            }
        }


        for (int m = i - 1 - 1; m >= 0; m--) {
            for (int n = j - 1 - 1; n >= 0; n--) {
                /**
                 * 选择一个代价最小的，然后计算当前位置需要的血量
                 */
                int res = Math.min(dp[m + 1][n], dp[m][n + 1]) - data[m][n];
                /**
                 * 假设dp[m + 1][n] 位置需要血量6，而当前位置正好新增6，那么当前位置的血量减去dp[m + 1][n] 就是0
                 * 但是为了保证 当前位置的血量必须是正数 ，因此当前位置必须要有初始血量1，然后累加当前的血量6
                 * dp[m + 1][n]位置消耗了6，可以保证dp[m + 1][n]位置剩余1
                 */
                if (res <= 0) {
                    res = 1;
                }
                dp[m][n] = res;
            }
        }

        for (int m = 0; m < i; m++) {
            StringBuilder builder = new StringBuilder();
            for (int n = 0; n < j; n++) {
                builder.append(" ").append(dp[m][n]);
            }
            System.out.println(builder.toString());

        }
        return dp[0][0];
    }


    public static void mai4n(String[] args) {
        //"cbaccccacbcaaaccccaacbccabba"
        //"babbacacbaabbcccabcaca"
        //"baabbacbacccacacbcabcaaabbaccccccccacabcbccabacaba"
        isInterleave("cbaccccacbcaaaccccaacbccabba", "babbacacbaabbcccabcaca", "baabbacbacccacacbcabcaaabbaccccccccacabcbccabacaba");
    }

    public static boolean isInterleave(String s1, String s2, String s3) {
        if (s3 == null) {
            s3 = "";
        }
        // s3是s1和s2交错组成，此时需要s3的长度s1+s2
        if (s3.length() != s1.length() + s2.length()) {
            return false;
        }
        boolean res = isInterleaveA(s1.toCharArray(), s2.toCharArray(), s3.toCharArray(), 0, 0, 0);
        System.out.println(res);
        return res;
    }

    public static boolean isInterleaveA(char[] s1, char[] s2, char[] s3, int cur, int s1Start, int s2Start) {
        // 如果当前处理 的位置已经大于s3可用位置，那么表示已经匹配了s3中的所有字符
        if (cur >= s3.length) {
            return true;
        }
        char symbol = s3[cur];
        /**
         * 尝试从 s1中寻找这个符号
         */
        boolean find = false;
        for (int i = s1Start; i < s1.length; i++) {
            if (symbol == s1[i]) {
                find = isInterleaveA(s1, s2, s3, cur + 1, i + 1, s2Start);
                if (find) {
                    return find;
                }
            }
        }
        /**
         * 从s2中找
         */
        for (int i = s2Start; i < s2.length; i++) {
            if (symbol == s2[i]) {
                find = isInterleaveA(s1, s2, s3, cur + 1, s1Start, i + 1);
                if (find) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<String> letterCombinations(String digits) {

        Map<Integer, String> map = new HashMap<>();
        map.put(2, "abc");
        map.put(3, "def");
        map.put(4, "ghi");
        map.put(5, "jkl");
        map.put(6, "mno");
        map.put(7, "pqrs");
        map.put(8, "tuv");
        map.put(9, "wxyz");

        List<String> res = new ArrayList<>();
        int[] array = new int[digits.length()];
        for (int i = 0; i < digits.toCharArray().length; i++) {
            array[i] = Integer.valueOf(digits.toCharArray()[i] + "");
        }
        test(array, 0, "", map, res);
        return res;

    }

    public void test(int[] array, int i, String res, Map<Integer, String> map, List<String> resList) {
        if (i >= array.length) {
            if (array.length == 0) {
                return;
            }
            resList.add(res);
            return;
        }
        int value = array[i];
        final String optionalStr = map.get(value);
        for (int k = 0; k < optionalStr.length(); k++) {
            final char targetChar = optionalStr.toCharArray()[k];
            test(array, i + 1, res + targetChar, map, resList);
        }
    }


    public static void mainv(String[] args) {
        System.out.println(romanToInt("MCMXCIV"));
    }

    public static int romanToInt(String s) {

        Map<String, Integer> map = new HashMap<>();
        map.put("I", 1);
        map.put("V", 5);
        map.put("X", 10);
        map.put("L", 50);
        map.put("C", 100);
        map.put("D", 500);
        map.put("M", 1000);
        Map<String, Integer> speciMap = new HashMap<>();
        speciMap.put("IV", 4);
        speciMap.put("IX", 9);
        speciMap.put("XL", 40);
        speciMap.put("XC", 90);
        speciMap.put("CD", 400);
        speciMap.put("CM", 900);
        final int i = romanToIntTest(s.toCharArray(), 0, 0, map, speciMap);
        return i;

    }


    public static int romanToIntTest(char[] array, int pos, int res, Map<String, Integer> map, Map<String, Integer> speciMap) {

        if (pos >= array.length) {
            return res;
        }
        final Iterator<String> iterator = speciMap.keySet().iterator();
        while (iterator.hasNext()) {
            final String key = iterator.next();
            boolean equal = true;
            for (int i = 0; i < key.length(); i++) {
                if (pos + i >= array.length) {
                    equal = false;
                    break;
                }
                if (array[pos + i] != key.toCharArray()[i]) {
                    equal = false;
                    break;
                }
            }
            if (equal) {
                return romanToIntTest(array, pos + key.length(), res + speciMap.get(key), map, speciMap);
            }
        }
        final Iterator<String> mapIt = map.keySet().iterator();
        while (mapIt.hasNext()) {
            final String key = mapIt.next();
            if (array[pos] == key.toCharArray()[0]) {
                return romanToIntTest(array, pos + key.length(), res + map.get(key), map, speciMap);
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        //[1,2,3,2,1]
        //[3,2,1,4,7]
        //[0,0,0,0,1]
        //[1,0,0,0,0]
//        System.out.println("张景瑞");
        int[] a1 = new int[]{0,0,0,0,1};
        int[] a2 = new int[]{1,0,0,0,0};
        System.out.println( findLength(a1, a2));
    }

    static int maxRes = -1;
    public  static int findLength(int[] nums1, int[] nums2) {

        String res = "";
        for (int i = 0; i < nums2.length; i++) {
            res += nums2[i] + "";
        }
        findLengthTest(nums1, 0, nums2, res);

        return maxRes;

    }

    public  static void findLengthTest(int[] nums1, int start, int[] nums2, String num2Str) {

        //start是s1开始的位置
        if (start >= nums1.length) {
            return;
        }
        for (int k = start; k < nums2.length; k++) {
            String tmStr = "";
            for (int i = start; i <= k; i++) {
                tmStr += nums1[i] + "";
            }
            if (num2Str.contains(tmStr)) {
                //将最大长度保存到静态变量中
                if (k - start + 1 > maxRes) {
                    System.out.println("最大：" + tmStr);
                }
                //包含
                maxRes = Math.max(maxRes, k - start + 1);
            }else{
                // num2Str 中不包含 tmStr， 那么 k++ 会导致tmStr更长，这个时候后面 就没必要再遍历了
                break;
            }
        }
        findLengthTest(nums1, start + 1, nums2, num2Str);
    }
}
