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
		String filecontent = "";
		inp = input.split(" ");
		
		httpversion = inp[2].split("\r\n");
		filetype = GetMime.getMimeType(inp[1]);
		System.out.println(filetype);
		System.out.println(inp[1]);
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
			filecontent = Fileread.main(inp[1]);
			finalresponse += filecontent;
			System.out.println(finalresponse);
			return finalresponse;
		
		}
		if((inp[0].equals("HEAD")))
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

	private static String response(String string, String httpversion, Integer errornumber,
			String error, String filetype) {
			
			String finalresponse = httpversion + " " + errornumber + " " + error + " \n" +
		                           "Server: Simple/1.0\n" +
					               "Content-Type: " + filetype + "\n\n";
			return finalresponse;
		
		
	}
	
}
