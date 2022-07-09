package definitions.aspectjtest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;

import definitions.prototypes.AspectJTest;
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

		trigonometricDegree = 5;
		setField(AspectJTest.getRealLine());
		setTrigonometricSpace(AspectJTest.getSpaceGenerator().getTrigonometricFunctionSpaceWithLinearGrowth(
				AspectJTest.getRealLine(), getTrigonometricDegree(), Math.PI));
		super.setUp();
	}
}
