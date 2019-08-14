/**
 * 
 */
package definitions.structures.field.impl;

import org.junit.BeforeClass;

import definitions.structures.abstr.VectorSpaceTest;
import definitions.structures.abstr.fields.impl.QuaternionSpace;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.fields.scalars.impl.Quaternion;
import definitions.structures.abstr.fields.scalars.impl.Real;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Vector;

/**
 * @author BAU12350
 *
 */
public class QuaternionSpaceTest extends VectorSpaceTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Override
	public VectorSpace getSpace() {
		return QuaternionSpace.getInstance();
	}

	@Override
	public Vector getVec1() {
		return new Quaternion(1, 0, 2, 0);
	}

	@Override
	public Vector getVec2() {
		return new Quaternion(1, 0, -2, 0);
	}

	@Override
	public Scalar getFactor() {
		return new Real(0.5);
	}

}
