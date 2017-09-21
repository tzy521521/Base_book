package chapter23;

/**
 * Created by tzy on 2017/9/21.
 */
public class Main {
    public static int[] getNums(int text){
        int[] nums=new int[100];
        int index = 0;
        if (text>nums[0]){
            while (index<100){
                if (text>nums[2*index+2]){

                }else if (text>nums[2*index+1]){

                }
                else {
                    nums[0]=text;
                }
            }
        }
        return nums;
    }
}
