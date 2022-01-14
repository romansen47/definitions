/**
 *
 */
package definitions.structures.abstr.algebra.fields.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.FieldElement;
import definitions.structures.abstr.algebra.fields.PrimeField;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Quaternion;
import definitions.structures.abstr.algebra.fields.scalars.impl.Real;
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
		if (QuaternionSpace.instance == null) {
			QuaternionSpace.instance = new QuaternionSpace();
		}
		return QuaternionSpace.instance;
	}

	public static void setInstance(final EuclideanSpace space) {
		QuaternionSpace.instance = space;
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
		dim = 4;
		base = new ArrayList<>();
		one = new Quaternion(realOne, realZero, realZero, realZero);
		zero = new Quaternion(realZero, realZero, realZero, realZero);
		i = new Quaternion(realZero, realOne, realZero, realZero);
		j = new Quaternion(realZero, realZero, realOne, realZero);
		k = new Quaternion(realZero, realZero, realZero, realOne);
		base.add(one);
		base.add(i);
		base.add(j);
		base.add(k);
		assignOrthonormalCoordinates(base, getField());
	}

	@Override
	public Quaternion addition(final Vector vec1, final Vector vec2) {
		if (vec1 == nullVec()) {
			return (Quaternion) vec2;
		}
		if (vec2 == nullVec()) {
			return (Quaternion) vec1;
		}
		final Vector ans = super.addition(vec1, vec2);
		try {
			Scalar test=new Quaternion(((FiniteVectorMethods) ans).getCoordinates().get(one),
					((FiniteVectorMethods) ans).getCoordinates().get(i),
					((FiniteVectorMethods) ans).getCoordinates().get(j),
					((FiniteVectorMethods) ans).getCoordinates().get(k));
		}catch(Exception e) {
			int i=0;
		}
		return new Quaternion(((FiniteVectorMethods) ans).getCoordinates().get(one),
				((FiniteVectorMethods) ans).getCoordinates().get(i),
				((FiniteVectorMethods) ans).getCoordinates().get(j),
				((FiniteVectorMethods) ans).getCoordinates().get(k));
	}

	@Override
	public
	FieldElement getInverseElement(Element element) {
		if (element.equals(getOne())) {
			return (FieldElement) getMinusOne();
		}
		else if (element.equals(getZero())) {
			return (FieldElement) getZero();
		}
		Real r=(Real) ((Quaternion)element).getReal();
		Real i=(Real) ((Quaternion)element).getI();
		Real j=(Real) ((Quaternion)element).getJ();
		Real k=(Real) ((Quaternion)element).getK();
		return new Quaternion(r,i,j,k);
	}

	@Override
	public Scalar conjugate(final Scalar value) {
		Map<Vector, Scalar>  map = value.getCoordinates();
		final Quaternion v = (Quaternion) value;
		return new Quaternion(v.getReal(), getField().getInverseElement(v.getI()),  getField().getInverseElement(v.getJ()),
				getField().getInverseElement(v.getK()));
	}

	@Override
	public boolean contains(final Vector vec) {
		return (vec instanceof Quaternion) || (vec == zero) || (vec == one) || (vec == null);
	}

	@Override
	public Scalar get(final double value) {
		return new Quaternion(value, 0, 0, 0);
	}

	/**
	 * @return the i
	 */
	public Vector getI() {
		return i;
	}

	/**
	 * @return the j
	 */
	public Vector getJ() {
		return j;
	}

	/**
	 * @return the k
	 */
	public Vector getK() {
		return k;
	}

	/**
	 * @return the multiplicationMatrix
	 */
	@Override
	public Map<Vector, VectorSpaceHomomorphism> getMultiplicationMatrix() {
		if (multiplicationMatrix == null) {

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

							newMap.put(one, oneHom);
							newMap.put(i, iHom);
							newMap.put(j, jHom);
							newMap.put(k, kHom);

							setMultiplicationMatrix(newMap);
		}
		return multiplicationMatrix;
	}

	@Override
	public Quaternion getNeutralElement() {
		return (Quaternion) super.getNeutralElement();
	}

	@Override
	public Quaternion getOne() {
		return one;
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
		return zero;
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
		// ??????
		if (r instanceof Quaternion) {
			return (Quaternion) super.innerProduct(vec1, r);
		}
		if (r == getField().getOne()) {
			return (Quaternion) vec1;
		}
		if (r == getField().getZero()) {
			return nullVec();
		}
		final Vector ans = super.stretch(vec1, r);
		return new Quaternion(((FiniteVectorMethods) ans).getCoordinates().get(one),
				((FiniteVectorMethods) ans).getCoordinates().get(i),
				((FiniteVectorMethods) ans).getCoordinates().get(j),
				((FiniteVectorMethods) ans).getCoordinates().get(k));
	}

	@Override
	public Element getMinusOne() {
		return new Quaternion(-1, 0, 0, 0);
	}

	@Override
	public PrimeField getPrimeField() {
		return Generator.getInstance().getGroupGenerator().getRationals();
	}

}
