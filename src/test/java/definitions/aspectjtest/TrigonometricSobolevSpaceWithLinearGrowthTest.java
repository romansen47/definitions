package definitions.aspectjtest;

import org.junit.Before;
import org.junit.Test;

import definitions.prototypes.AspectJTest;
import definitions.prototypes.GenericTrigonometricSpaceTest;
import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Real;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.euclidean.vectors.impl.GenericFunction;

public class TrigonometricSobolevSpaceWithLinearGrowthTest extends GenericTrigonometricSpaceTest {

	private int sobolevDegree = 2;
	private final int trigDegree = 10;

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
		setTrigonometricSpace(AspectJTest.getSpaceGenerator().getTrigonometricSobolevSpaceWithLinearGrowth(AspectJTest.getRealLine(),
				getSobolevDegree(), Math.PI, getTrigonometricDegree()));

	}
	@Test
	public void test() {
		final Function absolute = new GenericFunction() {

			/**
			 *
			 */
			private static final long serialVersionUID = -5009775881103765610L;

			@Override
			public Field getField() {
				return AspectJTest.getRealLine();
			}

			@Override
			public Scalar value(Scalar input) {
				final double x = ((Real) input).getDoubleValue();
				return RealLine.getInstance().get(Math.abs(x));
			}

		};
		final Function projectionOfAbsolute = absolute.getProjection(getTrigonometricSpace());
		projectionOfAbsolute.plotCompare(-Math.PI, Math.PI, absolute);
	}

}
