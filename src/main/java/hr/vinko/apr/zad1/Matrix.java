package hr.vinko.apr.zad1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Matrix {

	private int rows;
	private int columns;

	private static double eps = 10e-6;
	
	private double[][] elements;

	public Matrix(int rows, int cols) {
		this.rows = rows;
		this.columns = cols;
		this.elements = new double[rows][cols];

	}

	public Matrix(int rows, int cols, double[][] elements) {
		this(rows, cols);
		if (elements.length != rows * cols) {
			throw new IllegalArgumentException("Wrong dimensions!");
		}
		
		System.arraycopy(elements, 0, this.elements, 0, elements.length);
	}

	public Matrix(String path) {
		List<String> lines;
		try {
			lines = Files.readAllLines(Paths.get(path));
		} catch (IOException e) {
			throw new IllegalArgumentException(e.getMessage());
		}

		List<double[]> tmp = new ArrayList<double[]>();
		
		columns = lines.get(0).split("\\s+").length;
		rows = lines.size();
		
		lines.forEach(line -> {
			String[] split = line.split("\\s+");
			if (split.length != columns) {
				throw new IllegalArgumentException("All rows not the same dimension!");
			}

			double[] row = new double[columns];

			for (int i = 0; i < columns; i++) {
				try {
					row[i] = Double.parseDouble(split[i]);
				} catch (Exception e) {
					throw new IllegalArgumentException("Could not parse the file!");
				}
			}

			tmp.add(row);

		});

		elements = tmp.toArray(new double[][] {});

	}

	public double getAt(int row, int col) {
		if (row > rows || col > columns) {
			throw new IllegalArgumentException("Row or Column out of range of the matrix!");
		}
		return elements[row][col];
	}

	public void setAt(int row, int col, double value) {
		if (row > rows || col > columns) {
			throw new IllegalArgumentException("Row or Column out of range of the matrix!");
		}

		elements[row][col] = value;
	}

	public Matrix add(Matrix other) {
		if (rows != other.rows && columns != other.columns) {
			throw new IllegalArgumentException("Size of the matrices must be the same!");
		}

		Matrix added = new Matrix(rows, columns);

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				added.setAt(i, j, getAt(i, j) + (other.getAt(i, j)));
			}
		}

		return added;
	}

	public Matrix substract(Matrix other) {
		return add(other.scalarMultiply(-1));
	}

	public Matrix increment(Matrix other) {
		if (rows != other.rows && columns != other.columns) {
			throw new IllegalArgumentException("Size of the matrices must be the same!");
		}

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				elements[i][j] += other.getAt(i, j);
			}
		}
		return this;
	}

	public Matrix decrement(Matrix other) {
		return increment(other.scalarMultiply(-1));
	}

	public Matrix multiply(Matrix other) {
		if (columns != other.rows) {
			throw new IllegalArgumentException("Matrices for multiplication must be in form (m x n)(n x p)");
		}
		Matrix multiplied = new Matrix(rows, other.columns);

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < other.columns; j++) {
				double sum = 0;
				for (int k = 0; k < columns; k++) {
					sum += getAt(i, k) * other.getAt(k, j);
				}
				multiplied.setAt(i, j, sum);
			}
		}

		return multiplied;
	}

	public Matrix forwardSubstitution(Matrix goalVector) {
		if (goalVector.rows != rows || goalVector.columns != 1) {
			throw new IllegalArgumentException("Vector of dimensions " + rows + "x1 must be provided!");
		}

		Matrix L = toLowerMatrix();
		Matrix b = goalVector.copy();

		for (int i = 0; i < rows; i++) {
			for (int j = i + 1; j < columns; j++) {
				b.elements[j][0] -= b.elements[i][0] * L.elements[j][i];
			}
		}
		
		return b;

	}

	
	public Matrix backwardSubstitution(Matrix goalVector) {
		if (goalVector.rows != rows || goalVector.columns != 1) {
			throw new IllegalArgumentException("Vector of dimensions " + rows + "x 1 must be provided!");
		}

		Matrix U = toUpperMatrix();
		Matrix b = goalVector.copy();

		for (int i = rows - 1; i >= 0; i--) {
			b.elements[i][0] /= U.elements[i][i];
			for (int j = 0; j < i; j++) {
				b.elements[j][0] -= b.elements[i][0] * U.elements[j][i];
			}
		}

		return b;

	}
	
	private Matrix toLowerMatrix() {
		Matrix matrix = new Matrix(rows, columns);
		for (int i=0; i<rows; i++) {
			for(int j=0; j<columns; j++) {
				double value;
				if (i == j) value = 1.0;
				else if (i<j) value = 0.0;
				else value = elements[i][j];
				matrix.elements[i][j] = value;
			}
		}
		return matrix;
	}
	
	private Matrix toUpperMatrix() {
		Matrix matrix = new Matrix(rows, columns);
		for (int i=0; i<rows; i++) {
			for(int j=0; j<columns; j++) {
				double value;
				if (i <= j) value = elements[i][j];
				else value = 0;
				matrix.elements[i][j] = value;
			}
		}
		return matrix;
	}


	public static Matrix identityMatrix(int dimension) {
		Matrix e = new Matrix(dimension, dimension);
		for (int i = 0; i < dimension; i++) {
			e.elements[i][i] = 1;
		}

		return e;

	}
	
	public static Matrix[] LUDecomposition(Matrix matrix, boolean print) {
		return LUPDecomposition(matrix, (it, A, P) -> A, print);
	}
	
	public static Matrix[] LUPDecomposition(Matrix matrix, boolean print) {
		return LUPDecomposition(matrix, (it, A, P) -> {
			Matrix m = A.copy();
			
			double max = Math.abs(m.elements[it][it]);
			int row = it;
			for (int j=it; j<matrix.rows; j++) {
				if (Math.abs(m.elements[j][it]) > max) {
					max = Math.abs(m.elements[j][it]);
					row = j;
				}
			}
			
			m = switchRows(m, it, row);
			P = switchRows(P, it, row);
			
			return m;
		}, print);
	}
	
	private static Matrix switchRows(Matrix matrix, int row1, int row2) {
		
		double[] tmp = new double[matrix.getColumns()];
		System.arraycopy(matrix.elements[row1], 0, tmp, 0, matrix.getColumns());
						
		for (int i=0; i<matrix.columns; i++) {
			matrix.elements[row1][i] = matrix.elements[row2][i];
			matrix.elements[row2][i] = tmp[i];
		}
		
		return matrix;
	}

	private static Matrix[] LUPDecomposition(Matrix matrix, IPivotSelector selector, boolean print) {

		if (matrix.rows != matrix.columns) {
			throw new IllegalArgumentException("Matrix must be quadratic!");
		}
		
		Matrix P = identityMatrix(matrix.rows);
		Matrix LU = matrix.copy();
		
		if (print) {
			System.out.println("LU/LUP decomposition");
		}

		for (int i = 0; i < matrix.rows; i++) {
			
			if (print) {
				System.out.println("Iteration" + (i+1));
			}
			
			LU = selector.select(i, LU, P);
			if (Math.abs(LU.elements[i][i]) < eps) {
				return null;
			}
			
			for (int j=i+1; j<matrix.rows; j++) {
				LU.elements[j][i] /= LU.elements[i][i];
				for (int k=i+1; k<matrix.rows; k++) {
					LU.elements[j][k] -= LU.elements[j][i] * LU.elements[i][k];
				}
			}
			if (print) {
				System.out.println("LU\n" + LU);
				System.out.println("P\n" + P);
			}
		}

		return new Matrix[]{LU, P};
	}

	public Matrix copy() {
		return scalarMultiply(1);
	}

	public Matrix scalarMultiply(double value) {
		Matrix newMatrix = new Matrix(rows, columns);

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				newMatrix.setAt(i, j, getAt(i, j) * value);
			}
		}

		return newMatrix;
	}

	public Matrix transpose() {
		Matrix transposed = new Matrix(columns, rows);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				transposed.setAt(j, i, getAt(i, j));
			}
		}
		return transposed;
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				sb.append(elements[i][j] + " ");
			}
			sb.replace(sb.length() - 1, sb.length(), "\n");
		}

		return sb.toString();
	}

	public void toFile(String path) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
			writer.write(toString());
		} catch (Exception e) {
			throw new IllegalArgumentException("Could not write to file " + path + "!");
		}
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		double[][] array = new double[rows][columns];
				
		for (int i=0; i<rows; i++) {
			for (int j=0; j<columns; j++) {
				array[i][j] = (elements[i][j] * eps) / eps;
			}
		}
		
		
		result = prime * result + Arrays.deepHashCode(array);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Matrix other = (Matrix) obj;
		
		if (this.rows != other.rows || this.columns != other.columns) return false;
		
		for (int i=0; i<rows; i++) {
			for (int j=0; j<rows; j++) {
				if (Math.abs(elements[i][j] - other.elements[i][j]) > eps) return false;
			}
		}
		return true;
	}

	public static double getEps() {
		return eps;
	}

	public static void setEps(double eps) {
		Matrix.eps = eps;
	}

}
