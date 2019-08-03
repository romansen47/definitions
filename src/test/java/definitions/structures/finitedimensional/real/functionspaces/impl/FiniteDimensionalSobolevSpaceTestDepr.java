/**
 * 
 */
package definitions.structures.finitedimensional.real.functionspaces.impl;


import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.InnerProductSpace;
import definitions.structures.abstr.Scalar;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.finitedimensional.real.mappings.impl.DerivativeOperator;
import definitions.structures.finitedimensional.real.vectors.Function;
import definitions.structures.finitedimensional.real.vectors.specialfunctions.Sine;
import definitions.structures.finitedimensional.real.vectorspaces.EuclideanSpace;
import definitions.structures.finitedimensional.real.vectorspaces.impl.SpaceGenerator;

/**
 * @author RoManski
 *
 */
public class FiniteDimensionalSobolevSpaceTestDepr {

	final static int sobolevDegree = 1;
	final static int fourierDegree = 7;
	final static VectorSpace trigonometricSobolevSpace = SpaceGenerator.getInstance()
			.getTrigonometricSobolevSpace(fourierDegree, sobolevDegree);

	static Function sine;
	static Function sineProjection;
	static Function cosine;
	static Function cosineProjection;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		sine = new Sine(1, 0, 1);
		sineProjection = sine.getProjection((EuclideanSpace) trigonometricSobolevSpace);
		cosine = new Sine(1, Math.PI / 2.0, 1);
		cosineProjection = cosine.getProjection((EuclideanSpace) trigonometricSobolevSpace);
	}

	/**
	 * Test method for
	 * {@link definitions.structures.finitedimensional.real.functionspaces.impl.FiniteDimensionalSobolevSpace#innerProduct(definitions.structures.abstr.Vector, definitions.structures.abstr.Vector)}.
	 */
	@Test
	public void testInnerProduct() {
		Scalar ans = ((InnerProductSpace) trigonometricSobolevSpace)
				.innerProduct(sineProjection,cosineProjection);
		System.err.println("Answer: " + ans.getValue());
		Assert.assertTrue(Math.abs(ans.getValue()) < 1.e-2);
	}

	/**
	 * Test method for
	 * {@link definitions.structures.finitedimensional.real.functionspaces.impl.FiniteDimensionalSobolevSpace#getDerivativeBuilder()}.
	 */
	@Test
	public void testGetDerivativeBuilder() {
		try {
			DerivativeOperator derivativeOperator = ((FiniteDimensionalSobolevSpace) trigonometricSobolevSpace)
					.getDerivativeBuilder();
			final Function derivative = (Function) derivativeOperator.get(sineProjection);
			derivative.plotCompare(-Math.PI, Math.PI, cosineProjection);
		} catch (Exception e) {
			sine.plotCompare(-Math.PI, Math.PI, sineProjection);
		}

	}

}
