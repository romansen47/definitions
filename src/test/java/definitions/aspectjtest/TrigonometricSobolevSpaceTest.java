package definitions.aspectjtest;

import org.junit.Before;
import org.junit.Test;

import definitions.prototypes.AspectJTest;
import definitions.prototypes.GenericTrigonometricSpaceTest;
import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Real;
import definitions.structures.euclidean.vectors.impl.GenericFunction;

/**
 * Sobolev tests make sense only on continuous functions.
 * 
 * @author roman
 *
 */
public class TrigonometricSobolevSpaceTest extends GenericTrigonometricSpaceTest {

	private int degree = 6;
	private int sobolevDegree = 1;
	final double eps = 1d;

	public int getSobolevDegree() {
		return sobolevDegree;
	}

	private void setSobolevDegree(final int degree) {
		sobolevDegree = degree;
	}

	@Override
	@Before
	public void setUp() throws Exception {

		setTrigonometricDegree(degree);
		setSobolevDegree(sobolevDegree);

		setField(AspectJTest.getRealLine());
		super.setUp();
		setTrigonometricSpace(AspectJTest.getSpaceGenerator().getTrigonometricSobolevSpace(AspectJTest.getRealLine(),
				degree, sobolevDegree));

	}

	@Test
	public void testOnStairCaseFunction() {
		testOnFunction(getStaircaseFunction(), degree, sobolevDegree, eps);
	}

	@Test
	public void testOnContinuousFunction() throws Exception {
		testOnFunction(new GenericFunction() {
			private static final long serialVersionUID = 3842946945322219375L;

			@Override
			public Field getField() {
				return AspectJTest.getRealLine();
			}

			@Override
			public Scalar value(Scalar input) {
				final Double inputValue = ((Real) input).getDoubleValue();
				final double abs = Math.abs((Math.sin(inputValue) * Math.cos(inputValue)) - 0.25);
				return RealLine.getInstance().get(abs);
			}
		}, degree, sobolevDegree, eps);
	}

}
