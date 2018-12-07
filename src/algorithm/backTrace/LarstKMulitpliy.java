package algorithm.backTrace;

import java.util.Scanner;

// Fairy: wait to check
public class LarstKMulitpliy {
    private static long ans;
    private static Scanner cin;
    static{
        cin = new Scanner(System.in);
    }
    static void work(int cur, int i, int k, long v){
        if(i == k){
            ans = Math.max(ans, v);
            return;
        }
        if(cur == 0){
            return;
        }
        int MOD = 1;
        while(cur / MOD != 0){
            work(cur % MOD, i + 1, k, v * (cur / MOD));
            MOD *= 10;
        }
    }
    public static void main(String[] args) {
        int num, k;
        System.out.println("请输入数字num和要分成的段数k: ");
        while(cin.hasNext()){
            num = cin.nextInt();
            k = cin.nextInt();
            ans = Long.MIN_VALUE;
            work(num, 0, k, 1L);
            if(ans == Long.MIN_VALUE){
                System.out.println("整数" + num + "不能被分成" + k + "段");
            }else{
                System.out.println(num + "的最大" + k + "乘积是: " + ans);
            }
            System.out.println("请输入数字num和要分成的段数k: ");
        }
    }
}

