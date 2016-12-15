package hr.vinko.apr.zad4.operator.crossover;

import hr.vinko.apr.zad4.solution.BitVectorSolution;

public class AndCrossover implements ICrossover<BitVectorSolution> {

	@Override
	public BitVectorSolution mate(BitVectorSolution parent1, BitVectorSolution parent2) {
		BitVectorSolution child = (BitVectorSolution) parent1.newLikeThis();
		
		for (int i=0; i<child.solution.length; i++) {
			child.solution[i] = parent1.solution[i] & parent2.solution[i];
		}
		
		return child;
	}

}
