package Basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;

import org.testng.annotations.Test;

import algorithm.integerToChinese;

public class JavaCollection extends FairyBase {
	

	private void arrayList() {
		ArrayList<Integer> arrayList = new ArrayList<>();
		ArrayList<Integer> arrayList2 = new ArrayList<>();
		
		arrayList.add(0);
		arrayList.add(1);
		
		for(Integer num:arrayList) {}
		Integer[] converToArr = {1,2};
		arrayList2 = (ArrayList<Integer>) Arrays.asList(converToArr); // '=' 只是改变指针位置
		
		arrayList.size();
		arrayList.equals(arrayList2);
		
		// Arraylist的遍历
		// 遍历1
		Iterator<Integer> iterator = arrayList.iterator();
		while(iterator.hasNext()){
			println(iterator.next());
		}
		
		// 遍历2
		for(int temp:arrayList) {
			println(temp);
		}
		
		// 遍历3
		for(int i = 0;i<arrayList.size();i++){
			println(arrayList.get(i));
		}
		
//		arrayList2 = arrayList;  //!!! Wrong，此时 arrayList2 和 arraylist 的值都为[],因为 arraylist 赋值是把point指针指向了arraylist
		arrayList2.addAll(arrayList);
		arrayList.clear();
	}
	

	private void string() {
		String string = new String();
		
		string = "0123456789";
		
		string.length();
		println(string.substring(9, 10));  //9
		println(string.substring(0, 3));  //012
		println(string.charAt(3)+"");  //3
		string.toCharArray();
		
		// String Buffer
		StringBuilder sb = new StringBuilder(string);	                  
		String afterreverse = sb.reverse().toString();
		
		//清空
		sb.delete(0, sb.length());
		sb.setLength(0);
	}
	
	private void math() {
		Math.max(1, 2);
		int a = (int)(Math.random()*100+1);
		
		Math.pow(3, 2); // 3^2
	}
	
	@Test
	private void intArray() {
		int[][] result = new int[2][];  // Row number is needed
		int[] intArray = {1,2,4,6,8,10};
		
		int length = intArray.length;
		int[] copyArray = Arrays.copyOfRange(intArray, 0, 2);  // {1,2}
		int[] copyArray2 = Arrays.copyOfRange(intArray, 3, intArray.length);  // {6,,8,10}
		println(Arrays.binarySearch(intArray,4));
		println(Arrays.binarySearch(intArray,5));  // not found, return 插入点 -4
		println(Arrays.binarySearch(intArray,9));  // not found, return 插入点 -6
		println(Arrays.binarySearch(intArray,0));  // not found, return 插入点 -1
		
		for(int i:intArray) {
			
		}
		
	}
	
	private void stack() {
		Stack<Integer> stack = new Stack<>();
		stack.push(1);
		stack.isEmpty();
		stack.size();
		stack.get(1);
		stack.pop();
		stack.firstElement();
		stack.peek();   // top element	
	}
	
	private void intOperation() {
		int max = Integer.MAX_VALUE;
		int min = Integer.MIN_VALUE;
	
		//String int 转化
		String str="1";
		int a;
		a = Integer.valueOf(str).intValue();
		str = Integer.toString(a);
		
//		a^3;  // 不是a的三次方， ^异位运算
		Math.pow(a, 2);
		
		int num =5;
		switch(num) {
		case 1:
			;
		case 2:
			;
//		case num<7:  //Error
			
		}
				
	}
	

	private void charOperation() {
		Character character;
		char a;
		
		// int 和 char 相互转化
		int i_wrong = '9';
		println(i_wrong);    // Wrong, 转化为 ascii中的57
		
		char c = 9+'0';
		println(c);
		
		int i = Integer.parseInt('9'+"");
		println(i);
		
	}
	
	/*
	 * &：按位与。 同1为1
	 * 
	 * |：按位或。 同0为0
	 * 
	 * ~：按位非。   取反
	 * 
	 * ^：按位异或。  异1同0
	 * 
	 * <<：左位移运算符。 向左  00001000 <<. 00010000
	 * 
	 * >>：右位移运算符。 向右  
	 * 
	 * <<<：无符号右移运算符。??
	 */
	private void digit() {
		int num = 5;
		int complement = (~num) & ((Integer.highestOneBit(num) << 1) - 1);
		/**
		 * 按照这个解释，00001010算一个补数的方式是，去掉前导的0，得到1010，然后取反，得到0101，也就是101 然后看你的程序，00001010
		 * Integer.highestOneBit(num) 这个得到最高位的1，其余位的1忽略，都是0 也就是00001000
		 * (Integer.highestOneBit(num) << 1 向左移动1位，低位补0 因此得到 00010000 再-1 得到 00001111
		 * 注意这个1111的作用，它相当于过滤器，用and运算可以去掉num的前导0，因为0 and任意数都是0，1 and 1是1，所以用它和~num做
		 * and，前面的1肯定就过滤了。 而~num按照题目的意思，就是取反了。 取反+去掉前导0，就是结果，这是题目规定的
		 * 
		 */
		
		// 计算是不是2的幂次(只有一个1)，  计算1的count
		int count=0;
        while(num!=0){
            count+=(num & 1);
            num>>=1;
        }
        
        // 计算是不是2的幂次(最高位为1)，
        boolean is2 = (num&(num-1))==0?true:false;
    	
	}
	
	
	private void javaStructure() {
		int num=7;
		String str;
		// Wrong, no break, it will run to last
//        switch(num){
//        case 7:
//            str="pqrs";
//        case 8:
//            str="tuv";
//            }
       switch(num){
        case 7:
            str="pqrs";
            break;
        case 8:
            str="tuv";
            break;
            }
       
       int i=0;
//       for(i;i<5;i++) {  // As below
//    	   ;
//       }
       for(;i<5;i++) {
    	   ;
       }
	}
	

	private void sort() {
		int[] array= {4,2,8,10};
		List<Integer> list = new ArrayList();
		list.add(2);
		list.add(-2);
		Arrays.sort(array); 
		Collections.sort(list); 
		Collections.sort(list,Comparator.reverseOrder());  // 递减排序
	}
	
	private void hash() {
		Map<Integer, String> map = new HashMap<>();
		Set<Integer> set = new HashSet<>();
		
		set.add(5);
		boolean isContains = set.contains(5);
		set.iterator();
//		set.getClass();    // Hashset 不能取出某个特殊对象的值
		
		map.get(5);
		map.size();
		map.containsKey(5);
		map.put(1, "");
		
        for(Entry<Integer, String> entry:map.entrySet()) {
            if(entry.getValue() != "") {
                int result = entry.getKey();
            }
        }
	}
	
	// Vector和List区别：Vector是线程安全的， 
	// https://www.cnblogs.com/efforts-will-be-lucky/p/7053666.html
	private void vector() {
		Vector<Integer> vector = new Vector<>();
		vector.isEmpty();
		vector.addElement(1);
		vector.remove(vector.firstElement());   //FIFO
		vector.lastElement();
		
		
		Vector v=new Vector<>();
		v.add(1);
		v.size();
	}
	
	private void linkedList() {
		List<Integer> linkedlist= new LinkedList<Integer>();
		linkedlist.add(1);
		linkedlist.get(1);
		linkedlist.remove(1);
		
		Queue<Integer> queue = new LinkedList<Integer>();
		queue.offer(1);  // 满-false
		queue.add(1);   // 满-报错
		queue.peek();     // 查询头部数据， 空-null 
		queue.element();  // 空-报错
		queue.poll();  // 删除第一个元素， 空-null
		queue.remove(); // 空-报错
	}
	
	/*
	 * Summary  -- length
	 * int[].length
	 * String.length();
	 * List.size();  - stack,linkedList ,Vector, Map
	 * 
	 */
}
