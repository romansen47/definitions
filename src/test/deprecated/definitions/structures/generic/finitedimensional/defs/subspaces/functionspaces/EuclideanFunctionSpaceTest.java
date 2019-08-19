package definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.FunctionSpace;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectors.specialfunctions.ExponentialFunction;
import definitions.structures.euclidean.vectors.specialfunctions.LinearFunction;
import definitions.structures.euclidean.vectors.specialfunctions.Sine;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;
import exceptions.WrongClassException;

public class EuclideanFunctionSpaceTest {

	final static VectorSpace realLine = RealLine.getInstance();

	private static FunctionSpace polynomialSpace;
	private static FunctionSpace polynomialSpaceSobolev;

	private static FunctionSpace trigonometricSpace;
	private static FunctionSpace trigonometricSpaceSobolev;

	final static int polynomialDegree = 2;
	final static int trigonometricDegree = 15;
	final static int sobolevDegree = 0;

	final static Function exp = new ExponentialFunction(RealLine.getInstance().getZero(),
			RealLine.getInstance().getOne()) {
		private static final long serialVersionUID = 1172031978682722462L;

		@Override
		public Field getField() {
			return (Field) realLine;
		}
	};
	private static FunctionSpace extendedSpace;
	private static FunctionSpace extendedToSobolev;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		polynomialSpace = SpaceGenerator.getInstance().getPolynomialFunctionSpace((Field) realLine, polynomialDegree, 1,
				false);
		polynomialSpaceSobolev = (FunctionSpace) SpaceGenerator.getInstance()
				.getPolynomialSobolevSpace((Field) realLine, polynomialDegree, 1, sobolevDegree);

		trigonometricSpace = SpaceGenerator.getInstance().getTrigonometricSpace((Field) realLine, trigonometricDegree);
		trigonometricSpaceSobolev = SpaceGenerator.getInstance().getTrigonometricSobolevSpace((Field) realLine,
				trigonometricDegree, sobolevDegree);
	}

	@Test
	public void polynomialL2() throws WrongClassException {
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
	public void polynomialSobolev() throws WrongClassException {
		extendedToSobolev = (FunctionSpace) SpaceGenerator.getInstance().extend(polynomialSpaceSobolev,
				new Sine(1, 0, 1) {
					private static final long serialVersionUID = -4745540583929265097L;
				});
		((EuclideanSpace) extendedToSobolev).show();
	}

	@Test
	public void trigonometricL2() throws WrongClassException {
		extendedSpace = (FunctionSpace) SpaceGenerator.getInstance().extend(trigonometricSpace,
				new LinearFunction(((RealLine) realLine).getZero(), ((RealLine) realLine).getOne()) {
					private static final long serialVersionUID = -7342635822603926791L;
				});
		((EuclideanSpace) extendedSpace).show();
	}

	@Test
	public void trigonometricSobolev() throws WrongClassException {
		final Vector fun = new LinearFunction(((RealLine) realLine).getZero(), ((RealLine) realLine).getOne()) {
			private static final long serialVersionUID = -6657519463985748027L;

			@Override
			public Field getField() {
				return (Field) realLine;
			}
		};
		extendedToSobolev = (FunctionSpace) SpaceGenerator.getInstance().extend(trigonometricSpaceSobolev, fun);
		exp.getProjection((EuclideanSpace) extendedToSobolev).plotCompare(-Math.PI, Math.PI, exp);
		((EuclideanSpace) extendedToSobolev).show();
	}

}
