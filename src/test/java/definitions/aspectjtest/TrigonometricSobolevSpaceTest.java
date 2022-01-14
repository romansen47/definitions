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

/**
 * Sobolev tests make sense only on continuous functions.
 * 
 * @author roman
 *
 */
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

		setTrigonometricDegree(10);
		setSobolevDegree(2);

		setField(AspectJTest.getRealLine());
		super.setUp();
		setTrigonometricSpace(AspectJTest.getSpaceGenerator().getTrigonometricSobolevSpace(AspectJTest.getRealLine(),
				getTrigonometricDegree(), getSobolevDegree()));

	}

	@Test
	public void test1() {
		int sDegree = getSobolevDegree();
		/*
		 * It is sufficient to demonastrate the effect for sobolevDegree==1
		 */
		for (int i = 0; i < sDegree; i++) {
			setSobolevDegree(i);
			getStaircaseFunction().plot(-Math.PI, Math.PI);
			setTrigonometricSpace(AspectJTest.getSpaceGenerator().getTrigonometricSobolevSpace(
					AspectJTest.getRealLine(), getTrigonometricDegree(), getSobolevDegree()));
			final Function staircaseFunction1Projection = getStaircaseFunction().getProjection(getTrigonometricSpace());
			staircaseFunction1Projection.plotCompare(-Math.PI, Math.PI, getStaircaseFunction());
		}
	}

	@Test
	public void testOnContinuousFunction() throws Exception {
		final Function h = new GenericFunction() {
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
		};
		Function hProjection;
		int sDegree = getSobolevDegree();
		/*
		 * for sobolevDegree==1 it's still working, function is continuous. for
		 * sobolevDegree==2 the derivative of approximated function must be
		 * continuous...
		 */
		for (int i = 0; i <= sDegree; i++) {
			setSobolevDegree(i);
			setTrigonometricSpace(AspectJTest.getSpaceGenerator().getTrigonometricSobolevSpace(
					AspectJTest.getRealLine(), getTrigonometricDegree(), getSobolevDegree()));
			hProjection = h.getProjection(getTrigonometricSpace());
			h.plotCompare(-Math.PI, Math.PI, hProjection);
		}
	}

}
