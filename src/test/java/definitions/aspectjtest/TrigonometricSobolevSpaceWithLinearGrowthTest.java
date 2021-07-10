package definitions.aspectjtest;

import org.junit.Before;
import org.junit.Test;

import definitions.prototypes.GenericTrigonometricSpaceTest;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.euclidean.vectors.impl.GenericFunction;

public class TrigonometricSobolevSpaceWithLinearGrowthTest extends GenericTrigonometricSpaceTest {

	/*
	 * correct derivative computing algorithm here due to identity
	 */

	private int sobolevDegree = 3;
	private final int trigDegree = 5;

	public int getSobolevDegree() {
		return this.sobolevDegree;
	}

	private void setSobolevDegree(final int degree) {
		this.sobolevDegree = degree;
	}

	@Override
	@Before
	public void setUp() throws Exception {

		this.setTrigonometricDegree(this.trigDegree);
		this.setSobolevDegree(this.sobolevDegree);

		this.setField(getRealLine());
		super.setUp();
		this.setTrigonometricSpace(getSpaceGenerator().getTrigonometricSobolevSpaceWithLinearGrowth(getRealLine(),
				this.getSobolevDegree(), Math.PI, this.getTrigonometricDegree()));

	}

	@Test
	public void test1() {
		final Function staircaseFunctionProjection = this.getStaircaseFunction()
				.getProjection(this.getTrigonometricSpace());
		this.getStaircaseFunction().plotCompare(-Math.PI, Math.PI, staircaseFunctionProjection);
	}
	
	@Test
	public void test2() {
		final Function absolute = new GenericFunction() {
			private static final long serialVersionUID = -5009775881103765610L;
			@Override
			public Scalar value(Scalar input) {
				double x=input.getDoubleValue();
				return RealLine.getInstance().get(Math.abs(x));
			}
		};
		final Function projectionOfAbsolute = absolute.getProjection(this.getTrigonometricSpace());
		projectionOfAbsolute.plotCompare(-Math.PI, Math.PI, absolute);
	}

}
