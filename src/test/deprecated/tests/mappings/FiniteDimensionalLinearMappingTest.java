package tests.mappings;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.SpringConfiguration;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.mappings.VectorSpaceAutomorphism;
import definitions.structures.abstr.mappings.VectorSpaceIsomorphism;
import definitions.structures.abstr.mappings.VectorSpaceHomomorphism;
import definitions.structures.abstr.vectorspaces.Algebra;
import definitions.structures.euclidean.mappings.FiniteDimensionalHomomorphism;
import definitions.structures.euclidean.mappings.IMappingGenerator;
import definitions.structures.euclidean.mappings.impl.MappingGenerator;
import definitions.structures.euclidean.vectors.FiniteVector;
import definitions.structures.euclidean.vectors.impl.Tuple;
import junit.framework.Assert;

@SuppressWarnings("deprecation")
public class FiniteDimensionalLinearMappingTest {

	final static IMappingGenerator mapGen = SpringConfiguration.getSpringConfiguration().getApplicationContext()
			.getBean(MappingGenerator.class);

	final static Algebra realLine = RealLine.getInstance();
	final static Scalar one = ((RealLine) realLine).getOne();
	final static Scalar zero = ((RealLine) realLine).getZero();
	static FiniteVector e1;
	static FiniteVector e2;
	static FiniteVector e3;
	static VectorSpaceHomomorphism map;
	static VectorSpaceHomomorphism inv;
	static VectorSpaceHomomorphism composition;

	static Scalar[][] matrix = new Scalar[][] { { one, zero, one }, { zero, one, zero },
			{ RealLine.getInstance().get(-1), zero, one } };

	static FiniteDimensionalHomomorphism product;

	@BeforeClass
	public static void setUpBeforeClass() throws Throwable {

		e1 = new Tuple(new Scalar[] { one, zero, zero });
		e2 = new Tuple(new Scalar[] { zero, one, zero });
		e3 = new Tuple(new Scalar[] { zero, zero, one });

		map = mapGen.getFiniteDimensionalLinearMapping(matrix);
		inv = ((VectorSpaceAutomorphism) map).getInverse();

		composition = mapGen.getComposition(map, inv);

	}

	@Test
	public void first() throws Throwable {
		Assert.assertTrue(composition.get(e1).equals(e1));
	}

	@Test
	public void iso1() throws Throwable {
		Assert.assertTrue(map instanceof VectorSpaceIsomorphism);
	}

	@Test
	public void iso2() throws Throwable {
		Assert.assertTrue(inv instanceof VectorSpaceIsomorphism);
	}

	@Test
	public void iso3() throws Throwable {
		Assert.assertTrue(composition instanceof VectorSpaceIsomorphism);
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
