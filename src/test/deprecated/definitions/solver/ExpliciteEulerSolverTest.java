package definitions.solver;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.Scalar;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.field.impl.RealLine;
import definitions.structures.finitedimensional.real.Generator;
import definitions.structures.finitedimensional.real.vectors.Function;
import definitions.structures.finitedimensional.real.vectors.specialfunctions.ExponentialFunction;
import definitions.structures.finitedimensional.real.vectorspaces.EuclideanSpace;

public class ExpliciteEulerSolverTest {

	final static VectorSpace realLine=RealLine.getInstance();
	private static EuclideanSpace space;
	private static Function fun;

	@BeforeClass
	public static void setUpBeforeClass() throws Throwable {
		setSpace(Generator.getGenerator().getSpacegenerator().getTrigonometricSpace(1));
		fun = new ExponentialFunction((Scalar)realLine.nullVec(),(Scalar)realLine.nullVec());// (Function) getSpace().genericBaseToList().get(0);
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
