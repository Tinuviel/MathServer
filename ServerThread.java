import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Scanner;
/**
 * A Thread to a Server
 * @author Lovisa Colérus
 * 2016
 *
 */
public class ServerThread extends Thread{
	private Socket ss;
	protected static HashMap<String, Double> clientDiscountByIp = new HashMap<String, Double>();
	
	
	public ServerThread(Socket ss){
		this.ss = ss;
	}
	/**
	 * pads the string with space
	 * taken from example-file "Expression"
	 * @param s
	 * @return
	 */
	String padWithSpace(String s) {
        s = s.replaceAll("\\+", " + ");
        s = s.replaceAll("-"  , " - ");
        s = s.replaceAll("\\*", " * ");
        s = s.replaceAll("/"  , " / ");
        s = s.replaceAll("\\)", " ) ");
        s = s.replaceAll("\\(", " ( ");
        return s;
	}
	
	/**
	 * takes in a mathematic expression and calculates it
	 */
	public void run(){
		String ip = this.ss.getInetAddress().getHostAddress();
		System.out.println ("Connection from : " + ip + ':' + this.ss.getPort());
		
		/*
		 * Creates a hashmap to keep track of ips and how much discount they get
		 */
		if(clientDiscountByIp.containsKey(ip)){
			double discount = clientDiscountByIp.get(ip)/1.01;
			clientDiscountByIp.remove(ip);
			clientDiscountByIp.put(ip, discount);
		}else{
			clientDiscountByIp.put(ip,  1.0);
		}
		
		try{
			PrintWriter out = new PrintWriter(ss.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(ss.getInputStream()));
			out.println("Skriv in ett matematiskt uttryck: ");
			String inputLine = in.readLine();
			inputLine = padWithSpace(inputLine);
			Expression p = new Expression(new Scanner(inputLine));
			
			try{
				out.format("Answer is: " + p.readExpr() + "; " + "Price is: " + "%.3f%n", (p.getPrice()*clientDiscountByIp.get(ip)));
			}catch(Exception e){
				out.println("Invalid Expression");
			}

		}catch(Exception e){
			System.out.println("error");
			e.printStackTrace();
		}
	}
}
