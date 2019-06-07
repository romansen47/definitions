package math;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

public class IScalarTest {

	static IScalar SCALOP;

	final double TESTNUM = -3.14;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		SCALOP = new MathOp(1.e-5);
	}

	@Test
	public void testAbs() {
		assertTrue(SCALOP.Abs(TESTNUM) + TESTNUM == 0);
	}

	@Test
	public void testSignumFunction() {
		assertTrue(SCALOP.SignumFunction(TESTNUM) + 1 == 0);
	}

}
