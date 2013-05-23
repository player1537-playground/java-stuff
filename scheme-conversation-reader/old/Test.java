class Test {
    public static void main(String[] args) {
	Parser.parseString("(if (defined 'x) (say \"Meow\") (set! n 5))");
    }
}