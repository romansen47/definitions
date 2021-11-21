package definitions.aspectjtest;

import org.junit.Before;
import org.junit.Test;

import definitions.prototypes.GenericTrigonometricSpaceTest;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.euclidean.vectors.impl.GenericFunction;

public class TrigonometricSobolevSpaceWithLinearGrowthTest extends GenericTrigonometricSpaceTest {

	private int sobolevDegree = 1;
	private final int trigDegree = 5;

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

		setField(getRealLine());
		super.setUp();
		setTrigonometricSpace(getSpaceGenerator().getTrigonometricSobolevSpaceWithLinearGrowth(getRealLine(),
				getSobolevDegree(), Math.PI, getTrigonometricDegree()));

	}

	@Test
	public void test1() {
		final Function staircaseFunctionProjection = getStaircaseFunction().getProjection(getTrigonometricSpace());
		getStaircaseFunction().plotCompare(-Math.PI, Math.PI, staircaseFunctionProjection);
	}

	@Test
	public void test2() {
		final Function absolute = new GenericFunction() {

			/**
			 *
			 */
			private static final long serialVersionUID = -5009775881103765610L;

			@Override
			public Scalar value(Scalar input) {
				final double x = input.getDoubleValue();
				return RealLine.getInstance().get(Math.abs(x));
			}

		};
		final Function projectionOfAbsolute = absolute.getProjection(getTrigonometricSpace());
		projectionOfAbsolute.plotCompare(-Math.PI, Math.PI, absolute);
	}

}
