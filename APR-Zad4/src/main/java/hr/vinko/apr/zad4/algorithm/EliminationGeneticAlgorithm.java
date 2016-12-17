package hr.vinko.apr.zad4.algorithm;

import hr.vinko.apr.zad4.fitness.AbstractFitnessFunction;
import hr.vinko.apr.zad4.fitness.FitnessType;
import hr.vinko.apr.zad4.fitness.IFitnessFunction;
import hr.vinko.apr.zad4.operator.crossover.ICrossover;
import hr.vinko.apr.zad4.operator.mutation.IMutation;
import hr.vinko.apr.zad4.operator.selection.ISelection;
import hr.vinko.apr.zad4.population.Population;
import hr.vinko.apr.zad4.solution.AbstractSolution;
import hr.vinko.apr.zad4.solution.BitVectorSolution;
import hr.vinko.apr.zad4.solution.ISolution;

import java.util.Arrays;

public abstract class EliminationGeneticAlgorithm<T extends ISolution<?>> implements IAlgorithm<T> {
    ICrossover<T>[] crossovers;
    IMutation<T>[] mutations;
    ISelection<T> selection;
    double termVal;
    int maxEvaluations;
    int popSize;
    int maxIter;

    public EliminationGeneticAlgorithm(ICrossover<T>[] crossovers, IMutation<T>[] mutations, ISelection<T> selection, double termVal, int maxEvaluations, int maxIter, int popSize) {
        this.crossovers = crossovers;
        this.mutations = mutations;
        this.selection = selection;
        this.termVal = termVal;
        this.maxEvaluations = maxEvaluations;
        this.popSize = popSize;
        this.maxIter = maxIter;
    }

    @Override
    public T solve(AbstractFitnessFunction f, Object... parameters) {
        Population<T> population = randomizePopulation(f, parameters);

        int iter = 0;

        double best = population.getBest(f.getFitnessType()).getFitness();

        while (f.getNumEvaluations() <= maxEvaluations && best > termVal && iter++ < maxIter) {

            T[] selected = selection.select(population, f.getFitnessType());
            population.removeSolution(selected[2]);

            while (!population.isFull()) {
                T child = cross(selected[0], selected[1]);
                child = mutate(child);
                child.setFitness(f.fitnessValue(child));
                population.addSolution(child);
            }


//            double val = population.getBest(f.getFitnessType()).getFitness();
//            double factor = f.getFitnessType().equals(FitnessType.FITNESS_MAX) ? 1 : -1;
//
//            iter++;
//            if (factor * Double.compare(val, best) > 0) {
//                best = val;
//
//                System.out.println(population.getBest(f.getFitnessType()));
//                System.out.println("New best at " + iter + " : " + best);
//            }


        }

//        System.out.println(f.getNumEvaluations());
//        System.out.println(iter);
//        System.out.println(termVal);

        return population.getBest(f.getFitnessType());
    }

    private T cross(T p1, T p2) {
        return crossovers[rand.nextInt(crossovers.length)].mate(p1, p2);
    }

    private T mutate(T child) {
        return mutations[rand.nextInt(mutations.length)].mutate(child);
    }

    abstract Population<T> randomizePopulation(IFitnessFunction f, Object... parameters);

}
