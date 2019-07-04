package definitions.structures.generic.finitedimensional.defs.spaces.impl;

import org.junit.Test;

import definitions.structures.abstr.VectorSpace;
import definitions.structures.finitedimensional.Generator;
import definitions.structures.finitedimensional.IGenerator;
import definitions.structures.finitedimensional.vectorspaces.ISpaceGenerator;

public class CachingTest {

	static IGenerator gen = Generator.getGenerator();
	static ISpaceGenerator spacesGen = gen.getSpacegenerator();

	@Test
	public void test() throws Throwable {

//		gen.loadCoordinateSpaces();
		for (int i = 0; i < 100; i++) {
			spacesGen.getFiniteDimensionalVectorSpace(i);
		}

//		gen.loadFunctionSpaces();
		for (int i = 0; i < 5; i++) {
			spacesGen.getPolynomialFunctionSpace(i, -1, 1);
		}

		gen.saveCoordinateSpaces();
		gen.saveFunctionSpaces();
		final VectorSpace space = spacesGen.getFiniteDimensionalVectorSpace(199);
		final VectorSpace space2 = spacesGen.getPolynomialFunctionSpace(2, -1, 1);
	}

}
