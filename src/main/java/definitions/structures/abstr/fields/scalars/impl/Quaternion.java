package definitions.structures.abstr.fields.scalars.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.impl.ComplexPlane;
import definitions.structures.abstr.fields.impl.QuaternionSpace;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectors.impl.Tuple;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

@XmlRootElement
public class Quaternion extends Tuple implements Scalar {

	@XmlElement
	private static final long serialVersionUID = -7587290161110602891L;
	
	@XmlElement
	private final Scalar real;
	
	@XmlElement
	private final Scalar i;
	
	@XmlElement
	private final Scalar j;
	
	@XmlElement
	private final Scalar k;

	@XmlElement
	private Map<Vector, Scalar> coordinates;

	public Quaternion(Scalar re, Scalar i, Scalar j, Scalar k) {
		super(4);
		this.real = re;
		this.i = i;
		this.j = j;
		this.k = k;
	}

	public Quaternion(double r, double i, double j, double k) {
		this(new Real(r), new Real(i), new Real(j), new Real(k));
	}

	@Override
	public Integer getDim() {
		return 4;
	}

	@Override
	public boolean elementOf(VectorSpace space) {
		return space == QuaternionSpace.getInstance();
	}

	@Override
	public boolean equals(Object vec) {
		return vec instanceof Quaternion && ((Quaternion) vec).getReal().equals(this.getReal())
				&& ((Quaternion) vec).getI().equals(this.getI()) && ((Quaternion) vec).getJ().equals(this.getJ())
				&& ((Quaternion) vec).getK().equals(this.getK());
	}

	@Override
	public Map<Vector, Scalar> getCoordinates() {
		if (this.coordinates == null) {
			this.coordinates = new HashMap<>();
			this.coordinates.put(((QuaternionSpace) QuaternionSpace.getInstance()).getOne(), this.getReal());
			this.coordinates.put(((QuaternionSpace) QuaternionSpace.getInstance()).getI(), this.getI());
			this.coordinates.put(((QuaternionSpace) QuaternionSpace.getInstance()).getJ(), this.getJ());
			this.coordinates.put(((QuaternionSpace) QuaternionSpace.getInstance()).getK(), this.getK());
		}
		return this.coordinates;
	}

	@Override
	public void setCoordinates(Map<Vector, Scalar> coordinates) {
		this.coordinates = coordinates;
	}

	@XmlAttribute
	@Override
	public double getValue() {
		return this.getReal().getValue();
	}

	@XmlAttribute
	@Override
	public Scalar getInverse() {
		return (Scalar) ((Field) QuaternionSpace.getInstance()).inverse(this);
	}

	/**
	 * @return the real
	 */
	@XmlAttribute
	public Scalar getReal() {
		return this.real;
	}

	@Override
	@XmlAttribute
	public String toString() {
		return "1  ->  " + this.getReal().getValue() + "\r" + "i  ->  " + this.getI().getValue() + "\r" + "j  ->  "
				+ this.getJ().getValue() + "\r" + "k  ->  " + this.getK().getValue() + "\r";
	}

	@Override
	public void setCoordinates(Map<Vector, Scalar> coordinates, EuclideanSpace space) {
	}

	@Override
	public Set<Vector> getGenericBase() {
		return ComplexPlane.getInstance().getGenericBase();
	}

	@XmlAttribute
	@Override
	public Map<Vector, Scalar> getCoordinates(EuclideanSpace source) {
		return this.getCoordinates();
	}

	/**
	 * @return the i
	 */
	@XmlAttribute
	public Scalar getI() {
		return this.i;
	}

	/**
	 * @return the j
	 */
	@XmlAttribute
	public Scalar getJ() {
		return this.j;
	}

	/**
	 * @return the k
	 */
	@XmlAttribute
	public Scalar getK() {
		return this.k;
	}

}
