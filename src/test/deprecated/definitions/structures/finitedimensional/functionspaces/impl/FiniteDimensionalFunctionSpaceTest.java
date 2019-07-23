/**
 * 
 */
package definitions.structures.finitedimensional.functionspaces.impl;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.NormedSpace;
import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.finitedimensional.real.functionspaces.EuclideanFunctionSpace;
import definitions.structures.finitedimensional.real.vectorspaces.EuclideanSpace;
import definitions.structures.finitedimensional.real.vectorspaces.impl.SpaceGenerator;

/**
 * @author ro
 *
 */
@SuppressWarnings("unused")
public class FiniteDimensionalFunctionSpaceTest {

	static VectorSpace trigonometricSpace;
	final static int degree = 5;

	static Vector fun1;
	static Vector fun2;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		trigonometricSpace = SpaceGenerator.getInstance().getTrigonometricSpace(degree, 1);
		fun1 = ((EuclideanSpace) trigonometricSpace).genericBaseToList().get(1);
		fun2 = ((EuclideanSpace) trigonometricSpace).genericBaseToList().get(2);
	}

	/**
	 * Test method for
	 * {@link definitions.structures.finitedimensional.real.functionspaces.impl.FiniteDimensionalFunctionSpace#innerProduct(definitions.structures.abstr.Vector, definitions.structures.abstr.Vector)}.
	 */
	@Test
	public final void testProduct() {
		((EuclideanSpace) trigonometricSpace).show();
	}

	/**
	 * Test method for
	 * {@link definitions.structures.finitedimensional.real.functionspaces.impl.FiniteDimensionalFunctionSpace#nullVec()}.
	 */
	@Test
	public final void testNullVec() {
		final double d = ((NormedSpace) trigonometricSpace).norm(trigonometricSpace.nullVec());
//		Assert.assertEquals(0.,d);
	}

	/**
	 * Test method for
	 * {@link definitions.structures.finitedimensional.real.functionspaces.impl.FiniteDimensionalFunctionSpace#getOrthonormalBase(java.util.List)}.
	 */
	@SuppressWarnings({ "unused" })
	@Test
	public final void testGetOrthonormalBase() {
		final List<Vector> ortho = ((EuclideanFunctionSpace) trigonometricSpace)
				.getOrthonormalBase(((EuclideanFunctionSpace) trigonometricSpace).genericBaseToList());
		Assert.assertTrue(true);
	}

//	/**
//	 * Test method for {@link definitions.structures.finitedimensional.functionspaces.impl.FiniteDimensionalFunctionSpace#FiniteDimensionalFunctionSpace()}.
//	 */
//	@Test
//	public final void testFiniteDimensionalFunctionSpace() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	/**
//	 * Test method for {@link definitions.structures.finitedimensional.functionspaces.impl.FiniteDimensionalFunctionSpace#FiniteDimensionalFunctionSpace(java.util.List, double, double)}.
//	 */
//	@Test
//	public final void testFiniteDimensionalFunctionSpaceListOfVectorDoubleDouble() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	/**
//	 * Test method for {@link definitions.structures.finitedimensional.functionspaces.impl.FiniteDimensionalFunctionSpace#getInterval()}.
//	 */
//	@Test
//	public final void testGetInterval() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	/**
//	 * Test method for {@link definitions.structures.finitedimensional.functionspaces.impl.FiniteDimensionalFunctionSpace#getEpsilon()}.
//	 */
//	@Test
//	public final void testGetEpsilon() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	/**
//	 * Test method for {@link definitions.structures.finitedimensional.functionspaces.impl.FiniteDimensionalFunctionSpace#stretch(definitions.structures.abstr.Vector, double)}.
//	 */
//	@Test
//	public final void testStretch() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	/**
//	 * Test method for {@link definitions.structures.finitedimensional.functionspaces.impl.FiniteDimensionalFunctionSpace#normalize(definitions.structures.abstr.Vector)}.
//	 */
//	@Test
//	public final void testNormalize() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	/**
//	 * Test method for {@link definitions.structures.finitedimensional.functionspaces.impl.FiniteDimensionalFunctionSpace#getLeft()}.
//	 */
//	@Test
//	public final void testGetLeft() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	/**
//	 * Test method for {@link definitions.structures.finitedimensional.functionspaces.impl.FiniteDimensionalFunctionSpace#getRight()}.
//	 */
//	@Test
//	public final void testGetRight() {
//		fail("Not yet implemented"); // TODO
//	}

}
