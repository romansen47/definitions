/**
 * 
 */
package definitions.structures.abstr.fields.impl;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.groups.impl.BinaryField;
import definitions.structures.abstr.algebra.rings.impl.FiniteResidueClassRing;
import definitions.structures.abstr.vectorspaces.vectors.FiniteVectorMethods;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.abstr.vectorspaces.vectors.VectorTest;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

/**
 * @author RoManski
 *
 */
public class FiniteFieldTest extends VectorTest {

	static Field modulo2 = BinaryField.getInstance();
	static Scalar zero =  (Scalar) modulo2.nullVec();
	static Scalar unit = (Scalar) ((FiniteResidueClassRing) modulo2).getGenerator();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	public void getOrCreateTestTest() {
		final Scalar a = (Scalar) ((BinaryField) modulo2).get(0);
		final Scalar b = (Scalar) ((BinaryField) modulo2).get(1);
	}

	@Test
	public void getFieldTest() {
		final Field field = modulo2.getField();
	}

	@Test
	public void containsTest() {
		final boolean x = modulo2.contains(zero);
		final boolean y = modulo2.contains(unit);
	}

	@Test
	
	public void nullVecTest() {
		final Vector no = modulo2.nullVec();
		((FiniteVectorMethods) no).getCoordinates();
	}

	@Test
	
	public void addTest() {
		final Vector sum1 = modulo2.add(modulo2.getOne(), modulo2.getOne());
		final Vector sum2 = modulo2.add(modulo2.getOne(), modulo2.getZero());
		final Vector sum3 = modulo2.add(modulo2.getZero(), modulo2.getOne());
		final Vector sum4 = modulo2.add(modulo2.getZero(), modulo2.getZero());
	}

	@Test
	
	public void stretchTest() {
		final Vector stretch1 = modulo2.stretch(modulo2.getOne(), (Scalar) modulo2.getOne());
		final Vector stretch2 = modulo2.stretch(modulo2.getOne(), (Scalar) modulo2.getZero());
		final Vector stretch3 = modulo2.stretch(modulo2.getZero(), (Scalar) modulo2.getOne());
		final Vector stretch4 = modulo2.stretch(modulo2.getZero(), (Scalar) modulo2.getZero());
	}

	@Test
	
	public void inverseTest() {
		final Vector inv1 = modulo2.inverse(modulo2.getOne());
		final Vector inv2 = modulo2.inverse(modulo2.getZero());
	}

	@Test
	
	public void getOneTest() {
		final Vector yes = modulo2.getOne();
		((FiniteVectorMethods) yes).getCoordinates();
	}

	@Override
	public Vector getVector() {
		return unit;
	}

	@Override
	public EuclideanSpace getSpace() {
		return modulo2;
	}
}
