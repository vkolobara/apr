package hr.vinko.apr.zad4.solution;

import java.util.Random;

public interface ISolution<T> {

	static Random rand = new Random();

	public ISolution<T> newLikeThis();

	public ISolution<T> duplicate();

	public void randomize();

	public double getFitness();

	public void setFitness(double fitness);

	public double[] decode();

}
