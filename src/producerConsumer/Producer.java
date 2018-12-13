package producerConsumer;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * Refer to: http://www.cnblogs.com/chentingk/p/6497107.html
 */
public class Producer implements Runnable {
	private volatile boolean isRunning = true; // Fairy??? 为啥是volatile？？？Why True.
	private BlockingQueue<PCData> queue;// 内存缓冲区
	private static final int SLEEPTIME = 1000;
	private static AtomicInteger count = new AtomicInteger();// 总数 原子操作 ?? why AtomicInteger??
	
	public Producer(BlockingQueue<PCData> queue) {
        this.queue = queue;
    }
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		PCData data=null;
		Random random = new Random();
		System.out.println("Start producting id:" + Thread.currentThread().getId());
		
		try {
			while(isRunning) {
				Thread.sleep(random.nextInt(SLEEPTIME));
				data = new PCData(count.incrementAndGet());
				System.out.println(data + " 加入队列");
				if (!queue.offer(data, 2, TimeUnit.SECONDS)) {
                    System.err.println(" 加入队列失败");
                }
				
			}
		}catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
	}
	
	public void stop() {
        isRunning = false;
    }
}
