package definitions.xmltest;

import org.junit.Before;
import org.junit.Test;

import definitions.prototypes.GenericTrigonometricSpaceTest;
import definitions.structures.abstr.vectorspaces.vectors.Function;

public class TrigonometricSobolevSpaceTest extends GenericTrigonometricSpaceTest {

	private int sobolevDegree;
	
	@Override
	@Before
	public void setUp() throws Exception {

		setTrigonometricDegree(50);
		setSobolevDegree(1);
		
		setField(getRealLine());
		super.setUp();
		setTrigonometricSpace(getSpaceGenerator().getTrigonometricSobolevSpace(getRealLine(), getTrigonometricDegree(),getSobolevDegree()));
		
	}
	
	@Test
	public void test1() {
		final Function staircaseFunction1Projection = getStaircaseFunction().getProjection(getTrigonometricSpace());
		getStaircaseFunction().plotCompare(-Math.PI, Math.PI, staircaseFunction1Projection);
	}

	public int getSobolevDegree() {
		return sobolevDegree;
	}
	
	private void setSobolevDegree(int degree) {
		sobolevDegree=degree;
	}
	
}
