package hr.vinko.apr.zad3.function;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractFunction implements IFunction {
  

  private int valueCounter=0;
  private int derivationCounter=0;
  private int hessianCounter=0;

  private Map<Integer, Double> fMap;
  private Map<Integer, double[]> dMap;
  private Map<Integer, double[][]> hMap;

  public AbstractFunction() {
    fMap = new HashMap<>();
    dMap = new HashMap<>();
    hMap = new HashMap<>();
  }

  protected abstract double getFValue(double[] x);
  protected abstract double[] getDValue(double[] x);
  protected abstract double[][] getHValue(double[] x);

  


  @Override
  public double[] getDerivationAt(double[] x) {
    int hash = Arrays.hashCode(x);
    if (!dMap.containsKey(hash)) {
      dMap.put(hash, getDValue(x));
      derivationCounter++;
    }

    return dMap.get(hash);
  }

  @Override
  public double[][] getHessianAt(double[] x) {
  
    int hash = Arrays.hashCode(x);
    if (!hMap.containsKey(hash)) {
      hMap.put(hash, getHValue(x));
      hessianCounter++;
    }

    return hMap.get(hash);

  }

  @Override
  public double getValueAt(double[] x) {
    int hash = Arrays.hashCode(x);
    if (!fMap.containsKey(hash)) {
      fMap.put(hash, getFValue(x));
      valueCounter++;
    }

    return fMap.get(hash);

  }

  /**
   * @return the valueCounter
   */
  public int getValueCounter() {
    return valueCounter;
  }

  /**
   * @return the derivationCounter
   */
  public int getDerivationCounter() {
    return derivationCounter;
  }

  /**
   * @return the hessianCounter
   */
  public int getHessianCounter() {
    return hessianCounter;
  }

}
