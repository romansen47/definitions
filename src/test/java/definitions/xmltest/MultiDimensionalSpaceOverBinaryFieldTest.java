package definitions.xmltest;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import definitions.SpringConfiguration;
import definitions.prototypes.AspectJTest;
import definitions.structures.abstr.fields.impl.BinaryField;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;

/**
 * @author ro
 *
 */
public class MultiDimensionalSpaceOverBinaryFieldTest extends AspectJTest {

	final int dim = 3;

	@Test
	public void test() {

		boolean ans = true;
		
		final EuclideanSpace modulo2Space = (EuclideanSpace) getSpaceGenerator().getFiniteDimensionalVectorSpace(getBinaryField(),
				dim);
		modulo2Space.show();
		
		for (int i = 0; i < dim; i++) {
			final Vector x = modulo2Space.genericBaseToList().get(i);
			final Vector h = modulo2Space.add(x, x);
			if (!h.equals(modulo2Space.nullVec())) {
				ans = false;
			}
		}
		
		Assert.assertTrue(ans);
	}

}
