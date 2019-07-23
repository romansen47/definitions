package definitions.structures.finitedimensional.vectorspaces;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.VectorSpace;
import definitions.structures.finitedimensional.real.vectors.Function;
import definitions.structures.finitedimensional.real.vectors.impl.GenericFunction;
import definitions.structures.finitedimensional.real.vectors.specialfunctions.Sine;
import definitions.structures.finitedimensional.real.vectorspaces.EuclideanSpace;
import definitions.structures.finitedimensional.real.vectorspaces.impl.SpaceGenerator;

public class PolynomialFunctionSpaceTest {

	final static int polynomialDegree = 3;
	final static int polynomialSobolevDegree = 3;
	final static int sobolevDegree = 3;

	private static Function abs = new GenericFunction() {
		@Override
		public double value(double input) {
			return Math.abs(input);
		}
	};

	final static int tmpPeriod = 3;

	private static Function frequentAbs = new GenericFunction() {
		final int period = tmpPeriod;

		@Override
		public double value(double input) {
			final double val = abs.value(input) * this.period;
			if ((val - (val % 1.)) / this.period < 0.5) {
				return (val / this.period) % 1.;
			}
			return 1. - ((val / this.period) % 1.);
		}
	};

	private static VectorSpace space1 = SpaceGenerator.getInstance().getPolynomialFunctionSpace(polynomialDegree, 1);
	private static VectorSpace space2 = SpaceGenerator.getInstance().getPolynomialSobolevSpace(polynomialSobolevDegree,
			1, sobolevDegree);

	private static Function sine = new Sine(1, 0, 1);

	private static Function coordinates1;
	private static Function coordinates2;
	private static Function coordinates3;
	private static Function coordinates4;
	private static Function coordinates5;
	private static Function coordinates6;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		coordinates1 = sine.getProjection((EuclideanSpace) space1);
		coordinates2 = sine.getProjection((EuclideanSpace) space2);
		coordinates3 = abs.getProjection((EuclideanSpace) space1);
		coordinates4 = abs.getProjection((EuclideanSpace) space2);
		coordinates5 = frequentAbs.getProjection((EuclideanSpace) space1);
		coordinates6 = frequentAbs.getProjection((EuclideanSpace) space2);
	}

//	@Test
	public void testSine() {
		coordinates1.plotCompare(-1, 1, coordinates2);
	}

	@Test
	public void testFrequentAbs() {
//		coordinates5.plot(-1,1);
//		coordinates6.plot(-1,1);
		frequentAbs.plot(-1, 1);
//		coordinates5.plotCompare(-1,1,coordinates6);
	}

//	@Test
	public void testAbs() {
		coordinates3.plotCompare(-1, 1, coordinates4);
	}
}
