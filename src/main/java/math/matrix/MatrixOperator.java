package math.matrix;

public class MatrixOperator implements IMatrixTrix {

	private static IMatrixTrix instance = null;

	public static IMatrixTrix getInstance() {
		if (instance == null) {
			instance = new MatrixOperator();
		}
		return instance;
	}

	private MatrixOperator() {
	}

}
