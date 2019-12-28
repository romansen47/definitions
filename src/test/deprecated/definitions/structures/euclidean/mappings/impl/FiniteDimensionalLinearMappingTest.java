package definitions.structures.euclidean.mappings.impl;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Real;
import definitions.structures.abstr.mappings.VectorSpaceHomomorphism;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

public class FiniteDimensionalLinearMappingTest {

	Map<Vector, Map<Vector, Scalar>> ans1;
	Scalar[][] ans2;
	VectorSpace space = RealLine.getInstance();

	VectorSpaceHomomorphism lin = new FiniteDimensionalLinearMapping((EuclideanSpace) this.space, (EuclideanSpace) this.space) {
		private static final long serialVersionUID = 8542796160933542925L;

		@Override
		public Vector get(Vector vec) {
			return ((EuclideanSpace) FiniteDimensionalLinearMappingTest.this.space).stretch(vec, RealLine.getInstance().get(5));
		}
	};

	public static void main(String[] args) {
		FiniteDimensionalLinearMappingTest test = new FiniteDimensionalLinearMappingTest();
		test.testGetLinearity();
		test.testGetGenericMatrix();
	}

	@Test
	public void testGetLinearity() {
		ans1 = lin.getLinearity();
		ans2 = lin.getGenericMatrix();
		Assert.assertTrue(
				this.ans1.get(((RealLine) this.space).getOne()).get(((RealLine) this.space).getOne()).getValue() == 5.);
	}

	@Test
	public void testGetGenericMatrix() {
		if (ans1 == null || ans2 == null) {
			ans1 = lin.getLinearity();
			ans2 = lin.getGenericMatrix();
		}
		Assert.assertTrue(this.ans2[0][0].getValue() == 5.);
	}
}
