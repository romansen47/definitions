/**
 * 
 */
package definitions.structures.field.impl;

import java.util.ArrayList;
import java.util.List;

import definitions.structures.abstr.Homomorphism;
import definitions.structures.abstr.RealSpace;
import definitions.structures.abstr.Scalar;
import definitions.structures.abstr.Vector;
import definitions.structures.field.EuclideanField;
import definitions.structures.finitedimensional.vectorspaces.impl.FiniteDimensionalVectorSpace;

/**
 * @author RoManski
 *
 */
public class QuaternionSpace extends FiniteDimensionalVectorSpace implements EuclideanField, RealSpace {

	/**
	 * the base.
	 */
	final private List<Vector> base = new ArrayList<>();

	/**
	 * the dimension.
	 */
	private final int dim = 4;

	public QuaternionSpace() {
//		final EuclideanSpace coordinatesSpace=SpaceGenerator.getInstance().getFiniteDimensionalVectorSpace(4);
//		final Vector unit=new Tuple();
	}

	@Override
	public Homomorphism getMultiplicationMatrix() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMultiplicationMatrix(Homomorphism multiplicationMatrix) {
		// TODO Auto-generated method stub

	}

	@Override
	public Vector getOne() {
		// TODO Auto-generated method stub
		return this.base.get(0);
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
	public Vector inverse(Vector factor) {
		// TODO Auto-generated method stub
		return null;
	}

}
