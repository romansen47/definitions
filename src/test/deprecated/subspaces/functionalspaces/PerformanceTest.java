//package subspaces.functionalspaces;
//
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import definitions.structures.abstr.Vector;
//import definitions.structures.finitedimensional.real.Generator;
//import definitions.structures.finitedimensional.real.functionspaces.EuclideanFunctionSpace;
//import definitions.structures.finitedimensional.real.vectors.Function;
//import definitions.structures.finitedimensional.real.vectors.impl.GenericFunction;
//import junit.framework.Assert;
//
//@SuppressWarnings("deprecation")
//public class PerformanceTest {
//
//	final static int max = 200;
//
//	static EuclideanFunctionSpace space;
//
//	static Function parabel;
//	static Function abs;
//	static Function exp;
//	static Function symExp;
//	static Function circle;
//
//	@BeforeClass
//	public static void prepare() throws Throwable {
//		space = Generator.getGenerator().getSpacegenerator().getTrigonometricSpace(max);
//		parabel = new GenericFunction() {
//			@Override
//			public double value(double input) {
//				return Math.pow(input, 2) - Math.abs(input);
//			}
//		};
//		abs = new GenericFunction() {
//			@Override
//			public double value(double input) {
//				return Math.abs(input);
//			}
//		};
//		exp = new GenericFunction() {
//			@Override
//			public double value(double input) {
//				final double rest = -(Math.exp(Math.PI) - Math.exp(-Math.PI)) / (2 * Math.PI);
//				return Math.exp(input) + rest;
//			}
//		};
//		symExp = new GenericFunction() {
//			@Override
//			public double value(double input) {
//				return Math.exp(input) + Math.exp(-input);
//			}
//		};
//		circle = new GenericFunction() {
//			@Override
//			public double value(double input) {
//				return Math.sqrt(Math.pow(Math.PI, 2) - Math.pow(input, 2));
//			}
//		};
//	}
//
//	@Test
//	public void identity() throws Throwable {
//		final Vector toFourier = space.getCoordinates(parabel);
//		// ((Function)toFourier).plot(-Math.PI,Math.PI);
//		parabel.plotCompare(-Math.PI, Math.PI, ((Function) toFourier));
//		final double ans = space.getDistance(toFourier, parabel);
//		Assert.assertTrue(ans < 0.1);
//	}
//
//	@Test
//	public void abs() throws Throwable {
//		final Vector toFourier = space.getCoordinates(abs);
//		// ((Function)toFourier).plot(-Math.PI,Math.PI);
//		abs.plotCompare(-Math.PI, Math.PI, ((Function) toFourier));
//		final double ans = space.getDistance(toFourier, abs);
//		Assert.assertTrue(ans < 0.1);
//	}
//
//	@Test
//	public void exp() throws Throwable {
//		final Vector toFourier = space.getCoordinates(exp);
//		// ((Function)toFourier).plot(-Math.PI,Math.PI);
//		exp.plotCompare(-Math.PI, Math.PI, ((Function) toFourier));
//		final double ans = space.getDistance(toFourier, exp);
//		Assert.assertTrue(ans < 5);
//	}
//
//	@Test
//	public void symExp() throws Throwable {
//		final Vector toFourier = space.getCoordinates(symExp);
//		// ((Function)toFourier).plot(-Math.PI,Math.PI);
//		symExp.plotCompare(-Math.PI, Math.PI, ((Function) toFourier));
//		final double ans = space.getDistance(toFourier, symExp);
//		Assert.assertTrue(ans < 0.1);
//	}
//
//	@Test
//	public void circle() throws Throwable {
//		final Vector toFourier = space.getCoordinates(circle);
//		// ((Function)toFourier).plot(-Math.PI,Math.PI);
//		circle.plotCompare(-Math.PI, Math.PI, ((Function) toFourier));
//		final double ans = space.getDistance(toFourier, circle);
//		Assert.assertTrue(ans < 0.1);
//	}
//
//}
