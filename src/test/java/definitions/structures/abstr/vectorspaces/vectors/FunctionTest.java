/**
 * 
 */
package definitions.structures.abstr.vectorspaces.vectors;

import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.euclidean.functionspaces.EuclideanFunctionSpace;
import definitions.structures.euclidean.functionspaces.impl.FiniteDimensionalSobolevSpace;
import definitions.structures.euclidean.mappings.impl.DerivativeOperator;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.ISpaceGenerator;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;

/**
 * @author RoManski
 *
 */
public class FunctionTest {

	static final int trigonometricDegree = 1;
	static final int sobolevDegree = 3;
	static int derivativeDegree = 5;

	static EuclideanFunctionSpace trigSpace;
	static Function sine;
	static Function cosine;
	static Function derivative;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		final ISpaceGenerator spGen = SpaceGenerator.getInstance();
		final EuclideanFunctionSpace tempSpace = spGen.getTrigonometricSobolevSpace(RealLine.getInstance(),
				trigonometricDegree, sobolevDegree);

		trigSpace = tempSpace;// (EuclideanFunctionSpace) spGen.extend(tempSpace, abs);

		sine = (Function) trigSpace.genericBaseToList().get(1);
		cosine = (Function) trigSpace.genericBaseToList().get(2);

	}

	/**
	 * Test method for
	 * {@link definitions.structures.abstr.vectorspaces.vectors.Function#plot(double, double)}.
	 */
	// @Test
	public final void testPlot() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link definitions.structures.abstr.vectorspaces.vectors.Function#plotCompare(double, double, definitions.structures.abstr.vectorspaces.vectors.Function)}.
	 */
	// @Test
	public final void testPlotCompare() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link definitions.structures.abstr.vectorspaces.vectors.Function#value(definitions.structures.abstr.fields.scalars.Scalar)}.
	 */
	// @Test
	public final void testValue() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link definitions.structures.abstr.vectorspaces.vectors.Function#getDerivative(definitions.structures.euclidean.vectorspaces.EuclideanSpace)}.
	 */
	// @Test
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

		Function highDerivative;
		final EuclideanSpace space = trigSpace;
		final Function newSine = sine.getProjection(space);

		for (int i = 0; 4 * i < derivativeDegree; i++) {
			highDerivative = ((Function) derivativeBuilder.get(newSine, 4 * i + 1));
			cosine.plotCompare(-Math.PI, Math.PI, highDerivative);

			highDerivative = ((Function) derivativeBuilder.get(newSine, 4 * i + 2));
			trigSpace.stretch(sine, trigSpace.getField().get(-1)).plotCompare(-Math.PI, Math.PI, highDerivative);

			highDerivative = ((Function) derivativeBuilder.get(newSine, 4 * i + 3));
			trigSpace.stretch(cosine, trigSpace.getField().get(-1)).plotCompare(-Math.PI, Math.PI, highDerivative);

			highDerivative = ((Function) derivativeBuilder.get(newSine, 4 * i + 4));
			sine.plotCompare(-Math.PI, Math.PI, highDerivative);
		}

	}

	/**
	 * Test method for
	 * {@link definitions.structures.abstr.vectorspaces.vectors.Function#getPrimitiveIntegral()}.
	 */
	// @Test
	public final void testGetPrimitiveIntegral() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link definitions.structures.abstr.vectorspaces.vectors.Function#getPrimitiveIntegral(int)}.
	 */
	// @Test
	public final void testGetPrimitiveIntegralInt() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link definitions.structures.abstr.vectorspaces.vectors.Function#getProjectionOfDerivative(definitions.structures.euclidean.functionspaces.EuclideanFunctionSpace)}.
	 */
	// @Test
	public final void testGetProjectionOfDerivative() {
		fail("Not yet implemented"); // TODO
	}

}
