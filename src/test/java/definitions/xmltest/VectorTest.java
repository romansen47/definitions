package definitions.xmltest;

import org.junit.Test;

import definitions.prototypes.AspectJTest;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
// 
public class VectorTest extends AspectJTest{
 
	@Test
	public void test() {
		EuclideanSpace space =(EuclideanSpace) getSpaceGenerator().getFiniteDimensionalComplexSpace(1);
		getLogger().info(space.genericBaseToList().get(0).toString());
	}

}
