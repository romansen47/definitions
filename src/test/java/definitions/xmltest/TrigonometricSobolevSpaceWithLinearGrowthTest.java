package definitions.xmltest;

import org.junit.Before;
import org.junit.Test;

import definitions.prototypes.GenericTrigonometricSpaceTest;
import definitions.structures.abstr.vectorspaces.vectors.Function;

public class TrigonometricSobolevSpaceWithLinearGrowthTest extends GenericTrigonometricSpaceTest {

	private int sobolevDegree=1;
	private int trigDegree=20;
	
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
		final Function staircaseFunction1Projection = getStaircaseFunction().getProjection(getTrigonometricSpace());
		getStaircaseFunction().plotCompare(-Math.PI, Math.PI, staircaseFunction1Projection);
	}

	public int getSobolevDegree() {
		return sobolevDegree;
	}

	private void setSobolevDegree(int degree) {
		sobolevDegree = degree;
	}

}
