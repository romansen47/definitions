//package definitions.structures.finitedimensional.functionspaces.impl;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.Assert;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import definitions.structures.abstr.fields.impl.RealLine;
//import definitions.structures.abstr.fields.scalars.Scalar;
//import definitions.structures.abstr.fields.scalars.impl.Real;
//import definitions.structures.abstr.vectorspaces.VectorSpace;
//import definitions.structures.abstr.vectorspaces.vectors.Function;
//import definitions.structures.abstr.vectorspaces.vectors.Vector;
//import definitions.structures.euclidean.functionspaces.EuclideanFunctionSpace;
//import definitions.structures.euclidean.vectors.impl.GenericFunction;
//import definitions.structures.euclidean.vectors.specialfunctions.Constant;
//import definitions.structures.euclidean.vectors.specialfunctions.ExponentialFunction;
//import definitions.structures.euclidean.vectors.specialfunctions.LinearFunction;
//import definitions.structures.euclidean.vectors.specialfunctions.Sine;
//import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;
//
//public class FiniteDimensionalFunctionSpaceTest2 {
//
//	final static VectorSpace realLine = RealLine.getInstance();
//	final static List<Vector> list = new ArrayList<>();
//	static EuclideanFunctionSpace space;
//	static Function sine = new Sine(1, 0, Math.PI) {
//		private static final long serialVersionUID = -919496870406122757L;
//	};
//	static Function cosine = new Sine(1, 0.5 * Math.PI, Math.PI) {
//		private static final long serialVersionUID = 9042477282792001932L;
//	};
//	static Function id = new LinearFunction((Scalar) realLine.nullVec(), ((RealLine) realLine).getOne()) {
//		private static final long serialVersionUID = 7473924410300221465L;
//	};
//	static Function abs = new GenericFunction() {
//		private static final long serialVersionUID = 5155141949983257491L;
//
//		@Override
//		public Scalar value(Scalar input) {
//			return new Real(Math.abs(input.getValue()));
//		}
//	};
//	static Function projection;
//	static int max = 2;
//
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//
//		final Function fun = new Constant(((RealLine) realLine).getOne()) {
//			private static final long serialVersionUID = 4175061990936581238L;
//		};
//		list.add(fun);
//
//		for (int i  = 1; i < max; i++) {
//			final int j=i;
//			list.add(new GenericFunction() {
//				final int k=j;
//				@Override
//				public Scalar value(Scalar input) {
//					return getField().get(Math.exp(k*input.getValue()));
//				}});
//			list.add(new GenericFunction() {
//				final int k=j;
//				@Override
//				public Scalar value(Scalar input) {
//					return getField().get(Math.exp(-k*input.getValue()));
//				}});
//		}
//
//// space = SpaceGenerator.getInstance().getFiniteDimensionalSobolevSpace(RealLine.getInstance(), list,
//// -1, 1, 2);
//		space = SpaceGenerator.getInstance().getFiniteDimensionalFunctionSpace(RealLine.getInstance(), list, -1, 1);
//		space.show();
//	}
//
//	@Test
//	public final void testSine() {
//		projection = sine.getProjection(space);
//		sine.plotCompare(-1, 1, projection);
//		final Real ans = space.getDistance(sine, projection);
//		System.out.println("Distance Sine: " + ans.getValue());
//		Assert.assertTrue(Math.abs(ans.getValue()) < 1.e-2);
//	}
//
//	@Test
//	public final void testCosine() {
//		projection = cosine.getProjection(space);
//		cosine.plotCompare(-1, 1, projection);
//		final Real ans = space.getDistance(cosine, projection);
//		System.out.println("Distance Cosine: " + ans.getValue());
//		Assert.assertTrue(Math.abs(ans.getValue()) < 1.e-2);
//	}
//
//	@Test
//	public final void testId() {
//		projection = id.getProjection(space);
//		id.plotCompare(-1, 1,projection );
//		final Real ans = space.getDistance(id, projection);
//		System.out.println("Distance Identity: " + ans.getValue());
//		Assert.assertTrue(Math.abs(ans.getValue()) < 1.e-3);
//	}
//
////	@Test
//	public final void testabs() {
//		projection = abs.getProjection(space);
//		abs.plotCompare(-1, 1, projection);
//		final Real ans = space.getDistance(abs, projection);
//		System.out.println("Distance Absolute: " + ans.getValue());
//		Assert.assertTrue(Math.abs(ans.getValue()) < 1.e-1);
//	}
//
//}
