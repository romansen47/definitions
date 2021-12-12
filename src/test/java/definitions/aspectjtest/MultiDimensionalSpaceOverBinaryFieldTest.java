package definitions.aspectjtest;

import org.junit.Assert;
import org.junit.Test;

import definitions.prototypes.AspectJTest;
import definitions.structures.abstr.algebra.fields.PrimeField;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

/**
 * @author ro
 *
 */
public class MultiDimensionalSpaceOverBinaryFieldTest extends AspectJTest {

	final int dim = 2;
	PrimeField f = AspectJTest.getGenerator().getGroupGenerator().getBinaries();

	@Test
	public void test() {

		boolean ans = true;
		final EuclideanSpace modulo2Space = AspectJTest.getSpaceGenerator().getFiniteDimensionalVectorSpace(f, dim);
		modulo2Space.show();

		for (int i = 1; i < dim; i++) {
			final Vector x = modulo2Space.genericBaseToList().get(i);
			final Vector h = modulo2Space.addition(x, x);
			if (!h.equals(modulo2Space.nullVec())) {
				ans = false;
				continue;
			}
		}

		Assert.assertTrue(ans);
	}

	PrimeField cf = AspectJTest.getGenerator().getGroupGenerator().getConstructedBinaries();

	@Test
	public void test2() {

		boolean ans = true;
		final EuclideanSpace modulo2Space = AspectJTest.getSpaceGenerator().getFiniteDimensionalVectorSpace(cf, dim);
		modulo2Space.show();

		for (int i = 1; i < dim; i++) {
			final Vector x = modulo2Space.genericBaseToList().get(i);
			final Vector h = modulo2Space.addition(x, x);
			if (!h.equals(modulo2Space.nullVec())) {
				ans = false;
				continue;
			}
		}

		Assert.assertTrue(ans);
	}

	@Test
	public void test3() {

		final EuclideanSpace modulo2Space = AspectJTest.getSpaceGenerator().getFiniteDimensionalVectorSpace(cf, dim);
		modulo2Space.genericBaseToList().get(0).toString();
	}

}
