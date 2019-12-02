/**
 * 
 */
package definitions.xmltest;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import definitions.prototypes.AspectJTest;
import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.impl.ComplexPlane;
import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.abstr.mappings.Homomorphism;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

/**
 * @author ro
 *
 */
public class SubSpaceTest extends AspectJTest{

	/**
	 * Test method for
	 * {@link definitions.structures.euclidean.vectorspaces.SubSpace#getSuperSpace()}.
	 */
	@Test
	public void testGetSuperSpace() {
		Assert.assertTrue(getRealLine().getSuperSpace().equals(getComplexPlane()));
	}
	
	/**
	 * Test method for
	 * {@link definitions.structures.euclidean.vectorspaces.SubSpace#getEmbedding()}.
	 */
	@Test	
	public void testGetEmbedding() {
		final Homomorphism embedding = getRealLine().getEmbedding();
		Assert.assertTrue(embedding.get(getRealLine().getOne()).equals(getComplexPlane().getOne()));
	}

}
