/**
 *
 */
package definitions.structures.abstr.algebra.fields.scalars.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

	/**
	 * Getter for the representant
	 * 
	 * @return the double value
	 */
	public Double getRepresentant() {
		return representant;
	}

	private Map<Vector, Scalar> coordinates;

	@XmlElement
	private final double eps = GlobalSettings.REAL_EQUALITY_FEINHEIT;

	/**
	 * Constructor of zero
	 */
	public Real() {
		representant = 0d;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double doubleValue() {
		return getDoubleValue();
	}

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
		return ((vec instanceof Real) && (Math.abs(((Real) vec).getDoubleValue() - getDoubleValue()) < eps));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float floatValue() {
		return (float) getDoubleValue();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Vector, Scalar> getCoordinates() {
		if (coordinates == null) {
			coordinates = new ConcurrentHashMap<>();
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
	 * real numbers "have" a double value
	 * 
	 * @return the double value
	 */
	@XmlAttribute
	public double getDoubleValue() {
		return representant;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int intValue() {
		return (int) getDoubleValue();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long longValue() {
		return (long) getDoubleValue();
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

	/**
	 * Complex version of the real number
	 * 
	 * @return the given number as a complex number
	 */
	public Scalar toComplex() {
		return new Complex(this, RealLine.getInstance().getZero());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@XmlAttribute
	public String toString() {
		return "" + getDoubleValue();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toXml() {
		return "<real value=\"" + Double.toString(representant) + "\" />";
	}

	public void setRepresentant(Double representant) {
		this.representant = representant;
	}
}
