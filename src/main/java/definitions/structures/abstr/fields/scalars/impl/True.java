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
@javax.xml.bind.annotation.XmlRootElement
public final class True implements Scalar {

	@javax.xml.bind.annotation.XmlElement
	private static final long serialVersionUID = 9013421650270376537L;

	static private Scalar instance;
	
	@javax.xml.bind.annotation.XmlElement
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
		return space instanceof BinaryField;
	}

	@Override
	public boolean equals(Object vec) {
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
	
	@javax.xml.bind.annotation.XmlElement
	@Override
	public double getValue() {
		return 1;
	}

	@javax.xml.bind.annotation.XmlElement
	@Override
	public Scalar getInverse() {
		return False.getInstance();
	}

	@Override
	public void setCoordinates(Map<Vector, Scalar> coordinates, EuclideanSpace space) {
	}

	@javax.xml.bind.annotation.XmlElement
	@Override
	public String toString() {
		return "true";
	}
}
