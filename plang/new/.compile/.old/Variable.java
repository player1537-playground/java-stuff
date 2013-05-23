import java.util.*;

abstract class Variable extends TreeNode {
    static HashMap<String,Integer> variableMap = new HashMap<String,Integer>();
    String variable;

    public Variable() {
	System.err.println("Can't construct with no variable");
    }
    public Variable(char var) {
	this(String.valueOf(var));
    }
    public Variable(String var) {
	if (!variableMap.containsKey(var)) {
	    variableMap.put(var, new Integer(variableMap.size() * 2));
	}
	this.variable = var;
    }
    
    public static int getVariableCount() {
	return variableMap.size();
    }
    
    public String getVariable() {
	return this.variable;
    }

    public abstract String[] getCode();
}