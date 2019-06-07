package math;

public class MathOp implements IMathOp {

	private static IMathOp instance = null;

	public static IMathOp getInstance() {
		if (MathOp.instance == null) {
			instance = new MathOp(1.e-5);
		}
		return instance;
	}

	public static IMathOp getInstance(double squareRootCorrectness) {
		if (MathOp.instance == null) {
			return new MathOp(squareRootCorrectness);
		}
		return MathOp.instance;
	}

	private final double squareRootCorrectness;

	public MathOp(double squareRootCorrectness) {
		this.squareRootCorrectness = squareRootCorrectness;
	}

	public double getSquareRootCorrectness() {
		return squareRootCorrectness;
	}

}
