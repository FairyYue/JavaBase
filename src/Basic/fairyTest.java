package Basic;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.testng.annotations.Test;

public class fairyTest extends FairyBase{
	@Test
	public void runTest() {
		Boolean result;
		String s1 = "true";
		System.setProperty(s1,"false");
		result = Boolean.getBoolean(s1);
		result = Boolean.valueOf(s1);
		
		String s2 = "true";
		result = Boolean.getBoolean(s2);
		result = Boolean.valueOf(s2);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String printZ(String s,int numRows) {
		if (numRows == 1) return s;

        List<StringBuilder> rows = new ArrayList<>();
        for (int i = 0; i < Math.min(numRows, s.length()); i++)
            rows.add(new StringBuilder());

        int curRow = 0;
        boolean goingDown = false;

        for (char c : s.toCharArray()) {
            rows.get(curRow).append(c);
            if (curRow == 0 || curRow == numRows - 1) {
            	goingDown = !goingDown;
            }
            curRow += goingDown ? 1 : -1;
        }

        StringBuilder ret = new StringBuilder();
        for (StringBuilder row : rows) ret.append(row);
        return ret.toString();
	}
    public int lengthOfLongestSubstring(String s) {
        //https://blog.csdn.net/ccccc1997/article/details/81268248
        if(s.length()==0) return 0;
        
        // 方法1暴力法，穷举所有数组
        
        // 方法2 双指针
        int n = s.length();
        int res = 0;
        int end=0,start=0;
        Set<Character> set=new HashSet<>();
        while(start<n && end<n){
           if(set.contains(s.charAt(end))){
               set.remove(s.charAt(start++));
           }else{
               set.add(s.charAt(end++));
               res=Math.max(res,end-start);
           }
            
        }
        return res;
    }

	
//	public List<String> findAndReplacePattern(String[] words, String pattern) {
//        List<String> result = new ArrayList<>();
//        char[] patternChars = pattern.toCharArray();
//        for (String word : words) {
//            char[] wordChars = word.toCharArray();
//            //是否匹配
//            boolean match = true;
//            //用于存储映射关系的Map
//            Map<Character, Character> map = new HashMap();
//            for (int i = 0; i < patternChars.length; i++) {
//                char p = patternChars[i];
//                char w = wordChars[i];
//                if (map.containsKey(p)) {//模式中的字母是否已经映射已经被映射
//                    char value = map.get(p);
//                    if (value != w) {
//                        match = false;
//                        break;
//                    }
//                } else {
//                    if(map.containsValue(w)){//判断单词中的字母是否被映射，防止模式多个字母都映射单词中的同一个字母
//                        match = false;
//                        break;
//                    }else {
//                        map.put(p, w);
//                    }
//                }
//            }
//            if (match) {
//                result.add(word);
//            }
//        }
//        return result;
//	}

//	Map<String,String> longUrlToShortUrl = new HashMap<>();
//	Map<String,String> shortUrlToLongUrl = new HashMap<>();
// 
//	private static final String code = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
//	
//	// Encodes a URL to a shortened URL.
//    public String encode(String longUrl) {
//    	
//    	String encode=null;
//    	if(longUrlToShortUrl.get(longUrl)!=null)
//    	{
//    		return longUrlToShortUrl.get(longUrl);
//    	}
//    	else
//    	{
//    		encode = encodeUrl();
//    		while(shortUrlToLongUrl.get(encode)!=null)
//    		{
//    			encode = encodeUrl();
//    		}
//    		longUrlToShortUrl.put(longUrl,encode);
//    		shortUrlToLongUrl.put(encode, longUrl);
//    		return encode;	
//    	}
//    }
// 
//    public static String encodeUrl(){
//    	String encode="";
//		for(int i=0; i<6; i++)
//		{
//			String str = String.valueOf(code.charAt((int)(Math.random()*61)));
//			encode = encode + str;
//		}
//		return encode;
//    }
//    
//    // Decodes a shortened URL to its original URL.
//    public String decode(String shortUrl) {
//    	String decode = shortUrlToLongUrl.get(shortUrl);
//    	return decode == null ? "" : decode;
//    }

    // 回溯算法
//	public List<List<Integer>> subsets(int[] nums) {
//        List<List<Integer>> res = new ArrayList<List<Integer>>();
//        List<Integer> temp = new ArrayList<Integer>();
//        dfs(res, temp, nums, 0);
//        return res;
//    }
//    private void dfs(List<List<Integer>> res, List<Integer> temp, int[] nums, int j) {
//        res.add(new ArrayList<Integer>(temp));
//        for(int i = j; i < nums.length; i++) {
//            temp.add(nums[i]);  //① 加入 nums[i]
//            dfs(res, temp, nums, i + 1);  //② 递归
//            temp.remove(temp.size() - 1);  //③ 移除 nums[i]
//        }
//    }
    
//    // 非递归算法
//    public List<List<Integer>> subsets(int[] nums) {
//        List<List<Integer>> res = new ArrayList<List<Integer>>();
//        res.add(new ArrayList<Integer>());
//        for (int num : nums) {  // ①从数组中取出每个元素
//            int size = res.size();
//            for (int i = 0; i < size; i++) {
//                List<Integer> temp = new ArrayList<>(res.get(i));  // ②逐一取出中间结果集
//                temp.add(num);  // ③将 num 放入中间结果集
//                res.add(temp);  // ④加入到结果集中
//            }
//        }
//        return res;
//    }
//    
//    
////    // 回溯算法  --- Fairy check!!
////    // https://blog.csdn.net/happyaaaaaaaaaaa/article/details/51604217
//
//        public List<List<Integer>> subsets(int[] nums) {
//            List<List<Integer>> res = new ArrayList<List<Integer>>();
//            List<Integer> temp = new ArrayList<Integer>();
//            dfs(res, temp, nums, 0);
//            return res;
//        }
//        private void dfs(List<List<Integer>> res, List<Integer> temp, int[] nums, int j) {
//            res.add(new ArrayList<Integer>(temp));
//            for(int i = j; i < nums.length; i++) {
//                temp.add(nums[i]);  //① 加入 nums[i]
//                dfs(res, temp, nums, i + 1);  //② 递归
//                temp.remove(temp.size() - 1);  //③ 移除 nums[i]
//            }
//        }
}
