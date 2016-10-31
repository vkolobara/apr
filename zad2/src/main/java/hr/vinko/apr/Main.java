package hr.vinko.apr;

import hr.vinko.apr.GoldenSection;

public class Main {

	public static void main(String[] args) {
		GoldenSection.golden_section_search(0, 1, x -> Math.pow(x[0], 2));
	}

}
