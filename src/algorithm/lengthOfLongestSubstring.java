package algorithm;

import java.util.HashSet;
import java.util.Set;

import org.testng.annotations.Test;

public class lengthOfLongestSubstring {

	@Test
	public void runTest() {
		String str = "abccbaacanb";
		String str1 = "abbac";
		String result = findLongestPalindrome(str);
	}

	public int lengthOfLongestSubstring(String s) {
		int n = s.length();
		Set<Character> set = new HashSet<>();
		int ans = 0, i = 0, j = 0;
		while (i < n && j < n) {
			// try to extend the range [i, j]
			if (!set.contains(s.charAt(j))) {
				set.add(s.charAt(j++));
				ans = Math.max(ans, j - i);
			} else {
				set.remove(s.charAt(i++));
			}
		}
		return ans;
	}

	// 暴力法
	// T:O(N^3), S:O(1)
	public String findLongestPalindrome(String s) {
		int len = s.length(); // 字符串长度
		int maxlength = 0; // 最长回文字符串长度
		int start = 0; // 回文开始的地方
		for (int i = 0; i < len; i++) {
			for (int j = i + 1; j < len; j++) {
				int index1 = 0;
				int index2 = 0;
				// 对每个子串都从两边开始向中间遍历
				for (index1 = i, index2 = j; index1 < index2; index1++, index2--) {
					if (s.charAt(index1) != s.charAt(index2))
						break;
				}
				// 若index1=index2,表示串是类似于abcba这种类型；若大于，则是abccba这种类型
				if (index1 >= index2 && j - i > maxlength) {
					maxlength = j - i + 1;
					start = i;
				}
			}

		}
		if (maxlength > 0)
			return s.substring(start, start + maxlength);
		return null;
	}

	// 动态规划法 String str1 = "abbac";
	// T:O(n^2), S:O(n^2)
	public static String findLongestPalindrome1(String s){
        int len = s.length();
        int start = 0;
        int maxlength = 0;
        boolean p[][] = new boolean[s.length()][s.length()];
        // 子串长度为1和为2的初始化
        for(int i = 0; i < len; i++){
            p[i][i] = true;
            if(i < len - 1 && s.charAt(i) == s.charAt(i + 1)){
                p[i][i + 1] = true;
                start = i;
                maxlength = 2;
            }
        }
        // 使用上述结果可以dp出子串长度为 3 ~ len-1 的子串
        for(int strlen = 3; strlen < len; strlen ++){
            for(int i = 0; i <=len - strlen; i++){
                int j = i + strlen - 1; // 子串结束的位置
                if(p[i + 1][j - 1] && s.charAt(i) == s.charAt(j)){
                    p[i][j] = true;
                    maxlength = strlen;
                    start = i;
                }
            }
        }
        if(maxlength > 0)
            return s.substring(start, start + maxlength);
        return null;
    }

	public class Solution {
	    public String longestPalindrome(String s)
		{
			if (s.length() == 0)
				return "";
			String substring = s.substring(0,1);
			for(int i = 0;i<s.length()-1;i++)
			{
				//当以其为中心能够组成的最大回文序列已经<=max_string的长度时，停止遍历
				if(((s.length() - i)*2-1) <=substring.length())
					break;
				//长度为奇数的回文序列
				int lo1 = loindex(i,i,s);
				int len1 = (i-lo1)*2+1;
				//长度为偶数的回文序列
				int lo2 = loindex(i,i+1,s);
				int len2 = (i+1-lo2)*2;
				//更新max_string
				if(len1>substring.length() && len1 >=len2)
					substring = s.substring(lo1, lo1+len1);
				else if(len2>substring.length() && len2>len1)
					substring = s.substring(lo2, lo2+len2);
			}
			return substring;
		}
		public int loindex(int x1,int x2,String s)
		{
			while(x1 >= 0 && x2<s.length() && s.charAt(x1) == s.charAt(x2))
			{
				x1--;
				x2++;
			}
			return x1+1;
		}
	}


		

}
