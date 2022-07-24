package definitions.generictest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;

import definitions.prototypes.GenericTest;
import definitions.prototypes.GenericTrigonometricSpaceTest;

public class TrigonometricSpaceWithLinearGrowthTest extends GenericTrigonometricSpaceTest {

	public static final Logger logger = LogManager.getLogger(TrigonometricSpaceWithLinearGrowthTest.class);

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	@Before
	public void setUp() throws Exception {
		eps = 1d;
		trigonometricDegree = 3;
		sobolevDegree = null;
		setField(GenericTest.getRealLine());
		setTrigonometricSpace(GenericTest.getSpaceGenerator().getTrigonometricFunctionSpaceWithLinearGrowth(
				GenericTest.getRealLine(), getTrigonometricDegree(), Math.PI));
		super.setUp();
	}
}
