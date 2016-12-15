package hr.vinko.apr.zad4.operator.mutation;

import hr.vinko.apr.zad4.solution.FloatingPointSolution;

public class GaussMutation implements IMutation<FloatingPointSolution> {

	private double mu;
	private double sigma;
	private double p;

	public GaussMutation(double p, double mu, double sigma) {
		super();
		this.p = p;
		this.mu = mu;
		this.sigma = sigma;
	}

	@Override
	public FloatingPointSolution mutate(FloatingPointSolution solution) {
		FloatingPointSolution child = (FloatingPointSolution) solution.duplicate();

		for (int i = 0; i < child.solution.length; i++) {
			if (rand.nextDouble() <= p)
				child.solution[i] += rand.nextGaussian() * sigma + mu;
		}

		return child;
	}

}
