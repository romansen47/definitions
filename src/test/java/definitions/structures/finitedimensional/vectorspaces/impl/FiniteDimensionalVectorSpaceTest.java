package definitions.structures.finitedimensional.vectorspaces.impl;


import org.junit.Assert;
import org.junit.Test;
import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.finitedimensional.vectorspaces.impl.SpaceGenerator;

/**
 * @author RoManski
 *
 */
public class FiniteDimensionalVectorSpaceTest {

	final int dim = 1000;
	final VectorSpace space = SpaceGenerator.getInstance().getFiniteDimensionalVectorSpace(dim);
	final Vector nul = space.nullVec();
	private double factor = 1.e11;

	/**
	 * Test method for
	 * {@link definitions.structures.abstr.VectorSpace#contains(definitions.structures.abstr.Vector)}.
	 */
	@Test
	public void testContains() {
		Assert.assertTrue(space.contains(nul));
	}

	/**
	 * Test method for {@link definitions.structures.abstr.VectorSpace#nullVec()}.
	 */
	@Test
	public void testNullVec() {
		Assert.assertTrue(space.nullVec().equals(nul));
	}

	/**
	 * Test method for
	 * {@link definitions.structures.abstr.VectorSpace#add(definitions.structures.abstr.Vector, definitions.structures.abstr.Vector)}.
	 */
	@Test
	public void testAdd() {
		Assert.assertTrue(space.add(space.nullVec(),nul).equals(nul));
	}

	/**
	 * Test method for
	 * {@link definitions.structures.abstr.VectorSpace#stretch(definitions.structures.abstr.Vector, double)}.
	 */
	@Test
	public void testStretch() {
		Assert.assertTrue(space.stretch(nul,factor).equals(nul));
	}

}
