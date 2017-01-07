package hr.vinko.apr.zad2;

import java.util.Arrays;

public class GoldenSection implements IAlgorithm{

	private final static double GOLDEN_SECTION_FACTOR = 0.5 * (Math.sqrt(5) - 1);
	private double h;
	private double e;
	private double[] interval;
	
	public GoldenSection() {
		this(1, 1e-6);
	}
	public GoldenSection(double h, double e){
		this(h, e, new double[0]);
	}
	
	public GoldenSection(double h, double e, double[] interval) {
		this.h = h;
		this.e = e;
		this.interval = Arrays.copyOf(interval, interval.length);
	}
	
	public void setE(double e) {
		this.e = e;
	}
	
	@Override
	public double[] solve(double[] x0, FunctionWValue f) {
		double[] intervalToUse;
		if (interval == null || interval.length != 2) {
			intervalToUse = unimodalInterval(x0[0], f);
		} else {
			intervalToUse = interval;
		}

		double a = intervalToUse[0];
		double b = intervalToUse[1];
		
		double c = b - GOLDEN_SECTION_FACTOR * (b - a);
		double d = a + GOLDEN_SECTION_FACTOR * (b - a);
		
		double fc = f.getValueAt(c);
		double fd = f.getValueAt(d);
		while (b - a > e) {
			if (fc < fd) {
				b = d;
				d = c;
				c = b - GOLDEN_SECTION_FACTOR * (b - a);
				fd = fc;
				fc = f.getValueAt(c);
			} else {
				a = c;
				c = d;
				d = a + GOLDEN_SECTION_FACTOR * (b - a);
				fc = fd;
				fd = f.getValueAt(d);
			}
			System.out.println(a + "\t|\t" + c + "\t|\t" + d + "\t|\t" + b);
		}

		
		return new double[] { a, b };
		
	}

	private double[] unimodalInterval(double x0, FunctionWValue f) {
		double l = x0 - h;
		double r = x0 + h;
		int step = 1; 

		double m = x0;
		double fl, fm, fr;

		fm = f.getValueAt(x0);
		fl = f.getValueAt(l);
		fr = f.getValueAt(r);

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
			} while (fm > fl);
		}

		System.out.println("===============================================");
		System.out.println("UNIMODAL INTERVAL");
		System.out.println("[" + l  + "," + r + "]");
		System.out.println("===============================================");

		return new double[] { l, r };
	}


}
