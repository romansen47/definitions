package subspaces.functionalspaces;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.Vector;
import definitions.structures.generic.finitedimensional.defs.Generator;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.IFiniteDimensionalFunctionSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.Function;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.GenericFunction;
import junit.framework.Assert;

@SuppressWarnings("deprecation")
public class PerformanceTest {

	final static int max = 200;

	static IFiniteDimensionalFunctionSpace space;

	static Function parabel;
	static Function abs;
	static Function exp;
	static Function symExp;
	static Function circle;

	@BeforeClass
	public static void prepare() throws Throwable {
		space = Generator.getGenerator().getSpacegenerator().getTrigonometricSpace(max);
		parabel = new GenericFunction() {
			@Override
			public double value(double input) {
				return Math.pow(input, 2) - Math.abs(input);
			}
		};
		abs = new GenericFunction() {
			@Override
			public double value(double input) {
				return Math.abs(input);
			}
		};
		exp = new GenericFunction() {
			@Override
			public double value(double input) {
				final double rest = -(Math.exp(Math.PI) - Math.exp(-Math.PI)) / (2 * Math.PI);
				return Math.exp(input) + rest;
			}
		};
		symExp = new GenericFunction() {
			@Override
			public double value(double input) {
				return Math.exp(input) + Math.exp(-input);
			}
		};
		circle = new GenericFunction() {
			@Override
			public double value(double input) {
				return Math.sqrt(Math.pow(Math.PI, 2) - Math.pow(input, 2));
			}
		};
	}

	@Test
	public void identity() throws Throwable {
		final Vector toFourier = space.copyVector(parabel);
		// ((Function)toFourier).plot(-Math.PI,Math.PI);
		parabel.plotCompare(-Math.PI, Math.PI, ((Function) toFourier));
		final double ans = space.getDistance((Function) toFourier, parabel);
		Assert.assertTrue(ans < 0.1);
	}

	@Test
	public void abs() throws Throwable {
		final Vector toFourier = space.copyVector(abs);
		// ((Function)toFourier).plot(-Math.PI,Math.PI);
		abs.plotCompare(-Math.PI, Math.PI, ((Function) toFourier));
		final double ans = space.getDistance((Function) toFourier, abs);
		Assert.assertTrue(ans < 0.1);
	}

	@Test
	public void exp() throws Throwable {
		final Vector toFourier = space.copyVector(exp);
		// ((Function)toFourier).plot(-Math.PI,Math.PI);
		exp.plotCompare(-Math.PI, Math.PI, ((Function) toFourier));
		final double ans = space.getDistance((Function) toFourier, exp);
		Assert.assertTrue(ans < 5);
	}

	@Test
	public void symExp() throws Throwable {
		final Vector toFourier = space.copyVector(symExp);
		// ((Function)toFourier).plot(-Math.PI,Math.PI);
		symExp.plotCompare(-Math.PI, Math.PI, ((Function) toFourier));
		final double ans = space.getDistance((Function) toFourier, symExp);
		Assert.assertTrue(ans < 0.1);
	}

	@Test
	public void circle() throws Throwable {
		final Vector toFourier = space.copyVector(circle);
		// ((Function)toFourier).plot(-Math.PI,Math.PI);
		circle.plotCompare(-Math.PI, Math.PI, ((Function) toFourier));
		final double ans = space.getDistance((Function) toFourier, circle);
		Assert.assertTrue(ans < 0.1);
	}

}
