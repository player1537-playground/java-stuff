public class AddOp extends BinOp {
				public Value getValue() {
								return left.getValue() + right.getValue();
				}
}
