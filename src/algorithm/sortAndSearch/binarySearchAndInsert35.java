package algorithm.sortAndSearch;

import org.testng.Assert;
import org.testng.annotations.Test;
public class binarySearchAndInsert35 {
	
//	// 用Java本身的函数
//    public int searchInsert(int[] nums, int target) {
//        int temp = Arrays.binarySearch(nums,target);
//        if(temp>=0){
//            return temp;
//        }              
//        return Math.abs(temp)-1;
//    }
    
    // 自己实现 - 递归
	// Wrong, 只能实现查找，不是实现返回 index.
//	public int searchInsert(int[] nums, int target) {
//		if(nums.length==0 || (nums.length==1 && nums[0]!=target)) {
//			return -1;
//		}
//		
//		int middle = nums.length/2;
//		if(nums[middle]==target) {
//			return middle;
//		}
//		if(nums[middle]>target) {
//			return searchInsert(Arrays.copyOfRange(nums, 0, middle), target);
//		} else {
//			return searchInsert(Arrays.copyOfRange(nums, middle+1,nums.length), target);
//		}
//	}
	
	// Others: 递归
	// T: O(logN),S:O(1)
//	public int searchInsert(int[] nums, int target) {
//		return searchInsert(nums,0,nums.length-1, target);
//	}
//	
//	public int searchInsert(int[] nums, int low, int high, int target) {
//		if (low>high) {
//			return -low;
//		}
//
//		int middle = (low+high)/2;
//		if (nums[middle] == target) {
//			return middle;
//		}
//		if (nums[middle] > target) {
//			return searchInsert(nums,low,middle-1, target);
//		} else {
//			return searchInsert(nums,middle+1,high, target);
//		}
//	}
	
    //自己实现  - 不递归
	// T: O(logN),S:O(1)
	public int searchInsert(int[] nums, int target) {
		int low = 0;
		int high=nums.length -1;
		
		while(low<=high) {
			int middle = (low+high)/2;
			if (nums[middle] == target) {
				return middle;
			}
			if (nums[middle] > target) {
				high=middle-1;
			} else {
				low=middle+1;
			}
		}
		return -low;
	}
	
	@Test
	private void runTest() {
		Assert.assertEquals(-2, searchInsert(new int[] {2,4,6,8,10},5));
		Assert.assertEquals(-3, searchInsert(new int[] {2,4,6,8,10},7));
		Assert.assertEquals(3, searchInsert(new int[] {2,4,6,8,10},8));
	}
	
}
