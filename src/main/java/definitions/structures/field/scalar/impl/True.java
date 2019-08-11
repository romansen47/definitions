/**
 * 
 */
package definitions.structures.field.scalar.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.Scalar;
import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.field.impl.Modulo2;
import definitions.structures.finitedimensional.vectorspaces.EuclideanSpace;

/**
 * @author RoManski
 *
 */
public final class True implements Scalar {

	static private Scalar instance;

	private Map<Vector, Scalar> coordinates;

	private True() {
	}

	static public Scalar getInstance() {
		if (instance == null) {
			instance = new True();
		}
		return instance;
	}

	@Override
	public Integer getDim() {
		return 1;
	}

	@Override
	public boolean elementOf(VectorSpace space) {
		return space instanceof Modulo2;
	}

	@Override
	public Boolean equals(Vector vec) {
		return vec instanceof True;
	}

	@Override
	public Map<Vector, Scalar> getCoordinates() {
		if (this.coordinates == null) {
			this.coordinates = new ConcurrentHashMap<>();
			this.coordinates.put(this, this);
			this.coordinates.put(False.getInstance(), False.getInstance());
		}
		return this.coordinates;
	}

	@Override
	public void setCoordinates(Map<Vector, Scalar> coordinates) {
		this.coordinates = coordinates;
	}

	@Override
	public double getValue() {
		return 1;
	}

	@Override
	public Scalar getInverse() {
		return False.getInstance();
	}

	@Override
	public void setCoordinates(Map<Vector, Scalar> coordinates, EuclideanSpace space) {
	}

}
