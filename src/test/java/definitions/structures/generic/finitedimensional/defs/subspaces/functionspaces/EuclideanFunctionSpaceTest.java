package definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.generic.finitedimensional.defs.spaces.EuclideanSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.FunctionSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.impl.SpaceGenerator;
import definitions.structures.generic.finitedimensional.defs.vectors.functions.LinearFunction;
import definitions.structures.generic.finitedimensional.defs.vectors.functions.Sine;
import exceptions.WrongClassException;

public class EuclideanFunctionSpaceTest {

	private static FunctionSpace polynomialSpace;
	private static FunctionSpace polynomialSpaceSobolev;

	private static FunctionSpace trigonometricSpace;
	private static FunctionSpace trigonometricSpaceSobolev;

	final static int dim = 3;
	final static int degree = 1;

	private static FunctionSpace extendedSpace;
	private static FunctionSpace extendedToSobolev;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		polynomialSpace = (FunctionSpace) SpaceGenerator.getInstance().getPolynomialFunctionSpace(dim, -Math.PI,
				Math.PI);
		polynomialSpaceSobolev = (FunctionSpace) SpaceGenerator.getInstance().getPolynomialSobolevSpace(dim, -Math.PI,
				Math.PI, degree);

		trigonometricSpace = SpaceGenerator.getInstance().getTrigonometricSpace(dim);
		trigonometricSpaceSobolev = SpaceGenerator.getInstance().getTrigonometricSobolevSpace(dim, degree);
	}

	@Test
	public void polynomialL2() throws WrongClassException {
		extendedSpace = (FunctionSpace) SpaceGenerator.getInstance().extend(polynomialSpace, new Sine(1, 0, 1));
		((EuclideanSpace) extendedSpace).show();
	}

	@Test
	public void polynomialSobolev() throws WrongClassException {
		extendedToSobolev = (FunctionSpace) SpaceGenerator.getInstance().extend(polynomialSpaceSobolev,
				new Sine(1, 0, 1));
		((EuclideanSpace) extendedToSobolev).show();
	}

	@Test
	public void trigonometricL2() throws WrongClassException {
		extendedSpace = (FunctionSpace) SpaceGenerator.getInstance().extend(trigonometricSpace,
				new LinearFunction(1, 1));
		((EuclideanSpace) extendedSpace).show();
	}

	@Test
	public void trigonometricSobolev() throws WrongClassException {
		extendedToSobolev = (FunctionSpace) SpaceGenerator.getInstance().extend(trigonometricSpaceSobolev,
				new LinearFunction(1, 1));
		((EuclideanSpace) extendedToSobolev).show();
	}

}
