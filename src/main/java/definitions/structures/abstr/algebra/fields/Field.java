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
import exceptions.DevisionByZeroException;

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
	 * 
	 * @deprecated how to define in general?
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
		return this.isUnit(devisor);
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

		final Vector newOne = this.getOne();
		final Integer newOrder = this.getOrder();

		return new Group() {

			@Override
			public Element getInverseElement(final Element element) {
				try {
					return Field.this.getMultiplicativeInverseElement((Scalar) element);
				} catch (DevisionByZeroException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	default FieldElement getOne() {
		return (FieldElement) this.getMuliplicativeMonoid().getNeutralElement();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default FieldElement getInverseElement(Element element) {
		if (element.equals(this.getOne())) {
			return (FieldElement) this.getMinusOne();
		} else if (element.equals(this.getZero())) {
			return (FieldElement) this.getZero();
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
	 * @return zero scalar
	 */
	default Scalar getZero() {
		return (Scalar) this.nullVec();
	}

	/**
	 * method to get the inverse element w.r.t. multiplication, if exists
	 *
	 * @param element the element
	 * @return the multiplicative inverse, if exists, null otherwise
	 * @throws DevisionByZeroException
	 */
	default Vector getMultiplicativeInverseElement(final Vector element) throws DevisionByZeroException {
		final Field field = this;
		final VectorSpace multLinMaps = new LinearMappingsSpace() {

			private Field source = field;
			private Field target = field;

			@Override
			public Field getField() {
				return field;
			}

			@Override
			public EuclideanSpace getSource() {
				return this.source;
			}

			@Override
			public EuclideanSpace getTarget() {
				return this.target;
			}

		};

		FiniteDimensionalHomomorphism hom = new FiniteDimensionalLinearMapping(this, this) {

			@Override
			public Vector get(final Element vec) {
				return ((Field) this.getTarget()).nullVec();
			}

			@Override
			public Scalar[][] getGenericMatrix() {
				final Scalar[][] mat = new Scalar[((Field) this.target).getDim()][((Field) this.source).getDim()];
				for (int i = 0; i < mat.length; i++) {
					for (int j = 0; j < mat.length; j++) {
						Scalar scalar = i == j ? Field.this.getOne() : (Scalar) Field.this.getZero();
						mat[i][j] = scalar;
					}
				}
				return mat;
			}

			@Override
			public Map<Vector, Map<Vector, Scalar>> getLinearity() {
				final Map<Vector, Map<Vector, Scalar>> coord = new ConcurrentHashMap<>();
				((EuclideanSpace) this.getSource()).genericBaseToList().parallelStream().forEach(vec -> coord.put(vec,
						((FiniteVectorMethods) ((Field) this.target).nullVec()).getCoordinates()));
				return coord;
			}
		};
		for (final Vector vec : this.genericBaseToList()) {
			final Vector tmp = this.getMultiplicationMatrix().get(vec);
			hom = (FiniteDimensionalHomomorphism) multLinMaps.addition(hom,
					multLinMaps.stretch(tmp, ((FiniteVectorMethods) element).getCoordinates().get(vec)));
		}
		return hom.solve(this.getOne());
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
		return !this.isUnit(element);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default boolean isUnit(final Element element) {
		return !element.equals(this.getZero());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default FieldElement operation(final Element first, final Element second) {
		return (FieldElement) EuclideanAlgebra.super.operation(first, second);
	}

}
