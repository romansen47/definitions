package mappings;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.generic.finitedimensional.defs.mappings.Automorphism;
import definitions.structures.generic.finitedimensional.defs.mappings.IFiniteDimensionalLinearMapping;
import definitions.structures.generic.finitedimensional.defs.mappings.impl.MappingGenerator;
import definitions.structures.generic.finitedimensional.defs.vectors.FiniteVector;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.Tuple;
import junit.framework.Assert;

@SuppressWarnings("deprecation")
public class FiniteDimensionalLinearMappingTest {

	static FiniteVector e1;
	static FiniteVector e2;
	static FiniteVector e3;

	static IFiniteDimensionalLinearMapping composition;

	static double[][] matrix = new double[][] { { 1, 0, 1 }, { 0, 1., 0 }, { -1, 0, 1 } };

	static IFiniteDimensionalLinearMapping product;

	@BeforeClass
	public static void setUpBeforeClass() throws Throwable {

		e1 = new Tuple(new double[] { 1, 0, 0 });
		e2 = new Tuple(new double[] { 0, 1, 0 });
		e3 = new Tuple(new double[] { 0, 0, 1 });

		final IFiniteDimensionalLinearMapping map = MappingGenerator.getInstance()
				.getFiniteDimensionalLinearMapping(matrix);
		final IFiniteDimensionalLinearMapping inv = ((Automorphism) map).getInverse();
		composition = MappingGenerator.getInstance().getComposition(map, inv);

	}

	@Test
	public void first() throws Throwable {
		Assert.assertTrue(composition.get(e1).equals(e1));
	}

	@Test
	public void second() throws Throwable {
		Assert.assertTrue(composition.get(e2).equals(e2));
	}

	@Test
	public void third() throws Throwable {
		Assert.assertTrue(composition.get(e3).equals(e3));
	}
}
