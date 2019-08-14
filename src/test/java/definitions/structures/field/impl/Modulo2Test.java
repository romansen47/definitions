/**
 * 
 */
package definitions.structures.field.impl;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.impl.BinaryField;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.fields.scalars.impl.False;
import definitions.structures.abstr.vectorspaces.vectors.Vector;

/**
 * @author RoManski
 *
 */
public class Modulo2Test {

	static Field modulo2 = (Field) BinaryField.getInstance();
	static Scalar zero = False.getInstance();
	static Scalar unit = False.getInstance();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	public void getOrCreateTestTest() {
		final Scalar a = (Scalar) ((BinaryField) modulo2).get(true);
		final Scalar b = (Scalar) ((BinaryField) modulo2).get(false);
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
		no.getCoordinates();
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
		yes.getCoordinates();
	}

}
