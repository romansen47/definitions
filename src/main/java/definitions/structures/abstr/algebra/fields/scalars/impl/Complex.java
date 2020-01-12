package definitions.structures.abstr.algebra.fields.scalars.impl;

import java.util.Map;

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

//	@XmlElement
//	private Map<Vector, Scalar> coordinates;

	public Complex(final double x, final double y) {
		this(RealLine.getInstance().get(x), RealLine.getInstance().get(y));
	}

	public Complex(final Scalar re, final Scalar im) {
		super(2);
		this.real = re;
		this.imag = im;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean elementOf(final VectorSpace space) {
		return space == ComplexPlane.getInstance();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(final Object vec) {
		return vec instanceof Complex && ((Complex) vec).getReal().equals(this.getReal())
				&& ((Complex) vec).getImag().equals(this.getImag());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Vector, Scalar> getCoordinates() {
		final Map<Vector, Scalar> tmp = super.getCoordinates();
		if (tmp.isEmpty()) {
			tmp.put(((Field) ComplexPlane.getInstance()).getOne(), this.getReal());
			tmp.put(((ComplexPlane) ComplexPlane.getInstance()).getI(), this.getImag());
		}
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
		return this.imag;
	}
 
	/**
	 * @return the real
	 */
	public Scalar getReal() {
		return this.real;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getDoubleValue() {
		return this.getReal().getDoubleValue();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCoordinates(final Map<Vector, Scalar> coordinates) {
		super.setCoordinates(coordinates);
		this.real = coordinates.get(((Field) ComplexPlane.getInstance()).getOne());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCoordinates(final Map<Vector, Scalar> coordinates, final EuclideanSpace space) {
	}

	public void setValue(final double realValue, final double imValue) {
		this.real = RealLine.getInstance().get(realValue);
		this.imag = RealLine.getInstance().get(imValue);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toXml() {
		return "<complex>\r<realPart>" + this.real.getDoubleValue() + "</realPart>\r<imagPart>" + this.imag.getDoubleValue()
				+ "</imagPart>\r</complex>\r";
	}

}
