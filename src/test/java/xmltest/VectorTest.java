package xmltest;

import static org.junit.Assert.*;

import org.junit.Test;

import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.ISpaceGenerator;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;

public class VectorTest {

	@Test
	public void test() {
		SpaceGenerator gen=SpaceGenerator.getInstance();
		EuclideanSpace space =(EuclideanSpace) gen.getFiniteDimensionalComplexSpace(1);
		Vector vec=space.genericBaseToList().get(0);
	}

}
