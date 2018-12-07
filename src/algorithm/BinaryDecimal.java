package algorithm;

import org.testng.annotations.Test;

public class BinaryDecimal {
	
	// 十进制和二进制的转化
	// When digit=10, 100+1.0E10 出错
    public int findComplement(int num) {
        int quotient = num,tempQuotient, remainder, tempRemainder;
        int digit=0;
        int inverseNum = 0;
        while(quotient != 0){
            tempQuotient = quotient/2;
            tempRemainder = quotient%2;
            quotient = tempQuotient;
            if(tempRemainder == 1){
                remainder = 0;
            }else{
                remainder =1;
            }
            inverseNum += remainder * Math.pow(10,digit);
            digit++;
        }
        
        int result=0;
        String intStr = Integer.toString(inverseNum);
        int len=intStr.length();
        int i = len-1;
        while(i>=0){
            result += Integer.valueOf(intStr.charAt(i)+"").intValue() * Math.pow(2,len-i-1);
            i--;
        }
        return result;
    }
    
    
    
    @Test
    public void testFindComplement() {
    	findComplement(20161211);
    }
}
