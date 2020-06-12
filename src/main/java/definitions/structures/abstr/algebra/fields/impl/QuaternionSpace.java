/**
 * 
 */
package definitions.structures.abstr.algebra.fields.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.PrimeField;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Quaternion;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.mappings.VectorSpaceHomomorphism;
import definitions.structures.abstr.vectorspaces.RealSpace;
import definitions.structures.abstr.vectorspaces.vectors.FiniteVectorMethods;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.mappings.impl.MappingGenerator;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.impl.FiniteDimensionalVectorSpace;

/**
 * 
 * @author ro
 *
 *         Implementation of the field of quaternion numbers as a singleton
 *         class.
 */
public class QuaternionSpace extends FiniteDimensionalVectorSpace implements Field, RealSpace {

	private static final long serialVersionUID = -5960215336667005490L;

	private static EuclideanSpace instance;

	public static EuclideanSpace getInstance() {
		if (instance == null) {
			instance = new QuaternionSpace();
		}
		return instance;
	}

	public static void setInstance(final EuclideanSpace space) {
		instance = space;
	}

	private final Quaternion zero;
	private final Quaternion one;
	private final Quaternion i;
	private final Quaternion j;
	private final Quaternion k;

	private Map<Vector, VectorSpaceHomomorphism> multiplicationMatrix = null;

	public QuaternionSpace() {
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
		this.assignOrthonormalCoordinates(this.base, this.getField());
	}

	@Override
	public Quaternion addition(final Vector vec1, final Vector vec2) {
		if (vec1 == this.nullVec()) {
			return (Quaternion) vec2;
		}
		if (vec2 == this.nullVec()) {
			return (Quaternion) vec1;
		}
		final Vector ans = super.addition(vec1, vec2);
		return new Quaternion(((FiniteVectorMethods) ans).getCoordinates().get(this.one),
				((FiniteVectorMethods) ans).getCoordinates().get(this.i),
				((FiniteVectorMethods) ans).getCoordinates().get(this.j),
				((FiniteVectorMethods) ans).getCoordinates().get(this.k));
	}

	@Override
	public Scalar conjugate(final Scalar value) {
		final Quaternion v = (Quaternion) value;
		return new Quaternion(v.getReal().getDoubleValue(), -v.getI().getDoubleValue(), -v.getJ().getDoubleValue(), -v.getK().getDoubleValue());
	}

	@Override
	public boolean contains(final Vector vec) {
		return vec instanceof Quaternion || vec == this.zero || vec == this.one || vec == null;
	}

	@Override
	public Scalar get(final double value) {
		return new Quaternion(value, 0, 0, 0);
	}

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

	/**
	 * @return the multiplicationMatrix
	 */
	@Override
	public Map<Vector, VectorSpaceHomomorphism> getMultiplicationMatrix() {
		if (this.multiplicationMatrix == null) {

			final Scalar realOne = RealLine.getInstance().getOne();
			final Scalar realZero = RealLine.getInstance().getZero();
			final Scalar neg = RealLine.getInstance().get(-1);

			final Scalar[][] oneMat = new Scalar[][] { { realOne, realZero, realZero, realZero },
					{ realZero, realOne, realZero, realZero }, { realZero, realZero, realOne, realZero },
					{ realZero, realZero, realZero, realOne } };

			final VectorSpaceHomomorphism oneHom = MappingGenerator.getInstance()
					.getFiniteDimensionalLinearMapping(this, this, oneMat);

			final Scalar[][] iMat = new Scalar[][] { { realZero, neg, realZero, realZero },
					{ realOne, realZero, realZero, realZero }, { realZero, realZero, realZero, realOne },
					{ realZero, realZero, neg, realZero } };

			final VectorSpaceHomomorphism iHom = MappingGenerator.getInstance().getFiniteDimensionalLinearMapping(this,
					this, iMat);

			final Scalar[][] jMat = new Scalar[][] { { realZero, realZero, neg, realZero },
					{ realZero, realZero, realZero, neg }, { realOne, realZero, realZero, realZero },
					{ realZero, realOne, realZero, realZero } };

			final VectorSpaceHomomorphism jHom = MappingGenerator.getInstance().getFiniteDimensionalLinearMapping(this,
					this, jMat);

			final Scalar[][] kMat = new Scalar[][] { { realZero, realZero, realZero, neg },
					{ realZero, realZero, realOne, realZero }, { realZero, neg, realZero, realZero },
					{ realOne, realZero, realZero, realZero } };

			final VectorSpaceHomomorphism kHom = MappingGenerator.getInstance().getFiniteDimensionalLinearMapping(this,
					this, kMat);

			final Map<Vector, VectorSpaceHomomorphism> newMap = new HashMap<>();

			newMap.put(this.one, oneHom);
			newMap.put(this.i, iHom);
			newMap.put(this.j, jHom);
			newMap.put(this.k, kHom);

			this.setMultiplicationMatrix(newMap);
		}
		return this.multiplicationMatrix;
	}

	@Override
	public Quaternion getNeutralElement() {
		return (Quaternion) super.getNeutralElement();
	}

	@Override
	public Quaternion getOne() {
		return this.one;
	}

	@Override
	public Integer getOrder() {
		return null;
	}

	@Override
	public boolean isIrreducible(final Element element) {
		return Field.super.isIrreducible(element);
	}

	@Override
	public Quaternion nullVec() {
		return this.zero;
	}

	@Override
	public Quaternion product(final Vector vec1, final Vector vec2) {
//		return stretch(vec1,(Scalar)vec2);
		return (Quaternion) Field.super.product(vec1, vec2);
		
	}
 
	public QuaternionSpace quaternionSpace() {
		return new QuaternionSpace();
	}

	/**
	 * @param multiplicationMatrix the multiplicationMatrix to set
	 */
	@Override
	public void setMultiplicationMatrix(final Map<Vector, VectorSpaceHomomorphism> multiplicationMatrix) {
		this.multiplicationMatrix = multiplicationMatrix;
	}

	@Override
	public Quaternion stretch(final Vector vec1, final Scalar r) {
		if (r instanceof Quaternion) {
			return (Quaternion) super.innerProduct(vec1,r);
//			return this.product(vec1, r);
		}
		if (r == this.getField().getOne()) {
			return (Quaternion) vec1;
		}
		if (r == this.getField().getZero()) {
			return this.nullVec();
		}
		final Vector ans = super.stretch(vec1, r);
		return new Quaternion(((FiniteVectorMethods) ans).getCoordinates().get(this.one),
				((FiniteVectorMethods) ans).getCoordinates().get(this.i),
				((FiniteVectorMethods) ans).getCoordinates().get(this.j),
				((FiniteVectorMethods) ans).getCoordinates().get(this.k));
	}

//	@Override
//	public String toString() {
//		return "Custom field of quaternions as a 4-dimensional real vector space.";
//	}

	@Override
	public Element getMinusOne() { 
		return new Quaternion(-1,0,0,0);
	}

	@Override
	public PrimeField getPrimeField() {
		return Generator.getInstance().getGroupGenerator().getRationals();
	}

}
