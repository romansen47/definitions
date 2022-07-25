package definitions.generictest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;

import definitions.prototypes.GenericSpaceTest;
import definitions.prototypes.GenericTest;

/**
 * Sobolev tests make sense only on continuous functions.
 * 
 * @author roman
 *
 */
public class TrigonometricSobolevSpaceTest extends GenericSpaceTest {

	public static final Logger logger = LogManager.getLogger(TrigonometricSobolevSpaceTest.class);

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	@Before
	public void setUp() throws Exception {
		degree = 3;
		sobolevDegree = 1;
		eps = 1d;

		setField(GenericTest.getRealLine());
		setSpace(GenericTest.getSpaceGenerator().getTrigonometricSobolevSpace(getField(), getDegree(),
				getSobolevDegree()));
		super.setUp();
	}

}
