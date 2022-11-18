package definitions.structures.abstr.algebra.fields.scalars.impl;

import java.util.Map;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import org.springframework.stereotype.Component;

import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.FieldElement;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.vectors.impl.Tuple;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

@Component
public class Complex extends Tuple implements Scalar, FieldElement {

	@XmlElement
	private static final long serialVersionUID = 2160805146914088274L;

	@XmlElement
	private Scalar real;

	@XmlElement
	private Scalar imag;

	public Complex(final double x, final double y) {
		this(Generator.getInstance().getFieldGenerator().getRealLine().get(x),
				Generator.getInstance().getFieldGenerator().getRealLine().get(y));
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
		return space.equals(Generator.getInstance().getFieldGenerator().getComplexPlane());
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.real, this.imag);
	}

	@Override
	public boolean equals(Object vec) {
		if (this == vec) {
			return true;
		}
		if (!super.equals(vec) || (this.getClass() != vec.getClass())) {
			return false;
		}
		Complex other = (Complex) vec;
		return this.real.equals(other.real) && this.imag.equals(other.imag);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Vector, Scalar> getCoordinates() {
		final Map<Vector, Scalar> tmp = super.getCoordinates();
		if (tmp.isEmpty()) {
			tmp.put(((Field) Generator.getInstance().getFieldGenerator().getComplexPlane()).getOne(), this.getReal());
			tmp.put(Generator.getInstance().getFieldGenerator().getComplexPlane().getI(), this.getImag());
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
	public void setCoordinates(final Map<Vector, Scalar> coordinates) {
		super.setCoordinates(coordinates);
		this.real = coordinates.get(((Field) Generator.getInstance().getFieldGenerator().getComplexPlane()).getOne());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCoordinates(final Map<Vector, Scalar> coordinates, final EuclideanSpace space) {
		// for complex numbers we won't need a coordinates map
	}

	public void setValue(final double realValue, final double imValue) {
		this.real = Generator.getInstance().getFieldGenerator().getRealLine().get(realValue);
		this.imag = Generator.getInstance().getFieldGenerator().getRealLine().get(imValue);

	}

	@Override
	public String toString() {
		return this.toXml();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toXml() {
		return "<complex Re=\"" + this.real.toString() + "\" Im=\"" + this.imag.toString() + "\" />\r";
	}

}
