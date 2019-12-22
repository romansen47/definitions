package definitions.xmltest;

import org.junit.Before;
import org.junit.Test;

import definitions.prototypes.GenericTrigonometricSpaceTest;
import definitions.structures.abstr.vectorspaces.vectors.Function;

public class TrigonometricSpaceTest extends GenericTrigonometricSpaceTest {

	@Override
	@Before
	public void setUp() throws Exception {
		this.setField(getRealLine());
		this.setTrigonometricDegree(25);
		super.setUp();
	}

	@Test
	public void test1() {
		final Function staircaseFunction1Projection = this.getStaircaseFunction()
				.getProjection(this.getTrigonometricSpace());
		this.getStaircaseFunction().plotCompare(-Math.PI, Math.PI, staircaseFunction1Projection);
	}

}
