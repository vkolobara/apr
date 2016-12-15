package hr.vinko.apr.zad4.solution;

import java.util.Arrays;

public class BitVectorSolution extends AbstractSolution<Boolean> {

	private int[] variableCodeLength;

	public BitVectorSolution(int size, int[] variableCodeLength, double[] min, double[] max) {
		super(min, max);
		solution = new Boolean[size];
		this.variableCodeLength = Arrays.copyOf(variableCodeLength, variableCodeLength.length);
	}

	@Override
	public ISolution<Boolean> newLikeThis() {
		BitVectorSolution sol = new BitVectorSolution(solution.length, variableCodeLength, min, max);
		return sol;
	}

	@Override
	public ISolution<Boolean> duplicate() {
		BitVectorSolution dup = (BitVectorSolution) newLikeThis();
		dup.solution = Arrays.copyOf(solution, solution.length);
		dup.setFitness(getFitness());
		return dup;
	}

	@Override
	public void randomize() {
		for (int i = 0; i < solution.length; i++) {
			solution[i] = rand.nextBoolean();
		}
	}

	@Override
	public double[] decode() {
		int offset = 0;
		double[] x = new double[variableCodeLength.length];
		for (int i = 0; i < x.length; i++) {
			for (int index = 0; index < variableCodeLength[i]; index++) {
				if (solution[index + offset]) {
					x[index + offset] += Math.pow(2, variableCodeLength[i] - 1 - index - offset);
				}
			}
			offset += variableCodeLength[i];
		}
		return x;
	}

}
