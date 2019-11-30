//package definitions.xmltest; 
//
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import definitions.structures.abstr.FunctionSpaceTest;
//import definitions.structures.abstr.fields.Field;
//import definitions.structures.abstr.fields.impl.RealLine;
//import definitions.structures.abstr.fields.scalars.Scalar;
//import definitions.structures.abstr.fields.scalars.impl.Real;
//import definitions.structures.abstr.vectorspaces.VectorSpace;
//import definitions.structures.abstr.vectorspaces.vectors.Function;
//import definitions.structures.euclidean.Generator;
//import definitions.structures.euclidean.functionspaces.EuclideanFunctionSpace;
//import definitions.structures.euclidean.vectors.impl.GenericFunction;
//import definitions.structures.java.Reader;
//
///**
// * @author RoManski
// *
// */
//public class FiniteDimensionalSobolevSpaceTestDepr extends FunctionSpaceTest {
//
//	final static Field realSpace = RealLine.getInstance();
//	final static int sobolevDegree = 3;
//	final static int fourierDegree = 10;
//	static VectorSpace trigonometricSobolevSpace;
//
//	@Override
//	public EuclideanFunctionSpace getLinearSpace() {
//		return (EuclideanFunctionSpace) trigonometricSobolevSpace;
//	}
//
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
////		trigonometricSobolevSpace=Generator.getGenerator().getFiniteDimensionalSobolevSpace(realSpace, (EuclideanFunctionSpace) SpaceGenerator.getInstance()
////		.getTrigonometricFunctionSpaceWithLinearGrowth(realSpace, fourierDegree),sobolevDegree);
//		trigonometricSobolevSpace = Generator.getInstance().getSpacegenerator().extend(Generator.getInstance()
//				.getSpacegenerator().getTrigonometricSobolevSpace(realSpace, fourierDegree, sobolevDegree),
//				new GenericFunction() {
//					/**
//					 * 
//					 */
//					private static final long serialVersionUID = 5812927097511303688L;
//
//					@Override
//					public Scalar value(Scalar input) {
//						return (Scalar) this.getField().stretch(input, RealLine.getInstance().get(10.));
//					}
//
//				});
//		testValues = Reader.readFile(PATH);
//		testValues2 = Reader.readFile(PATH2);
//		staircaseFunction = new GenericFunction() {
//			/**
//			 * 
//			 */
//			private static final long serialVersionUID = -8361584686661267908L;
//			private final int length = (int) testValues[0][testValues[0].length - 1];
//
//			@Override
//			public Scalar value(Scalar input) {
//				final double newInput = ((this.length / (2 * Math.PI)) * input.getValue()) + (this.length / 2.);
//				int k = 0;
//				final int l = (int) (newInput - (newInput % 1));
//				while (k + 1 < testValues[0].length && testValues[0][k] < l) {
//					k++;
//				}
//				return this.getField().get(testValues[1][k]);
////				return new Real(Math.sin(newInput));
//			}
//
//			@Override
//			public Field getField() {
//				return f;
//			}
//		};
//		staircaseFunction2 = new GenericFunction() {
//			private static final long serialVersionUID = 2729095328251979949L;
//			int length = (int) testValues2[0][testValues2[0].length - 1];
//
//			@Override
//			public Scalar value(Scalar input) {
//				final double newInput = ((this.length / (2 * Math.PI)) * input.getValue()) + (this.length / 2.);
//				int k = 0;
//				final int l = (int) (newInput - (newInput % 1));
//				while (k + 1 < testValues2[0].length && testValues2[0][k] < l) {
//					k++;
//				}
//				return this.getField().get(testValues2[1][k]);
//			}
//
//			@Override
//			public Field getField() {
//				return f;
//			}
//
//		};
//	}
//
//	@Override
//	@Test
//	public void test1() {
//		final Function staircaseFunction1Projection = staircaseFunction.getProjection(this.getLinearSpace());
//		staircaseFunction.plotCompare(-Math.PI, Math.PI, staircaseFunction1Projection);
//	}
//
//	@Override
//	@Test
//	public void test2() {
//		final Function staircaseFunction2Projection = staircaseFunction2.getProjection(this.getLinearSpace());
//		staircaseFunction2.plotCompare(-Math.PI, Math.PI, staircaseFunction2Projection);
//	}
//}
