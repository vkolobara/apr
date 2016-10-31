package hr.vinko.apr;

public class GoldenSection {
	
	private final static double GOLDEN_SECTION_FACTOR = 0.5 * (Math.sqrt(5) - 1);
	public static double EPS = 1e-6;
	

	public static double[] golden_section_search(double x0, double h, IFunction f) {
		double[] interval = unimodalInterval(h, x0, f);
		return golden_section_search(interval, f);
	}
	
	public static double[] golden_section_search(double[] interval, IFunction f) {
		return golden_section(interval[0], interval[1], f);
	}
	
	private static double[] golden_section(double a, double b, IFunction f) {
	
		double c = b - GOLDEN_SECTION_FACTOR * (b-a);
		double d = a + GOLDEN_SECTION_FACTOR * (b-a);
		
		double fc = f.getValueAt(c);
		double fd = f.getValueAt(d);
		
		while(Math.abs(b - a) > EPS) {
			System.out.println(a + " | " + b + " | " + c + " | " + d);
			if (fc < fd) {
				b = d;
				d = c;
				c = b - GOLDEN_SECTION_FACTOR * (b-a);
				fd = fc;
				fc = f.getValueAt(c);
			} else {
				a = c;
				c = d;
				d = a + GOLDEN_SECTION_FACTOR * (b-a);
				fc = fd;
				fd = f.getValueAt(d);
			}
		}
		
		return new double[]{a, b};
	}
	
	private static double[] unimodalInterval(double h, double x0, IFunction f) {
		double l = x0 - h;
		double r = x0 + h;
		int step = 1;
		
		double m = x0;
		double fl, fm, fr;
		
		fm = f.getValueAt(x0);
		fl = f.getValueAt(l);
		fr = f.getValueAt(r);
		
		System.out.println(fl + "|" + fm + "|" + fr);
		
		if (fm > fr) {
			do {
				l = m;
				m = r;
				fm = fr;
				r = x0 + h * (step *= 2);
				fr = f.getValueAt(r);
			} while (fm > fr);
		} else if (fm > fl) {
			do {
				r = m;
				m = l;
				fm = fl;
				l = x0 - h * (step *= 2);
				fl = f.getValueAt(l);
			} while(fm > fl);
		}
	
		return new double[]{l, r};
	}

}
