import java.util.Vector;

public class Main {

    /* ALGORITHM
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


    }

    /**
     * Returns a vector of all numbers whose bitstring representation are of length n and include exactly k ones.  To be
     * used in generating subsets of size k from the overall graph of size n.
     * @param n
     * @param k
     * @return
     */
    private Vector<Integer> getCombinations(int n, int k){

        Vector<Integer> returnValue = new Vector<Integer>();

        int x = (int)(Math.pow(2, k) - 1);  // initialize x
        int count = 1;// x is the first combination
        int u = 0, v = 0, y = 0;
        while(count < combinations(n, k)){


        }

        return returnValue;
    }

    private int combinations(int n, int k){

        return factorial(n)/(factorial(k) * factorial(n - k));
    }

    private int factorial(int n) {

        int fact = 1;
        for (int i = 1; i <= n; i++) {
            fact *= i;
        }

        return fact;
    }
}
