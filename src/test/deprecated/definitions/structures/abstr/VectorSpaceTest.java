package definitions.structures.abstr;

import org.junit.Assert;

import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.VectorSpaceMethods;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

public abstract class VectorSpaceTest {

	public abstract Scalar getFactor();

	public abstract VectorSpace getSpace();

	public abstract Vector getVec1();

	public abstract Vector getVec2();

	public void show() {
		((EuclideanSpace) this.getSpace()).show();
	}

	public void testContains() {
		Assert.assertTrue(((VectorSpaceMethods) this.getSpace()).contains(this.getVec1()));
		Assert.assertTrue(((VectorSpaceMethods) this.getSpace()).contains(this.getVec2()));
	}

}
