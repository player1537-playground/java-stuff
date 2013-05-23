class Getter extends Variable {
    public Getter(char var) {
	super(var);
    }

    public Getter(String var) {
	super(var);
    }

    public String[] getCode() {
	String[] toReturn = { 
	    PlangCompiler.load(variableMap.get(this.variable).intValue())
	};
	System.err.println(variableMap);
	return toReturn;
    }
}