package algorithm;

public class printBaseOnRow {

	public static void main(String[] args) {
		print(3);
		print2(5);
		print2(7);
	}

	private static void print(int row) {  // O(n2), T(1)
		int center = (row + 1) / 2;

		for (int i = 1; i <= center; i++) {
			for (int j = 1; j <= center - i; j++) {System.out.print(" ");}
			for (int k = 1; k <= i * 2 - 1; k++) {System.out.print("*");}
			System.out.print("\n");
		}

		for (int i = row - center; i >= 0; i--) {
			for (int j = 1; j <= center - i; j++) {System.out.print(" ");}
			for (int k = 1; k <= i * 2 - 1; k++) {System.out.print("*");}
			System.out.print("\n");
		}
	}
	
	private static void print2(int row) { // Optimize
		System.out.println("Print for:" + row);
//		Map<Integer, Integer[]> map = new HashMap<>();
		int center = (row+1)/2;
		for(int i = 1; i <= row;i++) {
			int numEmpty = Math.abs(center -i) ,numXing;
			if(i <= center) {
				numXing = 2*i-1;
			}else {
				numXing = row - (i-center)*2;
			}
//			map.put(i, new Integer[] {numEmpty,numXing});
			for(int j = 1; j <= numEmpty;j++) {System.out.print(" ");}
			for(int k = 1; k <= numXing;k++) {System.out.print("*");}
			System.out.print("\n");
		}
	}
}
