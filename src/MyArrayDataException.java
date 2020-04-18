public class MyArrayDataException extends NumberFormatException{
    public void nonCorrectFormat(int i,int j){
        System.out.println("В ячейке под номером: ["+i+"]["+j+"] хранятся неверные данные");
        System.exit(0);
    }
}
