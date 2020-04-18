public class MyArraySizeException extends ArrayIndexOutOfBoundsException {
    public void indexOutOfBounds(){
        System.out.println("Индекс за пределами границ массива");
        System.exit(0);
    }
}
