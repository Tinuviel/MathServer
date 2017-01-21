import java.io.*;
import java.net.*;
/**
 * 
 * @author Lovisa Colérus
 * 2016
 *
 */

public class Server {
	public static void main(String[] args)throws IOException{
		ServerSocket ss;
		try{
			ss = new java.net.ServerSocket(25568);
			
			while(true){
				ServerThread r = new ServerThread(ss.accept()); 
			    r.start();
			}
		}catch(IOException e){
			System.out.println("IOException");
			System.exit(1);
		}

		
		
		
    }    
}
