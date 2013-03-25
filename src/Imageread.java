import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This program reads a text file line by line and print to the console. It uses
 * FileOutputStream to read the file.
 * 
 */
public class Imageread {

  private static BufferedInputStream bin;

public static byte[] readImage(String args) throws IOException {
//	  

	  String[] inp = null;
	  inp = args.split(" ");
    File file = new File(System.getProperty("user.dir") + "/www"+inp[1]);
    byte [] bytearray  = new byte [(int)file.length()];
  //  System.out.println(args);

   // byte[] filecontent = new byte[(int)file.length()] ;
    if(!(file.exists()))
    {
    	return ("not found").getBytes();
    }
    try {
    	 /*FileInputStream fis = new FileInputStream(file);
    	 BufferedInputStream bin = new BufferedInputStream(fis);*/
    	 
        
         FileInputStream fin = new FileInputStream(file);
         bin = new BufferedInputStream(fin);
         bin.read(bytearray,0,bytearray.length);


/*      fis.read(filecontent, 0,(int)file.length());
      bin.read(filecontent,0,filecontent.length);
      System.out.println(filecontent);*/
      
   //  fis.close();

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
	return bytearray;
  }
}
