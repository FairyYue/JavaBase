package Basic.threadTest;

import java.util.concurrent.CountDownLatch;

public class FairyThread extends Thread{
	private int i;

    @Override
    public void run() {
       for (; i < 10; i++) {
         System.out.println("FairyThread:"+"\t"+i);
    }
    }
}
