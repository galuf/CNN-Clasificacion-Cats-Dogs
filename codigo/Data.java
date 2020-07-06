import java.util.*;
public class Data {

  public ArrayList<double[][]> input = new ArrayList<double[][]>();
  public ArrayList<double[][]> output = new ArrayList<double[][]>();
  public ArrayList<double[][]> test = new ArrayList<double[][]>();

  public void ingresaInput(double[][] m){
    input.add(m);
  }

  public void ingresaOutput(double[][] m){
    output.add(m);
  }

  public void ingresaTest(double[][] m){
    test.add(m);
  }

}