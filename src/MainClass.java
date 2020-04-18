public class MainClass {
    public static void sumMassive(String[][]mas){
        int sum = 0;
        int Exi=0,Exj=0;
                try {
                    for (int i = 0; i < mas.length; i++) {
                        for (int j = 0; j < mas[i].length; j++) {
                            if(mas.length>4||mas[i].length>4){throw new MyArraySizeException();}
                            if (!mas[i][j].matches("-?\\d+(\\.\\d+)?")){Exi=i;Exj=j; throw new MyArrayDataException();}
                            sum += Integer.parseInt(mas[i][j]);
                        }
                    }
                }
                catch (MyArraySizeException e){
                    e.indexOutOfBounds();
                }
                catch(MyArrayDataException e){
                    e.nonCorrectFormat(Exi,Exj);
                }
        System.out.println(sum);
            }



    public static void main(String[] args) {
        String[][]mas1 = {
                {"1","1","1","1"},
                {"1","1","1","1"},
                {"1","1","1","1"},
                {"1","1","1","1"}};
        sumMassive(mas1);
    }
}
