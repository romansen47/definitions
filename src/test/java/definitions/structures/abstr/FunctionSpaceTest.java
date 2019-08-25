/**
 * 
 */
package definitions.structures.abstr;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.euclidean.functionspaces.EuclideanFunctionSpace;
import definitions.structures.euclidean.vectors.impl.GenericFunction;
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
	protected static GenericFunction staircaseFunction2;

	static EuclideanFunctionSpace linearSpace;
	final static Field f = RealLine.getInstance();

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testValues = Reader.readFile(PATH);
		testValues2 = Reader.readFile(PATH2);
		staircaseFunction = new GenericFunction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -8361584686661267908L;
			private final int length = (int) testValues[0][testValues[0].length - 1];

			@Override
			public Scalar value(Scalar input) {
				final double newInput = ((this.length / (2 * Math.PI)) * input.getValue()) + (this.length / 2.);
				int k = 0;
				final int l = (int) (newInput - (newInput % 1));
				while (testValues[0][k] < l) {
					k++;
				}
				return this.getField().get(testValues[1][k]);
			}

			@Override
			public Field getField() {
				return f;
			}
		};
		staircaseFunction2 = new GenericFunction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 2729095328251979949L;
			int length = (int) testValues2[0][testValues2[0].length - 1];

			@Override
			public Scalar value(Scalar input) {
				final double newInput = ((this.length / (2 * Math.PI)) * input.getValue()) + (this.length / 2.);
				int k = 0;
				final int l = (int) (newInput - (newInput % 1));
				while (testValues2[0][k] < l) {
					k++;
				}
				return this.getField().get(testValues2[1][k]);
			}

			@Override
			public Field getField() {
				return f;
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
