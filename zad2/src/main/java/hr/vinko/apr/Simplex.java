package hr.vinko.apr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Simplex implements IAlgorithm{

	private double epsilon;
	private double step;
	private double alpha, beta, gamma, sigma;

	public Simplex() {
		this(1e-6, 1, 1, 0.5, 2, 0.5);
	}
	
	public Simplex(double epsilon, double step, double alpha, double beta, double gamma, double sigma) {
		super();
		this.epsilon = epsilon;
		this.step = step;
		this.alpha = alpha;
		this.beta = beta;
		this.gamma = gamma;
		this.sigma = sigma;
	}

	@Override
	public double[] solve(double[] x0, IFunction f) {
		List<double[]> simplex = new ArrayList<>();
		simplex.add(Arrays.copyOf(x0, x0.length));
		
		for (int i=0; i<x0.length; i++) {
			double[] x = Arrays.copyOf(x0, x0.length);
			x[i] += step;
			simplex.add(x);
		}
		
		while (true) {
			int l = IExtremeFinder.findExtremeIndex(simplex, f, IExtremeFinder.ExtremeType.MIN);
			int h = IExtremeFinder.findExtremeIndex(simplex, f, IExtremeFinder.ExtremeType.MAX);
			
			double[] xL = Arrays.copyOf(simplex.get(l), simplex.get(l).length);
			double[] xH = simplex.get(h);
			
			if (calcError(simplex, f) < epsilon) return xL;
			
			double[] xC = calcCentroid(simplex, h);
			double[] xR = reflexion(xC, xH);

			System.out.println("XC = " + Arrays.toString(xC));
			System.out.println("f(xC) = " + f.getValueAt(xC));
			System.out.println();
			
			if (f.getValueAt(xR) < f.getValueAt(xL)) {
				double[] xE = expansion(xC, xR);
				if (f.getValueAt(xE) < f.getValueAt(xL)) {
					simplex.set(h, xE);
				} else {
					simplex.set(h, xR);
				}
			} else {
				if (greaterThanAll(xR, simplex, h, f)) {
					if (f.getValueAt(xR) < f.getValueAt(xH)) {
						simplex.set(h, xR);
					}
					double[] xK = contraction(xC, xH);
					if (f.getValueAt(xK) < f.getValueAt(xH)) {
						simplex.set(h, xK);
					} else {
						moveTowardTheBest(simplex, xL);
					}
				} else {
					simplex.set(h, xR);
				}
			}
		}
		
	}
	
	private double calcError(List<double[]> simplex, IFunction f) {
		double error = 0;
		double[] fs = new double[simplex.size()];
		double avg = 0;
		for (int i=0; i<fs.length; i++) {
			fs[i] = f.getValueAt(simplex.get(i));
			avg += fs[i];
		}
		avg /= fs.length;
		
		for (int i=0; i<fs.length; i++) {
			error += Math.pow(fs[i] - avg, 2);
		}
		
		return error / (fs.length-1);
	}

	private void moveTowardTheBest(List<double[]> simplex, double[] xL) {
		for (int i=0; i<simplex.size(); i++) {
			double[] x = simplex.get(i);
			for (int j=0; j<x.length; j++) {
				x[j] = sigma * (x[j] + xL[j]);
			}
			simplex.set(i, x);
		}

	}

	private double[] contraction(double[] centroid, double[] xH) {
		double[] contracted = new double[centroid.length];
		for (int i=0; i<contracted.length; i++) {
			contracted[i] = (1 - beta) * centroid[i] + beta * xH[i];
		}
		return contracted;
	}

	private static boolean greaterThanAll(double[] xR, List<double[]> simplex, int h, IFunction f) {
		for (int i = 0; i < simplex.size(); i++) {
			if (i != h) {
				if (f.getValueAt(xR) <= f.getValueAt(simplex.get(i)))
					return false;
			}
		}
		return true;
	}

	private double[] expansion(double[] centroid, double[] xR) {
		double[] expanded = new double[centroid.length];
		for (int i=0; i<expanded.length; i++) {
			expanded[i] = (1 - gamma) * centroid[i] + gamma * xR[i];
		}
		return expanded;
	}

	private double[] reflexion(double[] centroid, double[] worst) {
		double[] reflected = new double[centroid.length];
		for (int i=0; i<reflected.length; i++) {
			reflected[i] = (1 + alpha) * centroid[i] - alpha * worst[i];
		}
		return reflected;
	}

	private static double[] calcCentroid(List<double[]> simplex, int h) {
		double[] centroid = new double[simplex.get(0).length];
		int size = simplex.size();
		for (int i=0; i<size; i++) {
			if (i != h) {
				double[] x = simplex.get(i);
				for (int j=0; j<x.length; j++) {
					centroid[j] += x[j];
				}
			}
		}
		for (int i=0; i<centroid.length; i++) {
			centroid[i] /= (size-1);
		}
		return centroid;
	}

	private interface IExtremeFinder {
		enum ExtremeType {
			MIN, MAX
		}

		public static int findExtremeIndex(List<double[]> values, IFunction f, ExtremeType type) {
			int index = -1;

			if (type == ExtremeType.MIN) {
				double min = Double.MAX_VALUE;
				for (int i = 0; i < values.size(); i++) {
					double value = f.getValueAt(values.get(i));
					if (value < min) {
						index = i;
						min = value;
					}
				}
			} else {
				double max = -Double.MAX_VALUE;
				for (int i = 0; i < values.size(); i++) {
					double value = f.getValueAt(values.get(i));
					if (value > max) {
						index = i;
						max = value;
					}
				}
			}

			return index;

		}
	}

	public void setEpsilon(double epsilon) {
		this.epsilon = epsilon;
	}

	public void setStep(double step) {
		this.step = step;
	}

	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	public void setBeta(double beta) {
		this.beta = beta;
	}

	public void setGamma(double gamma) {
		this.gamma = gamma;
	}

	public void setSigma(double sigma) {
		this.sigma = sigma;
	}
	
	
}


