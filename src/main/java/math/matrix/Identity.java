package math.matrix;

public final class Identity extends Matrix implements IMatrix {

	public Identity(int n) {
		super(id(n));
	}

	final static double[][] id(int n) {
		final double[][] id = new double[n][n];
		for (int i = 0; i < n; i++) {
			id[i][i] = 1;
		}
		return id;
	}

}
