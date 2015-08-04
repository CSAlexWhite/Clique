import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

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


        /* SELECTION */
        Vector<Chromosome> parents = new Vector<Chromosome>();
        set = new TreeSet<Chromosome>();

        // start at the top of the list, roll the dice, if exceeds crossover rate, select, remove it and add it to parents list
        Iterator<Chromosome> it = previous.set.iterator();
        Chromosome candidate;
        int count = 0, roll = 0;
        while(parents.size() < previous.size*((double)crossoverRate/100)){

            while(it.hasNext()){

                candidate = it.next();

                if(parents.contains(candidate)) break;

                roll = randomInt(0, 100);
                if(roll > (100 - crossoverRate)){

                    parents.add(candidate);
                    previous.set.remove(candidate);
                    it = previous.set.iterator();
                    count++;
                    break;
                }
            }
        }

        /* CROSSOVER */
        Chromosome parent1, parent2;
        while(set.size() < previous.size){

            parent1 = parents.elementAt(randomInt(0, parents.size() - 1));
            parent2 = parents.elementAt(randomInt(0, parents.size() - 1));

            set.add(parent1.crossover(parent2));
        }


        /* MUTATION */
        it = set.iterator();
        while(it.hasNext()){

            Chromosome toMutate = it.next();
            toMutate.mutate(mutationRate);
        }

        best = set.first();
        printStatistics();
    }

    public void populate(){

        while(set.size() < size){

            set.add(new Chromosome(n, k, parentGraph));
        }
    }


    double avgFitness;
    double stdDeviation;
    double max;
    double min;
    double range;

    public String printStatistics(){

        Chromosome toMeasure;

        double sum = 0.0;

        Iterator<Chromosome> it = set.iterator();
        while(it.hasNext()){

            toMeasure = it.next();
            sum += toMeasure.fitness;
        }

        avgFitness = sum/set.size();

        DecimalFormat df = new DecimalFormat("##.##");
        df.setRoundingMode(RoundingMode.DOWN);
        df.format(avgFitness);

        sum = 0.0;
        it = set.iterator();
        while(it.hasNext()){

            toMeasure = it.next();
            sum += (toMeasure.fitness - avgFitness) * (toMeasure.fitness - avgFitness);
        }

        stdDeviation = Math.sqrt(sum / set.size());

        max = 0.0;
        min = 1.0;

        it = set.iterator();
        while(it.hasNext()) {

            toMeasure = it.next();

            if (toMeasure.fitness > max) max = toMeasure.fitness;
            if (toMeasure.fitness < min) min = toMeasure.fitness;
        }

        range = max - min;

        String output = "";
        output += ("Average Fitness: " + df.format(avgFitness));
        output += ("\nStandard Deviation: " + df.format(stdDeviation));
        output += ("\nMaximum Fitness: " + df.format(max));
        output += ("\nMinimum Fitness: " + df.format(min));
        output += ("\nRange: " + df.format(range));

        return output;
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
