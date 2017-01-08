package hr.vinko.apr.zad5;

import java.io.File;
import java.io.IOException;

import hr.vinko.apr.zad1.Matrix;
import hr.vinko.apr.zad5.algorithm.NumericalIntegrationAlgorithm;
import hr.vinko.apr.zad5.function.RungeKutta4Method;

public class Main {
	public static void main(String[] args) throws IOException, InterruptedException {

		double[][] a = new double[][] { { 0, 1 }, { -200, -102 } };

		double T = 0.01;
		double tMax = 2;

		Matrix A = new Matrix(2, 2, a);
		Matrix B = new Matrix(2, 2);
		NumericalIntegrationAlgorithm alg = new NumericalIntegrationAlgorithm(new RungeKutta4Method(), A, B);

		Matrix x = new Matrix(2, 1);
		x.setColumn(0, new double[] { 1, -2 });

		alg.solve(x, T, tMax, true, true);

		runOctaveScript(args[0]);

	}

	private static void runOctaveScript(String fileName) {
		String[] fileParts = fileName.split("\\.");
		String ext = fileParts[fileParts.length-1];
		
		StringBuilder cmd = new StringBuilder();
		
		if ("bat".equals(ext)) {
			cmd.append("cmd /C ");
		} 
		
		try {
			Process p = Runtime.getRuntime().exec(cmd.toString() + fileName, null,
					new File(System.getProperty("user.dir") + "/data"));
			p.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
