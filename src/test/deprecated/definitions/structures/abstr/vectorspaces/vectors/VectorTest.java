/**
 * 
 */
package definitions.structures.abstr.vectorspaces.vectors;

import org.junit.Assert;
import org.junit.Test;

import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

/**
 * @author RoManski
 *
 */
public abstract class VectorTest {

	public abstract EuclideanSpace getSpace();

	public abstract Vector getVector();

	/**
	 * Test method for
	 * {@link definitions.structures.abstr.vectorspaces.vectors.Vector#elementOf(definitions.structures.abstr.vectorspaces.VectorSpace)}.
	 */
	@Test

	public void testElementOf() {
		Assert.assertTrue(this.getVector().elementOf(this.getSpace()));
	}

	/**
	 * Test method for
	 * {@link definitions.structures.abstr.vectorspaces.vectors.FiniteVectorMethods#getCoordinates()}.
	 */
	@Test

	public void testGetCoordinates() {
		Assert.assertTrue(((FiniteVectorMethods) this.getVector()).getCoordinates() != null);
	}

	/**
	 * Test method for
	 * {@link definitions.structures.abstr.vectorspaces.vectors.Vector#getDim()}.
	 */
	@Test

	public void testGetDim() {
		Assert.assertTrue(this.getVector().getDim().intValue() == this.getSpace().getDim());
	}

}
