package math;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

public class IVectorTest {

	static IVector VEC;
	static double[] vec1;
	static double[] vec2;
	static double tolerance = 1.e-5;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		VEC = new MathOp(tolerance);
		vec1 = new double[] { 1, 1 };
		vec2 = new double[] { 1, -1 };
	}

	@Test
	public void testAdditionOfVectors() {
		assertTrue(same(VEC.AdditionOfVectors(vec1, vec2), new double[] { 2, 0 }));
	}

	@Test
	public void testMagnitudeOfVector() {
		assertTrue(Math.abs(VEC.MagnitudeOfVector(vec1) - VEC.SQRT(2)) < tolerance);
		assertTrue(Math.abs(VEC.MagnitudeOfVector(vec2) - VEC.SQRT(2)) < tolerance);
	}

	public boolean same(double[] vec1, double[] vec2) {

		double ans = 0.d;
		for (int i = 0; i < vec1.length; i++) {
			ans += Math.abs(vec1[i] - vec2[i]);
		}
		return ans < tolerance;

	}

}
