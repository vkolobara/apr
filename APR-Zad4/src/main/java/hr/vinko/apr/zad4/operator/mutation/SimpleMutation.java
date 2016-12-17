package hr.vinko.apr.zad4.operator.mutation;

import hr.vinko.apr.zad4.solution.BitVectorSolution;

public class SimpleMutation implements IMutation<BitVectorSolution> {

    private double p;

    public SimpleMutation(double p) {
        this.p = p;
    }

    @Override
    public BitVectorSolution mutate(BitVectorSolution solution) {

        BitVectorSolution child = (BitVectorSolution) solution.duplicate();

        for (int i = 0; i < child.solution.length; i++) {
            if (rand.nextDouble() <= p)
                child.solution[i] ^= true;
        }
        return child;
    }

}
