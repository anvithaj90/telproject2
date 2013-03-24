import java.io.IOException;
import java.util.Arrays;


public class ParserHelper {
	public static byte[] parseHttp(String input) throws IOException{
		String[] inp, httpversion;
		input = input.replaceAll("\0", "");
		//System.out.println(input);
		String error = "";
		Integer errornumber;
		String filetype = "text/html";
		byte[] finalresponse;
		byte[] filecontent;
	//	byte[] c = null;
		inp = input.split(" ");
		String checkcss;
		checkcss = inp[1].substring((inp[1].length())-3);
		if(checkcss.equals("css"))
		{
			filetype = "text/css";
		}
		httpversion = inp[2].split("\r\n");
		if (filetype != "text/css")
		filetype = GetMime.getMimeType(inp[1]);
		//System.out.println(filetype);
		//System.out.println(inp[1]);
		if(!((httpversion[0].trim().equals("HTTP/1.0"))||(httpversion[0].trim().equals("HTTP/1.1"))))
		{
			errornumber = 505;
			error = "HTTP version not supported";
	   		finalresponse = response(inp[0],httpversion[0], errornumber, error, filetype);
			return finalresponse;
		}
		if((inp[0].equals("GET")))
		{
			errornumber = 200;
			error = "OK";
			String[] temp =  null;
			temp = filetype.split("/");
			if (temp[0].equals("image"))
			{
				filecontent = Imageread.readImage(input);
			}
			else
			{
			filecontent = Fileread.main(input);
			}
			if(Arrays.equals(filecontent, ("not found").getBytes()))
			{
				errornumber = 404;
				error = "Not Found";
			/*	filecontent = ("<HTML>"
			              + "<HEAD><TITLE>404 Not Found</TITLE></HEAD>"
			              + "<BODY><h1>404 File Not Found</h1>"
			              + "<br><h3><i>usage:http://yourHostName:port/"
			              + "fileName.html <br><br>could not find " + inp[1] + "</h3></i></BODY></HTML>").getBytes();*/
			}
			finalresponse = response(inp[0],httpversion[0], errornumber, error, filetype);
			//if(!(filecontent.equals("not found")))
			/*c = new byte[finalresponse.length + filecontent.length];
			System.arraycopy(finalresponse, 0, c, 0, finalresponse.length);
			System.arraycopy(filecontent, 0, c, finalresponse.length, filecontent.length);
			System.out.println(finalresponse);*/
			return finalresponse;
		
		}
		else if((inp[0].equals("HEAD")))
		{
			errornumber = 200;
			error = "OK";
			finalresponse = response(inp[0],httpversion[0], errornumber, error, filetype);
			//System.out.println(finalresponse);
			return finalresponse;
		
		}
		else 
		{
			errornumber = 501;
			error = "Method Unimplemented";
			finalresponse = response(inp[0],httpversion[0], errornumber, error, filetype);
			return finalresponse;
		}
	
	}

	private static byte[] response(String string, String httpversion, Integer errornumber,
			String error, String filetype) {
			
			String finalresponse = httpversion + " " + errornumber + " " + error + 
									"\r\n" + "Server: Simple/1.0\r\n" +
					               "Content-Type: " + filetype + "\r\n\r\n";
			byte[] finals = finalresponse.getBytes();
			System.out.println(finalresponse);
			return finals;
		
		
	}
	
}
