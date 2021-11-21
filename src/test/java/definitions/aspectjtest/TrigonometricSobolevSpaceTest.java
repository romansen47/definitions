package definitions.aspectjtest;

import org.junit.Before;
import org.junit.Test;

import definitions.prototypes.GenericTrigonometricSpaceTest;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.euclidean.vectors.impl.GenericFunction;

public class TrigonometricSobolevSpaceTest extends GenericTrigonometricSpaceTest {

	private int sobolevDegree;

	public int getSobolevDegree() {
		return sobolevDegree;
	}

	private void setSobolevDegree(final int degree) {
		sobolevDegree = degree;
	}

	@Override
	@Before
	public void setUp() throws Exception {

		setTrigonometricDegree(7);
		setSobolevDegree(0);

		setField(getRealLine());
		super.setUp();
		setTrigonometricSpace(getSpaceGenerator().getTrigonometricSobolevSpace(getRealLine(), getTrigonometricDegree(),
				getSobolevDegree()));

	}

	@Test
	public void test1() {
		final Function staircaseFunction1Projection = getStaircaseFunction().getProjection(getTrigonometricSpace());
		getStaircaseFunction().plotCompare(-Math.PI, Math.PI, staircaseFunction1Projection);
	}

	@Test
	public void testOnContinuousFunction() throws Exception {
		final Function h = new GenericFunction() {
			/**
			 *
			 */
			private static final long serialVersionUID = 3842946945322219375L;

			@Override
			public Scalar value(Scalar input) {
				final Double inputValue = input.getRepresentant();
				final double abs = Math.abs((Math.sin(inputValue) * Math.cos(inputValue)) - 0.25);
				return RealLine.getInstance().get(abs);
			}
		};
		Function hProjection;
		for (int i = 0; i < 5; i++) {
			setSobolevDegree(i);
			setTrigonometricSpace(getSpaceGenerator().getTrigonometricSobolevSpace(getRealLine(),
					getTrigonometricDegree(), getSobolevDegree()));
			hProjection = h.getProjection(getTrigonometricSpace());
			h.plotCompare(-Math.PI, Math.PI, hProjection);
		}
	}

}
