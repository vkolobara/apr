package hr.vinko.apr.zad3;

public class Main {
 
  public static void main(String[] args) {
    double x = Math.log(-50);
    if (Double.isNaN(x)) {
      x = Double.MAX_VALUE;
    }
    System.out.println(x);
  }

}
