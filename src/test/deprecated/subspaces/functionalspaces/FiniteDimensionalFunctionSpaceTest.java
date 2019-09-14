package subspaces.functionalspaces;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.fields.scalars.impl.Real;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.vectors.impl.GenericFunction;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

public class FiniteDimensionalFunctionSpaceTest {

	static Function orthonormalSine;
	static Function orthonormalCosine;
	static Function orthonormalIdentity;
	static Function orthonormalConstant;

	static EuclideanSpace genericSpace;
	static EuclideanSpace functionSpace;

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
			private static final long serialVersionUID = 1L;

			@Override
			public Scalar value(Scalar input) {
				return input;
			}

		};

		functionSpace = Generator.getGenerator().getSpacegenerator()
				.getTrigonometricFunctionSpaceWithLinearGrowth(RealLine.getInstance(), 1);

		orthonormalConstant = (Function) functionSpace.genericBaseToList().get(0);
		orthonormalSine = (Function) functionSpace.genericBaseToList().get(1);
		orthonormalCosine = (Function) functionSpace.genericBaseToList().get(2);
		orthonormalIdentity = (Function) functionSpace.genericBaseToList().get(3);

		final List<Vector> list = new ArrayList<>();
		list.add(orthonormalSine);
		list.add(orthonormalCosine);
		list.add(orthonormalIdentity);
		list.add(orthonormalConstant);

		integral1 = functionSpace.innerProduct((functionSpace.stretch(orthonormalSine, new Real(2))), orthonormalSine)
				.getValue();
		integral2 = functionSpace.innerProduct(orthonormalCosine, orthonormalCosine).getValue();
		integral3 = functionSpace.innerProduct(orthonormalIdentity, orthonormalIdentity).getValue();
		integral4 = functionSpace.innerProduct(orthonormalIdentity, orthonormalSine).getValue();
		integral5 = functionSpace.innerProduct(orthonormalIdentity, orthonormalCosine).getValue();
		integral6 = functionSpace.innerProduct(orthonormalSine, orthonormalCosine).getValue();
		integral7 = functionSpace.innerProduct(orthonormalSine, orthonormalConstant).getValue();
		integral8 = functionSpace.innerProduct(orthonormalCosine, orthonormalConstant).getValue();
		integral9 = functionSpace.innerProduct(orthonormalIdentity, orthonormalConstant).getValue();
		integral10 = functionSpace.innerProduct(orthonormalConstant, orthonormalConstant).getValue();

		final double x = functionSpace.getDistance(orthonormalCosine, orthonormalSine).getValue();
		final double y = functionSpace.getDistance(orthonormalSine, orthonormalSine).getValue();

	}

	boolean almostEqual(double a, double b) {
		return Math.abs(a - b) < 1.e-2;
	}

	@Test@settings.Trace(trace = settings.GlobalSettings.LOGGING, depth = settings.GlobalSettings.LOGGING_DEPTH, initial = true, transit = true)
	public void test1() {
		Assert.assertTrue(this.almostEqual(integral1, 2));
	}

	@Test@settings.Trace(trace = settings.GlobalSettings.LOGGING, depth = settings.GlobalSettings.LOGGING_DEPTH, initial = true, transit = true)
	public void test2() {
		Assert.assertTrue(this.almostEqual(integral2, 1));
	}

	@Test@settings.Trace(trace = settings.GlobalSettings.LOGGING, depth = settings.GlobalSettings.LOGGING_DEPTH, initial = true, transit = true)
	public void test3() {
		Assert.assertTrue(this.almostEqual(integral3, 1));
	}

	@Test@settings.Trace(trace = settings.GlobalSettings.LOGGING, depth = settings.GlobalSettings.LOGGING_DEPTH, initial = true, transit = true)
	public void test4() {
		Assert.assertTrue(this.almostEqual(integral4, 0));
	}

	@Test@settings.Trace(trace = settings.GlobalSettings.LOGGING, depth = settings.GlobalSettings.LOGGING_DEPTH, initial = true, transit = true)
	public void test5() {
		Assert.assertTrue(this.almostEqual(integral5, 0));
	}

	@Test@settings.Trace(trace = settings.GlobalSettings.LOGGING, depth = settings.GlobalSettings.LOGGING_DEPTH, initial = true, transit = true)
	public void test6() {
		Assert.assertTrue(this.almostEqual(integral6, 0));
	}

	@Test@settings.Trace(trace = settings.GlobalSettings.LOGGING, depth = settings.GlobalSettings.LOGGING_DEPTH, initial = true, transit = true)
	public void test7() {
		Assert.assertTrue(this.almostEqual(integral7, 0));
	}

	@Test@settings.Trace(trace = settings.GlobalSettings.LOGGING, depth = settings.GlobalSettings.LOGGING_DEPTH, initial = true, transit = true)
	public void test8() {
		Assert.assertTrue(this.almostEqual(integral8, 0));
	}

	@Test@settings.Trace(trace = settings.GlobalSettings.LOGGING, depth = settings.GlobalSettings.LOGGING_DEPTH, initial = true, transit = true)
	public void test9() {
		Assert.assertTrue(this.almostEqual(integral9, 0));
	}

	@Test@settings.Trace(trace = settings.GlobalSettings.LOGGING, depth = settings.GlobalSettings.LOGGING_DEPTH, initial = true, transit = true)
	public void test10() {
		Assert.assertTrue(this.almostEqual(integral10, 1));
	}
}
