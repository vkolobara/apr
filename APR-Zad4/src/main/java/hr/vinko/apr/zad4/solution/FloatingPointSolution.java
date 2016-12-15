package hr.vinko.apr.zad4.solution;

import java.util.Arrays;

public class FloatingPointSolution extends AbstractSolution<Double> {

	public FloatingPointSolution(int size, double[] min, double[] max) {
		super(min, max);
		solution = new Double[size];
	}

	@Override
	public ISolution<Double> newLikeThis() {
		FloatingPointSolution sol = new FloatingPointSolution(solution.length, min, max);
		return sol;
	}

	@Override
	public ISolution<Double> duplicate() {
		FloatingPointSolution dup = (FloatingPointSolution) newLikeThis();
		dup.solution = Arrays.copyOf(solution, solution.length);
		dup.setFitness(getFitness());
		return dup;
	}

	@Override
	public void randomize() {
		for (int i = 0; i < solution.length; i++) {
			solution[i] = rand.nextDouble() * (max[i] - min[i]) + min[i];
		}
	}

	@Override
	public double[] decode() {

		double[] x = new double[solution.length];

		for (int i = 0; i < solution.length; i++) {
			x[i] = solution[i];
		}

		return x;
	}

}
