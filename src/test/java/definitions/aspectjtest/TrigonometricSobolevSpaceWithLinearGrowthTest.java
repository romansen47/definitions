package definitions.aspectjtest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;

import definitions.prototypes.AspectJTest;
import definitions.prototypes.GenericTrigonometricSpaceTest;

/**
 * 
 * @author ro
 *
 */
public class TrigonometricSobolevSpaceWithLinearGrowthTest extends GenericTrigonometricSpaceTest {

	public static final Logger logger = LogManager.getLogger(TrigonometricSobolevSpaceWithLinearGrowthTest.class);

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	@Before
	public void setUp() throws Exception {

		trigonometricDegree = 7;
		sobolevDegree = 1;
		setField(AspectJTest.getRealLine());
		setTrigonometricSpace(AspectJTest.getSpaceGenerator().getTrigonometricSobolevSpaceWithLinearGrowth(
				AspectJTest.getRealLine(), sobolevDegree, Math.PI, trigonometricDegree));
		super.setUp();

	}

}
