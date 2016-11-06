package hr.vinko.apr;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Main {

	private static final double DEF_STEP = 1;
	private static final double DEF_ALPHA = 1;
	private static final double DEF_BETA = 0.5;
	private static final double DEF_GAMMA = 2;
	private static final double DEF_SIGMA = 0.5;
	private static final double DEF_OFFSET = 0;
	private static final double DEF_DX = 0.5;
	private static final double DEF_EPS = 1e-6;

	public static void main(String[] args) {

		if (args.length == 1) {
			switch (args[0]) {
			case "zad1":
				zad1();
				return;
			case "zad2":
				zad2();
				return;
			case "zad3":
				zad3();
				return;
			case "zad4":
				zad4();
				return;
			case "zad5":
				zad5();
				return;
			default:

			}
		}

		Options options = createOptions();
		if (args.length == 0) {
			HelpFormatter help = new HelpFormatter();
			help.printHelp("apr2", options, true);
		} else {
			try {
				parseAndRun(options, args);
			} catch (ParseException e) {
				System.err.println(e.getMessage());
			}
		}

	}

	private static void zad5() {

		PrintStream dummyStream = new PrintStream(new OutputStream() {
			public void write(int b) {
				// NO-OP
			}
		});

		System.setOut(dummyStream);

		Random rand = new Random();
		AbstractFunction f6 = generateF6(2);
		Simplex simplex = new Simplex();

		double[] x0 = new double[2];

		x0[0] = rand.nextDouble() * (100) - 50;
		x0[1] = rand.nextDouble() * (100) - 50;

		double[] min = simplex.solve(x0, f6);
		System.err.println(Arrays.toString(x0));
		System.err.println(Arrays.toString(min));
		System.err.println(f6.getNumCalls());
		System.err.println(f6.getValueAt(x0));
		f6.clear();

	}

	private static void zad4() {

		PrintStream dummyStream = new PrintStream(new OutputStream() {
			public void write(int b) {
				// NO-OP
			}
		});

		System.setOut(dummyStream);

		Simplex simplex = new Simplex();
		AbstractFunction f1 = generateF1();
		double[] x0 = new double[] { 0.5, 0.5 };
		double[] x02 = new double[] { 20, 20 };

		IntStream.range(1, 21).forEach(step -> {
			simplex.setStep(step);
			double[] min = simplex.solve(x0, f1);
			System.err.println("Step = " + step);
			System.err.print(Arrays.toString(min));
			System.err.println(f1.getNumCalls());
			f1.clear();
			min = simplex.solve(x02, f1);
			System.err.print(Arrays.toString(min));
			System.err.println(f1.getNumCalls());
			System.err.println("===============================================");
			f1.clear();
		});

	}

	private static void zad3() {

		PrintStream dummyStream = new PrintStream(new OutputStream() {
			public void write(int b) {
				// NO-OP
			}
		});

		System.setOut(dummyStream);

		AbstractFunction f4 = generateF4();

		HookeJeves hj = new HookeJeves(2);
		Simplex simplex = new Simplex();

		double[] x0 = new double[] { 5, 5 };

		double[] min = hj.solve(x0, f4);
		System.err.println("HookeJeves: Minimum=" + Arrays.toString(min) + "|Evaluations=" + f4.getNumCalls());
		f4.clear();
		min = simplex.solve(x0, f4);
		System.err.println("Simplex: Minimum=" + Arrays.toString(min) + "|Evaluations=" + f4.getNumCalls());
		f4.clear();

	}

	private static void zad2() {
		PrintStream dummyStream = new PrintStream(new OutputStream() {
			public void write(int b) {
				// NO-OP
			}
		});

		System.setOut(dummyStream);

		Simplex simplex = new Simplex();

		CoordinateSearch cs = new CoordinateSearch(new double[] { 1e-6, 1e-6 });
		HookeJeves hj = new HookeJeves(2);

		IAlgorithm[] algs = new IAlgorithm[] { cs, simplex, hj };

		AbstractFunction f1 = generateF1();
		double[] x01 = new double[] { -1.9, 2 };
		AbstractFunction f2 = generateF2();
		double[] x02 = new double[] { 0.1, 0.3 };
		AbstractFunction f3 = generateF3(5, 0);
		double[] x03 = new double[5];
		AbstractFunction f4 = generateF4();
		double[] x04 = new double[] { 5.1, 1.1 };

		AbstractFunction[] functions = new AbstractFunction[] { f1, f2, f3, f4 };
		double[][] x0s = new double[][] { x01, x02, x03, x04 };

		IAlgorithm[] algs2 = new IAlgorithm[] {
				new CoordinateSearch(new double[] { DEF_EPS, DEF_EPS, DEF_EPS, DEF_EPS, DEF_EPS }), simplex,
				new HookeJeves(5) };

		System.err.println("Function\t|\t\tCoordinate\t\t|\t\tSimplex\t\t\t|\t\tHookeJeves");

		for (int i = 0; i < functions.length; i++) {
			System.err.print("F" + (i + 1));
			if (i == 2) {
				StringBuilder format = new StringBuilder();
				format.append("\t");
				format.append("\t|\t[");
				for (int j = 0; j < 5; j++) {
					format.append("%.0f ");
				}
				format.append("]\t%d");
				for (IAlgorithm alg : algs2) {
					double[] min = alg.solve(x0s[i], functions[i]);
					System.err.print(String.format(format.toString(), min[0], min[1], min[2], min[3], min[4],
							functions[i].getNumCalls()));
					functions[i].clear();
				}
			} else {
				StringBuilder format = new StringBuilder();
				format.append("\t");
				format.append("\t|\t[");
				for (int j = 0; j < 2; j++) {
					format.append("%.2f ");
				}
				format.append("]\t%d");
				for (IAlgorithm alg : algs) {
					double[] min = alg.solve(x0s[i], functions[i]);

					System.err.print(String.format(format.toString(), min[0], min[1], functions[i].getNumCalls()));
					functions[i].clear();
				}

			}
			System.err.println();
		}

	}

	private static void zad1() {

		PrintStream dummyStream = new PrintStream(new OutputStream() {
			public void write(int b) {
				// NO-OP
			}
		});

		System.setOut(dummyStream);

		double[] x0s = new double[] { 10.0, 20.0, 50.0, 100.0 };

		Simplex simplex = new Simplex();
		CoordinateSearch cs = new CoordinateSearch(new double[] { 1e-6 });
		HookeJeves hj = new HookeJeves(1);

		AbstractFunction f = generateF3(1, 2);
		for (double x0 : x0s) {
			System.err.println("===============================================");
			double[] sol = simplex.solve(new double[] { x0 }, f);
			System.err.println("Simplex");
			System.err.println(Arrays.toString(sol));
			System.err.println(f.getNumCalls());
			f.clear();
			System.err.println("===============================================");
			sol = cs.solve(new double[] { x0 }, f);
			System.err.println("Coordinate descent");
			System.err.println(Arrays.toString(sol));
			System.err.println(f.getNumCalls());
			f.clear();
			System.err.println("===============================================");
			sol = hj.solve(new double[] { x0 }, f);
			System.err.println("HookeJeves");
			System.err.println(Arrays.toString(sol));
			System.err.println(f.getNumCalls());
			f.clear();
			System.err.println("===============================================");
		}

	}

	private static void parseAndRun(Options options, String[] args) throws ParseException {
		CommandLineParser parser = new DefaultParser();
		CommandLine cl = parser.parse(options, args);
		IAlgorithm algorithm;
		AbstractFunction f = null;

		Integer dim = Integer.parseInt(cl.getOptionValue("dim"));

		Double step = (Double) cl.getParsedOptionValue("step");
		if (step == null)
			step = DEF_STEP;
		Double alpha = (Double) cl.getParsedOptionValue("alpha");
		if (alpha == null)
			alpha = DEF_ALPHA;
		Double beta = (Double) cl.getParsedOptionValue("beta");
		if (beta == null)
			beta = DEF_BETA;
		Double gamma = (Double) cl.getParsedOptionValue("gamma");
		if (gamma == null)
			gamma = DEF_GAMMA;
		Double sigma = (Double) cl.getParsedOptionValue("sigma");
		if (sigma == null)
			sigma = DEF_SIGMA;
		Double offset = (Double) cl.getParsedOptionValue("offset");
		if (offset == null)
			offset = DEF_OFFSET;

		double[] x0 = null;
		double[] e = null;
		double[] dx = null;
		double[] interval = null;

		String[] x0s = cl.getOptionValues("x0");
		if (x0s != null) {
			x0 = new double[dim];
			for (int i = 0; i < x0.length; i++) {
				x0[i] = Double.parseDouble(x0s[i]);
			}
		}

		String[] es = cl.getOptionValues("eps");
		if (es != null) {
			for (int i = 0; i < x0.length; i++) {
				e = new double[dim];
				e[i] = Double.parseDouble(es[i]);
			}

		}

		String[] dxs = cl.getOptionValues("dx");
		if (dxs != null) {
			dx = new double[dim];
			for (int i = 0; i < x0.length; i++) {
				dx[i] = Double.parseDouble(dxs[i]);
			}
		}

		String[] intervals = cl.getOptionValues("int");
		if (intervals != null) {
			interval = new double[2];
			interval[0] = Double.parseDouble(intervals[0]);
			interval[1] = Double.parseDouble(intervals[1]);
		}

		if (e == null || e.length == 0) {
			e = new double[dim];
			for (int i = 0; i < dim; i++) {
				e[i] = DEF_EPS;
			}
		}
		if (dx == null || dx.length == 0) {
			dx = new double[dim];
			for (int i = 0; i < dim; i++) {
				dx[i] = DEF_DX;
			}
		}

		String alg = (String) cl.getParsedOptionValue("alg");
		switch (alg) {
		case "gss":
			algorithm = new GoldenSection(step, e[0], interval);
			break;
		case "cs":
			algorithm = new CoordinateSearch(e);
			break;
		case "simplex":
			algorithm = new Simplex(e[0], step, alpha, beta, gamma, sigma);
			break;
		default:
			algorithm = new HookeJeves(e, dx);
		}

		String fun = (String) cl.getParsedOptionValue("f");
		switch (fun) {
		case "f1":
			f = generateF1();
			break;
		case "f2":
			f = generateF2();
			break;
		case "f3":
			f = generateF3(dim, offset);
			break;
		case "f4":
			f = generateF4();
			break;
		case "f6":
			f = generateF6(dim);
			break;
		default:
			System.err.println("Funkcija cilja mora biti zadana!");
			System.exit(-1);
		}

		if (x0 == null || x0.length == 0) {
			try (Scanner sc = new Scanner(System.in)) {
				while (true) {
					x0 = new double[dim];
					System.out.print("Input x0 (space separated): ");
					for (int i = 0; i < dim; i++) {
						x0[i] = sc.nextDouble();
					}
					printSolution(algorithm, x0, f);
				}
			}

		} else {
			printSolution(algorithm, x0, f);
		}
	}

	private static void printSolution(IAlgorithm algorithm, double[] x0, AbstractFunction f) {
		System.out.println("===============================================");
		System.out.println("Algoritam: " + algorithm.toString());
		System.out.println("===============================================");
		double[] sol = algorithm.solve(x0, f);
		System.out.println("===============================================");
		System.out.println("Rjesenje: " + Arrays.toString(sol));
		System.out.println("Broj evaluacija funkcija: " + f.getNumCalls());
		System.out.println("===============================================");
		f.clear();
	}

	private static Options createOptions() {
		Options options = new Options();
		Option alg = Option.builder().longOpt("alg").hasArg().type(String.class)
				.desc("algorithm to run (gss, cs, simplex, hj)").required().build();
		Option fun = Option.builder().longOpt("f").hasArg().type(String.class)
				.desc("cost function to use (f1, f2, f3, f4, f6)").required().build();
		Option x0 = Option.builder().longOpt("x0").hasArgs()
				.desc("x0 vector, if not provided will be prompted at start").numberOfArgs(Option.UNLIMITED_VALUES)
				.build();
		Option eps = Option.builder().longOpt("eps").hasArgs().type(Number[].class).desc("epsilon vector")
				.numberOfArgs(Option.UNLIMITED_VALUES).build();
		Option dx = Option.builder().longOpt("dx").hasArgs().type(Number[].class).desc("dx vector")
				.numberOfArgs(Option.UNLIMITED_VALUES).build();
		Option interval = Option.builder().longOpt("int").hasArgs().type(Number[].class)
				.desc("unimodal interval for golden section search, will be calculated if not provided").numberOfArgs(2)
				.build();

		Option dim = Option.builder().longOpt("dim").hasArg().type(Number.class).desc("dimensionality of x").required()
				.build();

		Option step = Option.builder().longOpt("step").hasArg().type(Number.class)
				.desc("step for simplex and golden section").build();
		Option alpha = Option.builder().longOpt("alpha").hasArg().type(Number.class)
				.desc("parameter \u03B1 for simplex method").build();
		Option beta = Option.builder().longOpt("beta").hasArg().type(Number.class)
				.desc("parameter \u03B2 for simplex method").build();
		Option gamma = Option.builder().longOpt("gamma").hasArg().type(Number.class)
				.desc("parameter \u03B3 for simplex method").build();
		Option sigma = Option.builder().longOpt("sigma").hasArg().type(Number.class)
				.desc("parameter \u03C3 for simplex method").build();
		Option offset = Option.builder().longOpt("offset").hasArg().type(Number.class).desc("offset for f3 function")
				.build();

		options.addOption(alg);
		options.addOption(fun);
		options.addOption(dx);
		options.addOption(eps);
		options.addOption(x0);
		options.addOption(interval);
		options.addOption(alpha);
		options.addOption(beta);
		options.addOption(gamma);
		options.addOption(sigma);
		options.addOption(step);
		options.addOption(offset);
		options.addOption(dim);

		return options;
	}

	private static AbstractFunction generateF1() {
		return new AbstractFunction(2) {
			@Override
			public double funcValue(double[] x) {
				return 100 * Math.pow((x[1] - Math.pow(x[0], 2)), 2) + Math.pow(1 - x[0], 2);
			}
		};
	}

	private static AbstractFunction generateF2() {
		return new AbstractFunction(2) {
			@Override
			public double funcValue(double[] x) {
				return Math.pow(x[0] - 4, 2) + 4 * Math.pow(x[1] - 2, 2);
			}
		};
	}

	private static AbstractFunction generateF3(int dimensionality, double offset) {
		return new AbstractFunction(dimensionality) {

			@Override
			public double funcValue(double[] x) {
				double sum = 0;
				for (int i = 0; i < getDimensionality(); i++) {
					sum += Math.pow(x[i] - (i + 1 + offset), 2);
				}
				return sum;
			}
		};
	}

	private static AbstractFunction generateF4() {
		return new AbstractFunction(2) {

			@Override
			public double funcValue(double[] x) {
				return Math.abs((x[0] - x[1]) * (x[0] + x[1])) + Math.sqrt(Math.pow(x[0], 2) + Math.pow(x[1], 2));
			}
		};
	}

	private static AbstractFunction generateF6(int dimensionality) {
		return new AbstractFunction(dimensionality) {

			@Override
			protected double funcValue(double[] x) {
				double sum = 0;
				for (int i = 0; i < getDimensionality(); i++) {
					sum += Math.pow(x[i], 2);
				}
				return 0.5 + (Math.pow(Math.sin(Math.sqrt(sum)), 2) - 0.5) / Math.pow((1 + 0.001 * sum), 2);
			}
		};
	}

}
