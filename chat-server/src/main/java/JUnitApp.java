import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;

public class JUnitApp {
    public int[] cutArr(int[] arr){
        int l = 0;
        int subI = 0;
        int[] b;
        for (int i = arr.length-1; i >=0; i--) {
            if (arr[i]==4){
                subI = i+1;
                break;
        }else l++;}
        if(l==arr.length){throw new RuntimeException();}
        b = new int[l];
        for (int i = 0; i < b.length; i++) {
            b[i] = arr[subI++];
        }
        return b;
    }

    public boolean checkArr(int[] arr){
        int kOne = 0;
        int kFour = 0;
        for (int i = 0; i < arr.length; i++) {
            if(arr[i]==1){
                kOne++;
            }
            if(arr[i]==4){
                kFour++;
            }
        }
        if (kOne+kFour==arr.length&&(kOne!=0&&kFour!=0)){return true;}
        return false;
    }

    public static void main(String[] args) {
        JUnitApp app = new JUnitApp();
        System.out.println(app.checkArr(new int[]{1,4,1,1,4,1,1,4,4}));
    }
}
