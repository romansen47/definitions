package definitions.generictest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;

import definitions.prototypes.GenericSpaceTest;
import definitions.prototypes.GenericTest;

public class TrigonometricSpaceWithLinearGrowthTest extends GenericSpaceTest {

	public static final Logger logger = LogManager.getLogger(TrigonometricSpaceWithLinearGrowthTest.class);

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	@Before
	public void setUp() throws Exception {
		eps = 1d;
		degree = 4;
		sobolevDegree = null;
		setField(GenericTest.getRealLine());
		setSpace(GenericTest.getSpaceGenerator()
				.getTrigonometricFunctionSpaceWithLinearGrowth(GenericTest.getRealLine(), getDegree(), Math.PI));
		super.setUp();
	}
}
