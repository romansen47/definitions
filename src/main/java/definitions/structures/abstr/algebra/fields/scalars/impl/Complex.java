package definitions.structures.abstr.algebra.fields.scalars.impl;

import java.util.Map;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.FieldElement;
import definitions.structures.abstr.algebra.fields.impl.ComplexPlane;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectors.impl.Tuple;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

public class Complex extends Tuple implements Scalar, FieldElement {

	@XmlElement
	private static final long serialVersionUID = 2160805146914088274L;

	@XmlElement
	private Scalar real;

	@XmlElement
	private Scalar imag;

	public Complex(final double x, final double y) {
		this(RealLine.getInstance().get(x), RealLine.getInstance().get(y));
	}

	public Complex(final Scalar re, final Scalar im) {
		super(2);
		real = re;
		imag = im;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean elementOf(final VectorSpace space) {
		return space == ComplexPlane.getInstance();
	}

	@Override
	public int hashCode() {
		return Objects.hash(real, imag);
	}

	@Override
	public boolean equals(Object vec) {
		if (this == vec)
			return true;
		if (!super.equals(vec))
			return false;
		if (getClass() != vec.getClass())
			return false;
		Complex other = (Complex) vec;
		return real.equals(other.real) && imag.equals(other.imag);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Vector, Scalar> getCoordinates() {
		final Map<Vector, Scalar> tmp = super.getCoordinates();
		if (tmp.isEmpty()) {
			tmp.put(((Field) ComplexPlane.getInstance()).getOne(), getReal());
			tmp.put(((ComplexPlane) ComplexPlane.getInstance()).getI(), getImag());
		}
		super.setCoordinates(tmp);
		return tmp;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Vector, Scalar> getCoordinates(final EuclideanSpace source) {
		return this.getCoordinates();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer getDim() {
		return 2;
	}

	/**
	 * @return the imag
	 */
	public Scalar getImag() {
		return imag;
	}

	/**
	 * @return the real
	 */
	public Scalar getReal() {
		return real;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCoordinates(final Map<Vector, Scalar> coordinates) {
		super.setCoordinates(coordinates);
		real = coordinates.get(((Field) ComplexPlane.getInstance()).getOne());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCoordinates(final Map<Vector, Scalar> coordinates, final EuclideanSpace space) {
		// for complex numbers we won't need a coordinates map
	}

	public void setValue(final double realValue, final double imValue) {
		real = RealLine.getInstance().get(realValue);
		imag = RealLine.getInstance().get(imValue);

	}

	@Override
	public String toString() {
		return toXml();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toXml() {
		return "<complex Re=\"" + real.toString() + "\" Im=\"" + imag.toString() + "\" />\r";
	}

}
