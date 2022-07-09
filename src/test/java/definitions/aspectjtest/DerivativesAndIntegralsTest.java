package definitions.aspectjtest;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import definitions.prototypes.AspectJTest;
import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.NormedSpace;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.euclidean.mappings.impl.DerivativeOperator;
import definitions.structures.euclidean.mappings.impl.FiniteDimensionalDerivativeOperator;
import definitions.structures.euclidean.vectors.impl.Monome;
import definitions.structures.euclidean.vectors.specialfunctions.Sine;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

public class DerivativesAndIntegralsTest extends AspectJTest {

	private static final Logger logger = LogManager.getLogger(DerivativesAndIntegralsTest.class);

	private static Sine sine;
	private static Sine cosine;
	private static Monome monome;
	private static Function exactDerivative;

	private static EuclideanSpace space;
	private static EuclideanSpace extendedSpace;
	private static EuclideanSpace extendedSobolevSpace;
	private final List<Function> testfunctions = new ArrayList<>();

	private final static int degree = 2;
	private final static int sobolevDegree = 1;
	private final static int monomeDegree = 5;

	private static FiniteDimensionalDerivativeOperator derivativeOperatorOnExtendedSpace;
	private static FiniteDimensionalDerivativeOperator derivativeOperatorOnExtendedSobolevSpace;

	public List<Function> getTestfunctions() {
		return testfunctions;
	}

	@BeforeClass
	public static void setUp() throws Throwable {
		logger.info("loading {}", "space");
		space = (EuclideanSpace) AspectJTest.getGenerator().getTrigonometricSpace(AspectJTest.getRealLine(), degree);

		logger.info("loading {}", "extendedSpace");
		extendedSpace = (EuclideanSpace) AspectJTest.getGenerator()
				.getTrigonometricFunctionSpaceWithLinearGrowth(AspectJTest.getRealLine(), degree);

		logger.info("loading {}", "extendedSobolevSpace");
		extendedSobolevSpace = AspectJTest.getSpaceGenerator().getTrigonometricSobolevSpaceWithLinearGrowth(
				AspectJTest.getRealLine(), sobolevDegree, Math.PI, degree);

		logger.info("loading {}", "derivativeOperatorOnExtendedSpace");
		derivativeOperatorOnExtendedSpace = new FiniteDimensionalDerivativeOperator(extendedSpace, extendedSpace);

		logger.info("loading {}", "derivativeOperatorOnExtendedSobolevSpace");
		derivativeOperatorOnExtendedSobolevSpace = new FiniteDimensionalDerivativeOperator(extendedSobolevSpace,
				extendedSobolevSpace);

		logger.info("loading {}", "sine");
		sine = new Sine(1, 0, 1) {
			private static final long serialVersionUID = 1L;

			@Override
			public Field getField() {
				return AspectJTest.getRealLine();
			}
		};

		logger.info("loading {}", "cosine");
		cosine = new Sine(1, Math.PI / 2, 1) {
			private static final long serialVersionUID = 1L;

			@Override
			public Field getField() {
				return AspectJTest.getRealLine();
			}
		};

		logger.info("loading {}", "monome");

	}

	/**
	 * for some reason parallel() for IntStream in
	 * FiniteDimensionalFunctionSpace.getSineFunctions and
	 * FiniteDimensionalFunctionSpace.getCosineFunctions returns 4 in some cases...
	 */
	@Test
	public void parallelTest() {
		logger.info("parallelTest");
		boolean ans = space.genericBaseToList().size() == 2 * degree + 1
				&& extendedSpace.genericBaseToList().size() == 2 * (degree + 1)
				&& extendedSobolevSpace.genericBaseToList().size() == 2 * (degree + 1);
		Assert.assertTrue(ans);
	}

	@Test
	public void testMonomeOnExtendedSpace() {
		logger.info("testLinearMonomeOnExtendedSpace");

		monome = new Monome(monomeDegree) {
			private static final long serialVersionUID = 1L;

			@Override
			public Field getField() {
				return AspectJTest.getRealLine();
			}
		};

		logger.info("loading {}", "derivative of the monome");
		exactDerivative = new Monome(monomeDegree - 1) {
			private static final long serialVersionUID = 1L;

			@Override
			public Field getField() {
				return RealLine.getInstance();
			}

			@Override
			public Scalar value(final Scalar input) {
				return (Scalar) getField().product(getField().get(monomeDegree), super.value(input));

			};
		};
		testMonome(derivativeOperatorOnExtendedSpace, monome, extendedSpace);
	}

	@Test
	public void testMonomeOnExtendedSobolevSpace() {
		logger.info("testMonomeOnExtendedSobolevSpace");

		monome = new Monome(monomeDegree) {
			private static final long serialVersionUID = 1L;

			@Override
			public Field getField() {
				return AspectJTest.getRealLine();
			}
		};

		logger.info("loading {}", "derivative of the monome");
		exactDerivative = new Monome(monomeDegree - 1) {
			private static final long serialVersionUID = 1L;

			@Override
			public Field getField() {
				return RealLine.getInstance();
			}

			@Override
			public Scalar value(final Scalar input) {
				return (Scalar) getField().product(getField().get(monomeDegree), super.value(input));

			};
		};
		testMonome(derivativeOperatorOnExtendedSobolevSpace, monome, extendedSobolevSpace);
	}

	@Test
	public void testDerivativeOfSineInL2() {
		logger.info("testDerivativeOfSineInL2");
		LogManager.getLogger(DerivativesAndIntegralsTest.class).info("Plotting derivative of sine in L^2:");
		final Function derivative = (Function) ((DerivativeOperator) derivativeOperatorOnExtendedSobolevSpace)
				.get(sine);
		((Function) derivative).plotCompare(-Math.PI, Math.PI, cosine);
		Assert.assertEquals(0d, extendedSobolevSpace.distance(cosine, derivative).getRepresentant(), 1e-1);
	}

	/**
	 * method to compute a derivative of a monom and plot it against a known one
	 * 
	 * @param derivativeOperator the derivative operator
	 * @param monome             the monome
	 * @param space              the given function space
	 */
	public void testMonome(DerivativeOperator derivativeOperator, Monome monome, NormedSpace space) {
		LogManager.getLogger(DerivativesAndIntegralsTest.class).info("Comparing implicite versus explicite derivative");
		final Function derivative = (Function) (derivativeOperator).get(monome, 1);
		final Function derivative2 = (Function) (derivativeOperator).get(monome);
		((Function) derivative).plotCompare(-Math.PI, Math.PI, (Function) derivative2);
		exactDerivative.plotCompare(-Math.PI, Math.PI, derivative);
		Assert.assertEquals(0d, space.distance(derivative, derivative2).getRepresentant(), 1e-1);
	}

}
