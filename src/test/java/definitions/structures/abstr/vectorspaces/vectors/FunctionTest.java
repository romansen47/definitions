/**
 * 
 */
package definitions.structures.abstr.vectorspaces.vectors;

import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.fields.scalars.impl.Real;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.euclidean.functionspaces.EuclideanFunctionSpace;
import definitions.structures.euclidean.functionspaces.impl.FiniteDimensionalSobolevSpace;
import definitions.structures.euclidean.mappings.impl.DerivativeOperator;
import definitions.structures.euclidean.vectors.impl.GenericFunction;
import definitions.structures.euclidean.vectors.specialfunctions.LinearFunction;
import definitions.structures.euclidean.vectorspaces.ISpaceGenerator;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;

/**
 * @author RoManski
 *
 */
public class FunctionTest {

	static final int trigonometricDegree = 2;
	static final int sobolevDegree = 1;
	static int derivativeDegree = 1;

	static EuclideanFunctionSpace trigSpace;
	static Function symExp;
	static Function derivative;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		final ISpaceGenerator spGen = SpaceGenerator.getInstance();
		final EuclideanFunctionSpace tempSpace = spGen.getTrigonometricSobolevSpace(RealLine.getInstance(),
				trigonometricDegree, sobolevDegree);
		final Function id = new LinearFunction(RealLine.getInstance().getZero(), RealLine.getInstance().getOne()) {
			@Override
			public Field getField() {
				return RealLine.getInstance();
			}
		};
		trigSpace = (EuclideanFunctionSpace) spGen.extend(tempSpace, id);
		symExp = new GenericFunction() {
			@Override
			public Scalar value(Scalar input) {
				final double x = input.getValue();
				final double pi = Math.PI;
				return new Real(Math.pow(Math.sin(input.getValue()), 2) + 0.1 * input.getValue());
			}

			@Override
			public Field getField() {
				return RealLine.getInstance();
			}
		};
		derivative = symExp.getProjectionOfDerivative(trigSpace);
	}

	/**
	 * Test method for
	 * {@link definitions.structures.abstr.vectorspaces.vectors.Function#plot(double, double)}.
	 */
//	@Test
	public final void testPlot() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link definitions.structures.abstr.vectorspaces.vectors.Function#plotCompare(double, double, definitions.structures.abstr.vectorspaces.vectors.Function)}.
	 */
//	@Test
	public final void testPlotCompare() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link definitions.structures.abstr.vectorspaces.vectors.Function#value(definitions.structures.abstr.fields.scalars.Scalar)}.
	 */
//	@Test
	public final void testValue() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link definitions.structures.abstr.vectorspaces.vectors.Function#getDerivative(definitions.structures.euclidean.vectorspaces.EuclideanSpace)}.
	 */
//	@Test
	public final void testGetDerivativeEuclideanSpace() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link definitions.structures.abstr.vectorspaces.vectors.Function#getDerivative(int)}.
	 */
	@Test
	public final void testGetDerivativeInt() {
		final DerivativeOperator derivativeBuilder = ((FiniteDimensionalSobolevSpace) trigSpace).getDerivativeBuilder();
		Function highDerivative = ((Function) derivativeBuilder.get(symExp)).getProjection(trigSpace);
		for (int i = 0; i < derivativeDegree; i++) {
			derivative.plotCompare(-Math.PI, Math.PI, highDerivative);
			derivative = ((Function) derivativeBuilder.get(derivative));
			highDerivative = ((Function) derivativeBuilder.get(highDerivative));
		}
	}

	/**
	 * Test method for
	 * {@link definitions.structures.abstr.vectorspaces.vectors.Function#getPrimitiveIntegral()}.
	 */
//	@Test
	public final void testGetPrimitiveIntegral() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link definitions.structures.abstr.vectorspaces.vectors.Function#getPrimitiveIntegral(int)}.
	 */
//	@Test
	public final void testGetPrimitiveIntegralInt() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link definitions.structures.abstr.vectorspaces.vectors.Function#getProjectionOfDerivative(definitions.structures.euclidean.functionspaces.EuclideanFunctionSpace)}.
	 */
//	@Test
	public final void testGetProjectionOfDerivative() {
		fail("Not yet implemented"); // TODO
	}

}
