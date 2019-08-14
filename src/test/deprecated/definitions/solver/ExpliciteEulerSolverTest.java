package definitions.solver;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.vectors.specialfunctions.ExponentialFunction;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

public class ExpliciteEulerSolverTest {

	final static VectorSpace realLine = RealLine.getInstance();
	private static EuclideanSpace space;
	private static Function fun;

	@BeforeClass
	public static void setUpBeforeClass() throws Throwable {
		setSpace(Generator.getGenerator().getSpacegenerator().getTrigonometricSpace(RealLine.getInstance(), 1));
		fun = new ExponentialFunction((Scalar) realLine.nullVec(), (Scalar) realLine.nullVec());// (Function)
																								// getSpace().genericBaseToList().get(0);
	}

	@Test
	public void testSolve() throws Throwable {
		final Solver solver = new ExpliciteEulerSolver(fun, 0, 1.e-3);
		final Function solution = solver.solve();
		solution.plot(-Math.PI, Math.PI);
	}

	public static EuclideanSpace getSpace() {
		return space;
	}

	public static void setSpace(EuclideanSpace space) {
		ExpliciteEulerSolverTest.space = space;
	}

}
