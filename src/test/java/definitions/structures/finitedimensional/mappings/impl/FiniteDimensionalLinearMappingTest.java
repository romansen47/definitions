package definitions.structures.finitedimensional.mappings.impl;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.fields.scalars.impl.Real;
import definitions.structures.abstr.mappings.Homomorphism;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.mappings.impl.FiniteDimensionalLinearMapping;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

public class FiniteDimensionalLinearMappingTest {

	Map<Vector, Map<Vector, Scalar>> ans1;
	Scalar[][] ans2;
	final VectorSpace space = RealLine.getInstance();

	final Homomorphism lin = new FiniteDimensionalLinearMapping((EuclideanSpace) this.space,
			(EuclideanSpace) this.space) {
		@Override
		public Vector get(Vector vec) {
			return ((EuclideanSpace) FiniteDimensionalLinearMappingTest.this.space).stretch(vec, new Real(5));
		}
	};

	@Before
	public void beforeClass() {
		this.ans1 = this.lin.getLinearity();
		this.ans2 = this.lin.getGenericMatrix();
	}

	@Test
	public void testGetLinearity() {
		Assert.assertTrue(
				this.ans1.get(((RealLine) this.space).getOne()).get(((RealLine) this.space).getOne()).getValue() == 5.);
	}

	@Test
	public void testGetGenericMatrix() {
		Assert.assertTrue(this.ans2[0][0].getValue() == 5.);
	}
}
