/**
 * 
 */
package definitions.structures.finitedimensional.vectorspaces.impl;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.Vector;
import definitions.structures.field.Field;
import definitions.structures.field.impl.Modulo2;
import definitions.structures.finitedimensional.vectorspaces.EuclideanSpace;
import definitions.structures.finitedimensional.vectorspaces.ISpaceGenerator;

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
		int dim = 100;

		final EuclideanSpace modulo2Space = (EuclideanSpace) this.gen
				.getFiniteDimensionalVectorSpace((Field) Modulo2.getInstance(), dim);

		boolean ans = true;
//		modulo2Space.show();
		for (int i = 0; i < dim; i++) {
			Vector x = modulo2Space.genericBaseToList().get(i);
			Vector h = modulo2Space.add(x, x);
			if (!h.equals(modulo2Space.nullVec())) {
				ans = false;
			}
			;

		}
		int i = 0;
	}

}
