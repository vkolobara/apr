package hr.vinko.apr.zad5.algorithm;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import hr.vinko.apr.zad1.Matrix;
import hr.vinko.apr.zad5.function.DifferentialEquationMethod;

public class NumericalIntegrationAlgorithm {

	private DifferentialEquationMethod diffMethod;
	private Matrix A, B;

	public NumericalIntegrationAlgorithm(DifferentialEquationMethod diffMethod, Matrix a, Matrix b) {
		super();
		this.diffMethod = diffMethod;
		A = a;
		B = b;
	}

	public void solve(Matrix x, double T, double tMax, boolean printResult, boolean plotResult) throws IOException {

		Matrix xK = x;

		double[] ts = new double[(int) Math.ceil(tMax / T) + 1];
		ts[0] = 0;

		double[][] points = new double[ts.length][x.getRows()];
		for (int i = 0; i < x.getRows(); i++) {
			points[0][i] = x.getAt(i, 0);
		}

		int index = 1;

		for (double t = T; t <= tMax + T; t += T, index++) {
			xK = diffMethod.getValue(A, B, xK, t, T);
			System.out.println(t + "\n" + xK);

			ts[index] = t;
			for (int i = 0; i < xK.getRows(); i++) {
				points[index][i] = xK.getAt(i, 0);
			}
		}

		if (plotResult)
			writeToFile(ts, points);

	}

	private void writeToFile(double[] ts, double[][] points) throws IOException {

		for (int i = 1; i <= points[0].length; i++) {
			try (Writer writer = new FileWriter("data/var" + i + ".out")) {
				for (int j = 0; j < ts.length; j++) {
					writer.write(ts[j] + " " + points[j][i - 1] + "\n");
				}
			}

		}

	}

}
