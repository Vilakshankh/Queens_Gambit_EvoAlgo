import java.io.Console;
import java.lang.Math;
import java.util.*;

/* YOU NEED TO ADD YOUR CODE TO THIS CLASS, REMOVING ALL DUMMY CODE
 *
 * DO NOT CHANGE THE NAME OR SIGNATURE OF ANY OF THE EXISTING METHODS
 * (Signature includes parameter types, return types and being static)
 *
 * You can add private methods to this class if it makes your code cleaner,
 * but this class MUST work with the UNMODIFIED Tester.java class.
 *
 * This is the ONLY class that you can submit for your assignment.
 *
 * MH 2021
 */
public class Queens
{
    private static int boardSize = 10;
    
    // creates a valid genotype with random values
    public static Integer [] randomGenotype()
    {
        Integer [] genotype = new Integer [10];

        
        Random random = new Random();

        for (int i = 0; i < genotype.length; i++) {
            genotype[i] = random.nextInt(10);
        }
        
        
        return genotype;
    }
    
    // swaps 2 genes in the genotype
    // the swap happens with probability p, so if p = 0.8
    // then 8 out of 10 times this method is called, a swap happens
    public static Integer[] swapMutate(Integer[] genotype, double p)
    {

        Random random = new Random();
        if (random.nextDouble() < p) {
            int randomGeneLocation_one = random.nextInt(10);
            int randomGeneLocation_two = random.nextInt(10);
    
            int geneOne = genotype[randomGeneLocation_one];
            int geneTwo = genotype[randomGeneLocation_two];
    
            genotype[randomGeneLocation_one] = geneTwo;
            genotype[randomGeneLocation_two] = geneOne;
        }
        return genotype;
    }
    
    // creates 2 child genotypes using the 'cut-and-crossfill' method
    public static Integer[][] cutAndCrossfill(Integer[] parent0, Integer[] parent1)
    {

        Integer [][] children = new Integer [2][boardSize];
        // Select a random cut point
        

        //creating two child genotypes, which will initially be identical to their parent genotypes.
        children[0] = Arrays.copyOf(parent0, boardSize);
        children[1] = Arrays.copyOf(parent1, boardSize);

        //Choose a random index (i.e. gene) in the parent genotypes where the crossover will occur.
        Random random = new Random();
        int crossover_point = random.nextInt(boardSize - 1);

        for(int i =0; i<crossover_point+1; i++)
        {
            children[0][i] = parent0[i];
            children[1][i] = parent1[i];

        }

        for (int c = 0; c < 2; c++) 
        { // Iterate over the two child genotypes
             
            for(int i = crossover_point+1; i < boardSize ; i++)
            {
                boolean alreadyInChild = false;
                for (int j = 0; j < boardSize; j++) 
                {
                    if (children[c][j] == parent1[i]) 
                    {
                        alreadyInChild = true;
                        break;
                    }
                }

                if (!alreadyInChild) 
                {
                    children[c][i] = parent1[i];
                }

                else 
                {
                    // Find the next gene in the parent genotype that is not already in the child genotype
                    int nextGene = (i + 1) % boardSize;
                    while (true) 
                    {
                        boolean alreadyInChild2 = false;
                        for (int j = 0; j < boardSize; j++) 
                        {
                            if (children[c][j] == parent1[nextGene]) 
                            {
                                alreadyInChild2 = true;
                                break;
                            }
                        }
                        if (!alreadyInChild2) 
                        {
                            children[c][i] = parent1[nextGene];
                            break;
                        }
                        nextGene = (nextGene + 1) % boardSize;
                    }
                }

            }


            
        }

        return children;
    }
    
    // calculates the fitness of an individual
    public static int measureFitness(Integer [] genotype)
    {
        /* The initial fitness is the maximum pairs of queens
         * that can be in check (all possible pairs in check).
         * So we are using it as the maximum fitness value.
         * We deduct 1 from this value for every pair of queens
         * found to be in check.
         * So, the lower the score, the lower the fitness.
         * For a 10x10 board the maximum fitness is 45 (no checks),
         * and the minimum fitness is 0 (all queens in a line).
         */
        int fitness = (int) (0.5 * boardSize * (boardSize - 1));
        for (int i = 0; i < boardSize; i++) 
        {
            for (int j = i + 1; j < boardSize; j++) 
            {
                if (genotype[i] == genotype[j] ||
                    genotype[i] - genotype[j] == i - j ||
                    genotype[i] - genotype[j] == j - i) 
                {
                    fitness--;
                }
            }
        }
        // YOUR CODE GOES HERE
        
        return fitness;
    }
}
