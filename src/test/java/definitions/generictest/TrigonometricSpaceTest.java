package definitions.generictest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;

import definitions.prototypes.GenericTest;
import definitions.prototypes.GenericTrigonometricSpaceTest;

public class TrigonometricSpaceTest extends GenericTrigonometricSpaceTest {

	public static final Logger logger = LogManager.getLogger(TrigonometricSpaceTest.class);

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	@Before
	public void setUp() throws Exception {
		eps = 2e-1;
		trigonometricDegree = 15;
		sobolevDegree = null;
		setField(GenericTest.getRealLine());
		super.setUp();
	}
}
