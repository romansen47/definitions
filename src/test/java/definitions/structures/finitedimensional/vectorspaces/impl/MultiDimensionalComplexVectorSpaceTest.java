/**
 * 
 */
package definitions.structures.finitedimensional.vectorspaces.impl;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.impl.BinaryField;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.ISpaceGenerator;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;

/**
 * @author ro
 *
 */
public class MultiDimensionalComplexVectorSpaceTest {

	ISpaceGenerator gen = SpaceGenerator.getInstance();

//	final EuclideanSpace complexSpace=(EuclideanSpace) SpaceGenerator.getInstance().getFiniteDimensionalComplexSpace(500);
//	EuclideanSpace asRealSpace=gen.convert(complexSpace,RealLine.getInstance());

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void test() {
		final int dim = 100;

		final EuclideanSpace modulo2Space = (EuclideanSpace) this.gen
				.getFiniteDimensionalVectorSpace((Field) BinaryField.getInstance(), dim);

		boolean ans = true;
//		modulo2Space.show();
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
