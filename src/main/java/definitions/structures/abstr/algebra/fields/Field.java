package definitions.structures.abstr.algebra.fields;

import java.util.HashMap;
import java.util.Map;

import definitions.settings.XmlPrintable;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.groups.Group; 
import definitions.structures.abstr.algebra.monoids.AbelianSemiGroup;
import definitions.structures.abstr.algebra.monoids.Monoid; 
import definitions.structures.abstr.algebra.rings.Domain;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.vectorspaces.EuclideanAlgebra;
import definitions.structures.abstr.vectorspaces.LinearMappingsSpace; 
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.FiniteVectorMethods;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.mappings.FiniteDimensionalHomomorphism;
import definitions.structures.euclidean.mappings.impl.FiniteDimensionalLinearMapping;
import definitions.structures.euclidean.vectors.FiniteVector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

public interface Field extends AbelianSemiGroup, XmlPrintable, Domain, EuclideanAlgebra, FieldMethods {

	Scalar conjugate(Scalar value);
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	default boolean divides(final Element devisor, final Element devident) {
		return this.isUnit(devisor);
	}

	default int getCharacteristic() {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default Monoid getMuliplicativeMonoid() {

		final Vector newOne = this.getOne();
		final Integer newOrder = this.getOrder();

		final Group multiplicativeGroup = new Group() {
			private long serialVersionUID = 1L;

			@Override
			public Element getInverseElement(final Element element) {
				return Field.this.inverse((Scalar) element);
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
		return (FieldElement) this.get(1);
	}

	/**
	 * Should return field of rational numbers in infinite case by default.
	 * 
	 * @return
	 */
	default PrimeField getPrimeField() {
		return null;
	}

	default Vector getZero() {
		return this.nullVec();
	}

	default Vector inverse(final Vector factor) {
		final VectorSpace multLinMaps = new LinearMappingsSpace(this, this);
		FiniteDimensionalHomomorphism hom = new FiniteDimensionalLinearMapping(this, this) {
			private long serialVersionUID = 1L;

			@Override
			public Vector get(final Element vec) {
				return ((Field) this.getTarget()).nullVec();
			}

			@Override
			public Scalar[][] getGenericMatrix() {
				final Scalar[][] mat = new Scalar[((Field) this.target).getDim()][((Field) this.source).getDim()];
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
				for (final Vector vec : ((EuclideanSpace) this.getSource()).genericBaseToList()) {
					coord.put(vec, ((FiniteVectorMethods) ((Field) this.target).nullVec()).getCoordinates());
				}
				return coord;
			}
		};
		for (final Vector vec : this.genericBaseToList()) {
			final Vector tmp = this.getMultiplicationMatrix().get(vec);
			hom = (FiniteDimensionalHomomorphism) multLinMaps.add(hom,
					multLinMaps.stretch(tmp, ((FiniteVectorMethods) factor).getCoordinates().get(vec)));
		}
		return hom.solve((FiniteVector) this.getOne());
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
