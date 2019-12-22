package definitions.xmltest;

import org.junit.Before;
import org.junit.Test;

import definitions.prototypes.GenericTrigonometricSpaceTest;
import definitions.structures.abstr.vectorspaces.vectors.Function;

public class TrigonometricSobolevSpaceWithLinearGrowthTest extends GenericTrigonometricSpaceTest {

	/*
	 * correct derivative computing algo here due to identity
	 */

	private int sobolevDegree = 3;
	private final int trigDegree = 20;

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
		final Function staircaseFunction1Projection = this.getStaircaseFunction()
				.getProjection(this.getTrigonometricSpace());
		this.getStaircaseFunction().plotCompare(-Math.PI, Math.PI, staircaseFunction1Projection);
	}

}
