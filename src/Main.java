import java.util.Date;
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

        int n;
        int k = 11;
        int numGenerations = 100;
        int generationSize = 1000;

        int crossoverRate = 3;
        int MutationRate = 1;

        Graph testGraph = new Graph("Graph3.txt");
        n = testGraph.size;
        testGraph.print();
        System.out.println();
        Generation start = new Generation(generationSize, n, k, testGraph);

        System.out.println("==================================");
        System.out.println("Generation 0 at " + new Date(System.currentTimeMillis()));
        System.out.println("Average Fitness: " + start.calculateStatistics());
        System.out.print("Best Chromosome: " + start.best.genotype);
        System.out.println(" having fitness: " + start.best.fitness());
        Generation next = new Generation(start, crossoverRate, MutationRate);

        //for(int i = 1; i < numGenerations; i++){
        int i=1;
        while(next.best.fitness < 1){

            System.out.println("==================================");
            System.out.println("Generation " + i++ + " at " + new Date(System.currentTimeMillis()));
            next = new Generation(next, crossoverRate, MutationRate);
            System.out.println("Average Fitness: " + next.calculateStatistics());
            System.out.print("Best Chromosome: " + next.best.genotype);
            System.out.println(" having fitness: " + next.best.fitness());
        }

        System.out.println("FINISHED");
    }

    private static void readParameters(){

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
