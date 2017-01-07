package hr.vinko.apr.zad4.operator.mutation;

import java.util.Random;

import hr.vinko.apr.zad4.solution.ISolution;

public interface IMutation<T extends ISolution<?>> {
	static Random rand = new Random();

	public T mutate(T solution);

}
