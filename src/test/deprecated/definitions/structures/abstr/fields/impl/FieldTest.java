package definitions.structures.abstr.fields.impl;

import org.junit.Test;

import definitions.structures.abstr.VectorSpaceTest;
import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.impl.ComplexPlane;
import definitions.structures.abstr.algebra.fields.impl.QuaternionSpace;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Complex;
import definitions.structures.abstr.algebra.fields.scalars.impl.Quaternion;
import definitions.structures.abstr.algebra.monoids.Monoid;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Vector;

public class FieldTest extends VectorSpaceTest {

	final Field field = (Field) ComplexPlane.getInstance();
	final Field field2 = (Field) this.getSpace();

	@Override
	public Scalar getFactor() {
		return RealLine.getInstance().get(0);
	}

	@Override
	public VectorSpace getSpace() {
		return QuaternionSpace.getInstance();
	}

	@Override
	public Vector getVec1() {
		return new Quaternion(1, 1, 1, 1);
	}

	@Override
	public Vector getVec2() {
		return new Quaternion(1, 1, 1, 1).getInverse();
	}

	@Test
	public void multiplicationTest() {
		((Field) this.getSpace()).show((Field) this.getSpace());
	}

	public void runTests() {
		this.test();
		this.multiplicationTest();
		this.getSpace();
		this.getVec1();
		this.getVec2();
		this.getFactor();
	}

	@Test
	public void test() {
		final Vector test = new Complex(1, 1);
		final Vector test2 = new Quaternion(1, 1, 1, 1);
		final Vector ans = this.field.inverse(test);
		final Vector ans2 = this.field2.inverse(test2);
		final Monoid m = this.field.getMuliplicativeMonoid();
	}

}
