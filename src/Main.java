public class Main {
    public static void main(String[] args) {
        new Main();
    }
    Main(){
        arctan1();
    }
    void arctan1(){
        int count = 0;
        double toAdd;
        double runningTotal = 0;
        double negative;
        while(true){
            if(count%2==1){
                negative = -1;
            } else {
                negative = 1;
            }
            toAdd = negative/(2*count+1);
            runningTotal +=toAdd;
            System.out.println("n = "+count+" pi = "+4*runningTotal);
            count++;
        }
    }
}