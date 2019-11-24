package definitions.xmltest;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import definitions.SpringConfiguration;
import definitions.structures.abstr.fields.impl.BinaryField;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;

/**
 * @author ro
 *
 */
public class MultiDimensionalSpaceOverBinaryFieldTest {

	public static void main(String[] args) {
		new MultiDimensionalSpaceOverBinaryFieldTest().test();
	}

	private SpringConfiguration springConfiguration=SpringConfiguration.getSpringConfiguration();

	@Test
	public void test() {

		Generator generator =  (Generator) springConfiguration.getApplicationContext().getBean("generator");
		SpaceGenerator spaceGenerator = generator.getSpacegenerator();
		BinaryField binaryField = (BinaryField) springConfiguration.getApplicationContext().getBean("binaryField");
		final int dim = 3;

		final EuclideanSpace modulo2Space = (EuclideanSpace) spaceGenerator.getFiniteDimensionalVectorSpace(binaryField,
				dim);

		boolean ans = true;
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

	public SpringConfiguration getSpringConfiguration() {
		return springConfiguration;
	}

	public void setSpringConfiguration(SpringConfiguration springConfiguration) {
		this.springConfiguration = springConfiguration;
	}

}
