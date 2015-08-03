import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.Vector;

/**
 * The main feature of a Chromosome is a bitstring of length n with exactly k ones,
 * which represents a subset of a graph.  Each chromosome contains a pointer to the
 * parent graph so that it can calculate its fitness.  The fitness is used to sort the
 * Chromosomes in each Generation.
 */
public class Chromosome implements Comparable<Chromosome>{

    Graph parent;
    String genotype;
    int length;
    int connections;
    int n, k;
    double fitness;

    public Chromosome(String genotype, int n, int k, Graph parent){

        this.n = n;
        this.k = k;
        this.parent = parent;
        this.genotype = genotype;
        this.length = genotype.length();
        connections = countEdges();
        fitness = getFitness(k);
    }

    public Chromosome(int n, int k, Graph parent){

        this.n = n;
        this.k = k;
        this.parent = parent;
        genotype = randomize(n, k);
        this.length = genotype.length();
        connections = countEdges();
        fitness = getFitness(k);
    }

    /**
     * If type 1: Iterates through the string and switches neighboring bits with probability p
     * @param p the mutation rate
     * @return
     */
    public void mutate(int p){

        StringBuilder tempString = new StringBuilder(genotype);  //TODO can improve complexity here

            char temp1, temp2;

            for(int i = 0; i<length; i++){

                if(randomInt(0, 100) < p){

                    temp1 = tempString.charAt(i);
                    temp2 = tempString.charAt((i+1)%length);

                    tempString.setCharAt(i, temp2);
                    tempString.setCharAt((i+1)%length, temp1);
                }
            }

        genotype = tempString.toString();
    }

    /**
     * Crossover combines two Chromosomes into one.  It finds positions where there are ones in both parents, then
     * randomly assigns those positions in the child as ones, until there are k of them.  In this way it both combines
     * the characteristics of the parents and is respectful of the necessary value k
     * @param mate
     * @return
     */
    public Chromosome crossover(Chromosome mate) throws GAException{

        if(k != mate.k) throw new GAException("Chromosomes differ in length");

        Vector<Integer> bitPositions = new Vector<Integer>();

        String padding = "%0" + Integer.toString(n) + "d";
        StringBuilder combination = new StringBuilder(String.format(padding, 0)); // create a bitstring with n zeros

        System.out.println("This chromosome:");
        print();
        System.out.println("That chromosome:");
        mate.print();

        System.out.print("Ones at positions:");
        for(int i = 0; i < length; i++){

            if(this.genotype.charAt(i) == '1' || mate.genotype.charAt(i) == '1'){
                bitPositions.add(i);
                System.out.print(i);
            }
        }

        System.out.println();

        int i, position, count = 0;
        while(count < k || numberChosen(combination.toString()) < k){

            i = randomInt(0, bitPositions.size()-1);
            position = bitPositions.elementAt(i);
            combination.setCharAt(position, '1');
            bitPositions.remove(i);
            count++;
        }

        return new Chromosome(combination.toString(), n, k, parent);
    }

    public String fitness(){

        DecimalFormat df = new DecimalFormat("##.##");
        df.setRoundingMode(RoundingMode.DOWN);
        return df.format(fitness);
    }

    public void print(){

        System.out.println(genotype);
    }

    /************************* OVERRIDDEN FUNCTIONS *******************************/

    @Override
    public int compareTo(Chromosome other) {

        if(other.genotype.equals(this.genotype)) return 0;

        if(other.fitness < this.fitness) return 1;
        if(other.fitness > this.fitness) return -1;

        if(other.fitness == this.fitness){

            if(Integer.parseInt(other.genotype) < Integer.parseInt(this.genotype)) return 1;
            if(Integer.parseInt(other.genotype) > Integer.parseInt(this.genotype)) return -1;
        }

        return 0;
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

    /************************** PRIVATE METHODS ***********************************/

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

    /**
     * counts the connections between the nodes of the graph encoded by this individual
     * @return
     */
    private int countEdges() {

        int count = 0;
        for(int i = 0; i < genotype.length(); i++){

            if(genotype.charAt(i) == '1'){      // for each node in the chromosome

                for(int j = 0; j < genotype.length(); j++){    // count the number of edges

                    if(genotype.charAt(j) == '1'
                       && (i != j)
                       && parent.matrix[i][j] > 0)

                        count++;
                }
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

        return (double) connections / (double)((k * (k - 1)) / 2);
    }

}
