package hr.vinko.apr.zad3.function;


public interface IFunction {
  
  public double getValueAt(double[] x);
  public double[] getDerivationAt(double[] x);
  public double[][] getHessianAt(double[] x);


}
