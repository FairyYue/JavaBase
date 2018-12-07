package algorithm;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class GCDAndLCM_TestNG {
	

	
	//最小公倍数和最大公约数
	List<Integer> divisors = new ArrayList<Integer>();
	List<Integer> remainders = new ArrayList<Integer>();
	
	private int[] getGCPAndLCM(int[] twoInt) throws Exception {
		System.out.println("执行 ： "+twoInt[0] +" - "+twoInt[1]);
		divisors.clear();
		return getGCPAndLCMHelper(twoInt);
	}
	
	// return GCD and LCM
	private int[] getGCPAndLCMHelper(int[] twoInt) throws Exception {
		remainders.clear();
		if(2 != twoInt.length || 0 == twoInt.length || null == twoInt) {
			throw new Exception("Inpurt valid value");
		}
		
		int[] result = new int[2];
		int min = Math.min(twoInt[0],twoInt[1]),max = Math.max(twoInt[0],twoInt[1]);
		if(1 == min || 0 == max%min) {
			return new int[] {min, max};
		}
		
		int divisor = 2 , gcd = 1, lcm;
		while(divisor < min) {
			if(0 == min%divisor && 0 == max%divisor) {
				divisors.add(divisor);
				break;
			} else {
				divisor++;
			}
		}
		if(divisor == min) {
			remainders.add(min);
			remainders.add(max);
			
			for(int i:divisors) {
				gcd *= i;
			}
			lcm = gcd * remainders.get(0) * remainders.get(1);
			result[0] = gcd;
			result[1] = lcm;
			return result;
		}
		return getGCPAndLCMHelper(new int[] {min/divisor,max/divisor});
	}
	// Time： O(min +  log min), Space:O(1 - 2 + 2 + divisor);
	// Others: 辗转相处法求最大公约数， 两数乘积/最大公约数得最小公倍数
	
	@Test(dataProvider = "twoInt")
	public void testGCDAndLCM(int[] input, int[] output) throws Exception{
		Assert.assertEquals(getGCPAndLCM(input), output);
	}
	
	@Test(dataProvider = "twoInt",expectedExceptions = Exception.class)
	public void testGCDAndLCMThrowError(int[] input, int[] output) throws Exception{
		Assert.assertEquals(getGCPAndLCM(input), output);
	}
	
	
	
	@DataProvider(name = "twoInt")
    public Object[][] provideData(Method method) {
		
        if (method.getName().equals("testGCDAndLCM")) {
        	return new Object[][] {
        		// General
        		{new int[] {1,5}, new int[] {1,5}},
        		{new int[] {3,5}, new int[] {1,15}},
        		{new int[] {4,6}, new int[] {2,12}},
        		{new int[] {2,6}, new int[] {2,6}},
        		{new int[] {3,18}, new int[] {3,18}},
        		{new int[] {12,18}, new int[] {6,36}},      		
        	};
        } else if(method.getName().equals("testGCDAndLCMThrowError")) {
        	return new Object[][] {
        		// Not general
//        		{new int[] {}},
//        		{new int[] {1}},
//        		{new int[] {1, 2, 3}},
        		
        	};
        }
        return null;
    }
	
	// 测异常
	
}
