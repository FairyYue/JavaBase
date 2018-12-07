package algorithm.greedyAlgorithm;

import org.testng.annotations.Test;

// 贪心算法 - 最少步骤能满足某个条件，先取最容易达到的最好的选项

public class MoneyCollection {

	
	
	private int[] moneyCollection(int money, int[] values, int[] counts) {
		int len=values.length, remaining = money;
		int[] resultsCount = new int[values.length];
		// Exception
		
		for (int i = len - 1; i > 0; i--) {
			int tempCount = remaining / values[i];
			int actualCount = Math.min(tempCount, counts[i]);
			resultsCount[i] = actualCount;

			remaining -= actualCount * values[i];
			if (remaining == 0)
				break;
		}
		
		
		return resultsCount;
	}
	
	@Test
	public void testMoneyCollection() {
		int[] values = { 1, 2, 5, 10, 20, 50, 100 };
		int[] counts = { 3, 1, 2, 1, 1, 3, 5 };  		//各种面值对应数量集合
		
		int[] result = moneyCollection(442,values,counts);
		
	}
}
