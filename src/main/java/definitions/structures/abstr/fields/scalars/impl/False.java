/**
 * 
 */
package definitions.structures.abstr.fields.scalars.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.fields.impl.BinaryField;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

/**
 * @author RoManski
 *
 */
public final class False implements Scalar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4824547668535611023L;

	static private Scalar instance;

	private Map<Vector, Scalar> coordinates;

	private False() {
	}

	static public Scalar getInstance() {
		if (instance == null) {
			instance = new False();
		}
		return instance;
	}

	@Override
	public Integer getDim() {
		return 1;
	}

	@Override
	public boolean elementOf(VectorSpace space) {
		return space instanceof BinaryField;
	}

	@Override
	public boolean equals(Object vec) {
		return vec instanceof False;
	}

	@Override
	public Map<Vector, Scalar> getCoordinates() {
		if (this.coordinates == null) {
			this.coordinates = new ConcurrentHashMap<>();
			this.coordinates.put(this, True.getInstance());
			this.coordinates.put(True.getInstance(), False.getInstance());
		}
		return this.coordinates;
	}

	@Override
	public void setCoordinates(Map<Vector, Scalar> coordinates) {
		this.coordinates = coordinates;
	}

	@Override
	public double getValue() {
		return 0;
	}

	@Override
	public Scalar getInverse() {
		return True.getInstance();
	}

	@Override
	public void setCoordinates(Map<Vector, Scalar> coordinates, EuclideanSpace space) {
	}

	@Override
	public String toString() {
		return "false";
	}

}