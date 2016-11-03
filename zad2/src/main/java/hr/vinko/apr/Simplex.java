package hr.vinko.apr;

import java.util.ArrayList;
import java.util.List;

import hr.vinko.apr.zad1.Matrix;

public class Simplex {

	public static double epsilon = 1e-6;

	public static Matrix simplex_algorithm(Matrix x0, double step, double alpha, double beta, double gamma,
			double sigma, IFunction f) {
		List<Matrix> simplex = new ArrayList<>();

		simplex.add(x0);
		for (int i = 0; i < x0.getRows(); i++) {
			Matrix stepM = new Matrix(x0.getRows(), 1);
			stepM.setAt(i, 0, step);
			simplex.add(x0.add(stepM));
		}
		
		double error = Double.MAX_VALUE;

		while(true) {

			int l = IExtremeFinder.findExtremeIndex(simplex, f, IExtremeFinder.ExtremeType.MIN);
			int h = IExtremeFinder.findExtremeIndex(simplex, f, IExtremeFinder.ExtremeType.MAX);

			Matrix xL = simplex.get(l);

			Matrix centroid = calcCentroid(simplex, h);
			System.out.println("Centroid : " + centroid);
			System.out.println("F(xC) : " + f.getValueAt(centroid));
			System.out.println("F(xL) : " + f.getValueAt(xL));
			System.out.println();
			
			if (Math.abs(error - f.getValueAt(centroid)) < epsilon) return centroid;
			error = f.getValueAt(centroid);

			Matrix xR = reflexion(centroid, simplex.get(h), alpha);

			if (f.getValueAt(xR) < f.getValueAt(xL)) {
				Matrix xE = expansion(centroid, xR, gamma);
				if (f.getValueAt(xE) < f.getValueAt(xL)) {
					simplex.set(h, xE);
				} else {
					simplex.set(h, xR);
				}
			} else {
				if (greaterThanAll(xR, simplex, h, f)) {
					if (f.getValueAt(xR) < f.getValueAt(simplex.get(h))) {
						simplex.set(h, xR);
					}
					Matrix xK = contraction(centroid, simplex.get(h), beta);
					if (f.getValueAt(xK) < f.getValueAt(simplex.get(h))) {
						simplex.set(h, xK);
					} else {
						moveTowardTheBest(simplex, xL, sigma);
					}
				} else {
					simplex.set(h, xR);
				}
			}
		}

	}

	private static void moveTowardTheBest(List<Matrix> simplex, Matrix xL, double sigma) {
		simplex.forEach(x -> x = x.add(xL).scalarMultiply(sigma));
	}

	private static Matrix contraction(Matrix centroid, Matrix xH, double beta) {
		return centroid.scalarMultiply(1 - beta).add(xH.scalarMultiply(beta));
	}

	private static boolean greaterThanAll(Matrix xR, List<Matrix> simplex, int h, IFunction f) {
		for (int i = 0; i < simplex.size(); i++) {
			if (i != h) {
				if (f.getValueAt(xR) <= f.getValueAt(simplex.get(i)))
					return false;
			}
		}
		return true;
	}

	private static Matrix expansion(Matrix centroid, Matrix xR, double gamma) {
		return centroid.scalarMultiply(1 - gamma).add(xR.scalarMultiply(gamma));
	}

	private static Matrix reflexion(Matrix centroid, Matrix worst, double alpha) {
		return centroid.scalarMultiply(1 + alpha).substract(worst.scalarMultiply(alpha));
	}

	private static Matrix calcCentroid(List<Matrix> simplex, int h) {
		Matrix centroid = new Matrix(simplex.get(0).getRows(), 1);
		for (Matrix x : simplex) {
			centroid = centroid.add(x);
		}
		centroid = centroid.substract(simplex.get(h));
		return centroid.scalarMultiply(1.0 / (simplex.size() - 1));
	}

	private interface IExtremeFinder {
		enum ExtremeType {
			MIN, MAX
		}

		public static int findExtremeIndex(List<Matrix> values, IFunction f, ExtremeType type) {
			int index = -1;

			if (type == ExtremeType.MIN) {
				double min = Double.MAX_VALUE;
				for (int i = 0; i < values.size(); i++) {
					double value = f.getValueAt(values.get(i));
					if (value < min) {
						index = i;
						min = value;
					}
				}
			} else {
				double max = Double.MIN_VALUE;
				for (int i = 0; i < values.size(); i++) {
					double value = f.getValueAt(values.get(i));
					if (value > max) {
						index = i;
						max = value;
					}
				}
			}

			return index;

		}
	}

}
