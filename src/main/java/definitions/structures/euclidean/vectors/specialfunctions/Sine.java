package definitions.structures.euclidean.vectors.specialfunctions;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.euclidean.vectors.impl.GenericFunction;

public abstract class Sine extends GenericFunction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4429300404671715544L; 
	private final Scalar magnitude;
	private final Scalar translation;
	private final Scalar frequency;


	public Sine(double a, double b, double c) {
		/*
		 * by default sine is defined 
		 */
		this(RealLine.getInstance().get(a),RealLine.getInstance().get(b),RealLine.getInstance().get(c),RealLine.getInstance());
		
//		this.setField(RealLine.getInstance());
//		this.magnitude = this.getField().get(a);
//		this.translation = this.getField().get(b);
//		this.frequency = this.getField().get(c);
	}
	
	public Sine(Scalar a, Scalar b, Scalar c) {
		this(a.getValue(),b.getValue(),c.getValue());
	}

	public Sine(Scalar a, Scalar b, Scalar c, Field field) {
		this.setField(field);
		this.magnitude = a;
		this.translation = b;
		this.frequency = c;
	}

	@Override
	public Scalar value(Scalar input) {
		return this.getField().get(this.magnitude.getValue()
				* Math.sin(this.getTranslation().getValue() + (this.getFrequency().getValue() * input.getValue())));
	}

	@Override
	public String toString() {
		return "x -> " + this.magnitude + "*sin(" + this.getTranslation() + "+" + this.getFrequency() + "*x) ";
	}

	/**
	 * @return the frequency
	 */
	public Scalar getFrequency() {
		return this.frequency;
	}

	/**
	 * @return the translation
	 */
	public Scalar getTranslation() {
		return this.translation;
	}

}
