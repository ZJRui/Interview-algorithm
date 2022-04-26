package zcy.com.sachin.chapter02;

/**
 * @Author Sachin
 * @Date 2022/3/30
 **/
public class SortAlag {


    /**
     * 选择排序，
     */
    public static void selectSort(int[] data) {

        int N = data.length;
        for (int i = 0; i < N; i++) {
            /**
             * 对于选择排序的第i个位置， 应该放入  i...N-1 位置上的最小元素
             *
             * 我们假定当前位置 i是最小元素， 因此那么就需要 遍历 i+1...N-1 确定 i位置是否是最小的， 其是 也就是使用min记录最小元素的位置
             * 然后 交换 i和min元素
             */

            //将 data[i] 和 data[i+1..N]中的最小元素交换
            //标记最小值的下标
            int min = i;
            for (int j = i + 1; j < N; j++) {
                if (data[j] < data[min]) {
                    min = j;
                }
            }
            int tmp = data[i];
            data[i] = data[min];
            data[min] = tmp;
        }

    }

}
