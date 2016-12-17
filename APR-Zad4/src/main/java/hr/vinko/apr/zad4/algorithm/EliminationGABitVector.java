package hr.vinko.apr.zad4.algorithm;

import hr.vinko.apr.zad4.fitness.IFitnessFunction;
import hr.vinko.apr.zad4.operator.crossover.ICrossover;
import hr.vinko.apr.zad4.operator.mutation.IMutation;
import hr.vinko.apr.zad4.operator.selection.ISelection;
import hr.vinko.apr.zad4.population.Population;
import hr.vinko.apr.zad4.solution.BitVectorSolution;
import hr.vinko.apr.zad4.solution.FloatingPointSolution;

import java.util.Arrays;

/**
 * Created by vkolobara on 17.12.16..
 */
public class EliminationGABitVector extends EliminationGeneticAlgorithm<BitVectorSolution> {


    public EliminationGABitVector(ICrossover<BitVectorSolution>[] crossovers, IMutation<BitVectorSolution>[] mutations, ISelection<BitVectorSolution> selection, double termVal, int maxEvaluations, int maxIter, int popSize) {
        super(crossovers, mutations, selection, termVal, maxEvaluations, maxIter, popSize);
    }

    @Override
    Population<BitVectorSolution> randomizePopulation(IFitnessFunction f, Object... parameters) {
        int[] variableCodeLength = calculateBitvectorlength((int[]) parameters[0], (double[]) parameters[1], (double[]) parameters[2]);

        int size = 0;
        for (int i = 0; i < variableCodeLength.length; i++) {
            size += variableCodeLength[i];
        }
        Population<BitVectorSolution> pop = new Population<>(popSize);

        while (!pop.isFull()) {
            BitVectorSolution sol = new BitVectorSolution(size, variableCodeLength, (double[]) parameters[1], (double[]) parameters[2]);
            sol.randomize();
            sol.setFitness(f.fitnessValue(sol));
            pop.addSolution(sol);
        }
        return pop;
    }

    private int[] calculateBitvectorlength(int[] precision, double[] min, double[] max) {
        int[] variableCodeLength = new int[precision.length];

        for (int i = 0; i < variableCodeLength.length; i++) {
            variableCodeLength[i] = (int) Math.ceil(Math.log(1 + (max[i] - min[i]) * Math.pow(10, precision[i])) / Math.log(2));
        }


        return variableCodeLength;
    }
}
