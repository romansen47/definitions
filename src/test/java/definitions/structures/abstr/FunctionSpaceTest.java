/**
 * 
 */
package definitions.structures.abstr;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.field.scalar.impl.Real;
import definitions.structures.finitedimensional.functionspaces.EuclideanFunctionSpace;
import definitions.structures.finitedimensional.vectors.Function;
import definitions.structures.finitedimensional.vectors.impl.GenericFunction;
import definitions.structures.java.Reader;

/**
 * @author RoManski
 *
 */
public abstract class FunctionSpaceTest {

	static final String PATH = "src/main/resources/test.csv";
	static final String PATH2 = "src/main/resources/test2.csv";

	static double[][] testValues;
	static double[][] testValues2;

	static GenericFunction staircaseFunction;
	static GenericFunction staircaseFunction2;

	static EuclideanFunctionSpace linearSpace;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
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
		final Function staircaseFunction1Projection = staircaseFunction.getProjection(this.getLinearSpace());
		staircaseFunction.plotCompare(-1, 1, staircaseFunction1Projection);
	}

	@Test
	public void test2() {
		final Function staircaseFunction2Projection = staircaseFunction2.getProjection(this.getLinearSpace());
		staircaseFunction2.plotCompare(-1, 1, staircaseFunction2Projection);
	}

	public abstract EuclideanFunctionSpace getLinearSpace();

}
