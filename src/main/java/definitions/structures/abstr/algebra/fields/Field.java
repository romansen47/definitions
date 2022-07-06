package definitions.structures.abstr.algebra.fields;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.groups.Group;
import definitions.structures.abstr.algebra.rings.CommutativeRing;
import definitions.structures.abstr.algebra.rings.Domain;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.vectorspaces.EuclideanAlgebra;
import definitions.structures.abstr.vectorspaces.LinearMappingsSpace;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.FiniteVectorMethods;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.mappings.FiniteDimensionalHomomorphism;
import definitions.structures.euclidean.mappings.impl.FiniteDimensionalLinearMapping;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

/**
 *
 * @author roman
 *
 *         A field is a commutative ring where the maltiplicative semi group is
 *         a group
 */
public interface Field extends CommutativeRing, Domain, EuclideanAlgebra, FieldMethods {

	@Override
	default FieldElement getNeutralElement() {
		return (FieldElement) EuclideanAlgebra.super.getNeutralElement();
	}

	/**
	 * actually not a native method of a field. to be removed in future
	 * 
	 * @param value the input scalar
	 * @return the conjugated scalar
	 */
	@Deprecated
	default Scalar conjugate(Scalar value) {
		return value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default boolean divides(final Element devisor, final Element devident) {
		return isUnit(devisor);
	}

	/**
	 * by default the characteristic is 0. we also have a FiniteField-class
	 * 
	 * @return the characteristic
	 */
	default int getCharacteristic() {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default Group getMuliplicativeMonoid() {

		final Vector newOne = getOne();
		final Integer newOrder = getOrder();

		return new Group() {

			@Override
			public Element getInverseElement(final Element element) {
				return Field.this.getMultiplicativeInverseElement((Scalar) element);
			}

			@Override
			public Element getNeutralElement() {
				return newOne;
			}

			@Override
			public Integer getOrder() {
				if (newOrder == null) {
					return newOrder;
				}
				return newOrder - 1;
			}

			@Override
			public Element operation(final Element first, final Element second) {
				return Field.this.product((Scalar) first, (Scalar) second);
			}

			@Override
			public String toString() {
				return "Multiplicative group of " + Field.this.getField().toString();
			}
		};
	}

	@Override
	default FieldElement getOne() {
		return (FieldElement) getMuliplicativeMonoid().getNeutralElement();
	}

	@Override
	default FieldElement getInverseElement(Element element) {
		if (element.equals(getOne())) {
			return (FieldElement) getMinusOne();
		} else if (element.equals(getZero())) {
			return (FieldElement) getZero();
		}
		return (FieldElement) EuclideanAlgebra.super.getInverseElement(element);
	}

	/**
	 * Should return field of rational numbers in infinite case by default.
	 *
	 * @return the prime field.
	 */
	default PrimeField getPrimeField() {
		return null;
	}

	/**
	 * getter for zero scalar
	 * 
	 * @return
	 */
	default Scalar getZero() {
		return (Scalar) nullVec();
	}

	default Vector getMultiplicativeInverseElement(final Vector factor) {
		final Field field = this;
		final VectorSpace multLinMaps = new LinearMappingsSpace() {

			final Field source = field;
			final Field target = field;

			@Override
			public Field getField() {
				return field;
			}

			@Override
			public EuclideanSpace getSource() {
				return source;
			}

			@Override
			public EuclideanSpace getTarget() {
				return target;
			}

		};

		FiniteDimensionalHomomorphism hom = new FiniteDimensionalLinearMapping(this, this) {

			private static final long serialVersionUID = 1L;

			@Override
			public Vector get(final Element vec) {
				return ((Field) getTarget()).nullVec();
			}

			@Override
			public Scalar[][] getGenericMatrix() {
				final Scalar[][] mat = new Scalar[((Field) target).getDim()][((Field) source).getDim()];
				for (int i = 0; i < mat.length; i++) {
					for (int j = 0; j < mat.length; j++) {
						Scalar scalar = i == j ? getOne() : (Scalar) getZero();
						mat[i][j] = scalar;
					}
				}
				return mat;
			}

			@Override
			public Map<Vector, Map<Vector, Scalar>> getLinearity() {
				final Map<Vector, Map<Vector, Scalar>> coord = new ConcurrentHashMap<>();
				for (final Vector vec : ((EuclideanSpace) getSource()).genericBaseToList()) {
					coord.put(vec, ((FiniteVectorMethods) ((Field) target).nullVec()).getCoordinates());
				}
				return coord;
			}
		};
		for (final Vector vec : genericBaseToList()) {
			final Vector tmp = getMultiplicationMatrix().get(vec);
			hom = (FiniteDimensionalHomomorphism) multLinMaps.addition(hom,
					multLinMaps.stretch(tmp, ((FiniteVectorMethods) factor).getCoordinates().get(vec)));
		}
		return hom.solve(getOne());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default boolean isIrreducible(final Element element) {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default boolean isPrimeElement(final Element element) {
		return !isUnit(element);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default boolean isUnit(final Element element) {
		return !element.equals(getZero());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default FieldElement operation(final Element first, final Element second) {
		return (FieldElement) EuclideanAlgebra.super.operation(first, second);
	}

}
