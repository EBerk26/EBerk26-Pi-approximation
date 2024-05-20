import java.math.BigDecimal;
public class Main {
    public static void main(String[] args) {
        new Main();
    }
    Main(){
        arctan_of_1();
    }
    void arctan_of_1(){
        //approximates pi using the maclaurin expansion of arctan and 4arctan(1) = pi
        int count = 0;
        BigDecimal toAdd;
        BigDecimal runningTotal = new BigDecimal(0);
        BigDecimal four = new BigDecimal("4");
        BigDecimal pi = new BigDecimal(Math.PI);
        BigDecimal error = new BigDecimal(200);
        BigDecimal errorGoal = BigDecimal.valueOf(Math.pow(10, -8));
        BigDecimal zero = new BigDecimal(0);
        BigDecimal negativeOne = new BigDecimal(-1);
        double negative;
        final long startTime = System.currentTimeMillis();
        while(error.compareTo(errorGoal)>0){
            if(count%2==1){
                negative = -1;
            } else {
                negative = 1;
            }
            toAdd = new BigDecimal(negative/(2*count+1));
            runningTotal = runningTotal.add(toAdd);
            System.out.println("n = "+count+" pi = "+runningTotal.multiply(four));
            count++;
            if(runningTotal.multiply(four).subtract(pi).compareTo(zero)>0){
                error = runningTotal.multiply(four).subtract(pi);
            } else {
                error = runningTotal.multiply(four).subtract(pi).multiply(negativeOne);
            }
        }
        System.out.println("Time to complete: "+((double)(System.currentTimeMillis()-startTime))/1000+" seconds");
    }
    void arctan_of_1_over_root_3(){
        //approximates pi using the maclaurin expansion of arctan and 6arctan(1/sqrt(3)) = pi
        //I'm multiplying by 10 thousand here just to have more digits
        int count = 0;
        double toAdd;
        double runningTotal = 0;
        double negative;
        final long startTime = System.currentTimeMillis();
        while(Math.abs(6*runningTotal-10000*Math.PI)/10000>=Math.pow(10,-1)){
            if(count%2==1){
                negative = -1;
            } else {
                negative = 1;
            }
            toAdd = Math.pow(1/Math.sqrt(3),2*count+1)*negative/(2*count+1);
            runningTotal += 10000*toAdd;
            System.out.println("n = "+count+" 10000pi = "+6*runningTotal);
            count++;
        }
        System.out.println("Time to complete: "+((double)(System.currentTimeMillis()-startTime))/1000+" seconds");
    }
}