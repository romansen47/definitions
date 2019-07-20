package definitions.structures.finitedimensional.mappings.impl;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import definitions.structures.abstr.Homomorphism;
import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.abstr.impl.RealLine;
import definitions.structures.finitedimensional.vectorspaces.EuclideanSpace;

public class FiniteDimensionalLinearMappingTest {

	final VectorSpace space=RealLine.getRealLine();
	final Homomorphism lin=new FiniteDimensionalLinearMapping(
			(EuclideanSpace)space,
			(EuclideanSpace)space) {
		@Override
		public Vector get(Vector vec) {
			return ((EuclideanSpace)space).stretch(vec, 5);
		}	
	};

	@Test
	public void testGetLinearity() {
		Map<Vector, Map<Vector, Double>> ans = lin.getLinearity();
		Assert.assertTrue(ans.get(((RealLine) space).getOne()).get(((RealLine) space).getOne())==5.);
	}

	@Test
	public void testGetGenericMatrix() {
		double[][] ans = lin.getGenericMatrix();
		Assert.assertTrue(ans[0][0]==5.);
	}
}
