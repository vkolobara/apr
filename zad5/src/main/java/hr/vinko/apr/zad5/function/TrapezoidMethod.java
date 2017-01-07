package hr.vinko.apr.zad5.function;

import hr.vinko.apr.zad1.Matrix;

public class TrapezoidMethod implements DifferentialEquationMethod {

	@Override
	public Matrix getValue(Matrix A, Matrix B, Matrix x, double t, double T) {

		Matrix AT = A.scalarMultiply(T / 2);
		Matrix U = Matrix.identityMatrix(A.getRows());

		Matrix inverseUA = U.substract(AT).inverse();
		Matrix UaddA = U.add(AT);

		return inverseUA.multiply(UaddA).multiply(x).add(inverseUA.multiply(B.scalarMultiply(T / 2)));
	}

}
