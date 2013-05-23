class PowerOperator extends Operator {
    public PowerOperator() {
	super('^');
    }

    public String[] getCode() {
	String[] toReturn = { "invokestatic java/lang/Math/pow (DD)D" };
	return toReturn;
    }
}
