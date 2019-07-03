package deprecated.math.matrix;

public final class Identity extends Matrix implements IMatrix {

	public Identity(final int n) {
		super(id(n));
	}

	static double[][] id(final int n) {
		final double[][] id = new double[n][n];
		for (int i = 0; i < n; i++) {
			id[i][i] = 1;
		}
		return id;
	}

}
