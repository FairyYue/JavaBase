package algorithm;

public class LarstInList {
	
	public static int getLarst(int[] intArr) throws Exception {
		if(intArr == null) {
			throw new Exception("Input cannot be null");
		}
		
		int max = intArr[0];
		for(int i = 0; i<intArr.length; i++) {
			if(intArr[i] > max) {
				max = intArr[i];
			}
		}
		return max;
	}

}
