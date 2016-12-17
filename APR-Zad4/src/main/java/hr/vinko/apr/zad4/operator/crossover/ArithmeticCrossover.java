package hr.vinko.apr.zad4.operator.crossover;

import hr.vinko.apr.zad4.solution.FloatingPointSolution;

/**
 * Created by vkolobara on 17.12.16..
 */
public class ArithmeticCrossover implements ICrossover<FloatingPointSolution> {


    @Override
    public FloatingPointSolution mate(FloatingPointSolution parent1, FloatingPointSolution parent2) {

        FloatingPointSolution child = (FloatingPointSolution) parent1.newLikeThis();

        double a = rand.nextDouble();

        for (int i = 0; i < child.solution.length; i++) {
            child.solution[i] = a * parent1.solution[i] + (1 - a) * parent2.solution[i];
        }

        return child;
    }
}
