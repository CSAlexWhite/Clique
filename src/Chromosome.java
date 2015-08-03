import java.util.Random;

/**
 * The main feature of a Chromosome is a bitstring of length n with exactly k ones,
 * which represents a subset of a graph.  Each chromosome contains a pointer to the
 * parent graph so that it can calculate its fitness.  The fitness is used to sort the
 * Chromosomes in each Generation.
 */
public class Chromosome implements Comparable<Chromosome>{

    Graph parent;
    String genotype;
    int connections;
    double fitness;

    public Chromosome(int n, int k, Graph parent){

        this.parent = parent;
        genotype = randomize(n, k);
        connections = countEdges();
        fitness = getFitness(k);
    }

    /**
     * counts the connections between the nodes of the graph encoded by this individual
     * @return
     */
    private int countEdges() {

        print();

        int count = 0;
        for(int i = 0; i < genotype.length(); i++){

            if(genotype.charAt(i) == '1'){      // for each node in the chromosome

                for(int j = 0; j < genotype.length(); j++){    // count the number of edges

                    if(genotype.charAt(j) == '1' && parent.matrix[i][j] > 0) {

                        System.out.print("[" + parent.matrix[i][j] + "} ");
                        count++;
                    }

                    else System.out.print(parent.matrix[i][j] + "  ");
                }

                System.out.println();
            }
        }

        return count/2;
    }

    /**
     * Calculates the ratio of existing edges to possible edges in the subgraph.
     * @param k
     * @return
     */
    public double getFitness(int k){

        double fitness = (double) connections / (double)((k * (k - 1)) / 2);
        System.out.println("Number of connections is " + connections);
        System.out.println("Possible connections is " + ((k * (k-1))/2));
        System.out.println("Fitness is " + fitness);
        return fitness;
    }

    public void print(){

        System.out.println(genotype);
    }

    @Override
    public int compareTo(Chromosome other) {

        if(other.fitness < this.fitness) return -1;
        if(other.fitness > this.fitness) return 1;
        else return 0;
    }

    @Override
    public int hashCode() {

        return Integer.parseInt(genotype, 2);
    }

    @Override
    public boolean equals(Object obj) {

        System.out.println("Testing Equality between " + this + " and " + obj );

        if(!(obj instanceof Chromosome)) return false;
        if(obj == this) return true;

        if(this.genotype.equals(((Chromosome)obj).genotype)) return true;

        return false;
    }

    /**
     * Generates a bit string of length n with random k bits as one.
     * @param n length of string
     * @param k number of bits to flip
     * @return
     */
    private static String randomize(int n, int k){

        String padding = "%0" + Integer.toString(n) + "d";
        StringBuilder combination = new StringBuilder(String.format(padding, 0)); // create a bitstring with n zeros

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
}
