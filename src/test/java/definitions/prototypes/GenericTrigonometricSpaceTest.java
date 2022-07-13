package definitions.prototypes;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Real;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.euclidean.vectors.impl.GenericFunction;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

/**
 * generic abstract test class for trigonometric spaces
 * 
 * @author roman
 *
 */
public abstract class GenericTrigonometricSpaceTest extends GenericTest {

	/**
	 * a tolerance parameter
	 */
	protected double eps = 1d;

	/**
	 * the trigonometric degree of tested space
	 */

	protected int trigonometricDegree;

	/**
	 * the sobolev degree of tested space
	 */
	protected Integer sobolevDegree;

	/**
	 * the trigonometric space
	 */
	protected EuclideanSpace trigonometricSpace;

	/**
	 * path to prepared values of input function
	 */
	protected String path = "src/main/resources/test.csv";

	/**
	 * the values
	 */
	protected double[][] testValues;

	/**
	 * the 'stair case function'.
	 */
	protected GenericFunction staircaseFunction;

	/**
	 * the field
	 */
	protected Field f;

	/**
	 * getter for path of input values
	 * 
	 * @return the path for input values
	 */
	public String getPath() {
		return path;
	}

	@Before
	public void setUp() throws Exception {

		testValues = definitions.generictest.Reader.readFile(getPath());
		setStaircaseFunction(new GenericFunction() {
			private final int length = (int) testValues[0][testValues[0].length - 1];

			@Override
			public Field getField() {
				return GenericTrigonometricSpaceTest.this.getField();
			}

			@Override
			public Scalar value(final Scalar input) {
				final double newInput = ((length / (2 * Math.PI)) * ((Real) input).getDoubleValue()) + (length / 2.);
				int k = 0;
				final int l = (int) (newInput - (newInput % 1));
				while (((k + 1) < testValues[0].length) && (testValues[0][k] < l)) {
					k++;
				}
				return getField().get(testValues[1][k]);
			}
		});
	}

	@Test
	public void testOnContinuousFunction() throws Exception {
		testOnFunction(new GenericFunction() {

			@Override
			public Field getField() {
				return GenericTest.getRealLine();
			}

			@Override
			public Scalar value(Scalar input) {
				final Double inputValue = ((Real) input).getDoubleValue();
				final double abs = Math.abs((Math.sin(inputValue) * Math.cos(inputValue)) - 0.25);
				return RealLine.getInstance().get(abs);
			}
		}, getTrigonometricDegree(), getSobolevDegree(), getEps());
	}

	public GenericFunction getStaircaseFunction() {
		staircaseFunction = new GenericFunction() {
			private final int length = (int) testValues[0][testValues[0].length - 1];

			@Override
			public Field getField() {
				return GenericTrigonometricSpaceTest.this.getField();
			}

			@Override
			public Scalar value(final Scalar input) {
				final double newInput = ((length / (2 * Math.PI)) * ((Real) input).getDoubleValue()) + (length / 2.);
				int k = 0;
				final int l = (int) (newInput - (newInput % 1));
				while (((k + 1) < testValues[0].length) && (testValues[0][k] < l)) {
					k++;
				}
				return getField().get(testValues[1][k]);
			}
		};
		return staircaseFunction;
	}

	protected void testOnFunction(Function f, int degree, Integer sobolevDegree, double eps) {
		Function fProjection = f.getProjection(getTrigonometricSpace());
		f.plotCompare(-Math.PI, Math.PI, fProjection);

		long time1 = System.nanoTime();
		double distance = ((Real) getTrigonometricSpace().distance(fProjection, f)).getRepresentant();
		time1 = System.nanoTime() - time1;
		getLogger().info("time for computing distance = {}", time1);
		long time2 = System.nanoTime();
		double norm_of_function = ((Real) getTrigonometricSpace().norm(f)).getRepresentant();
		time2 = System.nanoTime() - time2;
		getLogger().info("time for computing norm of the function  = {}", time2);
		long time3 = System.nanoTime();
		double norm_of_projection = ((Real) getTrigonometricSpace().norm(fProjection)).getRepresentant();
		time3 = System.nanoTime() - time3;
		getLogger().info("time for computing norm of projection = {}", time3);
		getLogger().info("distance = {}", distance);
		getLogger().info("norm of function = {}", norm_of_function);
		getLogger().info("norm of its projection = {}", norm_of_projection);
		String s = sobolevDegree == null ? "Trig(" + degree + ")" : "on SobTrig(" + degree + "," + sobolevDegree + ")";
		getLogger().info("distance / norm = {} / {} = {}  {}, expected tolerance: {}", distance, norm_of_function,
				distance / norm_of_function, s, getEps());
		Assert.assertTrue(distance / norm_of_function < eps);
	}

	@Test
	public void testOnStairCaseFunction() {
		testOnFunction(getStaircaseFunction(), getTrigonometricDegree(), getSobolevDegree(), getEps());
	}

	@Test
	public void testOnAbsolute() {
		final Function absolute = new GenericFunction() {
			@Override
			public Field getField() {
				return GenericTest.getRealLine();
			}

			@Override
			public Scalar value(Scalar input) {
				return getRealLine().get(Math.abs(((Real) input).getRepresentant()));
			}
		};

		testOnFunction(absolute, getTrigonometricDegree(), getSobolevDegree(), getEps());
	}

	@Test
	public void testOnIdentity() {
		final Function identity = new GenericFunction() {

			private Field f = GenericTest.getRealLine();

			@Override
			public Field getField() {
				return f;
			}

			@Override
			public Scalar value(Scalar input) {
				return input;
			}

		};
		testOnFunction(identity, getTrigonometricDegree(), getSobolevDegree(), getEps());
	}

	protected Field getField() {
		return f;
	}

	protected void setField(Field f) {
		this.f = f;
	}

	protected double getEps() {
		return eps;
	};

	protected int getTrigonometricDegree() {
		return trigonometricDegree;
	}

	protected void setTrigonometricDegree(Integer trigDegree) {
		this.trigonometricDegree = trigDegree;
	}

	protected EuclideanSpace getTrigonometricSpace() {
		return trigonometricSpace;
	}

	protected void setTrigonometricSpace(EuclideanSpace space) {
		this.trigonometricSpace = space;
	}

	protected void setPath(final String path) {
		this.path = path;
	}

	protected void setStaircaseFunction(final GenericFunction staircaseFunction) {
		this.staircaseFunction = staircaseFunction;
	}

	protected Integer getSobolevDegree() {
		return this.sobolevDegree;
	}
}
