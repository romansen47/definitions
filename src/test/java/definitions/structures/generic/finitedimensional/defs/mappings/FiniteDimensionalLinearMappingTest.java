package definitions.structures.generic.finitedimensional.defs.mappings;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.generic.finitedimensional.defs.vectors.FiniteVector;
import definitions.structures.generic.finitedimensional.defs.vectors.IFiniteVector;
import junit.framework.Assert;

public class FiniteDimensionalLinearMappingTest {

	static IFiniteVector e1;
	static IFiniteVector e2;
	static IFiniteVector e3;

	static IFiniteDimensionalLinearMapping composition;

	static double[][] matrix = new double[][] { { 1, 0, 1 }, { 0, 1., 0 }, { -1, 0, 1 } };

	static IFiniteDimensionalLinearMapping product;

	@BeforeClass
	public static void setUpBeforeClass() throws Throwable {

		e1 = new FiniteVector(new double[] { 1, 0, 0 });
		e2 = new FiniteVector(new double[] { 0, 1, 0 });
		e3 = new FiniteVector(new double[] { 0, 0, 1 });

		IFiniteDimensionalLinearMapping map = MappingGenerator.getInstance().getFiniteDimensionalLinearMapping(matrix);
		IFiniteDimensionalLinearMapping inv = ((Automorphism) map).getInverse();
		composition = MappingGenerator.getInstance().getComposition(map, inv);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void first1() throws Throwable {
		Assert.assertTrue(composition.get(e1).equals(e1));
	}

	@SuppressWarnings("deprecation")
	@Test
	public void second1() throws Throwable {
		Assert.assertTrue(composition.get(e2).equals(e2));
	}

	@SuppressWarnings("deprecation")
	@Test
	public void third1() throws Throwable {
		Assert.assertTrue(composition.get(e3).equals(e3));
	}
}
