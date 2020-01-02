/**
 * 
 */
package definitions.structures.abstr.fields.impl;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.VectorSpaceTest;
import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.impl.QuaternionSpace;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Quaternion;
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
	public Scalar getFactor() {
		return RealLine.getInstance().get(0.5);
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

	@Test

	public void testAddAndStretch() {
		Assert.assertTrue(this.getSpace().stretch(this.getSpace().add(this.getVec1(), this.getVec2()), this.getFactor())
				.equals(((Field) this.getSpace()).getOne()));
	}
}
