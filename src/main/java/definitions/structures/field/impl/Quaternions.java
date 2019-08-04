/**
 * 
 */
package definitions.structures.field.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import definitions.structures.abstr.Algebra;
import definitions.structures.abstr.Scalar;
import definitions.structures.abstr.Vector;
import definitions.structures.field.Field;
import definitions.structures.finitedimensional.real.vectorspaces.impl.FiniteDimensionalVectorSpace;

/**
 * @author RoManski
 *
 */
public class Quaternions extends FiniteDimensionalVectorSpace implements Algebra {

	/**
	 * the base.
	 */
	final private List<Vector> base;

	/**
	 * the dimension.
	 */
	private final int dim=4;

	public Quaternions() {
		base=new ArrayList<>();
	}

	@Override
	public Field getField() {
		return RealLine.getInstance();
	}

	@Override
	public boolean contains(Vector vec) {
		return false;
	}

	@Override
	public Vector nullVec() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector add(Vector vec1, Vector vec2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector stretch(Vector vec1, Scalar r) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector product(Vector vec1, Vector vec2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector getOne() {
		// TODO Auto-generated method stub
		return null;
	}

}
