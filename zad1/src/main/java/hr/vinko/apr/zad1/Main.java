package hr.vinko.apr.zad1;

public class Main {

	public static void main(String[] args) {		
		zad1();
		System.out.println();
		zad2();
		System.out.println();
		zad3();
		System.out.println();
		zad4();
		System.out.println();
		zad5();
		System.out.println();
		zad6();
		System.out.println();
		zad6_2();
		System.out.println();
	}

	private static void zad1() {
		System.out.println("ZADATAK 1.0");
		System.out.println("===================================================");

		Matrix m1 = new Matrix("data/zad1mat.txt");
		
		System.out.println(m1);
		
		Matrix m2 = m1.scalarMultiply(Math.PI);
		m2 = m2.scalarMultiply(1 / Math.PI);
		
		System.out.println(m2);
		
		System.out.println(m1.equals(m2));
		
		System.out.println("===================================================");

	}

	private static void zad2() {
		solve(2, "data/zad2mat.txt", "data/zad2vek.txt");
	}

	private static void zad3() {
		solve(3, "data/zad3mat.txt", "data/zad2vek.txt");
	}

	private static void zad4() {
		solve(4, "data/zad4mat.txt", "data/zad4vek.txt");
	}

	private static void zad5() {
		solve(5, "data/zad5mat.txt", "data/zad5vek.txt");
	}

	private static void zad6() {
		solve(6, "data/zad6mat.txt", "data/zad6vek.txt");
	}

	private static void zad6_2() {
		solve(6.2, "data/zad6mat2.txt", "data/zad6vek2.txt");
	}

	private static void solve(double num, String matPath, String vekPath) {
		System.out.println("ZADATAK " + num);
		System.out.println("===================================================");
		System.out.println("LUP\n");
		Matrix mat = new Matrix(matPath);
		Matrix vek = new Matrix(vekPath);

		Matrix[] LUP = Matrix.LUPDecomposition(mat, false);
		if (LUP == null) {
			System.out.println("Decomposition not possible with LUP!");
		} else {
			System.out.println("LU Matrix\n" + LUP[0]);
			System.out.println("P Matrix\n" + LUP[1]);

			Matrix LU = LUP[0];
			Matrix P = LUP[1];

			Matrix L = LU.toLowerMatrix();
			Matrix U = LU.toUpperMatrix();
			
			Matrix y = LU.forwardSubstitution(P.multiply(vek), L);
			System.out.println("Y\n" + y);

			System.out.println("X\n" + LU.backwardSubstitution(y, U));
		}

		System.out.println("________________________________\nLU\n");
		LUP = Matrix.LUDecomposition(mat, false);

		if (LUP == null) {
			System.out.println("Decomposition not possible with LU!");
		} else {
			Matrix LU = LUP[0];
			System.out.println("LU Matrix\n" + LUP[0]);

			Matrix L = LU.toLowerMatrix();
			Matrix U = LU.toUpperMatrix();
			
			Matrix y = LU.forwardSubstitution(vek, L);
			System.out.println("Y\n" + y);

			System.out.println("X\n" + LU.backwardSubstitution(y, U));
		}

		System.out.println("===================================================");
	}

}
