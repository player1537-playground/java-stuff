import java.util.regex.*;
import java.io.*;

public class Plang {

    String peekchar = null;
    boolean eof = false;

    public static void main(String[] args) {
	try {
	    Plang pp = new Plang();
	    pp.p(pp.read("0123456789"));

	    while (!eof) {
		if (pp.isnumber(peek())) {
		    number();}}

	    pp.isnumber("1.234");
	    pp.isnumber("Mordor");
	    pp.isnumber("Andy");
	    pp.isnumber("+51");
	    pp.isnumber("-2.31");
	    pp.isnumber("4.32211");
	    pp.isnumber("60000000000");
	    pp.isnumber("Jurva");
	} catch (IOException e) {
	    return;
	}
    }
    public void compile(String fname) {
    }
    private String readwhile(String chars) throws IOException {
	String ret = new String();
	char cur;
	while (chars.indexOf((cur = (char)System.in.read())) != -1) {
	    ret += cur;
	}
	return ret;
    }
    private String read() throws IOException {
	return read("\n");
    }
    private String read(String sep) throws IOException {
	String ret = new String();
	char cur;
	while (sep.indexOf((cur = (char)System.in.read())) == -1) {
	    ret += cur;
	}
	return ret;
    }	    
    private String peek() throws IOException {
	if (peekchar != null) {
	    peekchar = "" + ((char)System.in.read());
	}
	return peekchar;
    }
    private void p(String s) {
	System.out.println(s);
    }
    private boolean isoper(String s) {
	if ((Pattern.compile("[-+*/]").matcher(s)).matches()) {
	    p(s + ": True");
	    return true;
	} else {
	    p(s + ": False");
	    return false;
	}
    }
    private boolean isnumber(String s) {
	// One does not simply MATCH into Mordor.
	Pattern number = Pattern.compile("[-+]?[0-9]+\\.[0-9]*|[-+]?\\.?[0-9]+");
	if ((number.matcher(s)).matches()) {
	    p(s + ": True");
	    return true;
	} else {
	    p(s + ": False");
	    return false;
	}
    }
}