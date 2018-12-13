package producerConsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

public class main {
	
	// ?? ExecutorService??  学习？？？
	public static void main(String[] args) throws InterruptedException {
        BlockingQueue<PCData> queue = new LinkedBlockingDeque<>(10);
        Producer p1 = new Producer(queue);
        Producer p2 = new Producer(queue);
        Producer p3 = new Producer(queue);
        Consumer c1 = new Consumer(queue);
        Consumer c2 = new Consumer(queue);
        Consumer c3 = new Consumer(queue);
        
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(p1);
        service.execute(p2);
        service.execute(p3);
        service.execute(c1);
        service.execute(c2);
        service.execute(c3);
        Thread.sleep(10*1000);
        p1.stop();
        p2.stop();
        p3.stop();
        Thread.sleep(3000);
        service.shutdown();
    }
	
	/*start Consumer id :13
Start producting id:11
Start producting id:12
Start producting id:10
start Consumer id :15
start Consumer id :14
data:1 加入队列
data:2 加入队列
1*1=1
2*2=4
data:3 加入队列
3*3=9
data:4 加入队列
data:5 加入队列
4*4=16
data:6 加入队列
5*5=25
6*6=36
	 * 
	 */
}
