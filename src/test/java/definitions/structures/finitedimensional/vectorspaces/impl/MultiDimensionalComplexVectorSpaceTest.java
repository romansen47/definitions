/**
 * 
 */
package definitions.structures.finitedimensional.vectorspaces.impl;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.finitedimensional.real.vectorspaces.EuclideanSpace;
import definitions.structures.finitedimensional.real.vectorspaces.impl.FiniteDimensionalVectorSpace;
import definitions.structures.finitedimensional.real.vectorspaces.impl.SpaceGenerator;

/**
 * @author ro
 *
 */
public class MultiDimensionalComplexVectorSpaceTest {

	final EuclideanSpace complexSpace=(EuclideanSpace) SpaceGenerator.getInstance().getFiniteDimensionalComplexSpace(2);
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void test() {
		int i=0;
	}

}
