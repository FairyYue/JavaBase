package algorithm;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.testng.annotations.Test;

public class printZ_TestNG {
	char[] charArr= {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e'};
	Map<Integer, ArrayList<String>> resultCache = new HashMap<>();
	

	@Test
	// 常规测试
	public void testPrintN() throws Exception {
		ArrayList<String> strArr = new ArrayList<>();
		strArr.add("0       8");
		strArr.add("1     7 9");
		strArr.add("2   6   a   e");
		strArr.add("3 5     b d");
		strArr.add("4       c");
		
		ArrayList<String> actArr = PrintZ06.printN(charArr, 5);
		assertEquals(strArr, actArr);
		
		PrintZ06.printN(charArr, 4);
		PrintZ06.printN(charArr, 3);
		PrintZ06.printN(charArr, 2);
		PrintZ06.printN(charArr, 1);
	}
	
	@Test(expectedExceptions = Exception.class)
	// Error测试
	public void testPrintN_Error() throws Exception {	
		PrintZ06.printN(charArr, 0);
		PrintZ06.printN(charArr, -1);
		PrintZ06.printN(new char[] {}, -1);
		PrintZ06.printN(new char[] {}, 5);
	}
	
	@Test(invocationCount = 5,threadPoolSize = 2)
	// Other ： 特殊测试， cover Ali
	public void testPrintN_Extend() throws Exception {
		testPrintN_Extendmethod(5);
	}
	
	private void testPrintN_Extendmethod(int row) throws Exception {
		Random random = new Random();
		int randomRow = random.nextInt(row);
		
		ArrayList<String> actArr;
		if(!resultCache.containsKey(row)) {
			resultCache.put(row, PrintZ06.printN(charArr, row));
			
		} 
		actArr = getResultCache(row);

		String targetStr = actArr.get(randomRow);
		System.out.println("RandomRow:" + randomRow +". TargetString:'" + targetStr+"'");
		
		// Verify the String value
		final int columnGap = row-2,len = charArr.length;
		int index,secondIndex,times = len/(row + columnGap);
		
		for (int j = 0;j < times;j++) {
			index = randomRow + j*(row + columnGap);
			assertEquals(targetStr.charAt(2*(j*(columnGap+1))),charArr[index]);
			
			if(randomRow == 0 || randomRow == charArr.length-1) {
				// No other char
			} else {
				secondIndex = index + 2 * (row - randomRow - 1);
				assertEquals(targetStr.charAt(2*(row - randomRow-1)),charArr[secondIndex]);
			}
		}
	}
	
	private ArrayList<String> getResultCache(int row){
		System.out.println("Get result from Cache in: Row is " + row);
		return resultCache.get(row);
	}
}
