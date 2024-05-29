import java.math.BigDecimal;
import java.math.RoundingMode;
import java.math.MathContext;

public class Main {
    public static void main(String[] args) {
        new Main();
    }

    Main() {
        arctan_of_1_over_root_3();
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
        int scale = 2000;
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