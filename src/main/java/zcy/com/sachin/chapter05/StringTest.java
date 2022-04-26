package zcy.com.sachin.chapter05;

/**
 * @Author Sachin
 * @Date 2022/3/30
 **/
public class StringTest {

    /**
     * 判断两个字符串是否互为变形词
     */

    public boolean isDeformation(String str1, String str2) {

        char[] ch1 = str1.toCharArray();
        char[] ch2 = str2.toCharArray();
        if (str1 == null || str2 == null || str1.length() != str2.length()) {
            return false;
        }
        int[] map = new int[256];
        for (int i = 0; i < ch1.length; i++) {
            map[ch1[i]]++;
        }
        for (int i = 0; i < ch2.length; i++) {
            map[ch2[i]]--;
            if (map[ch2[i]] < 0) {
                return false;
            }

        }

        return true;
    }


    /**
     * 字符串中数字子串的求和
     */

    public int sum(String str) {

        if (str == null || str.length() == 0) {
            return 0;
        }
        char[] arr = str.toCharArray();

        int res=0;
        boolean posi = true;
        int num=0;
        for (int i = 0; i < arr.length;i++) {
            char cur = arr[i];

            /**
             * 以下这种写法是错误的，原因是  比如 -9-1 这个数字 加起来是-10.
             * 但是 程序 首先是-9， 然后遇到- ，此时会将 posi改为正数，然后就变成了 -9+1. 这个时候-9-1被当做了一个值
             *
             * 主要原因是 当出现新的-好的时候没有及时清空
             */
            if (cur == '-') {
                posi = !posi;
                continue;
            }
            if (cur >= '0' && cur <= '9') {
                //数字
                num = num * 10 + (posi ? (cur - '0') : -(cur - '0'));
                continue;
            }
            //清空
            posi=true;
            res += num;
            num=0;
        }
        if (num != 0) {
            res += num;
        }
        return res;

    }

    /**
     * 字符串中数字子串的求和
     */

    public int sum2(String str) {

        if (str == null || str.length() == 0) {
            return 0;
        }
        char[] arr = str.toCharArray();

        int res=0;
        boolean posi = true;
        int num=0;
        for (int i = 0; i < arr.length;i++) {
            char cur = arr[i];

            if (cur >= '0' && cur <= '9') {
                //数字
                num = num * 10 + (posi ? (cur - '0') : -(cur - '0'));
                continue;
            }else{
                /**
                 * 遇到非数字 字符，表示一个数字的结束，需要将num增加到res中。
                 *
                 */
                res += num;
                num=0;

                /**
                 * 对于符号不可以直接清空， 因为 当前的字符可能是 ‘-’ 也就意味着 符号正在被计算。 只有当前符号不是‘-’的时候才可以清空
                 */
                if (cur == '-') {
                    /**
                     * 这个地方不可以直接取反，比如-9-1，这字符串， 当前是第二个 -，那么因为前面是-9，因此 这个时候posi=false,
                     * 如果我们直接根据当前符号是- 就取反，则posi=true了。 显然和-1 符号不正确。 也就是说这个时候需要判断前一个符号。
                     * 也就是说如果前一个符号是非-字符或者数字的情况下 意味着当前‘-’是第一个 负号，则此时posi=false。其他情况下取反
                     */
                    if (i > 1 && arr[i - 1] != '-') {
                        posi=false;
                    }else{
                        posi = !posi;
                    }
                }else{
                    //当前字符不是数字也不是 ‘-’ 则清空posi
                    posi=true;
                }

            }
        }
        if (num != 0) {
            res += num;
        }
        return res;

    }


    public String removeKzeros(String str, int k) {

        if (str == null || k < 1) {
            return str;
        }
        char[] chas = str.toCharArray();

        int count=0;
        int start=-1;
        for (int i = 0; i < chas.length; i++) {

            char cur=chas[i];
            if (cur == '0') {
                start = start == -1 ? i : start;
                count++;
            }else{
                if (count == k) {
                    while (count-- != 0) {
                        chas[start++]=0;
                    }
                }
                count=0;
                start = -1;
            }

        }
        if (count == k) {
            while (count-- != 0) {
                chas[start++] = 0;
            }
        }
        return String.valueOf(chas);
    }


    /**
     * 替换字符串中连续出现的指定字符串
     * @param str
     * @param from
     * @param to
     */
    public void replace(String str, String from, String to) {


    }

    public static void main(String[] args) {
        StringTest test = new StringTest();
        test.rotateWord(null);
    }
    /**
     * 反转字符串
     */
    public void rotateWord(char[] chas) {

        if (chas == null || chas.length == 0) {
            return ;
        }

        reverse(chas, 0, chas.length - 1);
        int l=-1;
        int r=-1;
        /**
         * 单词开始
         */
        boolean word = false;
        for (int i = 0; i < chas.length; i++) {
            if (chas[i] != ' ') {
                if (!word) {
                    l = i;
                    word = true;
                }
                if (word) {
                    r = i;
                }
            }else{
                //空格- 设置单词结束
                word=false;
                if (l < r) {
                    reverse(chas, l,r);
                }
                l=-1;
                r=-1;
            }

        }
        if (l < r) {
            reverse(chas, l,r);
        }

    }

    public void reverse(char[] chas, int start, int end) {
        char tmp=0;
        while (start < end) {
            tmp = chas[start];
            chas[start] = chas[end];
            chas[end] = tmp;
            start++;
            end--;
        }
    }

    public String removeSpace(char[] chas) {
        char[] res = new char[chas.length];
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < chas.length; i++) {

            if (chas[i] != ' ') {
                builder.append(chas[i]);
            }else{
                if (i+1<= chas.length-1&&chas[i + 1] != ' ') {
                    builder.append(" ");
                }
            }
        }
        return builder.toString();
    }

}


