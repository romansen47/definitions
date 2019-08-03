/**
 * 
 */
package definitions.structures.finitedimensional.real.vectorspaces.impl;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.Scalar;
import definitions.structures.field.scalar.Real;
import definitions.structures.finitedimensional.real.functionspaces.EuclideanFunctionSpace;
import definitions.structures.finitedimensional.real.vectors.Function;
import definitions.structures.finitedimensional.real.vectors.impl.GenericFunction;
import definitions.structures.java.Reader;

/**
 * @author RoManski
 *
 */
public class PolynomialFunctionSpaceTest {

	static final int sobolevDegree = 3;
	private static final int polynomialDegree = 2;
	static EuclideanFunctionSpace linearSpace;

	static final String PATH = "src/main/resources/test.csv";
	static final String PATH2 = "src/main/resources/test2.csv";

	static double[][] testValues;
	static double[][] testValues2;
	private static GenericFunction staircaseFunction;
	private static GenericFunction staircaseFunction2;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		linearSpace = (EuclideanFunctionSpace) SpaceGenerator.getInstance()
				.getPolynomialSobolevSpace(polynomialDegree, 1, sobolevDegree);
		testValues = Reader.readFile(PATH);
		testValues2 = Reader.readFile(PATH2);
		staircaseFunction = new GenericFunction() {
			private final int length = (int) testValues[0][testValues[0].length - 1];

			@Override
			public Scalar value(Scalar input) {
				final double newInput = ((this.length / (2 * Math.PI)) * input.getValue()) + (this.length / 2.);
				int k = 0;
				final int l = (int) (newInput - (newInput % 1));
				while (testValues[0][k] < l) {
					k++;
				}
				return new Real(testValues[1][k]);
			}
		};
		staircaseFunction2 = new GenericFunction() {
			int length = (int) testValues2[0][testValues2[0].length - 1];

			@Override
			public Scalar value(Scalar input) {
				final double newInput = ((this.length / (2 * Math.PI)) * input.getValue()) + (this.length / 2.);
				int k = 0;
				final int l = (int) (newInput - (newInput % 1));
				while (testValues2[0][k] < l) {
					k++;
				}
				return new Real(testValues2[1][k]);
			}
		};
	}

	@Test
	public void test1() {
		final Function staircaseFunction1Projection = staircaseFunction.getProjection(linearSpace);
		staircaseFunction.plotCompare(-1, 1, staircaseFunction1Projection);
	}

	@Test
	public void test2() {
		final Function staircaseFunction2Projection = staircaseFunction2.getProjection(linearSpace);
		staircaseFunction2.plotCompare(-1, 1, staircaseFunction2Projection);
	}

}
