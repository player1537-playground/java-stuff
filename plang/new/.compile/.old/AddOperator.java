class AddOperator extends Operator {
    public AddOperator() {
	super('+');
    }

    public String[] getCode() {
	String[] toReturn = { "dadd" };
	return toReturn;
    }
}
