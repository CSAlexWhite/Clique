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

    public void populate(){

        int count = 0;
        while(count < size){

            System.out.println("____________________\n");
            set.add(new Chromosome(n, k, parentGraph));
            System.out.println("Generation size is : " + set.size());
            count++;
        }
    }

    public void addChromosome(Chromosome input){


    }

    public void print(){

        System.out.println("Printing");
        Iterator<Chromosome> it = set.iterator();

        while(it.hasNext()){

            it.next().print();
        }
    }
}
