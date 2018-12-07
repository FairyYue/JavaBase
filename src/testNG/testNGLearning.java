package testNG;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class testNGLearning {


	/*
	 * 注释 ！！ 待补充
	 */
	@Test
	public void runOtherTest1() {
		System.out.println("@Test - runOtherTest1");
	}
	
	/* 
	 * 处理异常
	 */
	@Test(expectedExceptions = ArithmeticException.class)
	public void divisionWithException() {
		int i = 1 / 0;
		System.out.println("After division the value of i is :" + i);
	}
	/*
	 * 自定义异常
	 * public void save(String order) throws OrderSaveException {}
	 */
	
	/*
	 * 忽略测试
	 */
	@Test(enabled = false)
	public void ignoreTest() {
		System.out.println("@Test - enabled = false");
	}
	
	/*
	 * 超时测试
	 */
    @Test(timeOut = 100)
    public void testThisShouldPass() throws InterruptedException {
        Thread.sleep(50);
    }
    // 失败-超时
    @Test(timeOut = 1000)
    public void testThisShouldFail() {
        while (true){
            // do nothing
        }
    }


    /*
	 * 分组测试
	 * 
	 * Output: runSelenium() runSelenium1() setupDB() testConnectMsSQL
	 * testConnectOracle() cleanDB() runFinal
	 */
	
    @BeforeGroups("database")    
    public void setupDB() {
        System.out.println("setupDB()");
    }

    @AfterGroups("database")
    public void cleanDB() {
        System.out.println("cleanDB()");
    }

    @Test(groups = "selenium-test")
    public void runSelenium() {
        System.out.println("runSelenium()");
    }

    @Test(groups = "selenium-test")
    public void runSelenium1() {
        System.out.println("runSelenium1()");
    }

    @Test(groups = "database")
    public void testConnectOracle() {
        System.out.println("testConnectOracle()");
    }

    @Test(groups = "database")
    public void testConnectMsSQL() {
        System.out.println("testConnectMsSQL");
    }

    /*
     * 依赖测试
     */
    @Test(dependsOnGroups = { "database", "selenium-test" })
    public void runFinal() {
        System.out.println("runFinal");
    }
    @Test(dependsOnGroups={"deploy","db"})
    public void method1() {
        System.out.println("This is method 1");
        //throw new RuntimeException();
    }
    
	/*
	 * 参数化测试 
	 * 使用testng.xml 使用数据提供者 
	 * 我们将向您展示如何通过XML @Parameters或@DataProvider 给@Test传参
	 */
    //01.通过 xml@Parameter
    @Test
    @Parameters({ "dbconfig", "poolsize" })
    public void createConnection(String dbconfig, int poolsize) {

        System.out.println("dbconfig : " + dbconfig);
        System.out.println("poolsize : " + poolsize);
    }
    
    //02.通过DataProvider
    @Test(dataProvider = "provideNumbers")
    public void testParameter(int number, int expected, Map<String, String> map) {
        Assert.assertEquals(number + 10, expected);
        
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println("[Key] : " + entry.getKey() + " [Value] : " + entry.getValue());
        }
    }

    @DataProvider(name = "provideNumbers")
    public Object[][] provideData() {
    	Map<String, String> map = new HashMap<>();
        return new Object[][] { { 10, 20,map }, { 100, 110,map } };
    }
    	
    @DataProvider(name = "dataProvider")
    public Object[][] provideData(Method method) {
    	Map<String, String> map = new HashMap<>();
        if (method.getName().equals("testParameter")) {
        	return new Object[][] { { 10, 20,map }, { 100, 110,map } };
        }
        return null;
    }
    
    /*
     * 多次执行 & 线程执行
     */
    @Test(invocationCount = 3, threadPoolSize = 3)
    public void testThreadPools() {

        System.out.printf("Thread Id : %s%n", Thread.currentThread().getId());

    }
}
