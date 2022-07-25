package definitions.generictest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;

import definitions.prototypes.GenericSpaceTest;
import definitions.prototypes.GenericTest;

/**
 * 
 * @author ro
 *
 */
public class TrigonometricSobolevSpaceWithLinearGrowthTest extends GenericSpaceTest {

	public static final Logger logger = LogManager.getLogger(TrigonometricSobolevSpaceWithLinearGrowthTest.class);

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	@Before
	public void setUp() throws Exception {

		degree = 3;
		sobolevDegree = 1;
		setField(GenericTest.getRealLine());
		setSpace(GenericTest.getSpaceGenerator().getTrigonometricSobolevSpaceWithLinearGrowth(GenericTest.getRealLine(),
				sobolevDegree, Math.PI, degree));
		super.setUp();

	}

}
