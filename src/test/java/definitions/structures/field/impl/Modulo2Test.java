/**
 * 
 */
package definitions.structures.field.impl;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.Scalar;
import definitions.structures.abstr.Vector;
import definitions.structures.field.Field;
import definitions.structures.field.scalar.impl.False;

/**
 * @author RoManski
 *
 */
public class Modulo2Test {

	static Field modulo2 = (Field) Modulo2.getInstance();
	static Scalar zero = False.getInstance();
	static Scalar unit = False.getInstance();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	public void getOrCreateTestTest() {
		Scalar a = (Scalar) ((Modulo2) modulo2).get(true);
		Scalar b = (Scalar) ((Modulo2) modulo2).get(false);
	}

	@Test
	public void getFieldTest() {
		final Field field = modulo2.getField();
	}

	@Test
	public void containsTest() {
		boolean x = modulo2.contains(zero);
		boolean y = modulo2.contains(unit);
	}

	@Test
	public void nullVecTest() {
		Vector no = modulo2.nullVec();
		no.getCoordinates();
	}

	@Test
	public void addTest() {
		Vector sum1 = modulo2.add(modulo2.getOne(), modulo2.getOne());
		Vector sum2 = modulo2.add(modulo2.getOne(), modulo2.getZero());
		Vector sum3 = modulo2.add(modulo2.getZero(), modulo2.getOne());
		Vector sum4 = modulo2.add(modulo2.getZero(), modulo2.getZero());
	}

	@Test
	public void stretchTest() {
		Vector stretch1 = modulo2.stretch(modulo2.getOne(), (Scalar) modulo2.getOne());
		Vector stretch2 = modulo2.stretch(modulo2.getOne(), (Scalar) modulo2.getZero());
		Vector stretch3 = modulo2.stretch(modulo2.getZero(), (Scalar) modulo2.getOne());
		Vector stretch4 = modulo2.stretch(modulo2.getZero(), (Scalar) modulo2.getZero());
	}

	@Test
	public void inverseTest() {
		Vector inv1 = modulo2.inverse(modulo2.getOne());
		Vector inv2 = modulo2.inverse(modulo2.getZero());
	}

	@Test
	public void getOneTest() {
		Vector yes = modulo2.getOne();
		yes.getCoordinates();
	}

}
