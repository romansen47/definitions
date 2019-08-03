package definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.FunctionSpace;
import definitions.structures.abstr.Scalar;
import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.field.impl.RealLine;
import definitions.structures.finitedimensional.real.vectors.Function;
import definitions.structures.finitedimensional.real.vectors.specialfunctions.ExponentialFunction;
import definitions.structures.finitedimensional.real.vectors.specialfunctions.LinearFunction;
import definitions.structures.finitedimensional.real.vectors.specialfunctions.Sine;
import definitions.structures.finitedimensional.real.vectorspaces.EuclideanSpace;
import definitions.structures.finitedimensional.real.vectorspaces.impl.SpaceGenerator;
import exceptions.WrongClassException;

public class EuclideanFunctionSpaceTest {

	final static VectorSpace realLine = RealLine.getInstance();
	
	private static FunctionSpace polynomialSpace;
	private static FunctionSpace polynomialSpaceSobolev;

	private static FunctionSpace trigonometricSpace;
	private static FunctionSpace trigonometricSpaceSobolev;

	final static int polynomialDegree = 2;
	final static int trigonometricDegree = 2;
	final static int sobolevDegree = 2;

	final static Function exp = new ExponentialFunction(RealLine.getInstance().getZero(),
			RealLine.getInstance().getOne());
	private static FunctionSpace extendedSpace;
	private static FunctionSpace extendedToSobolev;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		polynomialSpace = SpaceGenerator.getInstance().getPolynomialFunctionSpace(polynomialDegree, Math.PI, false);
		polynomialSpaceSobolev = (FunctionSpace) SpaceGenerator.getInstance()
				.getPolynomialSobolevSpace(polynomialDegree, Math.PI, sobolevDegree);

		trigonometricSpace = SpaceGenerator.getInstance().getTrigonometricSpace(trigonometricDegree);
		trigonometricSpaceSobolev = SpaceGenerator.getInstance().getTrigonometricSobolevSpace(trigonometricDegree,
				sobolevDegree);
	}

	@Test
	public void polynomialL2() throws WrongClassException {
		extendedSpace = (FunctionSpace) SpaceGenerator.getInstance().extend(polynomialSpace,
				new Sine(RealLine.getInstance().getOne(), (Scalar) RealLine.getInstance().nullVec(),
						RealLine.getInstance().getOne()));
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
				new LinearFunction(((RealLine) realLine).getZero(), ((RealLine) realLine).getOne()));
		((EuclideanSpace) extendedSpace).show();
	}

	@Test
	public void trigonometricSobolev() throws WrongClassException {
		Vector fun = new LinearFunction(((RealLine) realLine).getOne(), ((RealLine) realLine).getOne());
		extendedToSobolev = (FunctionSpace) SpaceGenerator.getInstance().extend(trigonometricSpaceSobolev, fun);
		exp.getProjection((EuclideanSpace) extendedToSobolev).plotCompare(-1, 1, exp);
		((EuclideanSpace) extendedToSobolev).show();
	}

}
