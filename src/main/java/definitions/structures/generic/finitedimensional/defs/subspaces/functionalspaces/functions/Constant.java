package definitions.structures.generic.finitedimensional.defs.subspaces.functionalspaces.functions;

public class Constant extends Function {

	final double value;

	public Constant(double[] coordinates, double value) throws Throwable {
		super(coordinates);
		this.value = value;
	}

	@Override
	public double value(double input) {
		return value;
	}

}
