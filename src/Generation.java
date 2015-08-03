import java.util.Iterator;
import java.util.TreeSet;

/**
 * Created by Alex on 8/1/2015.
 */
public class Generation {

    private TreeSet<Chromosome> set;
    private int size, n, k;
    Graph parentGraph;

    public Generation(int populationSize, int n, int k, Graph parentGraph){

        this.size = populationSize;
        this.n = n;
        this.k = k;
        this.parentGraph = parentGraph;

        set = new TreeSet<Chromosome>();
        populate();
    }

    public Generation(Generation previous){

        /*SELECTION*/

    }

    public void populate(){

        while(set.size() < size){

//            System.out.println("____________________\n");
            set.add(new Chromosome(n, k, parentGraph));
        }
    }

    public void addChromosome(Chromosome input){


    }

    public void print(){

        System.out.println("Printing Generation: \n======================");
        Iterator<Chromosome> it = set.iterator();

        while(it.hasNext()){

            Chromosome toPrint = it.next();
            System.out.println(toPrint.genotype + " has fitness " + toPrint.fitness());
        }
    }
}
