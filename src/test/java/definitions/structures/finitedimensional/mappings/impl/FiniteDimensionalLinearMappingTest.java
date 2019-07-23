package definitions.structures.finitedimensional.mappings.impl;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import definitions.structures.abstr.Homomorphism;
import definitions.structures.abstr.Scalar;
import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.finitedimensional.field.impl.RealLine;
import definitions.structures.finitedimensional.real.mappings.impl.FiniteDimensionalLinearMapping;
import definitions.structures.finitedimensional.real.vectors.Real;
import definitions.structures.finitedimensional.real.vectorspaces.EuclideanSpace;

public class FiniteDimensionalLinearMappingTest {

	static Map<Vector, Map<Vector, Scalar>> ans1;
	static Scalar[][] ans2;
	final VectorSpace space=RealLine.getRealLine();
	final Homomorphism lin=new FiniteDimensionalLinearMapping(
			(EuclideanSpace)space,
			(EuclideanSpace)space) {
		@Override
		public Vector get(Vector vec) {
			return ((EuclideanSpace)space).stretch(vec, new Real(5));
		}	
	};

	@Test
	public void testGetLinearity() {
		ans1 = lin.getLinearity();
		Assert.assertTrue(ans1.get(((RealLine) space).getOne()).get(((RealLine) space).getOne()).getValue()==5.);
	}

	@Test
	public void testGetGenericMatrix() {
		ans2 = lin.getGenericMatrix();
		Assert.assertTrue(ans2[0][0].getValue()==5.);
	}
}
