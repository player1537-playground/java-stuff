class DivideOperator extends Operator {
    public DivideOperator() {
	super('/');
    }

    public String[] getCode() {
	String[] toReturn = { "ddiv" };
	return toReturn;
    }
}
