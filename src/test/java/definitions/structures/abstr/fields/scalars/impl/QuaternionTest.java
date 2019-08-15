/**
 * 
 */
package definitions.structures.abstr.fields.scalars.impl;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.impl.QuaternionSpace;
import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.abstr.vectorspaces.vectors.VectorTest;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

/**
 * @author RoManski
 *
 */
public class QuaternionTest extends VectorTest {

	final EuclideanSpace space=((QuaternionSpace) QuaternionSpace.getInstance());
	final Scalar quat=new Quaternion(0.49999,-0.49999,0.49999,0.49999);
	
	/**
	 * Test method for
	 * {@link definitions.structures.abstr.fields.scalars.impl.Quaternion#getInverse()}.
	 */
	@Test
	public final void testGetInverse() {
		Scalar potency=(Scalar) ((Field) space).getOne();
		Scalar inverse;
		StringBuilder builder=new StringBuilder();
		for (int i=0;i<1000;i++) {
			potency=(Scalar) ((Field)space).product(potency, quat);
			builder.append(quat.toString()+" to the "+i+" ="+potency.toString());
			inverse=potency.getInverse();
			builder.append("Inverse: "+inverse.toString()+"\r");
			builder.append("Product of both: "+((Field)space).product(potency, inverse).toString()+"\r");
			Assert.assertTrue(((Field)space).product(potency, inverse).equals(((Field)space).getOne()));
		}
		System.out.println(builder.toString());
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
