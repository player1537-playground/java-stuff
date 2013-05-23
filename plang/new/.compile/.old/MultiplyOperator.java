class MultiplyOperator extends Operator {
    public MultiplyOperator() {
	super('*');
    }

    public String[] getCode() {
	String[] toReturn = { "dmul" };
	return toReturn;
    }
}
