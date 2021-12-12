/**
 *
 */
package definitions.structures.abstr.algebra.fields.scalars.impl;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import definitions.structures.abstr.algebra.fields.FieldElement;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectors.FiniteVector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import settings.GlobalSettings;

/**
 * @author RoManski
 *
 */
@XmlRootElement
@Component
public class Real extends Number implements Scalar, FieldElement, FiniteVector {

	@XmlElement
	private static final long serialVersionUID = 448447488896787384L;

	@XmlElement
	private Double representant;

	public Double getRepresentant() {
		return representant;
	}

	private Map<Vector, Scalar> coordinates;

	@XmlElement
	private final double eps = GlobalSettings.REAL_EQUALITY_FEINHEIT;

	public Real() {
		representant = 0d;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double doubleValue() {
		return this.getDoubleValue();
	}

	// public Real(double value) {
	// this.realValue = value;
	// }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean elementOf(final VectorSpace space) {
		return space == RealLine.getInstance();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(final Object vec) {
		return ((vec instanceof Real) && (Math.abs(((Real) vec).getDoubleValue() - this.getDoubleValue()) < eps));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float floatValue() {
		return (float) this.getDoubleValue();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Vector, Scalar> getCoordinates() {
		if (coordinates == null) {
			coordinates = new HashMap<>();
			coordinates.put(RealLine.getInstance().getOne(), this);
		}
		return coordinates;
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
	@XmlAttribute
	public Integer getDim() {
		return 1;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@XmlAttribute
	public double getDoubleValue() {
		return representant;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int intValue() {
		return (int) this.getDoubleValue();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long longValue() {
		return (long) this.getDoubleValue();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCoordinates(final Map<Vector, Scalar> coordinates) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCoordinates(final Map<Vector, Scalar> coordinates, final EuclideanSpace space) {
	}

	public void setValue(final double value) {
		representant = value;
	}

	// @Override
	// public boolean equals(Object obj) {
	// if (obj.equals(this)) {
	// return true;
	// }
	// return obj instanceof Real && ((Real) obj).getValue() == this.getValue();
	// }

	public Scalar toComplex() {
		return new Complex(this, RealLine.getInstance().getZero());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@XmlAttribute
	public String toString() {
		return "" + this.getDoubleValue();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toXml() {
		return "<real value=\"" + Double.toString(representant) + "\" />\r";
	}

	public void setRepresentant(Double representant) {
		this.representant = representant;
	}
}
