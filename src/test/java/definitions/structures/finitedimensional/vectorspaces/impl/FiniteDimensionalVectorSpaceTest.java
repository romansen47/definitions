package definitions.structures.finitedimensional.vectorspaces.impl;

import org.junit.Assert;
import org.junit.Test;

import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.field.scalar.impl.Real;

/**
 * @author RoManski
 *
 */
public class FiniteDimensionalVectorSpaceTest {

	final int dim = 2;
	final VectorSpace space = SpaceGenerator.getInstance().getFiniteDimensionalVectorSpace(this.dim);
	final Vector nul = this.space.nullVec();
	private final double factor = 1.e11;

	/**
	 * Test method for
	 * {@link definitions.structures.abstr.VectorSpace#contains(definitions.structures.abstr.Vector)}.
	 */
	@Test
	public void testContains() {
		Assert.assertTrue(this.space.contains(this.nul));
	}

	/**
	 * Test method for {@link definitions.structures.abstr.VectorSpace#nullVec()}.
	 */
	@Test
	public void testNullVec() {
		Assert.assertTrue(this.space.nullVec().equals(this.nul));
	}

	/**
	 * Test method for
	 * {@link definitions.structures.abstr.VectorSpace#add(definitions.structures.abstr.Vector, definitions.structures.abstr.Vector)}.
	 */
	@Test
	public void testAdd() {
		Assert.assertTrue(this.space.add(this.space.nullVec(), this.nul).equals(this.nul));
	}

	/**
	 * Test method for
	 * {@link definitions.structures.abstr.VectorSpace#stretch(definitions.structures.abstr.Vector, double)}.
	 */
	@Test
	public void testStretch() {
		Assert.assertTrue(this.space.stretch(this.nul, new Real(this.factor)).equals(this.nul));
	}

}
