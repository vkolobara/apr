package hr.vinko.apr.zad4;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;

import hr.vinko.apr.zad4.algorithm.EliminationGABitVector;
import hr.vinko.apr.zad4.algorithm.EliminationGAFloat;
import hr.vinko.apr.zad4.fitness.F1;
import hr.vinko.apr.zad4.fitness.F3;
import hr.vinko.apr.zad4.fitness.F6;
import hr.vinko.apr.zad4.fitness.F7;
import hr.vinko.apr.zad4.fitness.FitnessType;
import hr.vinko.apr.zad4.operator.crossover.ArithmeticCrossover;
import hr.vinko.apr.zad4.operator.crossover.HeuristicCrossover;
import hr.vinko.apr.zad4.operator.crossover.ICrossover;
import hr.vinko.apr.zad4.operator.crossover.UniformCrossover;
import hr.vinko.apr.zad4.operator.crossover.XorCrossover;
import hr.vinko.apr.zad4.operator.mutation.GaussMutation;
import hr.vinko.apr.zad4.operator.mutation.IMutation;
import hr.vinko.apr.zad4.operator.mutation.SimpleMutation;
import hr.vinko.apr.zad4.operator.selection.ISelection;
import hr.vinko.apr.zad4.operator.selection.KTournamentSelection;
import hr.vinko.apr.zad4.solution.AbstractSolution;

/**
 * Created by vkolobara on 17.12.16..
 */
public class Main {

	public static void main(String[] args) throws IOException {
		// zad1();
		// zad2();
		// zad3();
		// zad4();
		zad5();
	}

	private static void zad5() throws IOException {

		double pM = 0.3;
		double sigma = 10;

		int ks[] = new int[] { 3, 5, 7, 15 };

		int maxEvaluations = 10_000;
		double termVal = 1e-6;
		int popSize = 100;

		int maxIter = maxEvaluations;

		F6 f6 = new F6(2);

		double[] min;
		double[] max;
		int size;

		int numCalc = 10;

		double[][] valuesFloat = new double[ks.length][numCalc];

		int index = 0;

		for (int k : ks) {
			ICrossover[] crossoversFloat = new ICrossover[] { new HeuristicCrossover(FitnessType.FITNESS_MIN),
					new ArithmeticCrossover() };
			ISelection selection = new KTournamentSelection(k);
			IMutation[] mutationsFloat = new IMutation[] { new GaussMutation(pM, sigma) };

			EliminationGAFloat gaFloat = new EliminationGAFloat(crossoversFloat, mutationsFloat, selection, termVal,
					maxEvaluations, maxIter, popSize);
			for (int i = 0; i < numCalc; i++) {
				f6.clear();

				size = 2;
				min = new double[size];
				Arrays.fill(min, -50);
				max = new double[size];
				Arrays.fill(max, 150);
				AbstractSolution sol = gaFloat.solve(f6, size, min, max);
				System.out.println("========================");
				System.out.println("F6");
				System.out.println(sol);
				System.out.println(sol.getFitness());
				valuesFloat[index][i] = sol.getFitness();
			}
			index++;
		}

		writeStats(valuesFloat, "zad5.txt");

	}

	private static void zad4() throws IOException {

		int[] popSizes = new int[] { 30, 50, 100, 200 };
		double[] pMs = new double[] { 0.1, 0.3, 0.6, 0.9 };

		double pMFloat = 0.1;
		double sigma = 10;

		int maxEvaluations = 10_000;
		double termVal = 1e-6;
		int popSizeFloat = 50;

		int maxIter = maxEvaluations;

		F6 f6 = new F6(2);

		double[] min;
		double[] max;
		int size;

		int numCalc = 10;

		double[][] valuesFloat = new double[16][numCalc];

		int index = 0;

		for (int popSize : popSizes) {
			for (double pM : pMs) {
				ICrossover[] crossoversFloat = new ICrossover[] { new HeuristicCrossover(FitnessType.FITNESS_MIN),
						new ArithmeticCrossover() };
				ISelection selection = new KTournamentSelection(3);
				IMutation[] mutationsFloat = new IMutation[] { new GaussMutation(pM, sigma) };

				EliminationGAFloat gaFloat = new EliminationGAFloat(crossoversFloat, mutationsFloat, selection, termVal,
						maxEvaluations, maxIter, popSize);
				for (int i = 0; i < numCalc; i++) {
					f6.clear();

					size = 2;
					min = new double[size];
					Arrays.fill(min, -50);
					max = new double[size];
					Arrays.fill(max, 150);
					AbstractSolution sol = gaFloat.solve(f6, size, min, max);
					System.out.println("========================");
					System.out.println("F6");
					System.out.println(sol);
					System.out.println(sol.getFitness());
					valuesFloat[index][i] = sol.getFitness();
				}
				index++;
			}
		}

		writeStats(valuesFloat, "zad4.txt");

	}

	private static void zad1() {
		double pMFloat = 0.2;
		double pMBit = 0.001;
		double sigma = 10;

		int maxEvaluations = 100_000;
		double termVal = 1e-6;
		int popSizeFloat = 50;
		int popSizeBit = 30;

		int maxIter = maxEvaluations;

		ICrossover[] crossoversFloat = new ICrossover[] { new HeuristicCrossover(FitnessType.FITNESS_MIN),
				new ArithmeticCrossover() };
		ISelection selection = new KTournamentSelection(3);
		IMutation[] mutationsFloat = new IMutation[] { new GaussMutation(pMFloat, sigma) };

		ICrossover[] crossoverBitVector = new ICrossover[] { new UniformCrossover(), new XorCrossover() };
		IMutation[] mutationsBitVector = new IMutation[] { new SimpleMutation(pMBit) };

		F1 f1 = new F1();
		F3 f3 = new F3(5);
		F6 f6 = new F6(2);
		F7 f7 = new F7(2);

		double[] min;
		double[] max;
		int size;

		int[] precision;

		EliminationGAFloat gaFloat = new EliminationGAFloat(crossoversFloat, mutationsFloat, selection, termVal,
				maxEvaluations, maxIter, popSizeFloat);
		EliminationGABitVector gaBitVector = new EliminationGABitVector(crossoverBitVector, mutationsBitVector,
				selection, termVal, maxEvaluations, maxIter, popSizeBit);
		int numCalc = 10;

		double[][] valuesFloat = new double[4][numCalc];
		double[][] valuesBitVector = new double[4][numCalc];

		AbstractSolution sol;
		int prec = 6;

		for (int i = 0; i < numCalc; i++) {
			f1.clear();
			f3.clear();
			f6.clear();
			f7.clear();

			size = 2;
			min = new double[size];
			Arrays.fill(min, -50);
			max = new double[size];
			Arrays.fill(max, 150);
			sol = gaFloat.solve(f1, size, min, max);
			System.out.println("========================");
			System.out.println("F1");
			System.out.println(sol);
			System.out.println(sol.getFitness());
			valuesFloat[0][i] = sol.getFitness();

			precision = new int[size];
			Arrays.fill(precision, prec);

			f1.clear();
			sol = gaBitVector.solve(f1, precision, min, max);
			System.out.println(sol);
			System.out.println(sol.getFitness());
			valuesBitVector[0][i] = sol.getFitness();

			size = 5;
			min = new double[size];
			Arrays.fill(min, -50);
			max = new double[size];
			Arrays.fill(max, 150);
			sol = gaFloat.solve(f3, size, min, max);
			System.out.println("========================");
			System.out.println("F3");
			System.out.println(sol);
			System.out.println(sol.getFitness());
			valuesFloat[1][i] = sol.getFitness();

			f3.clear();
			precision = new int[size];
			Arrays.fill(precision, prec);
			sol = gaBitVector.solve(f3, precision, min, max);
			System.out.println(sol);
			System.out.println(sol.getFitness());
			valuesBitVector[1][i] = sol.getFitness();

			size = 2;
			min = new double[size];
			Arrays.fill(min, -50);
			max = new double[size];
			Arrays.fill(max, 150);
			sol = gaFloat.solve(f6, size, min, max);
			System.out.println("========================");
			System.out.println("F6");
			System.out.println(sol);
			System.out.println(sol.getFitness());
			valuesFloat[2][i] = sol.getFitness();

			f6.clear();
			precision = new int[size];
			Arrays.fill(precision, prec);
			sol = gaBitVector.solve(f6, precision, min, max);
			System.out.println(sol);
			System.out.println(sol.getFitness());
			valuesBitVector[2][i] = sol.getFitness();

			size = 2;
			min = new double[size];
			Arrays.fill(min, -50);
			max = new double[size];
			Arrays.fill(max, 150);
			sol = gaFloat.solve(f7, size, min, max);
			System.out.println("========================");
			System.out.println("F7");
			System.out.println(sol);
			System.out.println(sol.getFitness());
			valuesFloat[3][i] = sol.getFitness();

			f7.clear();
			precision = new int[size];
			Arrays.fill(precision, prec);
			sol = gaBitVector.solve(f7, precision, min, max);
			System.out.println(sol);
			System.out.println(sol.getFitness());
			valuesBitVector[3][i] = sol.getFitness();

		}

		int[] succFloat = new int[4];
		int[] succBit = new int[4];

		for (int i = 0; i < succFloat.length; i++) {
			for (int j = 0; j < valuesFloat[i].length; j++) {
				if (valuesFloat[i][j] <= 1e-6) {
					succFloat[i]++;
				}
				if (valuesBitVector[i][j] <= 1e-6) {
					succBit[i]++;
				}
			}
		}

		System.out.println("USPJESNOST NA POJEDINIM FUNKCIJAMA (f1, f3, f6, f7)");
		System.out.println(Arrays.toString(succFloat));
		System.out.println(Arrays.toString(succBit));

	}

	private static void zad2() {
		double pMFloat = 0.1;
		double sigma = 10;

		int maxEvaluations = 100_000;
		double termVal = 1e-6;
		int popSizeFloat = 100;

		int maxIter = maxEvaluations;

		ICrossover[] crossoversFloat = new ICrossover[] { new HeuristicCrossover(FitnessType.FITNESS_MIN),
				new ArithmeticCrossover() };
		ISelection selection = new KTournamentSelection(3);
		IMutation[] mutationsFloat = new IMutation[] { new GaussMutation(pMFloat, sigma) };
		int[] dimensions = new int[] { 1, 3, 6, 10 };

		double[] min, max;
		AbstractSolution sol;

		EliminationGAFloat gaFloat = new EliminationGAFloat(crossoversFloat, mutationsFloat, selection, termVal,
				maxEvaluations, maxIter, popSizeFloat);

		for (int dimension : dimensions) {
			System.out.println("Dimensionality: " + dimension);
			F6 f6 = new F6(dimension);
			F7 f7 = new F7(dimension);

			min = new double[dimension];
			Arrays.fill(min, -50);
			max = new double[dimension];
			Arrays.fill(max, 150);
			sol = gaFloat.solve(f6, dimension, min, max);
			System.out.println(sol);
			System.out.println(sol.getFitness());
			sol = gaFloat.solve(f7, dimension, min, max);
			System.out.println(sol);
			System.out.println(sol.getFitness());
		}

	}

	private static void zad3() throws IOException {
		double pMFloat = 0.1;
		double pMBit = 0.001;
		double sigma = 10;

		int maxEvaluations = 10_000;
		double termVal = 1e-6;
		int popSizeFloat = 50;
		int popSizeBit = 50;

		int maxIter = maxEvaluations;

		ICrossover[] crossoversFloat = new ICrossover[] { new HeuristicCrossover(FitnessType.FITNESS_MIN),
				new ArithmeticCrossover() };
		ISelection selection = new KTournamentSelection(3);
		IMutation[] mutationsFloat = new IMutation[] { new GaussMutation(pMFloat, sigma) };

		ICrossover[] crossoverBitVector = new ICrossover[] { new UniformCrossover(), new XorCrossover() };
		IMutation[] mutationsBitVector = new IMutation[] { new SimpleMutation(pMBit) };

		F6 f6 = new F6(2);
		F7 f7 = new F7(2);

		double[] min;
		double[] max;
		int size;

		int[] precision;

		EliminationGAFloat gaFloat = new EliminationGAFloat(crossoversFloat, mutationsFloat, selection, termVal,
				maxEvaluations, maxIter, popSizeFloat);
		EliminationGABitVector gaBitVector = new EliminationGABitVector(crossoverBitVector, mutationsBitVector,
				selection, termVal, maxEvaluations, maxIter, popSizeBit);
		int numCalc = 10;

		double[][] valuesFloat = new double[2][numCalc];
		double[][] valuesBitVector = new double[2][numCalc];

		AbstractSolution sol;
		int prec = 4;

		int[] sizes = new int[] { 3, 6 };

		for (int i = 0; i < numCalc; i++) {
			f6.clear();
			f7.clear();

			for (int size2 : sizes) {

				size = size2;
				min = new double[size];
				Arrays.fill(min, -50);
				max = new double[size];
				Arrays.fill(max, 150);
				sol = gaFloat.solve(f6, size, min, max);
				System.out.println("========================");
				System.out.println("F6");
				System.out.println(sol);
				System.out.println(sol.getFitness());
				valuesFloat[0][i] = sol.getFitness();

				f6.clear();
				precision = new int[size];
				Arrays.fill(precision, prec);
				sol = gaBitVector.solve(f6, precision, min, max);
				System.out.println(sol);
				System.out.println(sol.getFitness());
				valuesBitVector[0][i] = sol.getFitness();

				size = size2;
				min = new double[size];
				Arrays.fill(min, -50);
				max = new double[size];
				Arrays.fill(max, 150);
				sol = gaFloat.solve(f7, size, min, max);
				System.out.println("========================");
				System.out.println("F7");
				System.out.println(sol);
				System.out.println(sol.getFitness());
				valuesFloat[1][i] = sol.getFitness();

				f7.clear();
				precision = new int[size];
				Arrays.fill(precision, prec);
				sol = gaBitVector.solve(f7, precision, min, max);
				System.out.println(sol);
				System.out.println(sol.getFitness());
				valuesBitVector[1][i] = sol.getFitness();
			}
		}

		// TODO zapisi u datoteku
		writeStats(valuesFloat, "zad3Float.txt");
		writeStats(valuesBitVector, "zad3Bit.txt");

	}

	private static void writeStats(double[][] values, String filePath) throws IOException {
		try (Writer writer = new BufferedWriter(new FileWriter(filePath))) {
			for (int j = 0; j < values[0].length; j++) {
				for (double[] value : values) {
					writer.write(value[j] + ",");
				}
				writer.write("\n");
			}

		}
	}
}
