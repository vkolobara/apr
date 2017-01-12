package hr.vinko.apr.zad5;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

import hr.vinko.apr.zad1.Matrix;
import hr.vinko.apr.zad5.algorithm.NumericalIntegrationAlgorithm;
import hr.vinko.apr.zad5.function.DifferentialEquationMethod;
import hr.vinko.apr.zad5.function.RungeKutta4Method;
import hr.vinko.apr.zad5.function.TrapezoidMethod;

public class Main {
	public static void main(String[] args) throws IOException, InterruptedException {

		if (args.length != 1) {
			throw new IllegalArgumentException("Potrebno predati config datoteku kao jedini argument");
		}

		Properties properties = new Properties();
		try(Reader r = new FileReader(args[0])) {
			properties.load(r);
		}
		
		runAlgorithm(properties);
	}

	private static void runAlgorithm(Properties properties) throws IOException {
		String aPath = properties.getProperty("A", "data/A.txt");
		String bPath = properties.getProperty("B", null);
		String xPath = properties.getProperty("x", "data/X.txt");
		String method = properties.getProperty("method", "rk4");
		String TS = properties.getProperty("T", "0.1");
		String tMaxS = properties.getProperty("tMax", "10");
		
		double T = Double.parseDouble(TS);
		double tMax = Double.parseDouble(tMaxS);
		
		
		Matrix A = new Matrix(aPath);
		Matrix B = new Matrix(A.getRows(), A.getColumns());
		
		if (bPath != null) {
			B = new Matrix(bPath);
		}
		
		Matrix x = new Matrix(xPath);
		
		DifferentialEquationMethod diffMethod;
		
		if ("rk4".equals(method)) {
			diffMethod = new RungeKutta4Method();
		} else {
			diffMethod = new TrapezoidMethod();
		}
		
		NumericalIntegrationAlgorithm alg = new NumericalIntegrationAlgorithm(diffMethod, A, B);
		alg.solve(x, T, tMax, true, true);
		
		
	}

	private static void runOctaveScript(String fileName) {
		String[] fileParts = fileName.split("\\.");
		String ext = fileParts[fileParts.length - 1];

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
