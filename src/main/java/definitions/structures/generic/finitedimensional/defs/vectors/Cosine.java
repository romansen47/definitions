package definitions.structures.generic.finitedimensional.defs.vectors;

public class Cosine extends Sine {

	public Cosine(double[] coordinates) throws Throwable {
		super(coordinates);
	}

	final double pi = Math.PI;

	@Override
	public double value(double input) throws Throwable {
		return norm*Math.cos(input);
	}

	@Override
	public String toString() {
		return "renormed cosine";
	}

}
