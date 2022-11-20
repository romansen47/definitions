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
import definitions.structures.abstr.algebra.fields.RepresentableElement;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.vectors.FiniteVector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import settings.GlobalSettings;

/**
 * @author RoManski
 *
 */
@XmlRootElement
@Component
public class Real extends Number implements Scalar, FieldElement, FiniteVector, RepresentableElement {

	@XmlElement
	private static final long serialVersionUID = 448447488896787384L;

	@XmlElement
	private Double representant;

	@XmlElement
	private final double eps = GlobalSettings.REAL_EQUALITY_FEINHEIT;

	private Map<Vector, Scalar> coordinates;

	public Real() {

	}

	public Real(double value) {
		this.representant = value;
	}

	/**
	 * Getter for the representant
	 *
	 * @return the double value
	 */
	@Override
	public Double getRepresentant() {
		return this.representant;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double doubleValue() {
		return this.getDoubleValue();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean elementOf(final VectorSpace space) {
		return space.equals(Generator.getInstance().getFieldGenerator().getRealLine());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(final Object vec) {
		return ((vec instanceof Real) && (Math.abs(((Real) vec).getDoubleValue() - this.getDoubleValue()) < this.eps));
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
		return this.representant;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int intValue() {
		throw new Error("real has no int value");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long longValue() {
		throw new Error("real has no long value");
	}

	/**
	 * Complex version of the real number
	 *
	 * @return the given number as a complex number
	 */
	public Scalar toComplex() {
		return Generator.getInstance().getFieldGenerator().getComplexPlane().get(this.doubleValue());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@XmlAttribute
	public String toString() {
		return String.valueOf(this.getDoubleValue());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toXml() {
		return "<real value=\"" + Double.toString(this.representant) + "\" />";
	}

	public void setRepresentant(Double representant) {
		this.representant = representant;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Vector, Scalar> getCoordinates() {
		if (this.coordinates == null) {
			this.coordinates = new ConcurrentHashMap<>();
			this.coordinates.put(Generator.getInstance().getFieldGenerator().getRealLine().getOne(), this);
		}
		return this.coordinates;
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
	public void setCoordinates(final Map<Vector, Scalar> coordinates) {
		// no need for
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCoordinates(final Map<Vector, Scalar> coordinates, final EuclideanSpace space) {
		// no need for
	}
}
