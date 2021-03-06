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
public class Fileread {

  public static byte[] main(String args) throws IOException {

	  String[] inp = null;
	  inp = args.split(" ");
	  
    File file = new File("www/"+inp[1]);
  //  System.out.println(args);
    FileInputStream fis = null;
    BufferedInputStream bis = null;
    DataInputStream dis = null;
    String filecontent = "";
    if(!(file.exists()))
    {
    	return ("not found").getBytes();
    }
    try {
      fis = new FileInputStream(file);
      bis = new BufferedInputStream(fis);
      dis = new DataInputStream(bis);

      while (dis.available() != 0) {
      
       // System.out.println(dis.readLine());
    	  filecontent += dis.readLine();
      }
    //  System.out.println(filecontent);
      
      fis.close();
      bis.close();
      dis.close();

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
	return filecontent.getBytes();
  }
}
