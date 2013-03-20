import java.io.IOException;


public class ParserHelper {
	public static String parseHttp(String input) throws IOException{
		String[] inp, httpversion;
		input = input.replaceAll("\0", "");
		System.out.println(input);
		String error = "";
		Integer errornumber;
		String filetype = "text/html";
		String finalresponse = "";
		inp = input.split(" ");
		
		httpversion = inp[2].split("\r\n");
	//	filetype = GetMime.getMimeType(inp[1]);
	//	System.out.println(filetype);
		if(!(httpversion[0].trim().equals("HTTP/1.0")))
		{
			errornumber = 505;
			error += "http version not supported";
	   		finalresponse = response(inp[0],httpversion[0], errornumber, error, filetype);
			return finalresponse;
		}
		if((inp[0].equals("GET")))
		{
			errornumber = 200;
			error = "OK";
			finalresponse = response(inp[0],httpversion[0], errornumber, error, filetype);
			return finalresponse;
		
		}
		if((inp[0].equals("HEAD")))
		{
			errornumber = 200;
			error = "OK";
			finalresponse = response(inp[0],httpversion[0], errornumber, error, filetype);
			System.out.println(finalresponse);
			return finalresponse;
		
		}
		else 
		{
			return "501 Method Unimplemented";
		}
	
	}

	private static String response(String string, String httpversion, Integer errornumber,
			String error, String filetype) {
			//String eol = System.getProperty("line.separator");
		
		if(string.equals("HEAD"))
		{
			String finalresponse = httpversion + " " + errornumber + " " + error + " \n" +
		                           "Server: Simple/1.0\n" +
					               "Content-Type: " + filetype + "\n\n";
	//		System.out.println(finalresponse);
			return finalresponse;
		}
		return error;
	}
	
}
