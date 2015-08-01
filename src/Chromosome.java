import java.util.Random;

/**
 * Created by Alex on 8/1/2015.
 */
public class Chromosome {

    Graph parent;
    String genotype;
    int connections;
    double fitness;

    public Chromosome(int n, int k, Graph parent){

        genotype = generateCombination(n, k);
        connections = getConnections();
        this.parent = parent;
        fitness = getFitness(k);
    }

    /**
     * counts the connections between the nodes of the graph encoded by this individual
     * @return
     */
    private int getConnections() {

        int count = 0;
        for(int i = 0; i < genotype.length(); i++){

            if(genotype.charAt(i) == '1'){      // for each node in the chromosome

                for(int j = 0; j < 10; j++){    // count the number of edges

                    if(genotype.charAt(j) == 1 && parent.matrix[i][j] > 0) count++;
                }
            }
        }

        return count;
    }

    /**
     * Calculates the ratio of existing edges to possible edges in the subgraph.
     * @param k
     * @return
     */
    public double getFitness(int k){

        return getConnections() / ((k * (k - 1)) / 2);
    }

    public void print(){

        System.out.println(genotype);
    }

    /**
     * Generates a bit string of length n with random k bits as one.
     * @param n length of string
     * @param k number of bits to flip
     * @return
     */
    private static String generateCombination(int n, int k){

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
