
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ImageProcess  extends Component {

  // public static void main(String[] foo) {
  //   new ImageProcess();
  // }
  public void printPixelARGB(int pixel) {
    int alpha = (pixel >> 24) & 0xff;
    int red = (pixel >> 16) & 0xff;
    int green = (pixel >> 8) & 0xff;
    int blue = (pixel) & 0xff;
    System.out.println("argb: " + alpha + ", " + red + ", " + green + ", " + blue);
  }
  
  private BufferedImage maxPooling(BufferedImage image) throws IOException {
            
    //BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    //System.out.print("Ingresa dim maxPooling: ");
    int poolDim = 10;//Integer.parseInt(reader.readLine());
    //System.out.println(poolDim);
    
    int width_img = image.getWidth();
    int height_img = image.getHeight();
    //System.out.println("new width, new height: " + width_img + ", " +height_img);
    int out_width = (int)Math.ceil((float)width_img/poolDim);
    int out_height = (int)Math.ceil((float)height_img/poolDim);
    
    BufferedImage image_out = new BufferedImage(out_width, out_height, image.getType());
    
    int outX = 0, outY = 0 ;
    
    for(int x=0; x<width_img ; x += poolDim){
      for(int y=0 ;y<height_img ; y += poolDim){

              int maxr = -1, maxg = -1, maxb = -1;

              for(int maxPoolX=x; maxPoolX< Math.min(x+poolDim,width_img); maxPoolX++){
                  for(int maxPoolY=y; maxPoolY<Math.min(y+poolDim,height_img); maxPoolY++)
                  {
                          int imageX = maxPoolX;
                          int imageY = maxPoolY;

                          int rgb = image.getRGB(imageX,imageY);
                          int r = (rgb >> 16) & 0xff; 
                          int g = (rgb >> 8) & 0xff;
                          int b = (rgb & 0xff);
                          
                          if( r > maxr) maxr = r;
                          if( g > maxg) maxg = g;
                          if( b > maxb) maxb = b;

                  }
              }

              int rr = (int) maxr;
              int gg = (int) maxg;
              int bb = (int) maxb;

              int pixel = (rr << 16) + (gg << 8) + bb;
              image_out.setRGB(outX,outY,pixel);
              
              outY++;
      }
      outY = 0;   
      outX++;
    }
    
  //  System.out.print("Nombre de archivo modificado: ");
  //  String outputfname = reader.readLine();
   // File myNewJPegFile = new File(System.getProperty("user.dir")+"/src/javaapplication1/"+outputfname+".jpg");
 //   ImageIO.write(image_out, "jpg", myNewJPegFile);
  return image_out;
}
  
  private BufferedImage  applyKernel(BufferedImage image,double kernel[][]) throws IOException {
    //BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); 
    int width_img = image.getWidth();
    int height_img = image.getHeight();
    BufferedImage image_out = image;
    //System.out.println("kernel col, kernel row: " + kernel[0].length + ", " + kernel.length);
    //System.out.println("Width:, Heigth: " + width_img + ", " + height_img);
    
    int kernel_col = kernel[0].length;
    int kernel_row = kernel.length;
    int offset_x = kernel_col/2;
    int offset_y = kernel_row/2;
    
    for(int x=offset_x; x<width_img - offset_x ;x++)
        for(int y=offset_y ;y<height_img - offset_y ;y++){
                float red=0f,green=0f,blue=0f;
                for(int kr=0; kr<kernel_row; kr++)
                    for(int kc=0; kc<kernel_col; kc++)
                    {
                            int imageX = x + kr - offset_x; 
                            int imageY = y + kc - offset_y;

                            int rgb = image_out.getRGB(imageX,imageY);
                            int r = (rgb >> 16) & 0xff; 
                            int g = (rgb >> 8) & 0xff;
                            int b = (rgb & 0xff);

                            red += (r*kernel[kr][kc]);
                            green += (g*kernel[kr][kc]);
                            blue += (b*kernel[kr][kc]);
                    }


                
                int rr = Math.min(Math.max((int)(red*1),0),255);
                int gg = Math.min(Math.max((int)(green*1),0),255);
                int bb = Math.min(Math.max((int)(blue*1),0),255);
                /*
                int rr = (int) red;
                int gg = (int) green;
                int bb = (int) blue;*/

                int pixel = (rr << 16) + (gg << 8) + bb;
                image_out.setRGB(x,y,pixel);
        }
    
  //  System.out.print("Nombre de archivo modificado: ");
  //  String outputfname = reader.readLine();
  //  File myNewJPegFile = new File(System.getProperty("user.dir")+"//src//javaapplication1//"+outputfname+".jpg");
  //  ImageIO.write(image_out, "jpg", myNewJPegFile);
    return  image_out;
  }
  
  public double[][] escalar(BufferedImage img) {
    double matriz[][] = new double[img.getHeight()][img.getWidth()];

    for (int i = 0; i < img.getHeight(); i++) {

        for (int j = 0; j < img.getWidth(); j++) {
            double mediapixel, colorSRGB;

            Color c = new Color(img.getRGB(j, i));
            double pi = c.getRed();
            // System.out.println("hello fuckin world");
            //  System.out.println(c.getRed());
              
            // colorSRGB=(pi<<16) | (pi<<8) | pi;
            matriz[i][j] = (pi / 255) > 0.5 ? 1 : 0;
            //System.out.printf("%.2f ",matriz[i][j]);
        }

        //System.out.println(" ");
    }
    return matriz;

}
  
  private BufferedImage convertToGray(BufferedImage image) throws IOException {
    
    int width_img = image.getWidth();
    int height_img = image.getHeight();
    BufferedImage image_out = image;
    System.out.println("width, height: " + width_img + ", " +height_img);
    
    for (int x = 0; x < image_out.getWidth(); ++x)
        for (int y = 0; y < image_out.getHeight(); ++y)
        {
            int rgb = image_out.getRGB(x, y);
            int alpha = (rgb >> 24) & 0Xff;
            int red = (rgb >> 16) & 0xff;
            int green = (rgb >> 8) & 0xff;
            int blue = (rgb & 0xff);
            
            // normalize and gamma correct
            float rr = (float) Math.pow(red   / 255.0 , 2.2);
            float gg = (float) Math.pow(green / 255.0 , 2.2);
            float bb = (float) Math.pow(blue  / 255.0 , 2.2);
            
            float ylinear = (float) (0.2126*rr + 0.7152*gg + 0.0722*bb);
            
            // doc: https://entropymine.com/imageworsener/grayscale/
            int grayPixel = (int) (255.0 * Math.pow(ylinear, 1.0 / 2.2));
            int gray = (alpha << 24) + (grayPixel << 16) + (grayPixel << 8) + grayPixel;
            image_out.setRGB(x, y, gray);
        }
        return image_out;
    //File myNewJPegFile = new File(System.getProperty("user.dir")+"/src/nn_generic/"+image_name+"-gray.jpg");
    //ImageIO.write(image_out, "jpg", myNewJPegFile);
    
  }
  
  private void marchThroughImage(BufferedImage image, String image_name) {
    
    int width_img = image.getWidth();
    int height_img = image.getHeight();
    System.out.println("width, height: " + width_img + ", " +height_img);

    for (int i = 0; i < height_img; i++) {
      for (int j = 0; j < width_img; j++) {
        System.out.println("x,y: " + j + ", " + i);
        int pixel = image.getRGB(j, i);
        printPixelARGB(pixel);
        System.out.println("");
      }
    }
    
  }
  
  public void mostrar(BufferedImage img) {
        JLabel label = new JLabel(new ImageIcon(img));
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(label);
        f.pack();
        f.setLocation(200, 200);
        f.setVisible(true);
  }
  
  public void pastoEscalagrise(BufferedImage img) {

        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                //Almacenamos el color del píxel
                Color coloraux;
                coloraux = new Color(img.getRGB(x, y));
                int mediapixel, colorSRGB;
                //Almacenamos el color del píxel

                //calculamos la mediade los  tres canales(rojo,ver,azul)
                mediapixel = (int) ((coloraux.getRed() + coloraux.getGreen() + coloraux.getBlue()) / 3);
                //cambiamo a formato sRGB
                //  System.out.println("mediapixel:" +mediapixel);
                colorSRGB = (mediapixel << 16) | (mediapixel << 8) | mediapixel;
                // System.out.println("colorSRGB:" +colorSRGB);

                img.setRGB(x, y, colorSRGB);

                //     System.out.println(": "+colorSRGB);  
            }
        }
    }

  public ImageProcess(Data dato) {
    try {
      for(int i = 1;i<=100;i++){
          String image_name = "test/cats/cat."+i+".jpg";
          BufferedImage image = ImageIO.read(this.getClass().getResource(image_name));
          image_name = image_name.substring(0,image_name.length()-4);
          double[][] kernel = {{0 , -1, 0},
                              {-1, 5, -1},
                              {0, -1, 0}};
          
          
          dato.ingresaInput(escalar(
            maxPooling(
              applyKernel(
                convertToGray(image)
                ,kernel)
            )
          ));
          double[][] in = {{0,0}};
          in[0][0] = 1;
          dato.ingresaOutput(in);

        String image_name_2 = "test/dogs/dog."+i+".jpg";
        BufferedImage image_2 = ImageIO.read(this.getClass().getResource(image_name_2));
        image_name_2 = image_name_2.substring(0,image_name_2.length()-4);
        double[][] kernel_2 = {{0 , -1, 0},
                            {-1, 5, -1},
                            {0, -1, 0}};
        
        
        dato.ingresaInput(escalar(
          maxPooling(
            applyKernel(
              convertToGray(image_2),
              kernel_2)
          )
        ));
        double[][] in_2 = {{0,0}};
        in[0][1] = 1;
        dato.ingresaOutput(in_2);
      }

      String num = "prueba/cat.3.jpg";
      BufferedImage image_prueba = ImageIO.read(this.getClass().getResource(num));
      num = num.substring(0,num.length()-4);
      double[][] kernel_prueba = {{0 , -1, 0},
                              {-1, 5, -1},
                              {0, -1, 0}};
      dato.ingresaTest(escalar(
        maxPooling(
          applyKernel(image_prueba,kernel_prueba)
        )
      ));


    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
  }

}