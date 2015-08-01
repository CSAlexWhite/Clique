import java.util.Random;
import java.util.Vector;

public class Main {

    /*
    * I.    READ IN PROBLEM FILE: ADJACENCY LIST, NUMBER k
    * II.   READ IN PARAMETER FILE  - POPULATION SIZE
    *                               - # GENERATIONS n
    *                               - FITNESS THRESHOLD
    *                               - CROSSOVER RATE
    *                               - MUTATION RATE
    * III.  GENETIC ALGORITHM:
    *       A. GENERATE n l-BIT CHROMOSOMES
    *       B. CALCULATE FITNESS OF ALL CHROMOSOMES
    *       C. CREATE OFFSPRING:
    *           1. SELECT A PAIR OF PARENTS - FITNESS-PROPORTIONATE
    *           2. CROSS OVER THE PAIR      - ONE-POINT CROSSOVER
    *           3. MUTATE THE OFFSPRING
    *           4. REPEAT UNTIL n OFFSPRING
    *       D. REPLACE CURRENT GENERATION WITH OFFSPRING
    *       E. REPEAT FROM STEP 2 UNTIL CONVERGENCE
    */

    public static void main(String[] args) {

        Integer n = 12;
        int k = 5;
        String padding = "%0" + Integer.toString(n) + "d";

        for(int i=0; i<10; i++){

            System.out.println(generateCombination(n, k, padding));
        }
    }

    private static String generateCombination(int n, int k, String padding){

        StringBuilder combination = new StringBuilder(String.format(padding, 0)); // create a bitstring with n zerosSystem.outl.print

        int position, count = 0;
        while(count < k || numberChosen(combination.toString()) < k){

            position = randomInt(0, n-1);
            combination.setCharAt(position, '1');
            count++;
        }

        return combination.toString();
    }

    private static int numberChosen(String input){

        int count = 0;
        for(int i=0; i<input.length(); i++){

            if(input.charAt(i) == '1') count++;
        }

        return count;
    }

    private static int randomInt(int min, int max) {

        Random rand = new Random(System.currentTimeMillis());
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }



    /**
     * Returns a vector of all numbers whose bitstring representation are of length n and include exactly k ones.  To be
     * used in generating subsets of size k from the overall graph of size n.  (Credit http://bit.ly/1IzQjj9)
     * @param n
     * @param k
     * @return
     */
    private static Vector<String> getAllCombinations(long n, long k, String padding){

        Vector<String> combinations = new Vector<String>();

        long x = (long)(Math.pow(2, k) - 1); //+ (int)(Math.pow(2, n) - 1);  // initialize x
        long count = 1;// x is the first combination
        long u = 0, v = 0, y = 0;

        while(count <= combinations(n, k)){

            combinations.add(String.format(padding, Long.parseLong(Long.toBinaryString(x))));

            u = x & -x;
            v = x + u;
            y = v + (((v^x)/u)>>2);
            x = y;

            count++;
        }

        return combinations;
    }

    private static void printStringVector(Vector<String> vector){

        for(int i=0; i<vector.size(); i++){

            System.out.println(vector.elementAt(i));
        }
    }

    private static long combinations(long n, long k){

        return factorial(n)/(factorial(k) * factorial(n - k));
    }

    private static long factorial(long n) {

        long fact = 1;
        for (int i = 1; i <= n; i++) {
            fact *= i;
        }

        return fact;
    }
}
