package Basic.threadTest;

public class FairyRun implements Runnable{  // 可继承其他类
	private int i;
    public void run() {
        for (; i < 20; i++) {
            System.out.println("FairyRun："+" "+i);
            if(i==20)
            {
                System.out.println("FairyRun:"+"执行完毕");
            }
        }
    }
}
