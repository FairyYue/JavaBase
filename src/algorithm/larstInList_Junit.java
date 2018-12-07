package algorithm;

import junit.framework.TestCase;

public class larstInList_Junit extends TestCase{
//	public larstInList_UT(String name) {
//        super(name);
//    }
	
	// No need @Test label
	public void testNum() throws Exception{
		// Correct
		// Assert is Deprecate, please use extend TestCase in Junit
//		Assert.assertEquals(9, LarstInList.getLarst(new int[] {1,4,9}));
		assertEquals(9, LarstInList.getLarst(new int[] {1,4,9}));
		assertEquals(-1, LarstInList.getLarst(new int[] {-1,-5,-8}));
	}
	
	public void testisEmpty() {
		try {
			LarstInList.getLarst(new int[] {});
			fail("An exception should been thrown!");
		} catch (Exception e1) {
			assertTrue(true);
		}
	}
	
	
}
