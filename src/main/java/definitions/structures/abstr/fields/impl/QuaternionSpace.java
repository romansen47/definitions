/**
 * 
 */
package definitions.structures.abstr.fields.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.fields.scalars.impl.Complex;
import definitions.structures.abstr.fields.scalars.impl.Quaternion;
import definitions.structures.abstr.fields.scalars.impl.Real;
import definitions.structures.abstr.mappings.Homomorphism;
import definitions.structures.abstr.vectorspaces.RealSpace;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.mappings.impl.MappingGenerator;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.impl.FiniteDimensionalVectorSpace;

/**
 * @author RoManski
 *
 */
public class QuaternionSpace extends FiniteDimensionalVectorSpace implements Field, RealSpace {

	static private EuclideanSpace instance;

	private final Vector zero;
	private final Vector one;
	private final Vector i;
	private final Vector j;
	private final Vector k;

	private Map<Vector, Homomorphism> multiplicationMatrix = null;

	/**
	 * @return the i
	 */
	
	public Vector getI() {
		return this.i;
	}
	/**
	 * @return the j
	 */
	public Vector getJ() {
		return this.j;
	}

	/**
	 * @return the k
	 */
	public Vector getK() {
		return this.k;
	}

	private QuaternionSpace() {
		super(RealLine.getInstance());

		final Scalar realOne = RealLine.getInstance().getOne();
		final Scalar realZero = RealLine.getInstance().getZero();
		this.dim = 4;
		this.base = new ArrayList<>();
		this.one = new Quaternion(realOne, realZero, realZero, realZero);
		this.zero = new Quaternion(realZero, realZero, realZero, realZero);
		this.i = new Quaternion(realZero, realOne, realZero, realZero);
		this.j = new Quaternion(realZero, realZero, realOne, realZero);
		this.k = new Quaternion(realZero, realZero, realZero, realOne);
		this.base.add(this.one);
		this.base.add(this.i);
		this.base.add(this.j);
		this.base.add(this.k);
	}

	public static EuclideanSpace getInstance() {
		if (instance == null) {
			instance = new QuaternionSpace();
		}
		return instance;
	}
	
	@Override
	public Vector add(Vector vec1,Vector vec2) {
		Vector ans=super.add(vec1,vec2);
		return new Quaternion(ans.getCoordinates().get(one),ans.getCoordinates().get(i),ans.getCoordinates().get(j),ans.getCoordinates().get(k));
	}
	
	@Override
	public Vector stretch(Vector vec1,Scalar r) {
		Vector ans=super.stretch(vec1,r);
		return new Quaternion(ans.getCoordinates().get(one),ans.getCoordinates().get(i),ans.getCoordinates().get(j),ans.getCoordinates().get(k));
	}

	@Override
	public Vector product(Vector vec1, Vector vec2) {
		return Field.super.product(vec1, vec2);
	}

	@Override
	public Vector getOne() {
		return this.one;
	}

	@Override
	public String toString() {
		return "QuaternionSpace as 4-dimensional real vector space";
	}

	@Override
	public boolean contains(Vector vec) {
		return vec instanceof Quaternion || vec == this.zero || vec == this.one || vec == null;
	}

	@Override
	public Vector nullVec() {
		return this.zero;
	}

	/**
	 * @return the multiplicationMatrix
	 */
	@Override
	public Map<Vector, Homomorphism> getMultiplicationMatrix() {
		if (this.multiplicationMatrix == null) {

			final Scalar realOne = RealLine.getInstance().getOne();
			final Scalar realZero = RealLine.getInstance().getZero();
			final Scalar neg = new Real(-1);

			final Scalar[][] oneMat = new Scalar[][] { { realOne, realZero, realZero, realZero },
					{ realZero, realOne, realZero, realZero }, { realZero, realZero, realOne, realZero },
					{ realZero, realZero, realZero, realOne } };

			final Homomorphism oneHom = MappingGenerator.getInstance().getFiniteDimensionalLinearMapping(this, this,
					oneMat);

			final Scalar[][] iMat = new Scalar[][] { { realZero, neg, realZero, realZero },
					{ realOne, realZero, realZero, realZero }, { realZero, realZero, realZero, realOne },
					{ realZero, realZero, neg, realZero } };

			final Homomorphism iHom = MappingGenerator.getInstance().getFiniteDimensionalLinearMapping(this, this,
					iMat);

			final Scalar[][] jMat = new Scalar[][] { { realZero, realZero, neg, realZero },
					{ realZero, realZero, realZero, neg }, { realOne, realZero, realZero, realZero },
					{ realZero, realOne, realZero, realZero } };

			final Homomorphism jHom = MappingGenerator.getInstance().getFiniteDimensionalLinearMapping(this, this,
					jMat);

			final Scalar[][] kMat = new Scalar[][] { { realZero, realZero, realZero, neg },
					{ realZero, realZero, realOne, realZero }, { realZero, neg, realZero, realZero },
					{ realOne, realZero, realZero, realZero } };

			final Homomorphism kHom = MappingGenerator.getInstance().getFiniteDimensionalLinearMapping(this, this,
					kMat);

			final Map<Vector, Homomorphism> newMap = new HashMap<>();

			newMap.put(this.one, oneHom);
			newMap.put(this.i, iHom);
			newMap.put(this.j, jHom);
			newMap.put(this.k, kHom);

			this.setMultiplicationMatrix(newMap);
		}
		return this.multiplicationMatrix;
	}

	/**
	 * @param multiplicationMatrix the multiplicationMatrix to set
	 */
	@Override
	public void setMultiplicationMatrix(Map<Vector, Homomorphism> multiplicationMatrix) {
		this.multiplicationMatrix = multiplicationMatrix;
	}

}