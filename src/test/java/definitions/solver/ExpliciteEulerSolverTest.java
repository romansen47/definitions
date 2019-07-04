package definitions.solver;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.finitedimensional.Generator;
import definitions.structures.finitedimensional.vectors.Function;
import definitions.structures.finitedimensional.vectors.functions.ExponentialFunction;
import definitions.structures.finitedimensional.vectorspaces.EuclideanSpace;

public class ExpliciteEulerSolverTest {

	private static EuclideanSpace space;
	private static Function fun;

	@BeforeClass
	public static void setUpBeforeClass() throws Throwable {
		setSpace(Generator.getGenerator().getSpacegenerator().getTrigonometricSpace(1));
		fun = new ExponentialFunction(0, 0);// (Function) getSpace().genericBaseToList().get(0);
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
