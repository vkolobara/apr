package hr.vinko.apr.zad4.operator.mutation;

import hr.vinko.apr.zad4.solution.BitVectorSolution;

public class BitFlipMutation implements IMutation<BitVectorSolution> {

	private int p;

	public BitFlipMutation(int pM) {
		super();
		this.p = pM;
	}

	@Override
	public BitVectorSolution mutate(BitVectorSolution solution) {

		BitVectorSolution child = (BitVectorSolution) solution.duplicate();

		for (int i = 0; i < child.solution.length; i++) {
			if (rand.nextDouble() <= p)
				child.solution[i] ^= child.solution[i];
		}

		return child;
	}

}
