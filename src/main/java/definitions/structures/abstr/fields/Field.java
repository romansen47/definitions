package definitions.structures.abstr.fields;

import java.util.HashMap;
import java.util.Map;

import definitions.settings.XmlPrintable;
import definitions.structures.abstr.fields.impl.PrimeField;
import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.groups.Group;
import definitions.structures.abstr.groups.GroupElement;
import definitions.structures.abstr.groups.Monoid;
import definitions.structures.abstr.groups.MonoidElement;
import definitions.structures.abstr.vectorspaces.EuclideanAlgebra;
import definitions.structures.abstr.vectorspaces.LinearMappingsSpace;
import definitions.structures.abstr.vectorspaces.Ring;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.FiniteVectorMethods;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.mappings.FiniteDimensionalHomomorphism;
import definitions.structures.euclidean.mappings.impl.FiniteDimensionalLinearMapping;
import definitions.structures.euclidean.vectors.FiniteVector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

public interface Field extends XmlPrintable, Ring, EuclideanAlgebra, FieldMethods {
	
	default Vector inverse(Vector factor) {
		final VectorSpace multLinMaps = new LinearMappingsSpace(this, this);
		FiniteDimensionalHomomorphism hom = new FiniteDimensionalLinearMapping(this, this) {
			private static final long serialVersionUID = -4878554588629268392L;

			@Override
			public Vector get(Vector vec) {
				return ((Field) getTarget()).nullVec();
			}

			@Override
			public Map<Vector, Map<Vector, Scalar>> getLinearity() {
				final Map<Vector, Map<Vector, Scalar>> coord = new HashMap<>();
				for (final Vector vec : ((EuclideanSpace) getSource()).genericBaseToList()) {
					coord.put(vec, ((FiniteVectorMethods) ((Field) target).nullVec()).getCoordinates());
				}
				return coord;
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
		};
		for (final Vector vec : genericBaseToList()) {
			final Vector tmp = this.getMultiplicationMatrix().get(vec);
			hom = (FiniteDimensionalHomomorphism) multLinMaps.add(hom,
					multLinMaps.stretch(tmp, ((FiniteVectorMethods) factor).getCoordinates().get(vec)));
		}
		return hom.solve((FiniteVector) getOne());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	Vector getOne();

	default Vector getZero() {
		return nullVec();
	}

	Scalar conjugate(Scalar value);

	/**
	 * {@inheritDoc}
	 */
	@Override
	default Monoid getMuliplicativeMonoid() {

		final Vector newOne = getOne();
		final Integer newOrder = getOrder();

		final Group multiplicativeGroup = new Group() {
			private static final long serialVersionUID = 8357435449765655148L;

			@Override
			public MonoidElement getIdentityElement() {
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
			public MonoidElement operation(GroupElement first, GroupElement second) {
				return product((Scalar) first, (Scalar) second);
			}

			@Override
			public GroupElement getInverseElement(GroupElement element) {
				return inverse((Scalar) element);
			}

			@Override
			public String toString() {
				return "Multiplicative group of " + getField().toString();
			}
		};

		return multiplicativeGroup;
	}

	default int getCharacteristic() {
		return 0;
	}

	/**
	 * Should return field of rational numbers in infinite case by default.
	 * @return
	 */
	default PrimeField getPrimeField() {
		return null;
	}
}
