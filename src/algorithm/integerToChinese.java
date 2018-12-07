package algorithm;

import java.util.ArrayList;
import java.util.HashMap;

public class integerToChinese {
	public static void main(String[] arg) {

		
		Integer testNum = 12340008;
//		System.out.print(convertIntToString1(testNum));
		System.out.println(convertIntToString2(testNum));
		System.out.println(convertIntToString3(testNum));
	}
	
	
	// Int to Chinese
	// Own, not consider 0000
	@SuppressWarnings("null")
	private static String convertIntToString1(Integer num) {   // O("n")-两个for循环 & 0的处理, T("1")
		HashMap<String, String> numMap = new HashMap<String, String>();
		numMap.put("0", "零");  
		numMap.put("1", "一");  
		numMap.put("2", "二");  
		numMap.put("3", "三"); 
		numMap.put("4", "四"); 
		numMap.put("5", "五"); 
		numMap.put("6", "六"); 
		numMap.put("7", "七"); 
		numMap.put("8", "八"); 
		numMap.put("9", "九"); 
		
		String[] innerString = {"","十","百","千","万","十","百","千","亿","十","百","千"};
		
		String numStr = num.toString();
		int length = num.toString().length();
		StringBuilder builder = new StringBuilder();
		ArrayList<String> strArray = new ArrayList<String>();

		for(int i = length; i > 0; i--) {
			// For '0' in String, '0' cannot be the first character
			if(!numStr.substring(i-1, i).equals("0")) {
				strArray.add(numMap.get(numStr.substring(i-1, i)) + innerString[length-i]);
				System.out.println(strArray.toString());
			} else{
				System.out.println(length-i-1);
				if(!strArray.get(length-i-1).equals("零")){
					strArray.add(numMap.get(numStr.substring(i-1, i)));
					System.out.println(strArray.toString());
				} // else: do nothing`
				//!!! 去除字符串中的连续字符
			}
		}
		
		for(int j = strArray.size()-1;j>=0;j--) {
			builder.append(strArray.get(j));
		}
		
		return builder.toString();
	}
	
	// Own - 改进 
	// 1.不用从倒序排，直接取 units的length位
	// 2.不用hashmap取数值, 由index来实现 number map 汉字
	// 3.判断0，由判断上一位判断下一位
	private static String convertIntToString2(Integer num) {   // O("n")-两个for循环 & 0的处理, T("1")
		char[] numArray = { '零', '一', '二', '三', '四', '五', '六', '七', '八', '九' };
		String[] units = { "", "十", "百", "千", "万", "十", "百", "千", "亿",
				"十", "百", "千", "万" };
		
		StringBuilder sb = new StringBuilder();
		char[] charNum = num.toString().toCharArray();
		int len = charNum.length;
		
		for(int i =0; i < len;i++) {
			int intNum = Integer.valueOf(charNum[i]+"");
			if(intNum == 0) {
				if(Integer.valueOf(charNum[i+1]+"") != 0) {
					sb.append(numArray[intNum]);
				} // else: do nothing
			}else {
				sb.append(numArray[intNum]+units[len-1-i]);
			}
		}
		return sb.toString();
	}

	// From web
	private static String convertIntToString3(Integer num) {
		char[] numArray = { '零', '一', '二', '三', '四', '五', '六', '七', '八', '九' };
		String[] units = { "", "十", "百", "千", "万", "十万", "百万", "千万", "亿",
				"十亿", "百亿", "千亿", "万亿" };
		
			char[] val = String.valueOf(num).toCharArray();
			int len = val.length;

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < len; i++) {
				String m = val[i] + "";
				int n = Integer.valueOf(m);
				boolean isZero = n == 0;
				String unit = units[(len - 1) - i];
				if (isZero) {
					if ('0' == val[i - 1]) {
						//当前val[i]的下一个值val[i-1]为0则不输出零
						continue;
					} else {
						//只有当当前val[i]的下一个值val[i-1]不为0才输出零
						sb.append(numArray[n]);
					}
				} else {
					sb.append(numArray[n]);
					sb.append(unit);
				}
			}
			return sb.toString();
		}
}

// print

// Bullel sort


