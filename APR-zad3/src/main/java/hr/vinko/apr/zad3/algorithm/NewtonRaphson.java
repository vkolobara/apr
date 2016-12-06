package hr.vinko.apr.zad3.algorithm;

import java.util.Arrays;

import hr.vinko.apr.GoldenSection;
import hr.vinko.apr.zad1.Matrix;
import hr.vinko.apr.zad3.function.IFunction;

public class NewtonRaphson implements IAlgorithm {

	private boolean useGoldenSection;

	private double epsilon;
	private int iterWithoutProgress;

	public NewtonRaphson(boolean useGoldenSection, double epsilon, int iterWithoutProgress) {
		super();
		this.useGoldenSection = useGoldenSection;
		this.epsilon = epsilon;
		this.iterWithoutProgress = iterWithoutProgress;
	}

	@Override
	public double[] solve(double[] x0, IFunction f) {
		double[] xCur = Arrays.copyOf(x0, x0.length);

		double bestF = Double.MAX_VALUE;
		int iters = 0;

		while (true) {

			double val = f.getValueAt(xCur);

			if (val < bestF) {
				bestF = val;
				iters = 0;
			}
			iters++;

			if (iters >= iterWithoutProgress)
				break;

			Matrix H = new Matrix(xCur.length, xCur.length, f.getHessianAt(xCur));
			Matrix DF = new Matrix(xCur.length, 1);
			double[] grad = f.getDerivationAt(xCur);
			for (int i = 0; i < grad.length; i++) {
				DF.setAt(i, 0, -grad[i]);
			}

			Matrix[] LUP = Matrix.LUPDecomposition(H, false);

			double[] dx = LUP[0].backwardSubstitution(LUP[0].forwardSubstitution(LUP[1].multiply(DF))).getColumn(0);
			double lambda = 1;

			if (norm(dx) < epsilon)
				break;

			if (useGoldenSection) {
				GoldenSection gs = new GoldenSection();

				hr.vinko.apr.IFunction f1 = new hr.vinko.apr.IFunction() {
					public double getValueAt(double... x) {
						double[] x1 = Arrays.copyOf(xCur, xCur.length);
						for (int i = 0; i < dx.length; i++) {
							x1[i] += x[0] * dx[i];
						}
						return f.getValueAt(x1);

					}

				};
				double[] sol = gs.solve(new double[] { 0 }, f1);
				lambda = (sol[0] + sol[1]) / 2;
			}

			for (int i = 0; i < xCur.length; i++) {
				xCur[i] += lambda * dx[i];
			}

		}

		return xCur;

	}

	private double norm(double[] x) {
		double sum = 0;
		for (int i = 0; i < x.length; i++) {
			sum += x[i] * x[i];
		}

		return Math.sqrt(sum);
	}
}
