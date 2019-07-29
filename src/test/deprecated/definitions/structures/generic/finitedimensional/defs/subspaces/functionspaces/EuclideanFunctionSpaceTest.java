package definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.FunctionSpace;
import definitions.structures.abstr.Scalar;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.field.impl.RealLine;
import definitions.structures.finitedimensional.real.vectors.specialfunctions.LinearFunction;
import definitions.structures.finitedimensional.real.vectors.specialfunctions.Sine;
import definitions.structures.finitedimensional.real.vectorspaces.EuclideanSpace;
import definitions.structures.finitedimensional.real.vectorspaces.impl.SpaceGenerator;
import exceptions.WrongClassException;

public class EuclideanFunctionSpaceTest {

	final static VectorSpace realLine=RealLine.getRealLine();
	private static FunctionSpace polynomialSpace;
	private static FunctionSpace polynomialSpaceSobolev;

	private static FunctionSpace trigonometricSpace;
	private static FunctionSpace trigonometricSpaceSobolev;

	final static int dim = 1;
	final static int degree = 1;

	private static FunctionSpace extendedSpace;
	private static FunctionSpace extendedToSobolev;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		polynomialSpace = SpaceGenerator.getInstance().getPolynomialFunctionSpace(dim, Math.PI);
		polynomialSpaceSobolev = (FunctionSpace) SpaceGenerator.getInstance().getPolynomialSobolevSpace(dim, Math.PI,
				degree);

		trigonometricSpace = SpaceGenerator.getInstance().getTrigonometricSpace(dim);
		trigonometricSpaceSobolev = SpaceGenerator.getInstance().getTrigonometricSobolevSpace(dim, degree);
	}

	@Test
	public void polynomialL2() throws WrongClassException {
		extendedSpace = (FunctionSpace) SpaceGenerator.getInstance().extend(polynomialSpace, 
				new Sine(RealLine.getRealLine().getOne(), (Scalar) RealLine.getRealLine().nullVec(), RealLine.getRealLine().getOne()));
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
				new LinearFunction(((RealLine) realLine).getOne(), ((RealLine) realLine).getOne()));
		((EuclideanSpace) extendedSpace).show();
	}

	@Test
	public void trigonometricSobolev() throws WrongClassException {
		extendedToSobolev = (FunctionSpace) SpaceGenerator.getInstance().extend(trigonometricSpaceSobolev,
				new LinearFunction(((RealLine) realLine).getOne(), ((RealLine) realLine).getOne()));
		((EuclideanSpace) extendedToSobolev).show();
	}

}
