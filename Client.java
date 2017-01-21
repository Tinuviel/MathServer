import java.io.*;
import java.net.*;
/**
 * A Client to a server
 * Gives a mathematical expression
 * Can be both interactive and from commandoline
 * @author Lovisa Colérus
 * 2016
 *
 */
public class Client {
	
	public static void main(String[] args){
		int port = 25568;
		String host;
		try {
			if(args.length > 0){
				host = args[0];
			}else{
				host = "localhost";
			}
			
			Socket socket = new Socket(host, port);
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			String userInput;
			String greeting = in.readLine();
			
			if(args.length > 1){
				userInput = args[1];
			}else{
				System.out.println(greeting);
				userInput = stdIn.readLine(); 
			}
			
			out.println(userInput);
			System.out.println(in.readLine()); 

		} catch (UnknownHostException e) {
			System.out.print("Unknown host");
		} catch (IOException e) {
			System.out.print("IOException");
		}
	}

}
