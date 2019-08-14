/**
 * 
 */
package definitions.structures.abstr.fields.scalars.impl;

import static org.junit.Assert.fail;

import org.junit.Test;

import definitions.structures.abstr.fields.impl.QuaternionSpace;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.abstr.vectorspaces.vectors.VectorTest;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

/**
 * @author RoManski
 *
 */
public class QuaternionTest extends VectorTest {

	/**
	 * Test method for
	 * {@link definitions.structures.abstr.fields.scalars.impl.Quaternion#getInverse()}.
	 */
	@Test
	public final void testGetInverse() {
		fail("Not yet implemented"); // TODO
	}

	@Override
	public Vector getVector() {
		return ((QuaternionSpace) QuaternionSpace.getInstance()).product(
				((QuaternionSpace) QuaternionSpace.getInstance()).getI(),
				((QuaternionSpace) QuaternionSpace.getInstance()).getJ());
	}

	@Override
	public EuclideanSpace getSpace() {
		return QuaternionSpace.getInstance();
	}

}
