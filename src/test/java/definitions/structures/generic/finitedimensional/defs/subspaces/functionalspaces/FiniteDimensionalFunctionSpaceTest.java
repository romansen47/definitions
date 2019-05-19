package definitions.structures.generic.finitedimensional.defs.subspaces.functionalspaces;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.generic.finitedimensional.defs.spaces.IFiniteDimensionalVectorSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.SpaceGenerator;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionalspaces.functions.Constant;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionalspaces.functions.Cosine;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionalspaces.functions.Identity;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionalspaces.functions.Sine;
import definitions.structures.generic.finitedimensional.defs.vectors.IFiniteVector;

public class FiniteDimensionalFunctionSpaceTest {

	static IFunction sin;
	static IFunction cos;
	static IFunction identity;
	static IFunction one;

	static IFiniteDimensionalVectorSpace genericSpace;
	static IFiniteDimensionalFunctionSpace functionSpace;

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

		genericSpace = SpaceGenerator.getInstance().getFiniteDimensionalVectorSpace(4);

		sin = new Sine(genericSpace.genericBaseToList().get(0).getGenericCoordinates());
		cos = new Cosine(genericSpace.genericBaseToList().get(1).getGenericCoordinates());
		identity = new Identity(genericSpace.genericBaseToList().get(2).getGenericCoordinates());
		one = new Constant(genericSpace.genericBaseToList().get(3).getGenericCoordinates(), 1);

		List<IFiniteVector> list = new ArrayList<>();
		list.add(sin);
		list.add(cos);
		list.add(identity);
		list.add(one);

		functionSpace = new FiniteDimensionalFunctionSpace(list, -Math.PI, Math.PI);

		integral1 = functionSpace.product(((IFunction) functionSpace.stretch(sin, 2)), sin);
		integral2 = functionSpace.product(cos, cos);
		integral3 = functionSpace.product(identity, identity);
		integral4 = functionSpace.product(identity, sin);
		integral5 = functionSpace.product(identity, cos);
		integral6 = functionSpace.product(sin, cos);
		integral7 = functionSpace.product(sin, one);
		integral8 = functionSpace.product(cos, one);
		integral9 = functionSpace.product(identity, one);
		integral10 = functionSpace.product(one, one);

		double x=functionSpace.getDistance(cos,sin);
		double y=functionSpace.getDistance(sin,sin);

	}

	boolean almostEqual(double a, double b) {
		return Math.abs(a - b) < 1.e-3;
	}

	@Test
	public void test1() {
		Assert.assertTrue(almostEqual(integral1, 2 * Math.PI));
	}

	@Test
	public void test2() {
		Assert.assertTrue(almostEqual(integral2, Math.PI));
	}

	@Test
	public void test3() {
		Assert.assertTrue(almostEqual(integral3, 2. / 3. * Math.pow(Math.PI, 3)));
	}

	@Test
	public void test4() {
		Assert.assertTrue(almostEqual(integral4, 2 * Math.PI));
	}

	@Test
	public void test5() {
		Assert.assertTrue(almostEqual(integral5, 0));
	}

	@Test
	public void test6() {
		Assert.assertTrue(almostEqual(integral6, 0));
	}

	@Test
	public void test7() {
		Assert.assertTrue(almostEqual(integral7, 0));
	}

	@Test
	public void test8() {
		Assert.assertTrue(almostEqual(integral8, 0));
	}

	@Test
	public void test9() {
		Assert.assertTrue(almostEqual(integral9, 0));
	}

	@Test
	public void test10() {
		Assert.assertTrue(almostEqual(integral10, 2 * Math.PI));
	}
}
