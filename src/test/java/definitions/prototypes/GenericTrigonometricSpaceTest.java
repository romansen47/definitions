package definitions.prototypes;

import org.junit.Assert;
import org.junit.Before;

import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Real;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.euclidean.vectors.impl.GenericFunction;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

public class GenericTrigonometricSpaceTest extends AspectJTest {

	private int trigonometricDegree;
	private EuclideanSpace trigonometricSpace;
	private String path = "src/main/resources/test.csv";
	private double[][] testValues;
	private GenericFunction staircaseFunction;
	private Field f;

	public Field getField() {
		return f;
	}

	public String getPath() {
		return path;
	}

	public GenericFunction getStaircaseFunction() {
		return staircaseFunction;
	}

	public int getTrigonometricDegree() {
		return trigonometricDegree;
	}

	public EuclideanSpace getTrigonometricSpace() {
		return trigonometricSpace;
	}

	public void setField(final Field f) {
		this.f = f;
	}

	public void setPath(final String path) {
		this.path = path;
	}

	public void setStaircaseFunction(final GenericFunction staircaseFunction) {
		this.staircaseFunction = staircaseFunction;
	}

	public void setTrigonometricDegree(final int trigonometricDegree) {
		this.trigonometricDegree = trigonometricDegree;
	}

	public void setTrigonometricSpace(final EuclideanSpace trigonometricSpace) {
		this.trigonometricSpace = trigonometricSpace;
	}

	@Before
	public void setUp() throws Exception {

		f = AspectJTest.getRealLine();

		setTrigonometricSpace(AspectJTest.getSpaceGenerator().getNormedTrigonometricSpace(f, getTrigonometricDegree()));
		testValues = definitions.aspectjtest.Reader.readFile(getPath());
		setStaircaseFunction(new GenericFunction() {

			private static final long serialVersionUID = -8361584686661267908L;
			private final int length = (int) testValues[0][testValues[0].length - 1];

			@Override
			public Field getField() {
				return f;
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
		getLogger().info("distance / norm = {} / {} = {}  {}", distance, norm_of_function, distance / norm_of_function,
				s);
		Assert.assertTrue(distance / norm_of_function < eps);
	}
}
