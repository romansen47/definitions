package definitions.structures.euclidean.mappings.impl;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.mappings.VectorSpaceHomomorphism;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

public class FiniteDimensionalLinearMappingTest {

	public static void main(final String[] args) {
		final FiniteDimensionalLinearMappingTest test = new FiniteDimensionalLinearMappingTest();
		test.testGetLinearity();
		test.testGetGenericMatrix();
	}

	Map<Vector, Map<Vector, Scalar>> ans1;
	Scalar[][] ans2;

	VectorSpace space = RealLine.getInstance();

	VectorSpaceHomomorphism lin = new FiniteDimensionalLinearMapping((EuclideanSpace) this.space,
			(EuclideanSpace) this.space) {
		private static final long serialVersionUID = 8542796160933542925L;

		@Override
		public Vector get(final Element vec) {
			return ((EuclideanSpace) FiniteDimensionalLinearMappingTest.this.space).stretch((Vector) vec,
					RealLine.getInstance().get(5));
		}
	};

	@Test
	public void testGetGenericMatrix() {
		if (this.ans1 == null || this.ans2 == null) {
			this.ans1 = this.lin.getLinearity();
			this.ans2 = this.lin.getGenericMatrix();
		}
		Assert.assertTrue(this.ans2[0][0].getDoubleValue() == 5.);
	}

	@Test
	public void testGetLinearity() {
		this.ans1 = this.lin.getLinearity();
		this.ans2 = this.lin.getGenericMatrix();
		Assert.assertTrue(
				this.ans1.get(((RealLine) this.space).getOne()).get(((RealLine) this.space).getOne()).getDoubleValue() == 5.);
	}
}
