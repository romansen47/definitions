/**
 * 
 */
package definitions.structures.abstr;

import java.util.Map;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import definitions.structures.abstr.Vector;
import definitions.structures.finitedimensional.vectors.impl.Tuple;
import definitions.structures.finitedimensional.vectorspaces.EuclideanSpace;
import definitions.structures.finitedimensional.vectorspaces.impl.SpaceGenerator;

/**
 * @author RoManski
 *
 */
public class VectorTest {

	final int dim=Math.abs(new Random().nextInt(5000));
	
	final EuclideanSpace space=SpaceGenerator.getInstance().getFiniteDimensionalVectorSpace(dim);
	
	final Vector nul=space.nullVec();
	
	final StringBuilder strings=new StringBuilder();
	
	/**
	 * Test method for {@link definitions.structures.abstr.Vector#getDim()}.
	 */
	@Test
	public void testGetDim() {
		strings.append("Dimension test.");
		strings.append("getDim()="+nul.getDim());
		strings.append("dim="+dim);
		Assert.assertTrue(nul.getDim()==dim);
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
	@Test
	public void testEquals() {
		Vector nullVec=new Tuple(new double[dim]);
		Assert.assertTrue(nul.equals(nullVec));
	}

	/**
	 * Test method for {@link definitions.structures.abstr.Vector#getCoordinates()}.
	 */
	@Test
	public void testGetCoordinates() {
		final Map<Vector, Double> coordinates=nul.getCoordinates();
		for (Vector vec:nul.getCoordinates().keySet()) {
			Assert.assertTrue(coordinates.get(vec)==0);
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
