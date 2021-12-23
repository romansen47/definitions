package definitions.prototypes;

import org.junit.Before;

import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Real;
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

		setTrigonometricSpace(AspectJTest.getSpaceGenerator().getNormedTrigonometricSpace(AspectJTest.getRealLine(), getTrigonometricDegree()));
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
}
