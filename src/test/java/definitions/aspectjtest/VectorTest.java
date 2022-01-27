package definitions.aspectjtest;

import org.junit.Test;

import definitions.prototypes.AspectJTest;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

public class VectorTest extends AspectJTest {

	@Test
	public void euclideanSpaceTest() {
		final EuclideanSpace space = (EuclideanSpace) AspectJTest.getSpaceGenerator().getFiniteDimensionalComplexSpace(1);
		AspectJTest.getLogger().info(space.genericBaseToList().get(0).toString());
	}

}
