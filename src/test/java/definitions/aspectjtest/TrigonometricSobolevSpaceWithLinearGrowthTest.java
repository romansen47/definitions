package definitions.aspectjtest;

import org.junit.Before;
import org.junit.Test;

import definitions.prototypes.AspectJTest;
import definitions.prototypes.GenericTrigonometricSpaceTest;
import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Real;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.euclidean.vectors.impl.GenericFunction;

/**
 * 
 * @author ro
 *
 */
public class TrigonometricSobolevSpaceWithLinearGrowthTest extends GenericTrigonometricSpaceTest {

	private int sobolevDegree = 1;
	private final int trigDegree = 7;

	public int getSobolevDegree() {
		return sobolevDegree;
	}

	private void setSobolevDegree(final int degree) {
		sobolevDegree = degree;
	}

	@Override
	@Before
	public void setUp() throws Exception {

		setTrigonometricDegree(trigDegree);
		setSobolevDegree(sobolevDegree);

		setField(AspectJTest.getRealLine());
		super.setUp();
		setTrigonometricSpace(AspectJTest.getSpaceGenerator().getTrigonometricSobolevSpaceWithLinearGrowth(
				AspectJTest.getRealLine(), sobolevDegree, Math.PI, trigDegree));

	}

	@Test
	public void testOnStaircaseFunction() {
		testOnFunction(getStaircaseFunction(), trigDegree, sobolevDegree, 1d);
	}

	@Test
	public void testOnAbsolute() {
		final Function absolute = new GenericFunction() {
			private static final long serialVersionUID = -5009775881103765610L;

			@Override
			public Field getField() {
				return AspectJTest.getRealLine();
			}

			@Override
			public Scalar value(Scalar input) {
				return getRealLine().get(Math.abs(((Real) input).getRepresentant()));
			}
		};

		testOnFunction(absolute, trigDegree, sobolevDegree, 1d);
	}

	@Test
	public void testOnIdentity() {
		final Function identity = new GenericFunction() {
			private static final long serialVersionUID = -5009775881103765610L;

			@Override
			public Field getField() {
				return AspectJTest.getRealLine();
			}

			@Override
			public Scalar value(Scalar input) {
				return input;
			}

		};
		testOnFunction(identity, trigDegree, sobolevDegree, 1e-3);
	}

}
