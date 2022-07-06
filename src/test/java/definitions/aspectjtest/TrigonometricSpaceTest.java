package definitions.aspectjtest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import definitions.prototypes.GenericTrigonometricSpaceTest;

public class TrigonometricSpaceTest extends GenericTrigonometricSpaceTest {

	public static final Logger logger = LogManager.getLogger(TrigonometricSpaceTest.class);

	@Override
	public Logger getLogger() {
		return logger;
	}

	final int degree = 25;

	final double eps = 1e-1;

	@Override
	@Before
	public void setUp() throws Exception {
		setField(getRealLine());
		setTrigonometricDegree(degree);
		super.setUp();
	}

	@Test
	public void testDistance() {
		testOnFunction(getStaircaseFunction(), degree, null, eps);
	}

}
