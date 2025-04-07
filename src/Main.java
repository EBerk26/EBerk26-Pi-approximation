import java.math.BigDecimal;
import java.math.RoundingMode;
import java.math.MathContext;

public class Main {
    public static void main(String[] args) {
        new Main();
    }

    Main() {
        NewtonsZerotoHalfBigDecimal();

        //this lists how much time it takes for a probabilistic method to get a certain degree of closeness to pi
        /*int[] blah = getCount();
        for(int i:blah){
            System.out.println(i);
        }*/
    }

    int[] getCount(){
        int[] output = new int[300];
        for(int x=0;x< output.length;x++){
            double count = 0;
            double inCircle = 0;
            double runningTotal = 0;
            while(Math.abs(Math.PI-runningTotal)>=Math.pow(10,-5)){
                double a = Math.random();
                double b = Math.random();
                if(a*a+b*b<1){
                    inCircle++;
                }
                count++;
                runningTotal = inCircle/count*4;
            }
            output[x] = (int)count;
            System.out.println(x);
        }
        System.out.println("**********************");
        return output;
    }

    void probabilistic(){
        //approximates pi by placing random points in a 1x1 square and calculating the probability they're inside a quarter circle
        final long startTime = System.currentTimeMillis();
        double count = 0;
        double inCircle = 0;
        double runningTotal = 0;
        while(count<=Math.pow(10,8)){
            double a = Math.random();
            double b = Math.random();
            if(a*a+b*b<1){
                inCircle++;
            }
            count++;
            runningTotal = inCircle/count*4;
            System.out.println("Count: "+(int)count+". Pi = "+runningTotal);
        }
        System.out.println("Time to complete: " + ((double) (System.currentTimeMillis() - startTime)) / 1000 + " seconds");
    }
    void NewtonsZeroto1Double(){
        double n = 0;
        double previous = 1;
        double runningTotal = 1;
        final long startTime = System.currentTimeMillis();
        while(4*runningTotal-Math.PI>=Math.pow(10,-10)){
            double toSubtract = Math.abs(previous*(1+2*n)*(0.5-n)/(3+2*n)/(n+1));
            runningTotal-=toSubtract;
            System.out.println("Count: "+(int)n+". Pi = "+4*runningTotal);
            n++;
            previous=toSubtract;
        }
        System.out.println("Time to complete: " + ((double) (System.currentTimeMillis() - startTime)) / 1000 + " seconds");
    }
    void NewtonsZeroto1BigDecimal(){
        int scale = 20;
        int count = 0;
        BigDecimal zero = new BigDecimal(0).setScale(scale,RoundingMode.HALF_UP);
        BigDecimal n = zero;
        BigDecimal one = new BigDecimal(1).setScale(scale,RoundingMode.HALF_UP);
        BigDecimal two = new BigDecimal(2).setScale(scale,RoundingMode.HALF_UP);
        BigDecimal three = new BigDecimal(3).setScale(scale,RoundingMode.HALF_UP);
        BigDecimal four = new BigDecimal(4).setScale(scale,RoundingMode.HALF_UP);
        BigDecimal half = new BigDecimal("0.5").setScale(scale,RoundingMode.HALF_UP);
        System.out.println(half);
        BigDecimal previous = one;
        BigDecimal runningTotal = one;
        BigDecimal toSubtract = one;
        final long startTime = System.currentTimeMillis();
        while(!toSubtract.equals(zero)){
            toSubtract = previous.multiply(one.add(two.multiply(n))).multiply(half.subtract(n)).divide(three.add(two.multiply(n)),scale,RoundingMode.HALF_UP).divide(n.add(one),scale,RoundingMode.HALF_UP).abs().setScale(scale,RoundingMode.HALF_UP);
            runningTotal = runningTotal.subtract(toSubtract).setScale(scale,RoundingMode.HALF_UP);
            System.out.println("Count: "+count+". Pi = "+runningTotal.multiply(four).stripTrailingZeros());
            n = n.add(one);
            count++;
            previous = toSubtract;
        }
        System.out.println("Time to complete: " + ((double) (System.currentTimeMillis() - startTime)) / 1000 + " seconds");
    }
    void NewtonsZerotoHalfBigDecimal(){
        int scale = (int)Math.pow(10,3);
        int count = 0;
        BigDecimal zero = new BigDecimal(0).setScale(scale,RoundingMode.HALF_UP);
        BigDecimal n = zero;
        BigDecimal one = new BigDecimal(1).setScale(scale,RoundingMode.HALF_UP);
        BigDecimal two = new BigDecimal(2).setScale(scale,RoundingMode.HALF_UP);
        BigDecimal three = new BigDecimal(3).setScale(scale,RoundingMode.HALF_UP);
        BigDecimal four = new BigDecimal(4).setScale(scale,RoundingMode.HALF_UP);
        BigDecimal half = new BigDecimal("0.5").setScale(scale,RoundingMode.HALF_UP);
        BigDecimal quarter = new BigDecimal("0.25").setScale(scale,RoundingMode.HALF_UP);
        BigDecimal root3 = squareRoot(three,scale);
        BigDecimal eight = new BigDecimal(8).setScale(scale,RoundingMode.HALF_UP);
        BigDecimal root3over8 = root3.divide(eight,scale,RoundingMode.HALF_UP).setScale(scale,RoundingMode.HALF_UP);;
        BigDecimal twelve = new BigDecimal(12).setScale(scale,RoundingMode.HALF_UP);
        BigDecimal previous = half;
        BigDecimal runningTotal = half;
        BigDecimal toSubtract = one;
        final long startTime = System.currentTimeMillis();
        while(!toSubtract.equals(zero)){
            toSubtract = previous.multiply(one.add(two.multiply(n))).multiply(half.subtract(n)).divide(three.add(two.multiply(n)),scale,RoundingMode.HALF_UP).divide(n.add(one),scale,RoundingMode.HALF_UP).abs().multiply(quarter).setScale(scale,RoundingMode.HALF_UP);
            runningTotal = runningTotal.subtract(toSubtract).setScale(scale,RoundingMode.HALF_UP);
            System.out.println("Count: "+count+". Pi = "+runningTotal.subtract(root3over8).multiply(twelve).stripTrailingZeros());
            n = n.add(one);
            count++;
            previous = toSubtract;
        }
        System.out.println("Time to complete: " + ((double) (System.currentTimeMillis() - startTime)) / 1000 + " seconds");
    }

    void arctan_of_1() {
        //approximates pi using the maclaurin expansion of arctan and 4arctan(1) = pi
        int count = 0;
        double toAdd;
        double runningTotal = 0;
        double negative;
        final long startTime = System.currentTimeMillis();
        while (Math.abs(4 * runningTotal - Math.PI) >= Math.pow(10, -10)) {
            if (count % 2 == 1) {
                negative = -1;
            } else {
                negative = 1;
            }
            toAdd = negative / (2 * count + 1);
            runningTotal += toAdd;
            System.out.println("n = " + count + " pi = " + 4 * runningTotal);
            count++;
        }
        System.out.println("Time to complete: " + ((double) (System.currentTimeMillis() - startTime)) / 1000 + " seconds");
    }

    void arctan_of_1_over_root_3() {
        //approximates pi using the maclaurin expansion of arctan and 6arctan(1/sqrt(3)) = pi
        //between n=30 and n=31 not enough is subtracted
        int count = 0;
        int scale = 500;
        BigDecimal toAdd = new BigDecimal(2).setScale(scale,RoundingMode.HALF_UP);
        BigDecimal runningTotal = new BigDecimal(0).setScale(scale,RoundingMode.HALF_UP);
        BigDecimal three = new BigDecimal(3).setScale(scale,RoundingMode.HALF_UP);
        BigDecimal root3 = squareRoot(three,scale).setScale(scale,RoundingMode.HALF_UP);
        BigDecimal zero = new BigDecimal(0).setScale(scale,RoundingMode.HALF_UP);
        BigDecimal one = new BigDecimal(1).setScale(scale,RoundingMode.HALF_UP);
        BigDecimal one_over_root_3 = one.divide(root3, scale, RoundingMode.HALF_UP);
        BigDecimal six = new BigDecimal(6).setScale(scale,RoundingMode.HALF_UP);
        BigDecimal negative;
        final long startTime = System.currentTimeMillis();
        BigDecimal twoCountPlusOne = new BigDecimal(1).setScale(3, RoundingMode.HALF_UP);
        while (true) {
            if (count % 2 == 1) {
                negative = new BigDecimal(-1).setScale(scale, RoundingMode.HALF_UP);
            } else {
                negative = new BigDecimal(1).setScale(scale, RoundingMode.HALF_UP);
            }
            toAdd = one_over_root_3.pow(2 * count + 1).divide(twoCountPlusOne, scale, RoundingMode.HALF_UP).multiply(negative).setScale(scale, RoundingMode.HALF_UP);
            runningTotal = runningTotal.add(toAdd).setScale(scale, RoundingMode.HALF_UP);
            System.out.println("n = " + count + " pi = " + runningTotal.multiply(six).setScale(scale, RoundingMode.HALF_UP));
            count++;
            twoCountPlusOne = new BigDecimal(2 * count + 1).setScale(scale, RoundingMode.HALF_UP);
            if(toAdd.equals(zero)){
                break;
            }

        }
        System.out.println("Time to complete: " + ((double) (System.currentTimeMillis() - startTime)) / 1000 + " seconds");
    }

    void vietes() {
        //approximates pi using viète's formula
        int scale = 18000;
        BigDecimal runningTotal = new BigDecimal(1).setScale(scale, RoundingMode.HALF_UP);
        BigDecimal two = new BigDecimal(2).setScale(scale, RoundingMode.HALF_UP);
        BigDecimal zero = new BigDecimal(0).setScale(scale, RoundingMode.HALF_UP);
        BigDecimal one = new BigDecimal(1).setScale(scale,RoundingMode.HALF_UP);
        BigDecimal toMultiply = zero.add(squareRoot(two, scale).divide(two, scale, RoundingMode.HALF_UP)).setScale(scale, RoundingMode.HALF_UP);
        int count = 1;
        final long startTime = System.currentTimeMillis();
        while (true) {
            //Since this one gets so good, we can't compare to pi as there is a weird rounding error
            runningTotal = runningTotal.multiply(toMultiply).setScale(scale, RoundingMode.HALF_UP);
            toMultiply = zero.add(squareRoot(toMultiply.multiply(two).add(two), scale)).divide(two, scale, RoundingMode.HALF_UP);
            BigDecimal currentValueOfPi = zero.add(two.divide(runningTotal, scale, RoundingMode.HALF_UP));
            System.out.println("Count: " + count + " Pi: " + currentValueOfPi);
            if(toMultiply.equals(one)){
                break; //stops the loop if it's converged (if it would just multiply by 1)
            }
            count++;
        }
        System.out.println("Time to complete: " + ((double) (System.currentTimeMillis() - startTime)) / 1000 + " seconds");
    }

    void vietes_double() {
        double runningTotal = 1;
        double toMultiply = Math.sqrt(2) / 2;
        int count = 1;
        final long startTime = System.currentTimeMillis();
        while (Math.abs(2 / runningTotal - Math.PI) >= Math.pow(10, -15)) {
            runningTotal *= toMultiply;
            toMultiply = Math.sqrt(toMultiply * 2 + 2) / 2;
            System.out.println("Count: " + count + " Pi: " + 2 / runningTotal);
            count++;
        }
        System.out.println("Time to complete: " + ((double) (System.currentTimeMillis() - startTime)) / 1000 + " seconds");
    }

    BigDecimal squareRoot(BigDecimal radicand, int scale) {
        BigDecimal two = new BigDecimal(2);
        BigDecimal previousValue = new BigDecimal(6).add(radicand).divide(two, scale, RoundingMode.HALF_UP).setScale(scale, RoundingMode.HALF_UP);
        BigDecimal returnValue = new BigDecimal(8).setScale(scale, RoundingMode.HALF_UP).setScale(scale, RoundingMode.HALF_UP);
        while (returnValue.compareTo(previousValue) != 0) {
            previousValue = new BigDecimal(0).add(returnValue);
            returnValue = radicand.divide(previousValue, scale, RoundingMode.HALF_UP).add(previousValue).divide(two, scale, RoundingMode.HALF_UP).setScale(scale, RoundingMode.HALF_UP);
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