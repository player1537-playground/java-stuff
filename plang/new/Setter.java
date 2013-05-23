class Setter extends Variable {
    public Setter(char var) {
	super(var);
    }

    public Setter(String var) {
	super(var);
    }

    public String[] getCode() {
	System.err.println(variableMap);
	String[] toReturn = { 
	    PlangCompiler.store(variableMap.get(variable).intValue())
	};
	return toReturn;
    }
}