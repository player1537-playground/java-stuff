import java.util.*;

class Parser {
    static void parseFile(String filename) {
	//return Parser.parseString(filename); // TODO: Fix
	return;
    }

    static void parseString(String str) {
	String functionName = null;
	//	ArrayList<SchemeTree> args = new ArrayList<SchemeTree>();
	String[] strArray = str.split("((?<=\"(?:\\\\\"|[^\"])+\")|(?<=[()])|(?=[()]))|[ \n\t]");
	System.out.println(Arrays.toString(strArray));
    }
}