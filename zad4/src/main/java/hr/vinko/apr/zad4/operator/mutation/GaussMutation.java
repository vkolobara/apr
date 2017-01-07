package hr.vinko.apr.zad4.operator.mutation;

import hr.vinko.apr.zad4.solution.FloatingPointSolution;

public class GaussMutation implements IMutation<FloatingPointSolution> {

    private double p;
    private double sigma;

    public GaussMutation(double p, double sigma) {
        super();
        this.p = p;
        this.sigma = sigma;
    }

    @Override
    public FloatingPointSolution mutate(FloatingPointSolution solution) {
        FloatingPointSolution child = (FloatingPointSolution) solution.duplicate();

        for (int i = 0; i < child.solution.length; i++) {
            if (rand.nextDouble() <= p)
                child.solution[i] += rand.nextGaussian() * sigma;
        }

        return child;
    }

}
