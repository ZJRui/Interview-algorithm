package zcy.com.sachin.chapter01;

import java.util.Stack;

/**
 *
 *
 * 设计一个具有getmIn功能的栈
 * @Author Sachin
 * @Date 2022/3/28
 *
 **/
public class MyStack1 {

    private Stack<Integer> stackData;
    private Stack<Integer> stackMin;

    public MyStack1(){
        this.stackData = new Stack<>();
        this.stackMin = new Stack<>();
    }

    public void push(int newNum) {
        /**
         * 如果最小的栈中没有元素，则将当前值放入
         */
        if (this.stackMin.isEmpty()) {
            this.stackMin.push(newNum);
        } else if (newNum < this.getMin()) {
            //压如当前 newNum作为最小值
            this.stackMin.push(newNum);
        }else{
            //压入 stackMin栈顶的值作为最小值
            this.stackMin.push(this.stackMin.peek());
        }
        this.stackData.push(newNum);
    }

    public int pop() {
        if (this.stackData.isEmpty()) {
            throw new RuntimeException("error");
        }
        this.stackMin.pop();
        return this.stackData.pop();
    }

    public int getMin() {
        if (this.stackMin.isEmpty()) {
            throw new RuntimeException("error");
        }
        return this.stackMin.peek();
    }


    /**
     * 用一个栈实现另一个栈的排序
     */
    public static void sortStackByStack(Stack<Integer> stackData) {
        Stack<Integer> help = new Stack<>();


        while (!stackData.isEmpty()) {
            final Integer pop = stackData.pop();
            while (!help.isEmpty() && help.peek() > pop) {
                stackData.push(help.peek());
            }
            help.push(pop);
        }
        while (!help.isEmpty()) {
            stackData.push(help.pop());
        }

    }


}
