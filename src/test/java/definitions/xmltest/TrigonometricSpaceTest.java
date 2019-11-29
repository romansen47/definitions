package definitions.xmltest;

import org.junit.Before;
import org.junit.Test;

import definitions.prototypes.GenericTrigonometricSpaceTest;
import definitions.structures.abstr.vectorspaces.vectors.Function;

public class TrigonometricSpaceTest extends GenericTrigonometricSpaceTest{

	@Override
	@Before
	public void setUp() throws Exception {
		setField(getRealLine());
		setTrigonometricDegree(5);
		super.setUp();
	}
	
	@Test
	public void test1() {
		final Function staircaseFunction1Projection = getStaircaseFunction().getProjection(getTrigonometricSpace());
		getStaircaseFunction().plotCompare(-Math.PI, Math.PI, staircaseFunction1Projection);
	}
	
}
