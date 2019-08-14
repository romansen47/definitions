package definitions.structures.abstr;

import org.junit.Assert;
import org.junit.Test;

import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

public abstract class VectorSpaceTest {

	public abstract VectorSpace getSpace();

	public abstract Vector getVec1();

	public abstract Vector getVec2();

	public abstract Scalar getFactor();

	public void testContains() {
		Assert.assertTrue(this.getSpace().contains(this.getVec1()));
		Assert.assertTrue(this.getSpace().contains(this.getVec2()));
	}

	@Test
	public void show() {
		((EuclideanSpace) this.getSpace()).show();
	}

}
