package Basic;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    public List<String> generateParenthesis(int n) {
		List<String> result = new ArrayList<String>();
		if(n == 0)
			return result;
		getstring(0,0,"",2*n,result);
        return result;
    }
	public void getstring(int x,int num,String str,int n,List<String> result)
	{
		if(x == n-1)
			result.add(str+')');
		else
		{
			if(num > 0)
				getstring(x+1,num-1,str+')',n,result);
			if(num < n-x)
				getstring(x+1,num+1,str+'(',n,result);
		}
	}
}

