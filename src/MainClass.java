import java.util.Random;

public class MainClass {
    static private int[] arr;
    static private int size = 1000000;


    static public boolean binaryFind(int value){
        int low = 0;
        int high = size - 1;
        int mid;
        while (low <= high) {
            mid = (low + high) / 2;
            if (value == arr[mid]) {
                return true;
            } else {
                if (value < arr[mid]) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }
        }
        return false;
    }

    static public boolean find(int value) {
        int i;
        for (i = 0; i < size; i++) {
            if (arr[i] == value) {
                return true;
            }
        }
        return false;
    }

    static public void display() {
        for (int i = 0; i < size; i++) {
            System.out.println(arr[i]);
        }
    }

    static public void delete(int value){
        int i = 0;
        for(i = 0; i < size; i++) {
            if (arr[i] == value) {
                break;
            }
        }

        for (int j = i; j < size - 1; j++){
            arr[j] = arr[j + 1];
        }
        size--;
    }

    static public void insert(int value){
        int i;
        for(i=0;i<size;i++){
            if (arr[i]>value)
                break;
        }
        for(int j=size;j>i;j--){
            arr[j] = arr[j-1];
        }
        arr[i] = value;
        size++;
    }

    static public void sortBubble(){
        int out, in;
        long a = System.currentTimeMillis();
        for (out = size - 1; out >= 1; out--) {
            for(in = 0; in < out; in++) {
                if (arr[in] > arr[in + 1]) {
                    change(in, in + 1);
                }
            }
        }
        System.out.println("Время работы пузырьковой сортировки: "+(System.currentTimeMillis()-a));
    }

    static private void change(int a, int b){
        int tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }

    static public void sortSelect(){
        int out, in, mark;
        long a = System.currentTimeMillis();
        for(out=0;out<size;out++){
            mark = out;
            for(in = out+1;in<size;in++){
                if (arr[in]< arr[mark]){
                    mark = in;
                }
            }
            change(out, mark);
        }
        System.out.println("Время работы сортировки выбором: "+(System.currentTimeMillis()-a));
    }

    static public void sortInsert(){
        int in, out;
        long a = System.currentTimeMillis();
        for(out = 1;out < size; out++){
            int temp = arr[out];
            in = out;
            while(in > 0 && arr[in-1] >=temp){
                arr[in] = arr[in-1];
                --in;
            }
            arr[in] = temp;
        }
        System.out.println("Время работы сортировки вставкой: "+(System.currentTimeMillis()-a));
    }






    public static void main(String[] args) {
        arr = new int[size];
        for (int i = 0; i < size; i++) {
            Random random = new Random();
            arr[i] = random.nextInt(500);
        }
        sortBubble();
        sortInsert();
        sortSelect();
    }
}
