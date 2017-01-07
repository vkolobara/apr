package hr.vinko.apr.zad5;

import java.io.File;
import java.io.IOException;

import hr.vinko.apr.zad1.Matrix;
import hr.vinko.apr.zad5.algorithm.NumericalIntegrationAlgorithm;
import hr.vinko.apr.zad5.function.TrapezoidMethod;

public class Main {
	public static void main(String[] args) throws IOException, InterruptedException {

		double[][] a = new double[][] { { 0, 1 }, { -1, 0 } };

		double T = 0.1;
		double tMax = 10;

		Matrix A = new Matrix(2, 2, a);
		Matrix B = new Matrix(2, 2);
		NumericalIntegrationAlgorithm alg = new NumericalIntegrationAlgorithm(new TrapezoidMethod(), A, B);

		Matrix x = new Matrix(2, 1);
		x.setColumn(0, new double[] { 1, 1 });

		alg.solve(x, T, tMax, true, true);

		runOctaveScript();

	}

	private static void runOctaveScript() {
		try {
			Process p = Runtime.getRuntime().exec("octave plotStateVariables.m", null,
					new File(System.getProperty("user.dir") + "/data"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
