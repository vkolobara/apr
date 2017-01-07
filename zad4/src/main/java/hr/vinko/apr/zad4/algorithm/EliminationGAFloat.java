package hr.vinko.apr.zad4.algorithm;

import hr.vinko.apr.zad4.fitness.IFitnessFunction;
import hr.vinko.apr.zad4.operator.crossover.ICrossover;
import hr.vinko.apr.zad4.operator.mutation.IMutation;
import hr.vinko.apr.zad4.operator.selection.ISelection;
import hr.vinko.apr.zad4.population.Population;
import hr.vinko.apr.zad4.solution.FloatingPointSolution;

/**
 * Created by vkolobara on 17.12.16..
 */
public class EliminationGAFloat extends EliminationGeneticAlgorithm<FloatingPointSolution> {

    public EliminationGAFloat(ICrossover[] crossovers, IMutation[] mutations, ISelection selection, double termVal, int maxEvaluations, int maxIter, int popSize) {
        super(crossovers, mutations, selection, termVal, maxEvaluations, maxIter, popSize);
    }

    @Override
    Population<FloatingPointSolution> randomizePopulation(IFitnessFunction f, Object... parameters) {
        Population<FloatingPointSolution> pop = new Population<>(popSize);

        while (!pop.isFull()) {
            FloatingPointSolution sol = new FloatingPointSolution((Integer) parameters[0], (double[]) parameters[1], (double[]) parameters[2]);
            sol.randomize();
            sol.setFitness(f.fitnessValue(sol));
            pop.addSolution(sol);
        }
        return pop;
    }
}
