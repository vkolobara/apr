package hr.vinko.apr.zad3.algorithm;

import java.util.Arrays;
import java.util.List;

import hr.vinko.apr.HookeJeves;
import hr.vinko.apr.Simplex;
import hr.vinko.apr.zad3.constraints.IConstraint;
import hr.vinko.apr.zad3.constraints.ImplicitConstraint;
import hr.vinko.apr.zad3.constraints.ImplicitConstraintEquality;
import hr.vinko.apr.zad3.constraints.ImplicitConstraintInequality;
import hr.vinko.apr.zad3.function.IFunction;

public class TransformationMixed implements IAlgorithm {

	public TransformationMixed(double t, double epsilon, List<ImplicitConstraintEquality> equalityConstraints,
			List<ImplicitConstraintInequality> inequalityConstraints, int iterWithoutProgress) {
		super();
		this.t = t;
		this.epsilon = epsilon;
		this.equalityConstraints = equalityConstraints;
		this.inequalityConstraints = inequalityConstraints;
		this.iterWithoutProgress = iterWithoutProgress;
	}

	private double t;
	private double epsilon;
	private List<ImplicitConstraintEquality> equalityConstraints;
	private List<ImplicitConstraintInequality> inequalityConstraints;
	private int iterWithoutProgress;

	@Override
	public double[] solve(double[] x0, IFunction f) {

		double[] x = findInnerPoint(x0);

		double bestF = Double.MAX_VALUE;
		int iters = 0;
		double curT = t;
		while (true) {
			double t = curT;
			hr.vinko.apr.IFunction u = new hr.vinko.apr.IFunction() {
				public double getValueAt(double... x) {
					double sum = 0;

					sum += f.getValueAt(x);

					for (ImplicitConstraintInequality cons : inequalityConstraints) {
						double value = Math.log(cons.getF().getValueAt(x));
						if (Double.isNaN(value))
							value = -100_000_000_000.0;
						sum -= 1.0 / t * value;
					}

					for (ImplicitConstraintEquality cons : equalityConstraints) {
						sum += t * Math.pow(cons.getF().getValueAt(x), 2);
					}

					return sum;
				}
			};

			Simplex simplex = new Simplex();
			double[] solution = simplex.solve(x, u);
			int i = 0;
			for (; i < solution.length; i++) {
				if (Math.abs(solution[i] - x[i]) > epsilon)
					break;
			}
			x = solution;

			double value = u.getValueAt(x);

			if (value < bestF) {
				bestF = value;
				iters = 0;
			}
			iters++;
			if (iters >= iterWithoutProgress)
				break;
			if (i == solution.length)
				break;
			curT *= 10;
			
		}

		return x;
	}

	private double[] findInnerPoint(double[] x0) {
		double[] innerX = Arrays.copyOf(x0, x0.length);

		boolean satisfies = true;

		for (ImplicitConstraint cons : inequalityConstraints) {
			if (!cons.check(x0)) {
				satisfies = false;
				break;
			}
		}

		if (!satisfies) {

			hr.vinko.apr.IFunction G = new hr.vinko.apr.IFunction() {
				public double getValueAt(double... x) {
					double sum = 0;

					for (ImplicitConstraint cons : inequalityConstraints) {
						if (!cons.check(x)) {
							sum -= cons.getF().getValueAt(x);
						}
					}
					return sum;
				}

			};

			Simplex simplex = new Simplex();
			innerX = simplex.solve(x0, G);

		}

		return innerX;

	}

}
