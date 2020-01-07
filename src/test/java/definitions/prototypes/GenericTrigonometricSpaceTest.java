package definitions.prototypes;

import org.junit.Before;

import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
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
		return this.f;
	}

	public String getPath() {
		return this.path;
	}

	public GenericFunction getStaircaseFunction() {
		return this.staircaseFunction;
	}

	public int getTrigonometricDegree() {
		return this.trigonometricDegree;
	}

	public EuclideanSpace getTrigonometricSpace() {
		return this.trigonometricSpace;
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

		this.f = getRealLine();

		this.setTrigonometricSpace(
				getSpaceGenerator().getTrigonometricSpace(getRealLine(), this.getTrigonometricDegree()));
		this.testValues = definitions.aspectjtest.Reader.readFile(this.getPath());
		this.setStaircaseFunction(new GenericFunction() {

			private static final long serialVersionUID = -8361584686661267908L;
			private final int length = (int) GenericTrigonometricSpaceTest.this.testValues[0][GenericTrigonometricSpaceTest.this.testValues[0].length
					- 1];

			@Override
			public Field getField() {
				return GenericTrigonometricSpaceTest.this.f;
			}

			@Override
			public Scalar value(final Scalar input) {
				final double newInput = ((this.length / (2 * Math.PI)) * input.getDoubleValue()) + (this.length / 2.);
				int k = 0;
				final int l = (int) (newInput - (newInput % 1));
				while (k + 1 < GenericTrigonometricSpaceTest.this.testValues[0].length
						&& GenericTrigonometricSpaceTest.this.testValues[0][k] < l) {
					k++;
				}
				return this.getField().get(GenericTrigonometricSpaceTest.this.testValues[1][k]);
			}
		});
	}
}
