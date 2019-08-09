/**
 * 
 */
package definitions.structures.field.impl;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.Vector;
import definitions.structures.field.scalar.impl.Complex;
import definitions.structures.field.scalar.impl.Real;
import definitions.structures.finitedimensional.real.vectorspaces.EuclideanSpace;

/**
 * @author ro
 *
 */
public class ComplexPlaneTest {

	final ComplexPlane complexPlane = (ComplexPlane) ComplexPlane.getInstance();
	final Vector one = complexPlane.getOne();
	final Vector im = complexPlane.getI();

	Vector reMin = new Complex(-1, 0);
	Vector imMin = new Complex(0, -1);

	/**
	 * Test method for
	 * {@link definitions.structures.field.impl.ComplexPlane#nullVec()}.
	 */
	@Test
	public void testNullVec() {
		boolean ans = true;
		Complex nul = (Complex) complexPlane.nullVec();
		if (nul.getReal().getValue() != 0.0 || nul.getImag().getValue() != 0.0) {
			ans = false;
		}
		Assert.assertTrue(ans);
	}

	/**
	 * Test method for
	 * {@link definitions.structures.field.impl.ComplexPlane#getI()}.
	 */
	@Test
	public void testGetI() {
		Assert.assertTrue(complexPlane.getI().equals(new Complex(0, 1)));
	}

	/**
	 * Test method for
	 * {@link definitions.structures.field.impl.ComplexPlane#product(definitions.structures.abstr.Vector, definitions.structures.abstr.Vector)}.
	 */
	@Test
	public void testProduct() {
		Assert.assertTrue(complexPlane.product(one, one).equals(one));
		Assert.assertTrue(complexPlane.product(one, im).equals(im));
		Assert.assertTrue(complexPlane.product(im, one).equals(im));
		Assert.assertTrue(complexPlane.product(im, im).equals(new Complex(-1, 0)));
	}

	/**
	 * Test method for
	 * {@link definitions.structures.field.impl.ComplexPlane#add(definitions.structures.abstr.Vector, definitions.structures.abstr.Vector)}.
	 */
	@Test
	public void testAdd() {
		Assert.assertTrue(complexPlane.add(one, reMin).equals(complexPlane.nullVec()));
		Assert.assertTrue(complexPlane.add(im, imMin).equals(complexPlane.nullVec()));
	}

	/**
	 * Test method for
	 * {@link definitions.structures.field.impl.ComplexPlane#stretch(definitions.structures.abstr.Vector, definitions.structures.abstr.Scalar)}.
	 */
	@Test
	public void testStretch() {
		Assert.assertTrue(complexPlane.stretch(one, new Real(5)).equals(new Complex(5, 0)));
		Assert.assertTrue(complexPlane.stretch(im, new Real(-5)).equals(new Complex(0,-5)));
		Assert.assertTrue(complexPlane.stretch(one, RealLine.getInstance().getZero()).equals(complexPlane.nullVec()));
	}

	/**
	 * Test method for
	 * {@link definitions.structures.field.impl.ComplexPlane#inverse(definitions.structures.abstr.Vector)}.
	 */
	@Test
	public void testInverse() {
		Assert.assertTrue(complexPlane.inverse(im).equals(imMin));
	}

	/**
	 * Test method for
	 * {@link definitions.structures.field.impl.ComplexPlane#getOne()}.
	 */
	@Test
	public void testGetOne() {
Assert.assertTrue(complexPlane.getOne().equals(new Complex(1,0)));
	}

}
