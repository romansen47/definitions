/**
 * 
 */
package definitions.structures.finitedimensional.real.vectors;

import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.Scalar;
import definitions.structures.field.impl.RealLine;
import definitions.structures.field.scalar.impl.Real;
import definitions.structures.finitedimensional.functionspaces.EuclideanFunctionSpace;
import definitions.structures.finitedimensional.functionspaces.impl.FiniteDimensionalSobolevSpace;
import definitions.structures.finitedimensional.mappings.impl.DerivativeOperator;
import definitions.structures.finitedimensional.vectors.Function;
import definitions.structures.finitedimensional.vectors.impl.GenericFunction;
import definitions.structures.finitedimensional.vectors.specialfunctions.LinearFunction;
import definitions.structures.finitedimensional.vectorspaces.ISpaceGenerator;
import definitions.structures.finitedimensional.vectorspaces.impl.SpaceGenerator;

/**
 * @author RoManski
 *
 */
public class FunctionTest {

	static final int trigonometricDegree = 2;
	static final int sobolevDegree = 1;
	static int derivativeDegree = 2;

	static EuclideanFunctionSpace trigSpace;
	static Function symExp;
	static Function derivative;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ISpaceGenerator spGen = SpaceGenerator.getInstance();
		EuclideanFunctionSpace tempSpace = spGen.getTrigonometricSobolevSpace(RealLine.getInstance(),
				trigonometricDegree, sobolevDegree);
		Function id = new LinearFunction(RealLine.getInstance().getZero(), RealLine.getInstance().getOne());
		trigSpace = (EuclideanFunctionSpace) spGen.extend(tempSpace, id);
		symExp = new GenericFunction() {
			@Override
			public Scalar value(Scalar input) {
				double x = input.getValue();
				double pi = Math.PI;
				return new Real(Math.pow(Math.sin(input.getValue()), 2) + 0.1 * input.getValue());
			}
		};
		derivative = symExp.getProjectionOfDerivative(trigSpace);
	}

	/**
	 * Test method for
	 * {@link definitions.structures.finitedimensional.vectors.Function#plot(double, double)}.
	 */
//	@Test
	public final void testPlot() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link definitions.structures.finitedimensional.vectors.Function#plotCompare(double, double, definitions.structures.finitedimensional.vectors.Function)}.
	 */
//	@Test
	public final void testPlotCompare() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link definitions.structures.finitedimensional.vectors.Function#value(definitions.structures.abstr.Scalar)}.
	 */
//	@Test
	public final void testValue() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link definitions.structures.finitedimensional.vectors.Function#getDerivative(definitions.structures.finitedimensional.vectorspaces.EuclideanSpace)}.
	 */
//	@Test
	public final void testGetDerivativeEuclideanSpace() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link definitions.structures.finitedimensional.vectors.Function#getDerivative(int)}.
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
	 * {@link definitions.structures.finitedimensional.vectors.Function#getPrimitiveIntegral()}.
	 */
//	@Test
	public final void testGetPrimitiveIntegral() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link definitions.structures.finitedimensional.vectors.Function#getPrimitiveIntegral(int)}.
	 */
//	@Test
	public final void testGetPrimitiveIntegralInt() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link definitions.structures.finitedimensional.vectors.Function#getProjectionOfDerivative(definitions.structures.finitedimensional.functionspaces.EuclideanFunctionSpace)}.
	 */
//	@Test
	public final void testGetProjectionOfDerivative() {
		fail("Not yet implemented"); // TODO
	}

}
