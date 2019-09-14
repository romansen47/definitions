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

	public abstract Vector getVector();

	public abstract EuclideanSpace getSpace();

	/**
	 * Test method for
	 * {@link definitions.structures.abstr.vectorspaces.vectors.Vector#getDim()}.
	 */
	@Test
	@settings.Trace(trace = settings.GlobalSettings.LOGGING, depth = settings.GlobalSettings.LOGGING_DEPTH, initial = true, transit = true)
	public void testGetDim() {
		Assert.assertTrue(this.getVector().getDim().intValue() == this.getSpace().getDim());
	}

	/**
	 * Test method for
	 * {@link definitions.structures.abstr.vectorspaces.vectors.Vector#elementOf(definitions.structures.abstr.vectorspaces.VectorSpace)}.
	 */
	@Test
	@settings.Trace(trace = settings.GlobalSettings.LOGGING, depth = settings.GlobalSettings.LOGGING_DEPTH, initial = true, transit = true)
	public void testElementOf() {
		Assert.assertTrue(this.getVector().elementOf(this.getSpace()));
	}

	/**
	 * Test method for
	 * {@link definitions.structures.abstr.vectorspaces.vectors.FiniteVectorMethods#getCoordinates()}.
	 */
	@Test
	@settings.Trace(trace = settings.GlobalSettings.LOGGING, depth = settings.GlobalSettings.LOGGING_DEPTH, initial = true, transit = true)
	public void testGetCoordinates() {
		Assert.assertTrue(((FiniteVectorMethods) this.getVector()).getCoordinates() != null);
	}

}
