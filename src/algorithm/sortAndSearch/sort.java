package algorithm.sortAndSearch;

import java.util.Arrays;
import java.util.Spliterator;

public class sort {
	public static void main(String[] arg) {
		int[] arr = {6,1,2,7,9,3,4,5,10,8};
		int[] simpleArr = {1,7,3,95,27}; // len = 5
		
		printIntArry(quickSort(arr),"Final result");
	}
	
	private static void printIntArry(int[] arr, String description) {
		System.out.print(description + ": ");
		for(int i:arr) {
			System.out.print(i + ",");
		}
		System.out.print("\n");
	}
	
	// 各种排序算法。及复杂度分析  https://www.cnblogs.com/onepixel/articles/7674659.html 
	
	
	// 冒泡: 依次交换
	public int[] bubbleSort(int[] arr) {
		int len = arr.length;
		for(int i=0;i<len-1;i++) {
			for(int j=i;j<len-1-i;j++) {
				 if (arr[j] > arr[j+1]) {        // 相邻元素两两对比
		                int temp = arr[j+1];        // 元素交换
		                arr[j+1] = arr[j];
		                arr[j] = temp;
		            }
			}
		}
		return arr;
	}
	
	//选择： 从最小开始，交换依次放到队伍中， 有序区间和无序区间
	//时间稳定为O(n^2),
	public int[] selectSort(int[] arr) {
		int len = arr.length;
		int minIndex,temp;
		for(int i=0;i<len-1;i++) {
			minIndex = i;
			for (int j = i; j < len - 1; j++) {
				if (arr[j] < arr[minIndex]) { // 寻找最小的数 ！！！！ Need to double check
					minIndex = j; // 将最小数的索引保存
				}
				temp = arr[i];
				arr[i] = arr[minIndex];
				arr[minIndex] = temp;
			}
		}
		return arr;
	}
	
	//插入，从前定义已排序数据，依次往后选元素插到对应元素后面 - 失误：从后往前比 &挪动而非交换顺序
	public int[] insertSort(int[] arr) {
		int len = arr.length;
		int preIndex, current;
		for(int i=1;i<len;i++) {
			preIndex = i-1;
			current = arr[i];
			while(current > arr[preIndex] & preIndex>=0) {
				arr[preIndex+1] = arr[preIndex];
				preIndex--;
			}
			arr[preIndex+1] = current;
		}
		return arr;
	}
	
	// 希尔： 定义gap
	
	// 归并排序。 ！！！好难
	public static int[] sort(int[] a,int low,int high){
        int mid = (low+high)/2;
        if(low<high){
            sort(a,low,mid);
            sort(a,mid+1,high);
            //左右归并
            merge(a,low,mid,high);
        }
        return a;
    }
     
    public static void merge(int[] a, int low, int mid, int high) {
        int[] temp = new int[high-low+1];
        int i= low;
        int j = mid+1;
        int k=0;
        // 把较小的数先移到新数组中
        while(i<=mid && j<=high){
            if(a[i]<a[j]){
                temp[k++] = a[i++];
            }else{
                temp[k++] = a[j++];
            }
        }
        // 把左边剩余的数移入数组 
        while(i<=mid){
            temp[k++] = a[i++];
        }
        // 把右边边剩余的数移入数组
        while(j<=high){
            temp[k++] = a[j++];
        }
        // 把新数组中的数覆盖nums数组
        for(int x=0;x<temp.length;x++){
            a[x+low] = temp[x];
        }
    }
	
	// 快速排序 ， 分段递归， 左i往右找大，右j往左找小，交换，直至碰头交换-置位结束
	public static void quickSort1(int[] a) {  
        if(a.length>0) {  
            quickSort1(a, 0 , a.length-1);  
        }  
    }  
  
    private static void quickSort1(int[] a, int low, int high) {  
        if( low > high) {   //1,找到递归算法的出口  
            return;  
        }  
        int i = low,j = high;          //2, 存  
        int key = a[ low ];          //3,key  
        while( i< j) {          //4，完成一趟排序  
            while(i<j && a[j] > key){              //4.1 ，从右往左找到第一个小于key的数  
                j--;  
            }  
            while( i<j && a[i] <= key) {              // 4.2 从左往右找到第一个大于key的数  
                i++;  
            }  
            if(i<j) {  
                int p = a[i];  a[i] = a[j]; a[j] = p;     //4.3 交换  
            }  
        }  
        int p = a[i];          // 4.4，调整key的位置
        a[i] = a[low];  
        a[low] = p;  
        quickSort1(a, low, i-1 );          //5, 对key左边的数快排
        quickSort1(a, i+1, high);          //6, 对key右边的数快排  
    }  
    
	public static int[] quickSort(int[] arr){
		printIntArry(arr,"此时传入的是");
		if(arr.length == 1) {
			return arr;
		}
		int splitIndex = SplitForQuickSort(arr);
		if(splitIndex == 1) {
			return arr;
		}
		quickSort(Arrays.copyOfRange(arr, 0, splitIndex));
		quickSort(Arrays.copyOfRange(arr, splitIndex+1,arr.length));
		return arr;
	}
	
	private static int SplitForQuickSort(int[] arr) {
		int len = arr.length;
		int baseIndex = 0;
		int base = arr[baseIndex];
		int i=baseIndex+1,j=len-1;
		
		while(i!=j) {
			while(arr[j]>base && j>0) {
				j--;
			}
			while(arr[i]<base && i<len-1) {
				if(i == j) break;
				i++;
			}
			if(j>i) {
				swap(arr, i, j);
			}else if(j<i) {
				return 0;
			}
		}
		if(base > arr[i]) {
			swap(arr,0,i);
		}
		return i;
	}
	
	private static void swap(int[]arr,int i,int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
	
	
	// 有序数列 - 二分法排序
}
