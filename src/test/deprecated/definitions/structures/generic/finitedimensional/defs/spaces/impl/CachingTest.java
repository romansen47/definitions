package definitions.structures.generic.finitedimensional.defs.spaces.impl;

import org.junit.Test;

import definitions.structures.abstr.VectorSpace;
import definitions.structures.field.Field;
import definitions.structures.field.impl.RealLine;
import definitions.structures.finitedimensional.real.Generator;
import definitions.structures.finitedimensional.real.IGenerator;
import definitions.structures.finitedimensional.real.vectorspaces.ISpaceGenerator;

public class CachingTest {

	static IGenerator gen = Generator.getGenerator();
	static ISpaceGenerator spacesGen = gen.getSpacegenerator();

	final Field realSpace = RealLine.getInstance();

	@Test
	public void test() throws Throwable {

//		gen.loadCoordinateSpaces();
		for (int i = 0; i < 100; i++) {
			spacesGen.getFiniteDimensionalVectorSpace(i);
		}

//		gen.loadFunctionSpaces();
		for (int i = 0; i < 5; i++) {
			spacesGen.getPolynomialFunctionSpace(this.realSpace, i, 1, true);
		}

		gen.saveCoordinateSpaces();
		gen.saveFunctionSpaces();
		final VectorSpace space = spacesGen.getFiniteDimensionalVectorSpace(199);
		final VectorSpace space2 = spacesGen.getPolynomialFunctionSpace(this.realSpace, 2, -1, true);
	}

}
