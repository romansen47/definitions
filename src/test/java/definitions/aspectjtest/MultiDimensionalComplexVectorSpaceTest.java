/**
 *
 */
package definitions.aspectjtest;

import org.junit.Assert;
import org.junit.Test;

import definitions.prototypes.AspectJTest;
import definitions.structures.abstr.algebra.fields.scalars.impl.Real;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;

/**
 * @author ro
 *
 */
public class MultiDimensionalComplexVectorSpaceTest extends AspectJTest {

	@Test
	public void testNorm() {
		final int dim = 10;

		final EuclideanSpace complexSpace = (EuclideanSpace) SpaceGenerator.getInstance()
				.getFiniteDimensionalComplexSpace(dim);

		Vector vec = complexSpace.nullVec();
		for (int i = 0; i < dim; i++) {
			final Vector x = complexSpace.genericBaseToList().get(i);
			vec = complexSpace.addition(vec, x);
		}
		final double doubleAns = ((Real) complexSpace.norm(vec)).doubleValue() - Math.sqrt(dim);
		final boolean newAns = Math.abs(doubleAns) < 0.1;

		Assert.assertTrue(newAns);
		System.out.println("norm = " + ((Real) complexSpace.norm(vec)).doubleValue() + "\rsquare root of dim = " + Math.sqrt(dim)
		+ "\rdifference = " + doubleAns + "\r");
	}

}
