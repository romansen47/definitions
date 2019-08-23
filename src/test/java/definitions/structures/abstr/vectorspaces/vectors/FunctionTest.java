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
import definitions.structures.euclidean.functionspaces.EuclideanFunctionSpace;
import definitions.structures.euclidean.functionspaces.impl.FiniteDimensionalSobolevSpace;
import definitions.structures.euclidean.mappings.impl.DerivativeOperator;
import definitions.structures.euclidean.vectors.impl.GenericFunction;
import definitions.structures.euclidean.vectors.specialfunctions.Sine;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.ISpaceGenerator;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;

/**
 * @author RoManski
 *
 */
public class FunctionTest {

	static final int trigonometricDegree = 1;
	static final int sobolevDegree = 10;
	static int derivativeDegree = 10;

	static EuclideanFunctionSpace trigSpace;
	static EuclideanFunctionSpace tempSpace;
	static Function sine;
	static Function cosine;
	static Function newCosine;
	static Function derivative;

	final static Field field = RealLine.getInstance();

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		final ISpaceGenerator spGen = SpaceGenerator.getInstance();
		tempSpace = spGen.getTrigonometricSobolevSpace(field, trigonometricDegree, sobolevDegree);

		final Function abs = new GenericFunction() {
			private static final long serialVersionUID = 9176320860959699923L;

			@Override
			public Scalar value(Scalar input) {
				return this.getField().get(Math.abs(input.getValue()));
			}
		};

		trigSpace = tempSpace;// (EuclideanFunctionSpace) spGen.extend(tempSpace, abs);

		trigSpace.show();

		sine = (Function) trigSpace.genericBaseToList().get(1);
		cosine = (Function) trigSpace.genericBaseToList().get(2);
		final double factor = 0;
		newCosine = new Sine(new Real(1. / Math.sqrt((sobolevDegree + 1) * Math.PI)), new Real(Math.PI / 2.),
				(Scalar) field.getOne()) {
			private static final long serialVersionUID = -4115510885606993392L;
		};

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
		final Function newSine = sine;

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

		for (int i = 0; 4 * i < derivativeDegree; i++) {
			highDerivative = ((Function) derivativeBuilder.get(newCosine, 4 * i + 1));
			trigSpace.stretch(sine, trigSpace.getField().get(-1)).plotCompare(-Math.PI, Math.PI, highDerivative);

			highDerivative = ((Function) derivativeBuilder.get(newCosine, 4 * i + 2));
			trigSpace.stretch(cosine, trigSpace.getField().get(-1)).plotCompare(-Math.PI, Math.PI, highDerivative);

			highDerivative = ((Function) derivativeBuilder.get(newCosine, 4 * i + 3));
			trigSpace.stretch(sine, (Scalar) trigSpace.getField().getOne()).plotCompare(-Math.PI, Math.PI,
					highDerivative);

			highDerivative = ((Function) derivativeBuilder.get(newCosine, 4 * i + 4));
			trigSpace.stretch(cosine, (Scalar) trigSpace.getField().getOne()).plotCompare(-Math.PI, Math.PI,
					highDerivative);

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
