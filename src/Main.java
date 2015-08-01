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

        Integer n = 5;
        int k = 3;

        String padding = "%0" + Integer.toString(n) + "d";

        Vector<String> test = getCombinations(5, 3, padding);
        printCombinations(test);
    }

    /**
     * Returns a vector of all numbers whose bitstring representation are of length n and include exactly k ones.  To be
     * used in generating subsets of size k from the overall graph of size n.  (Credit http://bit.ly/1IzQjj9)
     * @param n
     * @param k
     * @return
     */
    private static Vector<String> getCombinations(int n, int k, String p){

        Vector<String> combinations = new Vector<String>();

        int x = (int)(Math.pow(2, k) - 1);  // initialize x
        int count = 1;// x is the first combination
        int u = 0, v = 0, y = 0;
        while(count <= combinations(n, k)){

            combinations.add(String.format(p, Integer.parseInt(Integer.toBinaryString(x))));

            u = x & -x;
            v = x + u;
            y = v + (((v^x)/u)>>2);
            x = y;

            count++;
        }

        return combinations;
    }

    private static void printCombinations(Vector<String> vector){

        for(int i=0; i<vector.size(); i++){

            System.out.println(vector.elementAt(i));
        }
    }

    private static int combinations(int n, int k){

        return factorial(n)/(factorial(k) * factorial(n - k));
    }

    private static int factorial(int n) {

        int fact = 1;
        for (int i = 1; i <= n; i++) {
            fact *= i;
        }

        return fact;
    }
}
