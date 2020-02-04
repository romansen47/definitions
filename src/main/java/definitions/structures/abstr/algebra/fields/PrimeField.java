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

/**
 * @author RoManski
 *
 */
public interface PrimeField extends Field {

	/**
	 * {@inheritDoc}
	 */
	default Map<Vector, VectorSpaceHomomorphism> getMultiplicationMatrix() {
		Map<Vector, VectorSpaceHomomorphism> multiplicationMatrix = new ConcurrentHashMap<>();
		Vector one=getOne();
		VectorSpaceHomomorphism hom = new Identity(this) {

			@Override
			public Scalar[][] getGenericMatrix() {
				if (genericMatrix==null) {
					genericMatrix=new Scalar[1][1];
					genericMatrix[0][0]=(Scalar) one;
				}
				return genericMatrix;
			}

			@Override
			public Map<Vector, Map<Vector, Scalar>> getLinearity() {
				if (linearity == null) {
					Map<Vector, Map<Vector, Scalar>> linearity = new ConcurrentHashMap<>();
					Map<Vector, Scalar> map = new ConcurrentHashMap<>();
					map.put(one, (Scalar) one);
					linearity.put(one, map);
				}
				return linearity;
			}

		};
		multiplicationMatrix.put(one,hom);
		return multiplicationMatrix;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default List<Vector> genericBaseToList() {
		List<Vector> genericBaseToList = new ArrayList<>();
		genericBaseToList.add((Vector) getOne());
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
	Vector addition(Vector a, Vector b);

	@Override
	default Vector nullVec() {
		return (Vector) getNeutralElement();
	}

}
