package definitions.structures.abstr.algebra.fields.scalars.impl;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import definitions.structures.abstr.algebra.fields.FieldElement;
import definitions.structures.abstr.algebra.fields.impl.QuaternionSpace;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectors.impl.Tuple;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

@XmlRootElement
public class Quaternion extends Tuple implements FieldElement {

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

//	@XmlElement
//	private Map<Vector, Scalar> coordinates;

	public Quaternion(final double r, final double i, final double j, final double k) {
		this(RealLine.getInstance().get(r), RealLine.getInstance().get(i), RealLine.getInstance().get(j),
				RealLine.getInstance().get(k));
	}

	public Quaternion(final Scalar re, final Scalar i, final Scalar j, final Scalar k) {
		super(4);
		real = re;
		this.i = i;
		this.j = j;
		this.k = k;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean elementOf(final VectorSpace space) {
		return space == QuaternionSpace.getInstance();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(final Object vec) {
		return (vec instanceof Quaternion) && ((Quaternion) vec).getReal().equals(getReal())
				&& ((Quaternion) vec).getI().equals(getI()) && ((Quaternion) vec).getJ().equals(getJ())
				&& ((Quaternion) vec).getK().equals(getK());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Vector, Scalar> getCoordinates() {
		Map<Vector, Scalar> tmp = super.getCoordinates();
		if ((tmp == null) || tmp.isEmpty()) {
			tmp = new HashMap<>();
			tmp.put(((QuaternionSpace) QuaternionSpace.getInstance()).getOne(), getReal());
			tmp.put(((QuaternionSpace) QuaternionSpace.getInstance()).getI(), getI());
			tmp.put(((QuaternionSpace) QuaternionSpace.getInstance()).getJ(), getJ());
			tmp.put(((QuaternionSpace) QuaternionSpace.getInstance()).getK(), getK());
		}
		return tmp;
	}

	/**
	 * {@inheritDoc}
	 */
	@XmlAttribute
	@Override
	public Map<Vector, Scalar> getCoordinates(final EuclideanSpace source) {
		return this.getCoordinates();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer getDim() {
		return 4;
	}

	/**
	 * @return the i
	 */
	@XmlAttribute
	public Scalar getI() {
		return i;
	}

	/**
	 * @return the j
	 */
	@XmlAttribute
	public Scalar getJ() {
		return j;
	}

	/**
	 * @return the k
	 */
	@XmlAttribute
	public Scalar getK() {
		return k;
	}

	/**
	 * @return the real
	 */
	@XmlAttribute
	public Scalar getReal() {
		return real;
	}

	/**
	 * {@inheritDoc}
	 */
	@XmlAttribute
	@Override
	public double getDoubleValue() {
		return getReal().getDoubleValue();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCoordinates(final Map<Vector, Scalar> coordinates, final EuclideanSpace space) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@XmlAttribute
	public String toString() {
		return "(" + getReal().getDoubleValue() + "," + getI().getDoubleValue() + "," + getJ().getDoubleValue() + ","
				+ getK().getDoubleValue() + ")";
	}

}
