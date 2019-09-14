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

	public static void main(String[] args) {
		new MultiDimensionalComplexVectorSpaceTest().test();
	}

	@Test
	@settings.Trace(trace = settings.GlobalSettings.LOGGING, depth = settings.GlobalSettings.LOGGING_DEPTH, initial = true, transit = true)

	public void test() {
		final int dim = 10;

		final EuclideanSpace complexSpace = (EuclideanSpace) SpaceGenerator.getInstance()
				.getFiniteDimensionalComplexSpace(dim);

		Vector vec = complexSpace.nullVec();
		for (int i = 0; i < dim; i++) {
			final Vector x = complexSpace.genericBaseToList().get(i);
			vec = complexSpace.add(vec, x);
		}
		final boolean newAns = Math.abs(complexSpace.norm(vec).doubleValue() - Math.sqrt(dim)) < 0.1;
		Assert.assertTrue(newAns);
	}

}
