package definitions.structures.abstr.fields;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.VectorSpaceTest;
import definitions.structures.abstr.fields.impl.ComplexPlane;
import definitions.structures.abstr.fields.impl.QuaternionSpace;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.fields.scalars.impl.Complex;
import definitions.structures.abstr.fields.scalars.impl.Quaternion;
import definitions.structures.abstr.fields.scalars.impl.Real;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Vector;

public class FieldTest extends VectorSpaceTest{

	final Field field=(Field) ComplexPlane.getInstance();
	final Field field2=(Field) getSpace();
	
	@Test
	public void test() {
		final Vector test=new Complex(1,1);
		final Vector test2=new Quaternion(1,1,1,1);
		final Vector ans=field.inverse(test);
		final Vector ans2=field2.inverse(test2);
	}

	@Test
	public void multiplicationTest(){
		((Field)getSpace()).show((Field)getSpace());
	}
	
	@Override
	public VectorSpace getSpace() {
		return (Field) QuaternionSpace.getInstance();
	}

	@Override
	public Vector getVec1() {
		return new Quaternion(1,1,1,1);
	}

	@Override
	public Vector getVec2() {
		return new Quaternion(1,1,1,1).getInverse();
	}

	@Override
	public Scalar getFactor() {
		return new Real(0);
	}

}
