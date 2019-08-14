/**
 * 
 */
package definitions.structures.field.impl;

import static org.junit.Assert.*;


import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.EuclideanAlgebra;
import definitions.structures.abstr.Scalar;
import definitions.structures.abstr.Vector;
import definitions.structures.field.Field;
import definitions.structures.finitedimensional.vectorspaces.EuclideanSpace;

/**
 * @author BAU12350
 *
 */
public class QuaternionSpaceTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void test() {
		final EuclideanSpace space=QuaternionSpace.getInstance();
		final Vector one=((QuaternionSpace)space).getOne();
		final Vector i=((QuaternionSpace)space).getI();
		final Vector j=((QuaternionSpace)space).getJ();
		final Vector k=((QuaternionSpace)space).getK();
		final Vector a=((EuclideanAlgebra) space).product(i, j);
		int m=0;
	}

}
