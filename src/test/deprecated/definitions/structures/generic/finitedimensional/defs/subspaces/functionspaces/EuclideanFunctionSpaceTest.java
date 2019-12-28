package definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.SpringConfiguration;
import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.FunctionSpace;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.functionspaces.EuclideanFunctionSpace;
import definitions.structures.euclidean.vectors.specialfunctions.ExponentialFunction;
import definitions.structures.euclidean.vectors.specialfunctions.LinearFunction;
import definitions.structures.euclidean.vectors.specialfunctions.Sine;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.ISpaceGenerator;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;

//@ContextConfiguration("definitions.SpringConfiguration.annotationConfigApplicationContext")
public class EuclideanFunctionSpaceTest {

	static private SpringConfiguration springConfiguration;

	final static VectorSpace realLine = RealLine.getInstance();

	private static FunctionSpace polynomialSpace;
	private static FunctionSpace polynomialSpaceSobolev;

	private static FunctionSpace trigonometricSpace;
	private static FunctionSpace trigonometricSpaceSobolev;

	final static int polynomialDegree = 1;
	final static int trigonometricDegree = 3;
	final static int sobolevDegree = 1;

	private static Function exp;
	
	private static FunctionSpace extendedSpace;
	private static FunctionSpace extendedToSobolev;

	public static void main(String[] args) throws Exception {
		setUpBeforeClass();
		new EuclideanFunctionSpaceTest().polynomialSobolev();
	}

	@BeforeClass
	public static void setUpBeforeClass() {
		springConfiguration=SpringConfiguration.getSpringConfiguration();
		polynomialSpace = SpaceGenerator.getInstance().getPolynomialFunctionSpace((Field) realLine, polynomialDegree, 1,
				false);
		polynomialSpaceSobolev = (FunctionSpace) SpaceGenerator.getInstance()
				.getPolynomialSobolevSpace((Field) realLine, polynomialDegree, 1, sobolevDegree);

		trigonometricSpace = SpaceGenerator.getInstance().getTrigonometricSpace((Field) realLine, trigonometricDegree);
		trigonometricSpaceSobolev = SpaceGenerator.getInstance().getTrigonometricSobolevSpace((Field) realLine,
				trigonometricDegree, sobolevDegree);
		exp = new ExponentialFunction(RealLine.getInstance().getZero(),
				RealLine.getInstance().getOne()) {
			private static final long serialVersionUID = 1172031978682722462L;

			@Override
			public Field getField() {
				return (Field) realLine;
			}
		};
	}

	@Test
	public void polynomialL2() throws java.lang.Exception {
		extendedSpace = (FunctionSpace) SpaceGenerator.getInstance().extend(polynomialSpace,
				new Sine(RealLine.getInstance().getOne(), (Scalar) RealLine.getInstance().nullVec(),
						RealLine.getInstance().getOne(), RealLine.getInstance()) {
					private static final long serialVersionUID = -690285361695626772L;

					@Override
					public Field getField() {
						return RealLine.getInstance();
					}
				});
		((EuclideanSpace) extendedSpace).show();
	}

	@Test

	public void polynomialSobolev() throws java.lang.Exception {
		extendedToSobolev = (FunctionSpace) SpaceGenerator.getInstance().extend(polynomialSpaceSobolev,
				new Sine(1, 0, 1) {
					private static final long serialVersionUID = -4745540583929265097L;
				});
		((EuclideanSpace) extendedToSobolev).show();
	}

	@Test
	public void trigonometricL2() throws java.lang.Exception {
		extendedSpace = (FunctionSpace) SpaceGenerator.getInstance().extend(trigonometricSpace,
				new LinearFunction(((RealLine) realLine).getZero(), ((RealLine) realLine).getOne()) {
					private static final long serialVersionUID = -7342635822603926791L;
				});
		((EuclideanSpace) extendedSpace).show();
	}

	@Test
	public void trigonometricSobolev() throws java.lang.Exception {
		final Vector fun = new LinearFunction(((RealLine) realLine).getZero(), ((RealLine) realLine).getOne()) {
			private static final long serialVersionUID = -6657519463985748027L;
		};
		extendedToSobolev = (FunctionSpace) SpaceGenerator.getInstance().extend(trigonometricSpaceSobolev, fun);
		exp.getProjection((EuclideanSpace) extendedToSobolev).plotCompare(-Math.PI, Math.PI, exp);
		((EuclideanSpace) extendedToSobolev).show();
	}

	@Test
	public void trigonometric2() throws java.lang.Exception {
		EuclideanSpace ans = SpaceGenerator.getInstance()
				.getTrigonometricFunctionSpaceWithLinearGrowth((Field) realLine, 49);
		exp.getProjection(ans).plotCompare(-Math.PI, Math.PI, exp);
	}

	@Test
	public void trigonometricSobolev2() throws java.lang.Exception {
		ISpaceGenerator sp = SpaceGenerator.getInstance();
		EuclideanSpace ans = sp.getTrigonometricFunctionSpaceWithLinearGrowth((Field) realLine, 7);
		EuclideanSpace sobolev = sp.getFiniteDimensionalSobolevSpace(RealLine.getInstance(),
				(EuclideanFunctionSpace) ans, sobolevDegree);
		exp.getProjection(sobolev).plotCompare(-Math.PI, Math.PI, exp);
	}

	public SpringConfiguration getSpringConfiguration() {
		return springConfiguration;
	}

}
