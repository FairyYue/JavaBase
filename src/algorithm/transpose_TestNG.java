package algorithm;

import static org.testng.Assert.ARRAY_MISMATCH_TEMPLATE;
import static org.testng.Assert.assertEquals;

import java.util.logging.Logger;

import javax.xml.soap.Text;

import org.testng.annotations.Test;

public class transpose_TestNG {
	
	private static int[][] transpose(int[][] A) {
		Logger.getLogger("Test2");
        if(A == null){
            return null;
        }
        
        // 暴力法
        int[][] result = new int[A[0].length][A.length];
        for(int i = 0;i<A.length;i++){
            for(int j=0;j<A[0].length;j++){
                result[j][i] = A[i][j];
            }
        }
        return result;
	}

	@Test
	// Test: general
	public void testGeneral() {
		int[][] test = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
		int[][] expected = { { 1, 4, 7 }, { 2, 5, 8 }, { 3, 6, 9 } };
		int[][] result = transpose(test);
		int i = 0;
		while (i < result.length) {
			assertEquals(result[i], expected[i]);
			i++;
		}
	}
	
	@Test
	// Test: Random
	public void testRandom() {
		int[][] test = { { 1, 2 }, { 3,4 }, { 5,6} };
		int[][] result = transpose(test);
		
		// Verify column and row
		assertEquals(test.length, result[0].length);
		assertEquals(test[0].length, result.length);
		
		// Verify random column
		int randomNo = (int)(Math.random()*test[0].length);
		int expected[] = new int[test.length];
		for(int i=0; i<test.length; i++){
			expected[i] = test[i][randomNo];
		}
		
		System.out.println("Random: " + randomNo);
		System.out.println("Actual:" + result[randomNo].toString()+" , Expected:" + expected.toString());
		
		assertEquals(result[randomNo], expected);
	}

	// Test: null
	// Test: Random
	// Test： Thread
	// Test: Large data

}
