/**
 * @file: Server.java
 * 
 * @author: Chinmay Kamat <chinmaykamat@cmu.edu>
 * 
 * @date: Feb 15, 2013 1:13:37 AM EST
 * 
 */
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Server {
	private static ServerSocket srvSock;

	public static void main(String args[]) {
		int port = 8080;
		/*	String buffer = null;
		
		BufferedReader inStream = null;
		DataOutputStream outStream = null;
		
		
		String[] inp;*/
		/* Parse parameter and do args checking */
		if (args.length < 1) {
			System.err.println("Usage: java Server <port_number>");
			System.exit(1);
		}

		try {
			port = Integer.parseInt(args[0]);
		} catch (Exception e) {
			System.err.println("Usage: java Server <port_number>");
			System.exit(1);
		}

		if (port > 65535 || port < 1024) {
			System.err.println("Port number must be in between 1024 and 65535");
			System.exit(1);
		}

		try {
			/*
			 * Create a socket to accept() client connections. This combines
			 * socket(), bind() and listen() into one call. Any connection
			 * attempts before this are terminated with RST.
			 */
			srvSock = new ServerSocket(port);
		} catch (IOException e) {
			System.err.println("Unable to listen on port " + port);
			System.exit(1);
		}
//boolean flag = true;
		while (true) {
			//flag = false;
			Socket clientSock;
			try {
				/*
				 * Get a sock for further communication with the client. This
				 * socket is sure for this client. Further connections are still
				 * accepted on srvSock
				 */
				clientSock = srvSock.accept();
				System.out.println("Accpeted new connection from "
						+ clientSock.getInetAddress() + ":"
						+ clientSock.getPort());
			} catch (IOException e) {
				continue;
			}
			MultithreadedServer instance = new MultithreadedServer(clientSock);
			new Thread(instance).start();
			
 
			}
	}
}
class MultithreadedServer extends Thread {
	private Socket clientSock;
	public MultithreadedServer(Socket clientSock){
		this.clientSock = clientSock;
	}
	BufferedReader inStream = null;
	DataOutputStream outStream = null;
	String[] inp;
	String buffer = "";
	public void run()
	{

		try {
			inStream = new BufferedReader(new InputStreamReader(
					clientSock.getInputStream()));
			outStream = new DataOutputStream(clientSock.getOutputStream());
			/* Read the data send by the client */
			buffer = inStream.readLine();
			System.out.println("Read from client "
					+ clientSock.getInetAddress() + ":"
					+ clientSock.getPort() + " " + buffer);
			long threadId = Thread.currentThread().getId();
			System.out.println(threadId);
			/*
			 * Echo the data back and flush the stream to make sure that the
			 * data is sent immediately
			 */
			byte[] temp = null;
			String req = "";
			req = buffer.replaceAll("\0", "");
			String[] inp1 = null;
			inp1 = req.split(" ");
			String filetype = "";
			filetype = GetMime.getMimeType(inp1[1]);
			System.out.println(filetype);
			String[] temp2 = null;
			if(filetype == null)
			{
				filetype ="a/a";
			}
			temp2 = filetype.split("/");
			if (temp2[0].equals("image"))
			{
				/*File imageFile = new File(System.getProperty("user.dir") + "/www"+inp[1]);
                FileInputStream fs = new FileInputStream(imageFile);
                byte[] data = new byte[(int)imageFile.length()];
                fs.read(data, 0, (int)imageFile.length());
                fs.close();*/
                //System.arraycopy(data, 0, temp, 0, data.length);
				//temp = Imageread.readImage(req);
			}
			else
			{
				temp = Fileread.main(req);
			}
			String[] httpversion = inp1[2].split("\r\n");
			
			
			if (!((httpversion[0].trim().equals("HTTP/1.0"))||(httpversion[0].trim().equals("HTTP/1.1"))))
			{
				byte[] wrongversion = ("<HTML>"
			              + "<HEAD><TITLE>505 Not Supported</TITLE></HEAD>"
			              + "<BODY><h1>505 Not Supported</h1>"
			              + "<br><h3><i>HTTP Version Not Supported <br><br>Server does not support " + inp1[2] + "</h3></i></BODY></HTML>").getBytes(); 
			outStream.write(ParserHelper.parseHttp(buffer));
			//System.out.println(temp.toString());
			outStream.write(wrongversion, 0, wrongversion.length);
			outStream.flush();
			}
			else if(Arrays.equals(temp, ("not found").getBytes()))
			{
				byte[] notfound = ("<HTML>"
			              + "<HEAD><TITLE>404 Not Found</TITLE></HEAD>"
			              + "<BODY><h1>404 File Not Found</h1>"
			              + "<br><h3><i>usage:http://yourHostName:port/"
			              + "fileName.html <br><br>could not find " + inp1[1] + "</h3></i></BODY></HTML>").getBytes(); 
			outStream.write(ParserHelper.parseHttp(buffer));
			//System.out.println(temp.toString());
			outStream.write(notfound, 0, notfound.length);
			outStream.flush();
			}
			else if(!((inp1[0].equals("GET") || (inp1[0].equals("HEAD")))))
			{
				byte[] notimplemented = ("<HTML>"
			              + "<HEAD><TITLE>501 Method Not Implemented</TITLE></HEAD>"
			              + "<BODY><h1>501 Method Not Implemented</h1>"
			              + "<br><h3><i>Method has not been implemented at sever </h3></i></BODY></HTML>").getBytes(); 
			outStream.write(ParserHelper.parseHttp(buffer));
			//System.out.println(temp.toString());
			outStream.write(notimplemented, 0, notimplemented.length);
			outStream.flush();
			}
			else
			{
				outStream.write(ParserHelper.parseHttp(buffer));
				//System.out.println(temp.toString());
				if (temp2[0].equals("image"))
				{
					File imageFile = new File(System.getProperty("user.dir") + "/www"+inp1[1]);
	                FileInputStream fs = new FileInputStream(imageFile);
	                byte[] data = new byte[(int)imageFile.length()];
	                fs.read(data, 0, (int)imageFile.length());
	                fs.close();
					outStream.write(data);                
	                outStream.flush();
				}
				else
				{
				outStream.write(temp,0,temp.length);
				outStream.flush();
				}
				System.out.println("Response Sent");
			}
			/* Interaction with this client complete, close() the socket */
			//inStream.close();
			//outStream.close();
			clientSock.close();
		} catch (IOException e) {
			clientSock = null;
		
		}

	}
}
