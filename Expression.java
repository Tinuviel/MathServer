import java.util.*;
import java.io.*;
/**
 * Calculates an mathematical expression
 * @author Lovisa Colérus
 * 2016
 *
 */
public class Expression {

    Scanner scanner;
    int price;
    private StringBuilder soFar = new StringBuilder();
    
    public static void main(String[] argv) {
        Expression p = new Expression(new Scanner(padWithSpace("4/4*1")));
        if (argv.length > 0) { 
            String s = padWithSpace(argv[0]);
            p = new Expression(new Scanner(s));
        }
        double result = p.readExpr();
        System.out.println("Parsed line: " + p.soFar);
        System.out.println("Result: " + result);
    }
    
    public int getPrice(){
    	return this.price;
    }
    
    public String getExpr(){
    	return soFar.toString();
    }
    /** 
     *  @param s Expression String where operators will be space separated
     *           1+2*3*(1-2) -> 1 + 2 * 3 * ( 1 - 2 ) 
     */
    static String padWithSpace(String s) {
            s = s.replaceAll("\\+", " + ");
            s = s.replaceAll("-"  , " - ");
            s = s.replaceAll("\\*", " * ");
            s = s.replaceAll("/"  , " / ");
            s = s.replaceAll("\\)", " ) ");
            s = s.replaceAll("\\(", " ( ");
            return s;
    }
    /**
     *  Constructor
     * @param s
     */
    Expression(Scanner s) {
        scanner = s;
        this.price = 0;
    }

    /** 
     * I/O methods - note must have space between tokens, 3*2 will not be parsed
     * @return
     */
    
    /**
     * makes a positive double
     * @return
     */
    private double popValue() {
        String next = scanner.next();
        soFar.append(" " + next);
        return Double.parseDouble(next);
    }
    
    /**
     * makes a negative double
     * @return
     */
    private double popNegativeValue() {
        scanner.next("-");
        String next = scanner.next();
        soFar.append(" -" + next);
        return -1 * Double.parseDouble(next);
    }
    
    /**
     * 
     * @return which token
     */
    private String popToken() {
        String next = scanner.next();
        soFar.append(" " + next);
        return next;
    }
    
    /**
     * checks if the next char is a specified char
     * @param s
     * @return true if its the same
     */
    private boolean peek(char s) {
        if (scanner.hasNext()) {
            if (scanner.hasNext("\\" + s)){
            	return true;// if s = '*' then must prefix "\\*"
            	
            }
        }
              
        return false;
    }

    /**
     * read an expression
     * @return
     */
    double readExpr() {

        double x = readTerm();      

        for(;;){
        	if(peek('+')){
        		++this.price;
        		popToken();
        		x += readTerm();
        	}else if(peek('-')){
        		++this.price;
        		popToken();
        		x -= readTerm();
        	}else{
            	return x;
        	}
        }  
    }
    
    protected double readTerm(){
    	double x = readFactor();
    	for(;;){
    		if(peek('/')){
    			++this.price;
    			popToken();
    			x /= readFactor();
    		}else if(peek('*')){
    			++this.price;
    			popToken();
    			x *= readFactor();
    		}else{
    			return x;
    		}
    	}
    }
    
    protected double readFactor(){
    	double x;
    	boolean negative = false;
    	
    	if(peek('+')||peek('-')){
    		negative = peek('-');
    		popToken();
    	}
    	
    	if(peek('(')){
    		++this.price;
    		popToken();
    		x = readExpr();
    		
    		if(peek(')')){
    			popToken();
    		}
    		
    	}else{
    		x = popValue();
    	}
    	
    	if(negative){
    		x = -x;
    	}
    	return x;	
    }
}

