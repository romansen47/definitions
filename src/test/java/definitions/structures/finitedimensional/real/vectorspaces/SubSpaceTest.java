/**
 * 
 */
package definitions.structures.finitedimensional.real.vectorspaces;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.Homomorphism;
import definitions.structures.field.Field;
import definitions.structures.field.impl.ComplexPlane;
import definitions.structures.field.impl.RealLine;

/**
 * @author ro
 *
 */
public class SubSpaceTest {

	final EuclideanSpace realLine=RealLine.getInstance();
	final EuclideanSpace complexPlane=ComplexPlane.getInstance();
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * Test method for {@link definitions.structures.finitedimensional.real.vectorspaces.SubSpace#getSuperSpace()}.
	 */
	@Test
	public void testGetSuperSpace() {
		Assert.assertTrue(((RealLine) realLine).getSuperSpace().equals(complexPlane));
	}

	/**
	 * Test method for {@link definitions.structures.finitedimensional.real.vectorspaces.SubSpace#getEmbedding()}.
	 */
	@Test
	public void testGetEmbedding() {
		Homomorphism embedding=((RealLine) realLine).getEmbedding();
		Assert.assertTrue(embedding.get(((Field) realLine).getOne()).equals(((Field) complexPlane).getOne()));
	}

}
