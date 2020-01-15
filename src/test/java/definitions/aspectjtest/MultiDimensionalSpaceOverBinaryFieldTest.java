package definitions.aspectjtest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import definitions.prototypes.AspectJTest;
import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.FiniteField;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

/**
 * @author ro
 *
 */
public class MultiDimensionalSpaceOverBinaryFieldTest extends AspectJTest {

	final int dim = 8;
	Field f;
	
	
	@Test
	public void test() {
		
		boolean ans = true;
		final EuclideanSpace modulo2Space = (EuclideanSpace) getSpaceGenerator().getFiniteDimensionalVectorSpace(f,
				this.dim);
		modulo2Space.show();

		for (int i = 1; i < this.dim; i++) {
			final Vector x = modulo2Space.genericBaseToList().get(i);
			final Vector h = modulo2Space.add(x, x);
			if (!h.equals(modulo2Space.nullVec())) {
				ans = false;
			}
		}

		Assert.assertTrue(ans);
	}

}
