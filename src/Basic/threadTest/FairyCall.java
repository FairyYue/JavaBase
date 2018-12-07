package Basic.threadTest;

import java.util.concurrent.Callable;

public class FairyCall<String> implements Callable<String>{  // 可继承其他类并返回结果

	@Override
    public String call() throws Exception {  
        System.out.println("FairyCall：做任务----"+Thread.currentThread().getName());  
        return (String) "返回运算结果";  
	}
}
