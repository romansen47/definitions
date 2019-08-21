/**
 * 
 */
package definitions.structures.euclidean.vectorspaces.impl;

import org.junit.Assert;
import org.junit.Test;

import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.ISpaceGenerator; 

/**
 * @author ro
 *
 */
public class MultiDimensionalComplexVectorSpaceTest {

	ISpaceGenerator gen = SpaceGenerator.getInstance();

	@Test
	public void test() {
		final int dim = 100;

		final EuclideanSpace complexSpace = (EuclideanSpace) SpaceGenerator.getInstance()
				.getFiniteDimensionalComplexSpace(dim);

		Vector vec = complexSpace.nullVec();
		for (int i = 0; i < dim; i++) {
			final Vector x = complexSpace.genericBaseToList().get(i);
			vec = complexSpace.add(vec, x);
		}
		boolean newAns = Math.abs(complexSpace.norm(vec).doubleValue() - Math.sqrt(dim)) < 0.1;
		Assert.assertTrue(newAns);
	}

}
