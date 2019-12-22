/**
 * 
 */
package definitions.xmltest;

import org.junit.Assert;
import org.junit.Test;

import definitions.prototypes.AspectJTest;
import definitions.structures.abstr.mappings.Homomorphism;

/**
 * @author ro
 *
 */
public class SubSpaceTest extends AspectJTest {

	/**
	 * Test method for
	 * {@link definitions.structures.euclidean.vectorspaces.SubSpace#getEmbedding()}.
	 */
	@Test
	public void testGetEmbedding() {
		final Homomorphism embedding = getRealLine().getEmbedding();
		Assert.assertTrue(embedding.get(getRealLine().getOne()).equals(getComplexPlane().getOne()));
	}

	/**
	 * Test method for
	 * {@link definitions.structures.euclidean.vectorspaces.SubSpace#getSuperSpace()}.
	 */
	@Test
	public void testGetSuperSpace() {
		Assert.assertTrue(getRealLine().getSuperSpace().equals(getComplexPlane()));
	}

}
