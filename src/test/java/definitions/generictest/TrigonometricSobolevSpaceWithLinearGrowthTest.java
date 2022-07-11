package definitions.generictest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;

import definitions.prototypes.GenericTest;
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
		setField(GenericTest.getRealLine());
		setTrigonometricSpace(GenericTest.getSpaceGenerator().getTrigonometricSobolevSpaceWithLinearGrowth(
				GenericTest.getRealLine(), sobolevDegree, Math.PI, trigonometricDegree));
		super.setUp();

	}

}
