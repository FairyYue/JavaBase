package algorithm;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

public class PrintZ06 {

	// Cover Ali - Own
	public static ArrayList<String> printN(char[] charArr, int row) throws Exception {
		ArrayList<String> strArr = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		System.out.println("\n Input a new N as below. Row is:" + row);
		if (charArr == null || row <= 0) {
			throw new Exception("Please input valid array and row");
		}

		if (row == 1) {
			sb.setLength(0);
			for (char c : charArr) {
				sb.append(c + " ");
			}
			System.out.println(sb.toString());
			strArr.add(sb.toString().trim());
			return strArr;
		}

		final int columnGap = row - 2, len = charArr.length;
		int index, secondIndex, times = len / (row + columnGap);
		if (Math.round(len / (row + columnGap)) != 0)
			times += 1;

		for (int i = 0; i < row; i++) {
			sb.setLength(0);
			for (int j = 0; j < times; j++) {
				index = i + j * (row + columnGap);
				if (index > len - 1)
					break;
				sb.append(charArr[index] + " ");
				if (i == 0 || i == row - 1) { // print first and last row
					for (int k = columnGap; k > 0; k--) {
						sb.append("  ");
					}
				} else { // print other row
					secondIndex = index + 2 * (row - i - 1);
					if (secondIndex >= len)
						break;
					for (int k = columnGap - i; k > 0; k--) {
						sb.append("  ");
					}
					sb.append(charArr[secondIndex] + " ");
					for (int k = i - 1; k > 0; k--) {
						sb.append("  ");
					}
				}
			}
			System.out.println(sb.toString());
			strArr.add(sb.toString().trim());
		}
		return strArr;
	}

	public void printZ0918(String str, int row) throws Exception {
		int len = str.length(), gap = row - 2,
				group = len % (row + gap) == 0 ? len / (row + gap) : len / (row + gap) + 1;
		// gap=row-2, firstIndex = i+j*(row+gap)
		// 前： gap-i; secondIndex=firstIndex+2*(row-1-i); 后：i-1  -- 可从递增、递减趋势判断他们的关联
		//		& 注意index<char[].length
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < group; j++) {
				int firstIndex = i + j * (row + gap);
				char firstValue = str.charAt(firstIndex);
				print(firstValue);
				if (i == 0 || i == row - 1) {
					if (firstIndex >= len)
						break;
					for (int z = 0; z < gap; z++) {
						print(' ');
					}
				} else {

					for (int z = 0; z < gap - i; z++) {
						print(' ');
					}

					int secondIndex = firstIndex + 2 * (row - 1 - i);
					if (secondIndex >= len)
						break;
					char secondValue = str.charAt(secondIndex);
					print(secondValue);

					for (int z = 0; z < i - 1; z++) {
						print(' ');
					}
				}
			}
			System.out.print("\n");
		}
	}

	// Own
	// Time(O(n)), n = row * group, Space(O(1))  - StringBuilder都是对对象本身进行操作，不会生成新的字符串对象
	public String convertZ(String s, int numRows) {
		if (numRows == 0) {
			return null;
		}
		if (numRows == 1) {
			return s;
		}

		StringBuilder sb = new StringBuilder();
		int len = s.length(), gap = numRows - 2,
				groups = len % (numRows + gap) == 0 ? len / (numRows + gap) : len / (numRows + gap) + 1;
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < groups; j++) {
				int firstIndex = i + j * (numRows + gap);
				if (firstIndex >= len)
					break;
				sb.append(s.charAt(firstIndex));
				if (i == 0 || i == numRows - 1) {
					// Nothing
				} else {
					int secondIndex = firstIndex + 2 * (numRows - 1 - i);
					if (secondIndex >= len)
						break;
					sb.append(s.charAt(secondIndex));
				}
			}
		}
		return sb.toString();
	}


	// Enhance1: 依次将字符加入不同行
	// Time - O(n),n=s.length, Space - O(n),n=rows
	public String convertZ2(String s, int numRows) {

		if (numRows == 1)
			return s;

		List<StringBuilder> rows = new ArrayList<>();
		for (int i = 0; i < Math.min(numRows, s.length()); i++)
			rows.add(new StringBuilder());

		int curRow = 0;
		boolean goingDown = false;

		for (char c : s.toCharArray()) {
			rows.get(curRow).append(c);
			if (curRow == 0 || curRow == numRows - 1)
				goingDown = !goingDown;
			curRow += goingDown ? 1 : -1;
		}

		StringBuilder ret = new StringBuilder();
		for (StringBuilder row : rows)
			ret.append(row);
		return ret.toString();
	}
	

	private void print(char c) {
		String string = c + " ";
		System.out.print(string);
	}

	@Test
	public void testPrintZ() throws Exception {
		printZ0918("0123456789abcdefg", 4);
		convertZ2("0123456789abcdefg", 4);
	}
	
	/* 
	 * 测试
	 * 传入（特殊/null, 小于group，大于group）
	 * 随机 - 验row, 验第一行， 验某一行数据
	 * 并发 - 没有影响
	 * 反逻辑 - 按照规则循环取值
	 * 根据它随机测某个数在不在 预想的范围内
	 * 
	 */
	

}
