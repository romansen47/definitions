package definitions.generictest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;

import definitions.prototypes.GenericSpaceTest;
import definitions.prototypes.GenericTest;

public class TrigonometricSpaceTest extends GenericSpaceTest {

	public static final Logger logger = LogManager.getLogger(TrigonometricSpaceTest.class);

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	@Before
	public void setUp() throws Exception {
		eps = 4e-1;
		degree = 5;
		sobolevDegree = null;
		setField(GenericTest.getRealLine());
		setSpace(
				GenericTest.getSpaceGenerator().getTrigonometricSpace(GenericTest.getRealLine(), getDegree(), Math.PI));
		super.setUp();
	}
}
