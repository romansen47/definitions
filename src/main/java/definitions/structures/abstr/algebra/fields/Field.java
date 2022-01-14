package definitions.structures.abstr.algebra.fields;

import java.util.HashMap;
import java.util.Map;

import definitions.structures.abstr.algebra.fields.impl.RealLine;
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

		final Group multiplicativeGroup = new Group() {

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

		return multiplicativeGroup;
	}

	@Override
	default FieldElement getOne() {
		return (FieldElement) getMuliplicativeMonoid().getNeutralElement();
	}

	@Override
	default FieldElement getInverseElement(Element element) {
		if (element.equals(getOne())) {
			return (FieldElement) getMinusOne();
		}
		else if (element.equals(getZero())) {
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

	default Vector getZero() {
		return nullVec();
	}

	default Vector getMultiplicativeInverseElement(final Vector factor) {
		final VectorSpace multLinMaps = new LinearMappingsSpace(this, this);
		FiniteDimensionalHomomorphism hom = new FiniteDimensionalLinearMapping(this, this) {

			@Override
			public Vector get(final Element vec) {
				return ((Field) getTarget()).nullVec();
			}

			@Override
			public Scalar[][] getGenericMatrix() {
				final Scalar[][] mat = new Scalar[((Field) target).getDim()][((Field) source).getDim()];
				for (final Scalar[] entry : mat) {
					for (Scalar scalar : entry) {
						scalar = RealLine.getInstance().getZero();
					}
				}
				return mat;
			}

			@Override
			public Map<Vector, Map<Vector, Scalar>> getLinearity() {
				final Map<Vector, Map<Vector, Scalar>> coord = new HashMap<>();
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
