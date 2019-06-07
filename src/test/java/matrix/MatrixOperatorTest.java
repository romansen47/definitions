package matrix;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import math.matrix.IMatrixTrix;
import math.matrix.MatrixOperator;

public class MatrixOperatorTest {

	final IMatrixTrix MatOp = MatrixOperator.getInstance();

	public final double tolerance = 1.e-5;
	private static double[][] Matrix1 = new double[2][2];
	private static double[][] Matrix2 = new double[2][2];
	private static double[] vec1 = new double[2];
	private static double[] vec2 = new double[2];

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Matrix1[0][0] = 0;
		Matrix1[0][1] = 1;
		Matrix1[1][0] = -1;
		Matrix1[1][1] = 0;

		Matrix2[0][0] = 1;
		Matrix2[0][1] = 0;
		Matrix2[1][0] = 0;
		Matrix2[1][1] = -1;

		vec1[0] = 1;
		vec1[1] = 0;
		vec2[0] = 0;
		vec2[1] = 1;
	}

	@Test
	public void det() {
		final double det1 = MatOp.det(Matrix1);
		final double det2 = MatOp.det(Matrix2);

		assertTrue(Math.abs(det1 - 1.0d) < tolerance);
		assertTrue(Math.abs(det2 + 1.0d) < tolerance);
	}

	@Test
	public void matrixMult() {

		final double[] firstRow1 = MatOp.matrixMult(Matrix1, vec1);
		final double[] secondRow1 = MatOp.matrixMult(Matrix1, vec2);

		final double[] firstRow2 = MatOp.matrixMult(Matrix2, vec1);
		final double[] secondRow2 = MatOp.matrixMult(Matrix2, vec2);

		assertTrue(same(firstRow1, new double[] { 0, -1 }));
		assertTrue(same(secondRow1, new double[] { 1, 0 }));

		assertTrue(same(firstRow2, new double[] { 1, 0 }));
		assertTrue(same(secondRow2, new double[] { 0, -1 }));
	}

	@Test
	public void inverse() throws Throwable {
		final double[][] inverse1 = MatOp.inverse(Matrix1);
		final double[][] inverse2 = MatOp.inverse(Matrix2);

		assertTrue(same(MatOp.matrixMult(inverse1, vec1), vec2));
		assertTrue(same(MatOp.matrixMult(inverse1, vec2), new double[] { -1, 0 }));

		assertTrue(same(MatOp.matrixMult(inverse2, vec1), vec1));
		assertTrue(same(MatOp.matrixMult(inverse2, vec2), new double[] { 0, -1 }));
	}

	public boolean same(double[] vec1, double[] vec2) {
		double ans = 0.d;
		for (int i = 0; i < vec1.length; i++) {
			ans += Math.abs(vec1[i] - vec2[i]);
		}
		return ans < tolerance;
	}
}
