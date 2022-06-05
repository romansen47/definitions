/**
 *
 */
package definitions.aspectjtest;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import definitions.prototypes.AspectJTest;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.vectorspaces.vectors.Function;
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
public class FunctionTest extends AspectJTest {

	static final int trigonometricDegree = 20;
	static final int sobolevDegree = 20;
	static int derivativeDegree = 4;

	static EuclideanFunctionSpace trigSpace;
	static Function sine;
	static Function cosine;
	static Function derivative;

	/**
	 * @throws java.lang.Exception an exception if sth went wrong
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		final ISpaceGenerator spGen = SpaceGenerator.getInstance();
		FunctionTest.trigSpace = spGen.getTrigonometricSobolevSpace(RealLine.getInstance(),
				FunctionTest.trigonometricDegree, FunctionTest.sobolevDegree);

		FunctionTest.sine = (Function) FunctionTest.trigSpace.genericBaseToList().get(1);
		FunctionTest.cosine = (Function) FunctionTest.trigSpace.genericBaseToList()
				.get(1 + FunctionTest.trigonometricDegree);

	}

	/**
	 * Test method for
	 * {@link definitions.structures.abstr.vectorspaces.vectors.Function#getDerivative(definitions.structures.euclidean.vectorspaces.EuclideanSpace)}.
	 */
	public final void testGetDerivativeEuclideanSpace() {
		Assert.fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link definitions.structures.abstr.vectorspaces.vectors.Function#getDerivative(int)}.
	 */
	@Test
	public final void testGetDerivativeInt() {
		final DerivativeOperator derivativeBuilder = ((FiniteDimensionalSobolevSpace) FunctionTest.trigSpace)
				.getDerivativeBuilder();

		Function highDerivative;
		final EuclideanSpace space = FunctionTest.trigSpace;
		final Function newSine = FunctionTest.sine.getProjection(space);

		for (int i = 0; (4 * i) < FunctionTest.derivativeDegree; i++) {
			highDerivative = ((Function) derivativeBuilder.get(newSine, (4 * i) + 1));
			FunctionTest.cosine.plotCompare(-Math.PI, Math.PI, highDerivative);

			highDerivative = ((Function) derivativeBuilder.get(newSine, (4 * i) + 2));
			FunctionTest.trigSpace.stretch(FunctionTest.sine, FunctionTest.trigSpace.getField().get(-1))
					.plotCompare(-Math.PI, Math.PI, highDerivative);

			highDerivative = ((Function) derivativeBuilder.get(newSine, (4 * i) + 3));
			FunctionTest.trigSpace.stretch(FunctionTest.cosine, FunctionTest.trigSpace.getField().get(-1))
					.plotCompare(-Math.PI, Math.PI, highDerivative);

			highDerivative = ((Function) derivativeBuilder.get(newSine, (4 * i) + 4));
			FunctionTest.sine.plotCompare(-Math.PI, Math.PI, highDerivative);
		}

	}

	/**
	 * Test method for
	 * {@link definitions.structures.abstr.vectorspaces.vectors.Function#getProjectionOfDerivative(definitions.structures.euclidean.functionspaces.EuclideanFunctionSpace)}.
	 */
	public final void testGetProjectionOfDerivative() {
		Assert.fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link definitions.structures.abstr.vectorspaces.vectors.Function#plot(double, double)}.
	 */
	public final void testPlot() {
		Assert.fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link definitions.structures.abstr.vectorspaces.vectors.Function#plotCompare(double, double, definitions.structures.abstr.vectorspaces.vectors.Function)}.
	 */
	public final void testPlotCompare() {
		Assert.fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link definitions.structures.abstr.vectorspaces.vectors.Function#value(definitions.structures.abstr.algebra.fields.scalars.Scalar)}.
	 */
	public final void testValue() {
		Assert.fail("Not yet implemented"); // TODO
	}

}
