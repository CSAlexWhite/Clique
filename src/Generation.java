import java.util.Iterator;
import java.util.Random;
import java.util.TreeSet;
import java.util.Vector;

/**
 * Created by Alex on 8/1/2015.
 */
public class Generation {

    private TreeSet<Chromosome> set;
    private int size, n, k;
    Graph parentGraph;
    Random randNum;
    Chromosome best;

    //TODO include generation statistics

    public Generation(int populationSize, int n, int k, Graph parentGraph){

        this.size = populationSize;
        this.n = n;
        this.k = k;
        this.parentGraph = parentGraph;
        randNum = new Random(System.currentTimeMillis());

        set = new TreeSet<Chromosome>();
        populate();
        best = set.first();
    }

    public Generation(Generation previous, int crossoverRate, int mutationRate){

        this.size = previous.size;
        this.n = previous.n;
        this.k = previous.k;
        this.parentGraph = previous.parentGraph;
        randNum = new Random(System.currentTimeMillis());

        Vector<Chromosome> parents = new Vector<Chromosome>();
        set = new TreeSet<Chromosome>();
        // choose parents

        // start at the top of the list, roll the dice, if exceeds crossover rate, select, remove it and add it to parents list
        Iterator<Chromosome> it = previous.set.iterator();

        int count = 0, iter = 0, roll = 0;

        while(parents.size() < previous.size*((double)crossoverRate/100)){

            //System.out.println("Selecting.");
            while(it.hasNext()){

                iter++;
                Chromosome candidate = it.next();

                roll = randomInt(0, 100);

                if(roll > 0){//(100 - crossoverRate)){

                    parents.add(candidate);
                    previous.set.remove(candidate);
                    it = previous.set.iterator();
                    count++;
                    break;
                }
            }
        }

        Chromosome parent1, parent2;

        while(set.size() < previous.size){

            //System.out.println("Crossing Over");
            parent1 = parents.elementAt(randomInt(0, parents.size() - 1));
            parent2 = parents.elementAt(randomInt(0, parents.size() - 1));

            set.add(parent1.crossover(parent2));
        }


        // mutate all those offspring
        it = set.iterator();
        while(it.hasNext()){

            //System.out.println("Mutating");
            Chromosome toMutate = it.next();
            toMutate.mutate(mutationRate);
        }

        best = set.first();
    }

    public void populate(){

        while(set.size() < size){

            set.add(new Chromosome(n, k, parentGraph));
        }
    }

    public double calculateStatistics(){

        double sum = 0.0;

        Iterator<Chromosome> it = set.iterator();
        while(it.hasNext()){

            Chromosome toMeasure = it.next();
            sum += toMeasure.fitness;
        }

        double avgFitness = sum/set.size();

        return avgFitness;
    }

    public void print(){

        System.out.println("Printing Generation: \n======================");
        Iterator<Chromosome> it = set.iterator();

        while(it.hasNext()){

            Chromosome toPrint = it.next();
            System.out.println(toPrint.genotype + " has fitness " + toPrint.fitness());
        }
    }

    private int randomInt(int min, int max) {

        int randomNum = randNum.nextInt((max - min) + 1) + min;

        return randomNum;
    }
}
