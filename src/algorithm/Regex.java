package algorithm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
	/*
	 * For 正则表达式
	 */

	public static void main(String[] args) {
		
		// 用法一： Pattern  - Pattern.matches(regex, targetObject)
	    String content = "I am noob " +
	    	        "from runoob.com."; 
	    String pattern = ".*runoob.*";
	    
	    boolean isMatch = Pattern.matches(pattern, content);
	    System.out.println("字符串中是否包含了 'runoob' 子字符串? " + isMatch);
	    
	    
	    // 用法二：
	    String line = "This order was placed for QT3000! OK?";
	    String pattern2 = "(\\D*)(\\d+)(.*)";
	 
	      // 创建 Pattern 对象
	    Pattern r = Pattern.compile(pattern2);
	 
	      // 现在创建 matcher 对象
	    Matcher m = r.matcher(line);
	    if (m.find( )) {
	         System.out.println("Found value: " + m.group(0) );
	         System.out.println("Found value: " + m.group(1) );
	         System.out.println("Found value: " + m.group(2) );
	         System.out.println("Found value: " + m.group(3) ); 
	    } else {
	         System.out.println("NO MATCH");
	    }
	}
	
	
	
	

}
