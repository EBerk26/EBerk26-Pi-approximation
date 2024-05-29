import java.math.BigDecimal;
import java.math.RoundingMode;
import java.math.MathContext;

public class Main {
    public static void main(String[] args) {
        new Main();
    }
    Main(){
        vietes();
    }
    void arctan_of_1(){
        //approximates pi using the maclaurin expansion of arctan and 4arctan(1) = pi
        int count = 0;
        double toAdd;
        double runningTotal = 0;
        double negative;
        final long startTime = System.currentTimeMillis();
        while(Math.abs(4*runningTotal-Math.PI)>=Math.pow(10,-10)){
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
        System.out.println("Time to complete: "+((double)(System.currentTimeMillis()-startTime))/1000+" seconds");
    }
    void arctan_of_1_over_root_3(){
        //approximates pi using the maclaurin expansion of arctan and 6arctan(1/sqrt(3)) = pi
        //between n=30 and n=31 not enough is subtracted
        int count = 0;
        int scale = 40;
        BigDecimal toAdd = new BigDecimal(0);
        BigDecimal runningTotal = new BigDecimal(0);
        BigDecimal root3 = new BigDecimal(Math.sqrt(3));
        BigDecimal zero = new BigDecimal(0);
        BigDecimal one = new BigDecimal(1);
        BigDecimal one_over_root_3 = one.divide(root3,scale,RoundingMode.HALF_UP);
        BigDecimal six = new BigDecimal(6);
        BigDecimal negative;
        BigDecimal error = new BigDecimal(200);
        BigDecimal errorGoal = BigDecimal.valueOf(Math.pow(10, -17)).setScale(scale,RoundingMode.HALF_UP);
        BigDecimal negativeOne = new BigDecimal(-1);
        BigDecimal pi = new BigDecimal(3.1415926535897932384626433832795028841971693993751058209749445923078164062862089986280348253421170679);
        final long startTime = System.currentTimeMillis();
        BigDecimal twoCountPlusOne = new BigDecimal(1).setScale(3,RoundingMode.HALF_UP);
        while(error.compareTo(errorGoal)>0){
            if(count%2==1){
                negative = new BigDecimal(-1).setScale(scale, RoundingMode.HALF_UP);
            } else {
                negative = new BigDecimal(1).setScale(scale, RoundingMode.HALF_UP);
            }
            toAdd = one_over_root_3.pow(2*count+1).divide(twoCountPlusOne,scale,RoundingMode.HALF_UP).multiply(negative).setScale(scale,RoundingMode.HALF_UP);
            runningTotal = runningTotal.add(toAdd).setScale(scale, RoundingMode.HALF_UP);
            System.out.println("n = "+count+" pi = "+runningTotal.multiply(six).setScale(scale, RoundingMode.HALF_UP));
            count++;
            twoCountPlusOne = new BigDecimal(2*count+1).setScale(scale,RoundingMode.HALF_UP);
            if(runningTotal.multiply(six).subtract(pi).setScale(scale, RoundingMode.HALF_UP).compareTo(zero)>0){
                error = runningTotal.multiply(six).subtract(pi).setScale(scale, RoundingMode.HALF_UP);
            } else {
                error = runningTotal.multiply(six).subtract(pi).multiply(negativeOne).setScale(scale, RoundingMode.HALF_UP);
            }
        }
        System.out.println("Time to complete: "+((double)(System.currentTimeMillis()-startTime))/1000+" seconds");
    }
    void vietes(){
        //approximates pi using viète's formula
        BigDecimal runningTotal = new BigDecimal(1);
        int scale =50;
        BigDecimal two = new BigDecimal(2);
        BigDecimal pi = new BigDecimal(3.1415926535897932384626433832795028841971693993751058209749445923078164062862089986280348253421170679).setScale(scale,RoundingMode.HALF_UP);
        BigDecimal toMultiply = BigDecimal.valueOf(Math.sqrt(2) / 2);
        BigDecimal error = new BigDecimal(5);
        BigDecimal errorGoal = BigDecimal.valueOf(Math.pow(10, -16)).setScale(scale,RoundingMode.HALF_UP);
        int count = 1;
        final long startTime = System.currentTimeMillis();
        while(error.compareTo(errorGoal)>0){
            runningTotal=runningTotal.multiply(toMultiply).setScale(scale,RoundingMode.HALF_UP);
            toMultiply = new BigDecimal(0).add(squareRoot(toMultiply.multiply(two).add(two),scale)).divide(two,scale,RoundingMode.HALF_UP);
            count++;
            System.out.println("Count: "+count+" Pi: "+two.divide(runningTotal,scale,RoundingMode.HALF_UP));
            error = pi.subtract(two.divide(runningTotal,scale,RoundingMode.HALF_UP)).setScale(scale,RoundingMode.HALF_UP);
        }
        System.out.println("Time to complete: "+((double)(System.currentTimeMillis()-startTime))/1000+" seconds");
    }
    void vietes_double(){
        double runningTotal = 1;
        double toMultiply = Math.sqrt(2)/2;
        int count = 1;
        final long startTime = System.currentTimeMillis();
        while(Math.abs(2/runningTotal-Math.PI)>=Math.pow(10,-15)){
            runningTotal*=toMultiply;
            toMultiply = Math.sqrt(toMultiply*2+2)/2;
            System.out.println("Count: "+count+" Pi: "+2/runningTotal);
            count++;
        }
        System.out.println("Time to complete: "+((double)(System.currentTimeMillis()-startTime))/1000+" seconds");
    }
    BigDecimal squareRoot(BigDecimal radicand,int scale){
        BigDecimal two = new BigDecimal(2);
        BigDecimal previousValue = new BigDecimal(6).add(radicand).divide(two,scale,RoundingMode.HALF_UP).setScale(scale,RoundingMode.HALF_UP);
        BigDecimal returnValue = new BigDecimal(8).setScale(scale,RoundingMode.HALF_UP).setScale(scale,RoundingMode.HALF_UP);
        while(returnValue.compareTo(previousValue)!=0){
            previousValue = new BigDecimal(0).add(returnValue);
            returnValue = radicand.divide(previousValue,scale,RoundingMode.HALF_UP).add(previousValue).divide(two,scale,RoundingMode.HALF_UP).setScale(scale,RoundingMode.HALF_UP);
        }
        return returnValue;
    }
    void arctan_of_1_over_root_3_double() {
        int count = 0;
        double toAdd;
        double runningTotal = 0;
        double negative;
        final long startTime = System.currentTimeMillis();
        while (Math.abs(6 * runningTotal - Math.PI) / Math.PI >= Math.pow(10, -16)) {
            if (count % 2 == 1) {
                negative = -1;
            } else {
                negative = 1;
            }
            toAdd = Math.pow(1 / Math.sqrt(3), 2 * count + 1) * negative / (2 * count + 1);
            runningTotal += toAdd;
            System.out.println("n = " + count + " pi = " + 6 * runningTotal);
            count++;
        }
        System.out.println("Time to complete: " + ((double) (System.currentTimeMillis() - startTime)) / 1000 + " seconds");
    }
}