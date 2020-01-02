/**
 * 
 */
package definitions.structures.abstr.fields.scalars.impl;

import org.junit.Assert;
import org.junit.Test;

import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.impl.QuaternionSpace;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Quaternion;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.abstr.vectorspaces.vectors.VectorTest;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import settings.Trace;

/**
 * @author RoManski
 *
 */
public class QuaternionTest extends VectorTest {

	final EuclideanSpace space = (QuaternionSpace.getInstance());
	final Scalar quat = new Quaternion(0.49999, -0.49999, 0.49999, 0.49999);

	@Override
	public EuclideanSpace getSpace() {
		return QuaternionSpace.getInstance();
	}

	@Override
	public Vector getVector() {
		return ((QuaternionSpace) QuaternionSpace.getInstance()).product(
				((QuaternionSpace) QuaternionSpace.getInstance()).getI(),
				((QuaternionSpace) QuaternionSpace.getInstance()).getJ());
	}

	/**
	 * Test method for
	 * {@link definitions.structures.abstr.algebra.fields.scalars.impl.Quaternion#getInverse()}.
	 */
	@Test
	@Trace
	public final void testGetInverse() {
		Scalar potency = ((Field) this.space).getOne();
		Scalar inverse;
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 1000; i++) {
			potency = (Scalar) ((Field) this.space).product(potency, this.quat);
			builder.append(this.quat.toString() + " to the " + i + " =" + potency.toString());
			inverse = potency.getInverse();
			builder.append("Inverse: " + inverse.toString() + "\r");
			builder.append("Product of both: " + ((Field) this.space).product(potency, inverse).toString() + "\r");
			Assert.assertTrue(((Field) this.space).product(potency, inverse).equals(((Field) this.space).getOne()));
		}
		System.out.println(builder.toString());
	}

}
