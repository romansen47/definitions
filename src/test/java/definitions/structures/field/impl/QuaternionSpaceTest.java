/**
 * 
 */
package definitions.structures.field.impl;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.fields.impl.QuaternionSpace;
import definitions.structures.abstr.vectorspaces.EuclideanAlgebra;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

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
		final EuclideanSpace space = QuaternionSpace.getInstance();
		final Vector one = ((QuaternionSpace) space).getOne();
		final Vector i = ((QuaternionSpace) space).getI();
		final Vector j = ((QuaternionSpace) space).getJ();
		final Vector k = ((QuaternionSpace) space).getK();
		final Vector a = ((EuclideanAlgebra) space).product(i, j);
		final int m = 0;
	}

}
