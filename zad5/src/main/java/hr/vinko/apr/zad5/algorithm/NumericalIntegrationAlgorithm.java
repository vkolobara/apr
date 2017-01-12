package hr.vinko.apr.zad5.algorithm;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

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

		List<Double> ts = new ArrayList<>();
		ts.add(0.);

		List<double[]> points = new ArrayList<>();
		points.add(xK.getColumn(0));

		int index = 1;

		for (double t = T; t < tMax + T; t += T, index++) {
			xK = diffMethod.getValue(A, B, xK, t, T);

			if (printResult)
				System.out.println(t + "\n" + xK);
			
			ts.add(t);
			points.add(xK.getColumn(0));
		}

		if (plotResult)
			writeToFile(ts, points);

	}

	private void writeToFile(List<Double> ts, List<double[]> points) throws IOException {

		for (int i = 1; i <= points.get(0).length; i++) {
			try (Writer writer = new FileWriter("data/var" + i + ".out")) {
				for (int j = 0; j < ts.size(); j++) {
					writer.write(ts.get(j) + " " + points.get(j)[i - 1] + "\n");
				}
			}

		}

	}

}
