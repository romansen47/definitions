package definitions.structures.generic.finitedimensional.defs.vectors;

public class Cosine extends Sine {

	public Cosine(final double[] coordinates) throws Throwable {
		super(coordinates);
	}

	final double pi = Math.PI;

	@Override
	public double value(final double input) throws Throwable {
		return this.norm * Math.cos(input);
	}

	@Override
	public String toString() {
		return "renormed cosine";
	}

}
