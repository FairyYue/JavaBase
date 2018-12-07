package Basic.threadTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class threadTest {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
//		// 方式1
//		for (int i = 0; i <10; i++) {
//            System.out.println(Thread.currentThread().getName()+"\t"+i+"======");
//            if(i==5){
//                
//                FairyThread mt2 =new FairyThread();
//                FairyThread mt =new FairyThread();
//                mt2.start();
//                mt.start();
//            }
//		}
//        
//        // 方式2 -- runable, Thread(runable)
//		FairyRun fairyRun = new FairyRun();
//		Thread runT= new Thread(fairyRun);
//		runT.start();
//		
//		// 方式3 -- callable, 通过FutureTask包装器
//		FairyCall<String> fairyCall = new FairyCall<String>();
//		FutureTask<String> oneTask = new FutureTask<>(fairyCall);
//		Thread callT = new Thread(oneTask);
//		callT.start();
		
		
		// 线程池:  ExecutorSerivce+callable+Future
        List<Future<String>> result = new ArrayList<Future<String>>();
        ExecutorService es = Executors.newCachedThreadPool();  
        for(int i=0;i<100;i++){  
            result.add(es.submit(new FairyCall<>()));  
        }  
        for(Future<String> f : result){  
            System.out.println(f.get());   // Feture<V>.ge(); 取到返回结果，并可隔一段时间再来
        } 
    }  
}
