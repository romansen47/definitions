package definitions.prototypes;

import java.nio.file.Path;

import org.junit.Before;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.euclidean.vectors.impl.GenericFunction;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.java.Reader;

public class GenericTrigonometricSpaceTest extends AspectJTest {

	private int trigonometricDegree;
	private EuclideanSpace trigonometricSpace;
	private String path = "src/main/resources/test.csv";
	private double[][] testValues;
	private GenericFunction staircaseFunction;
	private Field f;
	
	@Before
	public void setUp() throws Exception {
		
		setTrigonometricSpace(getSpaceGenerator().getTrigonometricSpace(getRealLine(), getTrigonometricDegree()));
		testValues = Reader.readFile(getPath());
		setStaircaseFunction(new GenericFunction() { 
			
			private static final long serialVersionUID = -8361584686661267908L;
			private final int length = (int) testValues[0][testValues[0].length - 1];

			@Override
			public Scalar value(Scalar input) {
				final double newInput = ((this.length / (2 * Math.PI)) * input.getValue()) + (this.length / 2.);
				int k = 0;
				final int l = (int) (newInput - (newInput % 1));
				while (k + 1 < testValues[0].length && testValues[0][k] < l) {
					k++;
				}
				return this.getField().get(testValues[1][k]); 
			}

			@Override
			public Field getField() {
				return f;
			}
		});
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public EuclideanSpace getTrigonometricSpace() {
		return trigonometricSpace;
	}

	public void setTrigonometricSpace(EuclideanSpace trigonometricSpace) {
		this.trigonometricSpace = trigonometricSpace;
	}

	public GenericFunction getStaircaseFunction() {
		return staircaseFunction;
	}

	public void setStaircaseFunction(GenericFunction staircaseFunction) {
		this.staircaseFunction = staircaseFunction;
	}

	public Field getField() {
		return f;
	}

	public void setField(Field f) {
		this.f = f;
	}

	public int getTrigonometricDegree() {
		return trigonometricDegree;
	}

	public void setTrigonometricDegree(int trigonometricDegree) {
		this.trigonometricDegree = trigonometricDegree;
	}
}
