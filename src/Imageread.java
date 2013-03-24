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

  public static byte[] readImage(String args) throws IOException {
//	  

	  String[] inp = null;
	  inp = args.split(" ");
    File file = new File(System.getProperty("user.dir") + "/www"+inp[1]);
  //  System.out.println(args);
    FileInputStream fis = null;

    byte[] filecontent = new byte[(int)file.length()] ;
    if(!(file.exists()))
    {
    	return ("not found").getBytes();
    }
    try {
      fis = new FileInputStream(file);

      fis.read(filecontent, 0,(int)file.length());

      System.out.println(filecontent);
      
     /* fis.close();
      bis.close();
      dis.close();*/

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
	return filecontent;
  }
}
