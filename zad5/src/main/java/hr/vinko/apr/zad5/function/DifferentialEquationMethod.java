package hr.vinko.apr.zad5.function;

import hr.vinko.apr.zad1.Matrix;

public interface DifferentialEquationMethod {

	public Matrix getValue(Matrix A, Matrix B, Matrix x, double t, double T);
	
	default Matrix diffValue(Matrix A, Matrix x, Matrix B, double t) {
		return A.multiply(x).add(B);
	}
	
}
