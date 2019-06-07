package math.matrix;

public interface IMatrix {

	final IMatrixTrix operator = MatrixOperator.getInstance();

	double[][] getValues();

	default IMatrix getRow(int i) throws Exception {
		final double[][] values = new double[1][getValues().length];
		values[0] = getValues()[i];
		return new Matrix(values);
	}

	default IMatrix getColumn(int i) throws Exception {
		final double[][] values = new double[getValues().length][1];
		for (int j = 0; j < getValues().length; j++) {
			values[j][0] = getValues()[j][i];
		}
		return new Matrix(values);
	}

	default int getLength() {
		return getValues()[0].length;
	}

	default int getHeight() {
		return getValues().length;
	}

	default double getEntry(int i, int j) throws Exception {
		return getValues()[i][j];
	}

	default IMatrix inverse() throws Throwable {
		return new Matrix(operator.inverse(getValues()));
	}

	default double det() throws Exception {
		if (getHeight() != getLength()) {
			throw new Exception("non-quadratic");
		}
		return operator.det(getValues());
	}

	default IMatrix transposed() throws Exception {
		final double[][] trans = new double[getLength()][getHeight()];
		for (int i = 0; i < getHeight(); i++) {
			for (int j = 0; j < getLength(); j++) {
				trans[j][i] = getEntry(i, j);
			}
		}
		return new Matrix(trans);
	}

	default IMatrix multiplyWith(IMatrix mat) throws Exception {
		final double[][] ans = new double[getHeight()][mat.getLength()];
		for (int i = 0; i < getHeight(); i++) {
			for (int j = 0; j < mat.getLength(); j++) {
				;
				for (int m = 0; m < getLength(); m++) {
					ans[i][j] += getEntry(i, m) * mat.getEntry(m, j);
				}
			}
		}
		return new Matrix(ans);
	}

	default IMatrix lsolve(IMatrix A) throws Exception {

		final double[][] ans = new double[A.getLength()][getLength()];
		final double[][] tmpA = new double[getHeight()][getLength()];
		for (int i = 0; i < getHeight(); i++) {
			for (int j = 0; j < getLength(); j++) {
				;
				tmpA[i][j] = getEntry(i, j);
			}
		}
		double[] b;
		for (int i = 0; i < A.getLength(); i++) {
			b = A.transposed().getValues().clone()[i];
			ans[i] = GaussianElimination.lsolve(tmpA, b);
		}
		return new Matrix(ans).transposed();
	}

	default double norm() throws Exception {
		double ans = 0;
		for (int i = 0; i < getHeight(); i++) {
			for (int j = 0; j < getLength(); j++) {
				ans += Math.pow(getEntry(i, j), 2);
			}
		}
		return Math.sqrt(ans);
	}

	default IMatrix add(IMatrix mat) throws Exception {
		if (getLength() != mat.getLength() || getHeight() != mat.getHeight()) {
			throw new Exception();
		}
		final double[][] ans = new double[getHeight()][getLength()];
		for (int i = 0; i < getHeight(); i++) {
			for (int j = 0; j < getLength(); j++) {
				ans[i][j] = getEntry(i, j) + mat.getEntry(i, j);
			}
		}
		return new Matrix(ans);
	}

	default IMatrix scaleBy(double r) throws Exception {
		final double[][] ans = new double[getHeight()][getLength()];
		for (int i = 0; i < getHeight(); i++) {
			for (int j = 0; j < getLength(); j++) {
				ans[i][j] = r * getEntry(i, j);
			}
		}
		return new Matrix(ans);
	}

	default IMatrix toUnit() throws Exception {
		return scaleBy(Math.pow(norm(), -1));
	}

	default IMatrix getUnit(int i, int j) {
		final double[][] tmp = new double[getHeight()][getLength()];
		tmp[i][j] = 1;
		return new Matrix(tmp);
	}
}
