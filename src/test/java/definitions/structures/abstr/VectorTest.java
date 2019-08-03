/**
 * 
 */
package definitions.structures.abstr;

import java.util.Map;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import definitions.structures.abstr.Vector;
import definitions.structures.finitedimensional.real.vectors.impl.Tuple;
import definitions.structures.finitedimensional.real.vectorspaces.EuclideanSpace;
import definitions.structures.finitedimensional.real.vectorspaces.impl.SpaceGenerator;

/**
 * @author RoManski
 *
 */
public class VectorTest {

	final int dim=Math.abs(new Random().nextInt(10));
	
	final EuclideanSpace space=(EuclideanSpace) SpaceGenerator.getInstance().getFiniteDimensionalVectorSpace(dim);
	
	final Vector nul=space.nullVec();
	
	final StringBuilder strings=new StringBuilder();
	
	/**
	 * Test method for {@link definitions.structures.abstr.Vector#getDim()}.
	 */
	@Test
	public void testGetDim() {
		strings.append("Dimension test.\r");
		strings.append("getDim()="+nul.getDim()+"\r");
		strings.append("dim="+dim+"\r");
		Assert.assertTrue(nul.getDim()==dim);
		System.out.println(strings);
	}

	/**
	 * Test method for {@link definitions.structures.abstr.Vector#elementOf(definitions.structures.abstr.VectorSpace)}.
	 */
	@Test
	public void testElementOf() {
		Assert.assertTrue(nul.elementOf(space));
	}

	/**
	 * Test method for {@link definitions.structures.abstr.Vector#equals(definitions.structures.abstr.Vector)}.
	 */
//	@Test
//	public void testEquals() {
//		Vector nullVec=new Tuple(new double[dim]);
//		Assert.assertTrue(nul.equals(nullVec));
//	}

	/**
	 * Test method for {@link definitions.structures.abstr.Vector#getCoordinates()}.
	 */
	@Test
	public void testGetCoordinates() {
		final Map<Vector, Scalar> coordinates=nul.getCoordinates();
		for (Vector vec:nul.getCoordinates().keySet()) {
			Assert.assertTrue(coordinates.get(vec).getValue()==0.);
		}
	}

	/**
	 * Test method for {@link definitions.structures.abstr.Vector#getGenericCoordinates()}.
	 */
	@Test
	public void testGetGenericCoordinates() {
		testGetCoordinates() ;
	}

}
