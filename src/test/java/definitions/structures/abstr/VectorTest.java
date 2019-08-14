/**
 * 
 */
package definitions.structures.abstr;

import java.util.Map;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;

/**
 * @author RoManski
 *
 */
public class VectorTest {

	final int dim = Math.abs(new Random().nextInt(10));

	final EuclideanSpace space = (EuclideanSpace) SpaceGenerator.getInstance()
			.getFiniteDimensionalVectorSpace(this.dim);

	final Vector nul = this.space.nullVec();

//	final StringBuilder strings = new StringBuilder();

	/**
	 * Test method for
	 * {@link definitions.structures.abstr.vectorspaces.vectors.Vector#getDim()}.
	 */
	@Test
	public void testGetDim() {
//		this.strings.append("Dimension test.\r");
//		this.strings.append("getDim()=" + this.nul.getDim() + "\r");
//		this.strings.append("dim=" + this.dim + "\r");
		Assert.assertTrue(this.nul.getDim() == this.dim);
//		System.out.println(this.strings);
	}

	/**
	 * Test method for
	 * {@link definitions.structures.abstr.vectorspaces.vectors.Vector#elementOf(definitions.structures.abstr.VectorSpace)}.
	 */
	@Test
	public void testElementOf() {
		Assert.assertTrue(this.nul.elementOf(this.space));
	}

	/**
	 * Test method for
	 * {@link definitions.structures.abstr.Vector#equals(definitions.structures.abstr.Vector)}.
	 */
//	@Test
//	public void testEquals() {
//		Vector nullVec=new Tuple(new double[dim]);
//		Assert.assertTrue(nul.equals(nullVec));
//	}

	/**
	 * Test method for
	 * {@link definitions.structures.abstr.vectorspaces.vectors.Vector#getCoordinates()}.
	 */
	@Test
	public void testGetCoordinates() {
		final Map<Vector, Scalar> coordinates = this.nul.getCoordinates();
		for (final Vector vec : this.nul.getCoordinates().keySet()) {
			Assert.assertTrue(coordinates.get(vec).getValue() == 0.);
		}
	}

	/**
	 * Test method for
	 * {@link definitions.structures.abstr.vectorspaces.vectors.Vector#getGenericCoordinates()}.
	 */
	@Test
	public void testGetGenericCoordinates() {
		this.testGetCoordinates();
	}

}
