package definitions.aspectjtest;

import org.junit.Before;
import org.junit.Test;

import definitions.prototypes.GenericTrigonometricSpaceTest;
import definitions.structures.abstr.vectorspaces.vectors.Function;

public class TrigonometricSobolevSpaceTest extends GenericTrigonometricSpaceTest {

	private int sobolevDegree;

	public int getSobolevDegree() {
		return this.sobolevDegree;
	}

	private void setSobolevDegree(final int degree) {
		this.sobolevDegree = degree;
	}

	@Override
	@Before
	public void setUp() throws Exception {

		this.setTrigonometricDegree(20);
		this.setSobolevDegree(2);

		this.setField(getRealLine());
		super.setUp();
		this.setTrigonometricSpace(getSpaceGenerator().getTrigonometricSobolevSpace(getRealLine(),
				this.getTrigonometricDegree(), this.getSobolevDegree()));

	}

	@Test
	public void test1() {
		final Function staircaseFunction1Projection = this.getStaircaseFunction()
				.getProjection(this.getTrigonometricSpace());
		this.getStaircaseFunction().plotCompare(-Math.PI, Math.PI, staircaseFunction1Projection);
	}

}
