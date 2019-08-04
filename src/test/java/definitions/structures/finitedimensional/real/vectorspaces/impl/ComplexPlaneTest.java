/**
 * 
 */
package definitions.structures.finitedimensional.real.vectorspaces.impl;

import definitions.structures.finitedimensional.real.vectors.impl.Tuple;
import definitions.structures.finitedimensional.real.vectorspaces.EuclideanSpace;
import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.Algebra;
import definitions.structures.abstr.Scalar;
import definitions.structures.abstr.Vector;
import definitions.structures.field.Field;
import definitions.structures.field.impl.ComplexPlane;
import definitions.structures.field.impl.RealLine;
import definitions.structures.field.scalar.impl.Real;

/**
 * @author RoManski
 *
 */
public class ComplexPlaneTest {

	static EuclideanSpace complexPlane;
	static Vector x;
	static Vector y;
	
	static Vector z;
	static Vector inv;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		complexPlane=(EuclideanSpace) ComplexPlane.getInstance();
		x=(complexPlane).genericBaseToList().get(0);
		y=complexPlane.genericBaseToList().get(1);
		
		z=((EuclideanSpace) complexPlane).add(x,y);
		inv=((Field)complexPlane).inverse(z);
		
	}

	/**
	 * Test method for {@link definitions.structures.field.impl.ComplexPlane#product(definitions.structures.abstr.Vector, definitions.structures.abstr.Vector)}.
	 */
	@Test
	public void testProduct() {
		Vector a=((Algebra) complexPlane).product(x, y);
		Vector b=((Algebra) complexPlane).product(z,inv);
		Vector c=((Algebra) complexPlane).product(y,
				inv=((Field)complexPlane).inverse(y));
	}

	/**
	 * Test method for {@link definitions.structures.field.impl.ComplexPlane#add(definitions.structures.abstr.Vector, definitions.structures.abstr.Vector)}.
	 */
	@Test
	public void testAdd() {
		Vector z=complexPlane.add(x, y);
	}

	/**
	 * Test method for {@link definitions.structures.field.impl.ComplexPlane#stretch(definitions.structures.abstr.Vector, definitions.structures.abstr.Scalar)}.
	 */
	@Test
	public void testStretch() {
		Vector z=complexPlane.stretch(x, new Real(2));
	}

	/**
	 * Test method for {@link definitions.structures.field.impl.ComplexPlane#inverse(definitions.structures.abstr.Vector)}.
	 */
	@Test
	public void testInverse() {
		z=z;
	}

}
