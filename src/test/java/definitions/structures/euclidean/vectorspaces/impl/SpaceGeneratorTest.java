/**
 * 
 */
package definitions.structures.euclidean.vectorspaces.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.ISpaceGenerator;

/**
 * @author RoManski
 *
 */
public class SpaceGeneratorTest {

	final int dim = 100;

	final ISpaceGenerator gen = SpaceGenerator.getInstance();

	final List<EuclideanSpace> spaces = new ArrayList<>();

	@Test
	public final void spaceCachingTest() {
		EuclideanSpace ans;
		for (int i = 0; i < this.dim; i++) {
			this.spaces.add(this.gen.getFiniteDimensionalVectorSpace(i));
		}
		for (int i = 0; i < this.dim; i++) {
			ans = this.gen.getFiniteDimensionalVectorSpace(i);
			Assert.assertTrue(ans.equals(this.spaces.get(i)));
		}
	}

}
