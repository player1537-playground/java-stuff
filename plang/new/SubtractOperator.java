class SubtractOperator extends Operator {
    public SubtractOperator() {
	super('-');
    }

    public String[] getCode() {
	String[] toReturn = { "" };
	String code;
	System.err.println("Inside SubtractOperator, getRight() = " + this.getRight());
	if (this.getRight() != null) {
	    code = "dsub";
	} else {
	    code = "dneg";
	}
	toReturn[0] = code;
	return toReturn;
    }
}
