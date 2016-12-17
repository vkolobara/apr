package hr.vinko.apr.zad4.operator.crossover;

import hr.vinko.apr.zad4.fitness.FitnessType;
import hr.vinko.apr.zad4.solution.FloatingPointSolution;

/**
 * Created by vkolobara on 17.12.16..
 */
public class HeuristicCrossover implements ICrossover<FloatingPointSolution> {

    private FitnessType fitnessType;

    public HeuristicCrossover(FitnessType fitnessType) {
        this.fitnessType = fitnessType;
    }

    @Override
    public FloatingPointSolution mate(FloatingPointSolution parent1, FloatingPointSolution parent2) {

        FloatingPointSolution better;
        FloatingPointSolution worse;

        if (FitnessType.FITNESS_MAX.equals(fitnessType)) {
            if (parent1.getFitness() > parent2.getFitness()) {
                better = parent1;
                worse = parent2;
            } else {
                better = parent2;
                worse = parent1;
            }
        } else {
            if (parent1.getFitness() < parent2.getFitness()) {
                better = parent1;
                worse = parent2;
            } else {
                better = parent2;
                worse = parent1;
            }
        }

        double[] min = parent1.getMin();
        double[] max = parent1.getMax();

        FloatingPointSolution child;
        child = (FloatingPointSolution) parent1.newLikeThis();
        double a = rand.nextDouble();

        for (int i = 0; i < child.solution.length; i++) {
            child.solution[i] = a * (better.solution[i] - worse.solution[i]) + better.solution[i];
            child.solution[i] = Math.max(min[i], child.solution[i]);
            child.solution[i] = Math.min(max[i], child.solution[i]);
        }


        return child;
    }
}
