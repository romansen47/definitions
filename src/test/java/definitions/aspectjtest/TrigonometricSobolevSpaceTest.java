package definitions.aspectjtest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;

import definitions.prototypes.AspectJTest;
import definitions.prototypes.GenericTrigonometricSpaceTest;

/**
 * Sobolev tests make sense only on continuous functions.
 * 
 * @author roman
 *
 */
public class TrigonometricSobolevSpaceTest extends GenericTrigonometricSpaceTest {

	public static final Logger logger = LogManager.getLogger(TrigonometricSobolevSpaceTest.class);

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	@Before
	public void setUp() throws Exception {
		trigonometricDegree = 6;
		sobolevDegree = 1;
		eps = 1d;

		setField(AspectJTest.getRealLine());
		setTrigonometricSpace(AspectJTest.getSpaceGenerator().getTrigonometricSobolevSpace(getField(),
				getTrigonometricDegree(), getSobolevDegree()));
		super.setUp();

	}

}
