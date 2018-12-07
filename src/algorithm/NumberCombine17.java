package algorithm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.testng.annotations.Test;

public class NumberCombine17 {

	// Own
	@Test
	public void testLetterCombinations() {
		// List<String> test = letterCombinations("234");
		List<String> test = letterCombinations2("234");
	}

	final String letterCollection = "abcdefghijklmnopqlstuvwxyz";

	public List<String> letterCombinations(String digits) {
		int len = digits.length();
		List<String> tempArray = new ArrayList(), resultArray = new ArrayList();
		if (len == 0) {
			return resultArray;
		}

		char[] firstChar = getLetter(digits.charAt(0));
		for (char c : firstChar)
			resultArray.add(c + "");
		for (int i = 1; i < len; i++) {
			char[] tempChar = getLetter(digits.charAt(i));
			for (String str : resultArray)
				for (char c : tempChar) {
					tempArray.add(str + c);
				}
			resultArray.clear();
			resultArray.addAll(tempArray);
			tempArray.clear();
			// resultArray = tempArray; //!! Wrong, 都会被清理到
			// tempArray.clear();
		}

		return resultArray;
	}

	private char[] getLetter(char ch) {
		int num = Integer.parseInt(ch + "");
		// int num1 = Integer.valueOf(ch).intValue(); // Wrong, 会得到char的Ascii码值

		char[] result;
		String str = "";
		if (num < 2 || num > 9) {
			// throw exception.
			return null;
		}

		if (num < 7) {
			int startIndex = 3 * (num - 2);
			str = letterCollection.substring(startIndex, startIndex + 3);
		} else {
			switch (num) {
			case 7:
				// str="pqrs"; // Wrong, break is needed.
				str = "pqrs";
				break;
			case 8:
				str = "tuv";
				break;
			case 9:
				str = "wxyz";
				break;
			}
		}
		return str.toCharArray();
	}

	// String[] ans = {"","","abc","def","ghi","jkl","mno","pqrs","tuv","wxyz"};
	// //1. 对应1,2,3的可以用 数据等 i来取得，不需要专门转化

	// 用递归的思想
	public List<String> letterCombinations2(String digits) {
		List<String> list = new ArrayList<String>();
		if (!digits.isEmpty()) {
			helper(digits, "", 0, list);
		}
		return list;
	}

	public void helper(String digits, String combo, int position, List<String> list) {
		if (position == digits.length()) {
			list.add(combo);
			return;
		}
		String[] letters = getMapping(digits.charAt(position));
		for (int i = 0; i < letters.length; i++) {
			helper(digits, combo + letters[i], position + 1, list);
		}
	}

	public String[] getMapping(char c) {
		switch (c) {
		case '2':
			return new String[] { "a", "b", "c" };
		case '3':
			return new String[] { "d", "e", "f" };
		case '4':
			return new String[] { "g", "h", "i" };
		case '5':
			return new String[] { "j", "k", "l" };
		case '6':
			return new String[] { "m", "n", "o" };
		case '7':
			return new String[] { "p", "q", "r", "s" };
		case '8':
			return new String[] { "t", "u", "v" };
		case '9':
			return new String[] { "w", "x", "y", "z" };
		}
		return new String[] {};
	}
}
