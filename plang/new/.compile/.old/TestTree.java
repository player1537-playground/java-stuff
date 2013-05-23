class TestTree {
    public static void main(String[] args) {
	Tree tree = new Tree();
	tree.getHead().setLeft(new Operator('-'));
	tree.getHead().getLeft().setLeft(new Number(10));
	tree.getHead().getLeft().setRight(new Operator('+'));
	tree.getHead().getLeft().getRight().setLeft(new Number(5));
	tree.getHead().getLeft().getRight().setRight(new Number(7));
	System.out.println(tree.toString());
	tree.insertTop(new Operator('+'));
	tree.getHead().getLeft().setRight(new Number(3));
	System.out.println(tree.toString());
	tree.insertTop(new Operator('/'));
	tree.addLeftMost(new Number(3.5));
	System.out.println(tree.toString());
    }
}