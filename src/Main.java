import java.io.*;
import java.util.Date;

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
    static int graphSize;
    static int cliqueSize;
    static int numGenerations;
    static int generationSize;
    static int selectionRate;
    static int mutationRate;
    static double fitnessThreshold;

    public static void main(String[] args) {

        String paramFilename = args[0];
        String inputFilename = args[1];
        String outputFilename = args[2];

//        String paramFilename = "params.txt";
//        String inputFilename = "Graph3.txt";
//        String outputFilename = "outfile.txt";

        readParameters(paramFilename);

        Graph graph = new Graph(inputFilename);
        graphSize = graph.size;
        graph.print();

        try{

            PrintWriter newFile = new PrintWriter(outputFilename);

            System.out.println();
            System.out.println("Graph size is: " + graphSize + " x " + graphSize);
            System.out.println("Searching for clique of size: " + cliqueSize);
            System.out.println("Maximum generations will be: " + numGenerations);
            System.out.println("Generations are of size: " + generationSize);
            System.out.println("Selection rate set at: " + selectionRate);
            System.out.println("Mutation rate set at: " + mutationRate);
            System.out.println("Fitness threshold set at " + fitnessThreshold);
            System.out.println("Processing...");
            System.out.println();

            Generation start = new Generation(generationSize, graphSize, cliqueSize, graph);
            newFile.println("INITIAL GENERATION");
            newFile.println(start.printStatistics());
            newFile.println("\n==================================");
            newFile.println("Generation 1 at " + new Date(System.currentTimeMillis()));
            Generation next = new Generation(start, selectionRate, mutationRate);
            newFile.println(next.printStatistics());
            newFile.print("Best Chromosome: " + start.best.genotype);
            newFile.println(" having fitness: " + start.best.fitness());

            int i=2;
            while(next.best.fitness < fitnessThreshold){

                newFile.println("==================================");
                newFile.println("Generation " + i++ + " at " + new Date(System.currentTimeMillis()));
                next = new Generation(next, selectionRate, mutationRate);
                newFile.println(next.printStatistics());
                newFile.print("Best Chromosome: " + next.best.genotype);
                newFile.println(" having fitness: " + next.best.fitness());
            }

            System.out.println("\nFINISHED");
            System.out.println("See " + outputFilename + " for details");
            newFile.close();
        }
        catch(FileNotFoundException fnfe){ fnfe.printStackTrace();}
    }

    private static void readParameters(String filename){

        try{

            BufferedReader inFile = new BufferedReader(new FileReader(filename));

            cliqueSize = Integer.parseInt(inFile.readLine());
            numGenerations = Integer.parseInt(inFile.readLine());
            generationSize = Integer.parseInt(inFile.readLine());
            selectionRate = Integer.parseInt(inFile.readLine());
            mutationRate = Integer.parseInt(inFile.readLine());
            fitnessThreshold = Double.parseDouble(inFile.readLine());
            inFile.close();
        }

        catch(FileNotFoundException fnfe)   { fnfe.printStackTrace(); }
        catch(IOException ioe)              { ioe.printStackTrace(); }
    }
}
