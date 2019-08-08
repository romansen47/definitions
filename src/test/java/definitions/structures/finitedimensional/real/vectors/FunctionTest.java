/**
 * 
 */
package definitions.structures.finitedimensional.real.vectors;

import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.Homomorphism;
import definitions.structures.abstr.Scalar;
import definitions.structures.field.impl.RealLine;
import definitions.structures.field.scalar.impl.Real;
import definitions.structures.finitedimensional.real.functionspaces.EuclideanFunctionSpace;
import definitions.structures.finitedimensional.real.functionspaces.impl.FiniteDimensionalSobolevSpace;
import definitions.structures.finitedimensional.real.mappings.impl.DerivativeOperator;
import definitions.structures.finitedimensional.real.vectors.impl.GenericFunction;
import definitions.structures.finitedimensional.real.vectors.specialfunctions.ExponentialFunction;
import definitions.structures.finitedimensional.real.vectorspaces.impl.SpaceGenerator;

/**
 * @author RoManski
 *
 */
public class FunctionTest {

	static final int trigonometricDegree = 10;
	static final int sobolevDegree = 1;
	static int derivativeDegree = sobolevDegree+5;

	static EuclideanFunctionSpace trigSpace;
	static Function symExp;
	static Function derivative;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		trigSpace = SpaceGenerator.getInstance().getTrigonometricSobolevSpace(RealLine.getInstance(),
				trigonometricDegree, sobolevDegree);
		symExp = new GenericFunction() {
			final Function exp = new ExponentialFunction();

			@Override
			public Scalar value(Scalar input) {
//				return new Real(Math.pow(Math.sin(input.getValue()),1));
				return new Real(this.exp.value(input).getValue()
						+ this.exp.value(new Real(input.getValue() * (-1))).getValue());
			}
		};
//		Function expProjected = exp.getProjectionOfDerivative(trigSpace);
//		trigSpace.show();
//		exp.plotCompare(-Math.PI, Math.PI, expProjected);
		symExp.plot(-Math.PI, Math.PI);
		derivative = symExp.getDerivative();
//		trigSpace.show();
	}

	/**
	 * Test method for
	 * {@link definitions.structures.finitedimensional.real.vectors.Function#plot(double, double)}.
	 */
//	@Test
	public final void testPlot() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link definitions.structures.finitedimensional.real.vectors.Function#plotCompare(double, double, definitions.structures.finitedimensional.real.vectors.Function)}.
	 */
//	@Test
	public final void testPlotCompare() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link definitions.structures.finitedimensional.real.vectors.Function#value(definitions.structures.abstr.Scalar)}.
	 */
//	@Test
	public final void testValue() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link definitions.structures.finitedimensional.real.vectors.Function#getDerivative(definitions.structures.finitedimensional.real.vectorspaces.EuclideanSpace)}.
	 */
//	@Test
	public final void testGetDerivativeEuclideanSpace() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link definitions.structures.finitedimensional.real.vectors.Function#getDerivative(int)}.
	 */
	@Test
	public final void testGetDerivativeInt() {
		final DerivativeOperator derivativeBuilder = 
				((FiniteDimensionalSobolevSpace) trigSpace).getDerivativeBuilder();
		
		for (int i = 0; i < derivativeDegree; i++) {
			Function highDerivative=(Function) derivativeBuilder.get(symExp, i+1 );
			derivative.plotCompare(-Math.PI, Math.PI,highDerivative);
//			highDerivative.plot(-Math.PI, Math.PI);
//			derivative.plot(-Math.PI, Math.PI);
			derivative = derivative.getDerivative();
		}
	}

	/**
	 * Test method for
	 * {@link definitions.structures.finitedimensional.real.vectors.Function#getPrimitiveIntegral()}.
	 */
//	@Test
	public final void testGetPrimitiveIntegral() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link definitions.structures.finitedimensional.real.vectors.Function#getPrimitiveIntegral(int)}.
	 */
//	@Test
	public final void testGetPrimitiveIntegralInt() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link definitions.structures.finitedimensional.real.vectors.Function#getProjectionOfDerivative(definitions.structures.finitedimensional.real.functionspaces.EuclideanFunctionSpace)}.
	 */
//	@Test
	public final void testGetProjectionOfDerivative() {
		fail("Not yet implemented"); // TODO
	}

}
