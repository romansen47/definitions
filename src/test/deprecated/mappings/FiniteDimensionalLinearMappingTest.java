package mappings;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.Homomorphism;
import definitions.structures.finitedimensional.real.mappings.Automorphism;
import definitions.structures.finitedimensional.real.mappings.FiniteDimensionalHomomorphism;
import definitions.structures.finitedimensional.real.mappings.Isomorphism;
import definitions.structures.finitedimensional.real.mappings.impl.MappingGenerator;
import definitions.structures.finitedimensional.real.vectors.FiniteVector;
import definitions.structures.finitedimensional.real.vectors.impl.Tuple;
import junit.framework.Assert;

@SuppressWarnings("deprecation")
public class FiniteDimensionalLinearMappingTest {

	static FiniteVector e1;
	static FiniteVector e2;
	static FiniteVector e3;
	static Homomorphism map;
	static Homomorphism inv;
	static Homomorphism composition;

	static double[][] matrix = new double[][] { { 1, 0, 1 }, { 0, 1., 0 }, { -1, 0, 1 } };

	static FiniteDimensionalHomomorphism product;

	@BeforeClass
	public static void setUpBeforeClass() throws Throwable {

		e1 = new Tuple(new double[] { 1, 0, 0 });
		e2 = new Tuple(new double[] { 0, 1, 0 });
		e3 = new Tuple(new double[] { 0, 0, 1 });

		map = MappingGenerator.getInstance().getFiniteDimensionalLinearMapping(matrix);
		inv = ((Automorphism) map).getInverse();

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

	@Test
	public void iso1() throws Throwable {
		Assert.assertTrue(map instanceof Isomorphism);
	}

	@Test
	public void iso2() throws Throwable {
		Assert.assertTrue(inv instanceof Isomorphism);
	}

	@Test
	public void iso3() throws Throwable {
		Assert.assertTrue(composition instanceof Isomorphism);
	}
}
