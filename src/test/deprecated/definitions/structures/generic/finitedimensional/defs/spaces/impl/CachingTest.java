package definitions.structures.generic.finitedimensional.defs.spaces.impl;

import org.junit.Test;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.IGenerator;
import definitions.structures.euclidean.vectorspaces.ISpaceGenerator;

public class CachingTest {

	static IGenerator gen = Generator.getGenerator();
	static ISpaceGenerator spacesGen = gen.getSpacegenerator();

	final Field realSpace = RealLine.getInstance();

	@Test
	public void test() throws Throwable {

		// gen.loadCoordinateSpaces();
		for (int i = 0; i < 100; i++) {
			spacesGen.getFiniteDimensionalVectorSpace(i);
		}

		// gen.loadFunctionSpaces();
		for (int i = 0; i < 5; i++) {
			spacesGen.getPolynomialFunctionSpace(this.realSpace, i, 1, true);
		}

		gen.saveCoordinateSpaces();
		gen.saveFunctionSpaces();
		final VectorSpace space = spacesGen.getFiniteDimensionalVectorSpace(199);
		final VectorSpace space2 = spacesGen.getPolynomialFunctionSpace(this.realSpace, 2, 1, true);
	}

}
