/**
 *
 */
package definitions.structures.abstr.algebra.fields;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.mappings.VectorSpaceHomomorphism;
import definitions.structures.abstr.mappings.impl.Identity;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectors.FiniteVector;

/**
 * @author RoManski
 *
 */
public interface PrimeField extends Field {

	/**
	 * {@inheritDoc}
	 */
	@Override
	default Map<Vector, VectorSpaceHomomorphism> getMultiplicationMatrix() {
		final Map<Vector, VectorSpaceHomomorphism> multiplicationMatrix = new ConcurrentHashMap<>();
		final Vector one = this.getOne();
		final VectorSpaceHomomorphism hom = new Identity(this) {
			@Override
			public Scalar[][] getGenericMatrix() {
				if (this.genericMatrix == null) {
					this.genericMatrix = new Scalar[1][1];
					this.genericMatrix[0][0] = (Scalar) one;
				}
				return this.genericMatrix;
			}

			@Override
			public Map<Vector, Map<Vector, Scalar>> getLinearity() {
				if (this.linearity == null) {
					final Map<Vector, Map<Vector, Scalar>> linearity = new ConcurrentHashMap<>();
					final Map<Vector, Scalar> map = new ConcurrentHashMap<>();
					map.put(one, (Scalar) one);
					linearity.put(one, map);
				}
				return this.linearity;
			}

		};
		multiplicationMatrix.put(one, hom);
		return multiplicationMatrix;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default List<Vector> genericBaseToList() {
		final List<Vector> genericBaseToList = new ArrayList<>();
		genericBaseToList.add(this.getOne());
		return genericBaseToList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default int getCharacteristic() {
		return this.getOrder();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default Field getField() {
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default PrimeField getPrimeField() {
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default Integer getDim() {
		return 1;
	}

	@Override
	FiniteVector addition(Vector a, Vector b);

}
