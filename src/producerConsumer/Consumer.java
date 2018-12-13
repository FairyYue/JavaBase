package producerConsumer;

import java.text.MessageFormat;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable{
    private BlockingQueue<PCData> queue;
    private static final int SLEEPTIME = 1000;
    
    public Consumer(BlockingQueue<PCData> queue){
        this.queue = queue;
    }
    
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("start Consumer id :"+Thread.currentThread().getId());
        Random random = new Random();
        try{
            while(true){
                PCData data = queue.take(); // 当 queue长度为0时，阻塞
                if(data != null)
                {
                    int re = data.getData() * data.getData();
                    System.out.println(MessageFormat.format("{0}*{1}={2}", data.getData(),data.getData(),re));
                    Thread.sleep(random.nextInt(SLEEPTIME));
                }
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
