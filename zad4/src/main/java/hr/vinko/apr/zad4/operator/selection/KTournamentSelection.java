package hr.vinko.apr.zad4.operator.selection;

import java.util.ArrayList;
import java.util.List;

import hr.vinko.apr.zad4.fitness.FitnessType;
import hr.vinko.apr.zad4.population.Population;
import hr.vinko.apr.zad4.solution.ISolution;

public class KTournamentSelection implements ISelection<ISolution<?>> {

    private int k;

    public KTournamentSelection(int k) {
        this.k = k;
    }

    @Override
    public ISolution[] select(Population<ISolution<?>> population, FitnessType fitnessType) {

        Population<ISolution<?>> tournament = new Population<>(k);

        List<ISolution> pop = new ArrayList<>(population.getPopulation());

        while (!tournament.isFull()) {
            tournament.addSolution(pop.get(rand.nextInt(pop.size())));
        }

        ISolution parent1 = tournament.getBest(fitnessType);
        tournament.removeSolution(parent1);
        ISolution parent2 = tournament.getBest(fitnessType);

        return new ISolution[]{parent1, parent2, tournament.getWorst(fitnessType)};
    }

}
