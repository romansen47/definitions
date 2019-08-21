package definitions.structures.euclidean.vectorspaces.impl;

import org.junit.Test;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.impl.BinaryField;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.ISpaceGenerator;

/**
 * @author ro
 *
 */
public class MultiDimensionalSpaceOverBinaryFieldTest {

	ISpaceGenerator gen = SpaceGenerator.getInstance();

	// EuclideanSpace asRealSpace=gen.convert(complexSpace,RealLine.getInstance());

	@Test
	public void test() {
		final int dim = 100;

		final EuclideanSpace modulo2Space = (EuclideanSpace) this.gen
				.getFiniteDimensionalVectorSpace((Field) BinaryField.getInstance(), dim);

		boolean ans = true;
		modulo2Space.show();
		for (int i = 0; i < dim; i++) {
			final Vector x = modulo2Space.genericBaseToList().get(i);
			final Vector h = modulo2Space.add(x, x);
			if (!h.equals(modulo2Space.nullVec())) {
				ans = false;
			}

		}
		final int i = 0;
	}

}
