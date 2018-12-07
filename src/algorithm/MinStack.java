package algorithm;

import static org.testng.Assert.assertEquals;

import java.util.Stack;

import org.testng.annotations.Test;

public class MinStack {
private Stack<Integer> stack;
    
    private Stack<Integer> minStack;

    /** initialize your data structure here. */
    public MinStack() {
        stack= new Stack();
        minStack= new Stack();
    }
    
    public void push(int x) {
        if(minStack.isEmpty() || x <= minStack.peek()) {
            minStack.push(x);
        }
        stack.push(x);
    }
    
    public void pop() {
        if(stack.peek() == minStack.peek()) {
            minStack.pop();
        }
        stack.pop();
    }
    
    public int top() {
        return stack.peek();
    }
    
    public int getMin() {
        return minStack.peek();
    }
    
    // 把当前值大的数没加入栈内，把当前值挪走怎么半 - 不会，因为当前值为入栈第一个值，当挪到它说栈里已经为空
    @Test
    public void testMinStack() {
    	MinStack minStack = new MinStack();
    	minStack.push(-2);
    	minStack.push(0);
    	minStack.push(-3);
    	assertEquals(-3, minStack.getMin());   //--> 返回 -3.
    	minStack.pop();
    	assertEquals(0, minStack.top());      //--> 返回 0.
    	assertEquals(-2, minStack.getMin());   //--> 返回 -2.
    }
}
