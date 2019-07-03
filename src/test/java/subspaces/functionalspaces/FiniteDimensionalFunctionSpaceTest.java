package subspaces.functionalspaces;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.Vector;
import definitions.structures.generic.finitedimensional.defs.Generator;
import definitions.structures.generic.finitedimensional.defs.spaces.EuclideanSpace;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.EuclideanFunctionSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.Function;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.GenericFunction;

public class FiniteDimensionalFunctionSpaceTest {

	static Function orthonormalSine;
	static Function orthonormalCosine;
	static Function orthonormalIdentity;
	static Function orthonormalConstant;

	static EuclideanSpace genericSpace;
	static EuclideanFunctionSpace functionSpace;

	static double integral1;
	static double integral2;
	static double integral3;
	static double integral4;
	static double integral5;
	static double integral6;
	static double integral7;
	static double integral8;
	static double integral9;
	static double integral10;

	@BeforeClass
	public static void setUpBeforeClass() throws Throwable {

		final Function fun = new GenericFunction() {
			@Override
			public double value(double input) {
				return input;
			}
		};

		functionSpace = Generator.getGenerator().getSpacegenerator().getTrigonometricFunctionSpaceWithLinearGrowth(1);

		orthonormalConstant = (Function) functionSpace.genericBaseToList().get(0);
		orthonormalSine = (Function) functionSpace.genericBaseToList().get(1);
		orthonormalCosine = (Function) functionSpace.genericBaseToList().get(2);
		orthonormalIdentity = (Function) functionSpace.genericBaseToList().get(3);

		final List<Vector> list = new ArrayList<>();
		list.add(orthonormalSine);
		list.add(orthonormalCosine);
		list.add(orthonormalIdentity);
		list.add(orthonormalConstant);

		integral1 = functionSpace.product((functionSpace.stretch(orthonormalSine, 2)), orthonormalSine);
		integral2 = functionSpace.product(orthonormalCosine, orthonormalCosine);
		integral3 = functionSpace.product(orthonormalIdentity, orthonormalIdentity);
		integral4 = functionSpace.product(orthonormalIdentity, orthonormalSine);
		integral5 = functionSpace.product(orthonormalIdentity, orthonormalCosine);
		integral6 = functionSpace.product(orthonormalSine, orthonormalCosine);
		integral7 = functionSpace.product(orthonormalSine, orthonormalConstant);
		integral8 = functionSpace.product(orthonormalCosine, orthonormalConstant);
		integral9 = functionSpace.product(orthonormalIdentity, orthonormalConstant);
		integral10 = functionSpace.product(orthonormalConstant, orthonormalConstant);

		final double x = functionSpace.getDistance(orthonormalCosine, orthonormalSine);
		final double y = functionSpace.getDistance(orthonormalSine, orthonormalSine);

	}

	boolean almostEqual(double a, double b) {
		return Math.abs(a - b) < 1.e-2;
	}

	@Test
	public void test1() {
		Assert.assertTrue(this.almostEqual(integral1, 2));
	}

	@Test
	public void test2() {
		Assert.assertTrue(this.almostEqual(integral2, 1));
	}

	@Test
	public void test3() {
		Assert.assertTrue(this.almostEqual(integral3, 1));
	}

	@Test
	public void test4() {
		Assert.assertTrue(this.almostEqual(integral4, 0));
	}

	@Test
	public void test5() {
		Assert.assertTrue(this.almostEqual(integral5, 0));
	}

	@Test
	public void test6() {
		Assert.assertTrue(this.almostEqual(integral6, 0));
	}

	@Test
	public void test7() {
		Assert.assertTrue(this.almostEqual(integral7, 0));
	}

	@Test
	public void test8() {
		Assert.assertTrue(this.almostEqual(integral8, 0));
	}

	@Test
	public void test9() {
		Assert.assertTrue(this.almostEqual(integral9, 0));
	}

	@Test
	public void test10() {
		Assert.assertTrue(this.almostEqual(integral10, 1));
	}
}
