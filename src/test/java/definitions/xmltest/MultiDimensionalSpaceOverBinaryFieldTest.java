package definitions.xmltest;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Import;

import definitions.SpringConfiguration;
import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.impl.BinaryField;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;

/**
 * @author ro
 *
 */
@Configurable
@Import(SpringConfiguration.class)
public class MultiDimensionalSpaceOverBinaryFieldTest {

	@BeforeClass
	public static void prepare() {

	}

	@Autowired(required = true)
	private Generator generator;
	
	@Autowired(required = true)
	private SpaceGenerator spaceGenerator;
	
	@Autowired
	private static SpringConfiguration springConfiguration = new SpringConfiguration();

	@Test
	public void test() {

		generator = (Generator) springConfiguration.getAnnotationConfigApplicationContext().getBean("generator");
		spaceGenerator = generator.getSpacegenerator();

		final int dim = 3;

		final EuclideanSpace modulo2Space = (EuclideanSpace) this.spaceGenerator
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

	public SpringConfiguration getSpringConfiguration() {
		return springConfiguration;
	}

	public void setSpringConfiguration(SpringConfiguration springConfiguration) {
		this.springConfiguration = springConfiguration;
	}

//	public static SpringConfiguration getSpringConfiguration() {
//		return springConfiguration;
//	}
//
//	public static void setSpringConfiguration(SpringConfiguration springConfiguration) {
//		MultiDimensionalSpaceOverBinaryFieldTest.springConfiguration = springConfiguration;
//	}

}
