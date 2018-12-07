package algorithm;

public class DigitOperation {
	// // Error!! when pow(10,10), 10 + 1.0E10
	// public int findComplement(int num) {
	// int quotient = num,tempQuotient, remainder, tempRemainder;
	// int digit=0;
	// int inverseNum = 0;
	// while(quotient != 0){
	// tempQuotient = quotient/2;
	// tempRemainder = quotient%2;
	// quotient = tempQuotient;
	// if(tempRemainder == 1){
	// remainder = 0;
	// }else{
	// remainder =1;
	// }
	// inverseNum += remainder * Math.pow(10,digit);
	// digit++;
	// }
	//
	// int result=0;
	// String intStr = Integer.toString(inverseNum);
	// int len=intStr.length();
	// int i = len-1;
	// while(i>=0){
	// result += Integer.valueOf(intStr.charAt(i)+"").intValue() *
	// Math.pow(2,len-i-1);
	// i--;
	// }
	// return result;
	// }

	public int findComplement(int num) {
		return (~num) & ((Integer.highestOneBit(num) << 1) - 1);
		/**
		 * 按照这个解释，00001010算一个补数的方式是，去掉前导的0，得到1010，然后取反，得到0101，也就是101 然后看你的程序，00001010
		 * Integer.highestOneBit(num) 这个得到最高位的1，其余位的1忽略，都是0 也就是00001000
		 * (Integer.highestOneBit(num) << 1 向左移动1位，低位补0 因此得到 00010000 再-1 得到 00001111
		 * 注意这个1111的作用，它相当于过滤器，用and运算可以去掉num的前导0，因为0 and任意数都是0，1 and 1是1，所以用它和~num做
		 * and，前面的1肯定就过滤了。 而~num按照题目的意思，就是取反了。 取反+去掉前导0，就是结果，这是题目规定的
		 * 
		 */

	}

}
