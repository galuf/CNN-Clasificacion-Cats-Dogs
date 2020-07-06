public class Principal {
  public static void main(String[] args) {
    
    Data datos = new Data();
    ImageProcess imgs = new ImageProcess(datos);

    double [][] input = new double[datos.input.size()][625]; 
    double [][] output = new double[datos.output.size()][2];
    double [][] evaluar = new double[datos.test.size()][625];

    for(int i=0;i<input.length;i++){
      input[i] = generaVector(datos.input.get(i));
    }
    for(int i=0;i<output.length;i++){
      output[i] = datos.output.get(i)[0];
    }

    for(int i=0;i<evaluar.length;i++){
      evaluar[i] = generaVector(datos.test.get(i));
    } 

    prueba2 rn = new prueba2(625,300,100,50,30,20,2);
    rn.entrenamiento(input, output,1000);
    
    rn.prueba(evaluar);


  }

  public static double[] generaVector(double[][] m){
    double[] array = new double[625];

    for(int i=0;i<m.length;i++){
      for(int j = 0; j<m[0].length;j++){
        if(i*25 +j < 625)
          array[i*25 +j] = m[i][j];
      }
    }
    if(m.length*m[0].length < 625 ){
      for (int i = m.length*m[0].length -1 ; i<625;i++)
        array[i] = 0;
    }

    return array;
  }
  public static void mostrar(double[][] m){
    for(int i=0;i<m.length;i++){
      for(int j = 0; j<m[0].length;j++){
        System.out.print(m[i][j]+" ");
      }
      System.out.println();
    }
  }
}