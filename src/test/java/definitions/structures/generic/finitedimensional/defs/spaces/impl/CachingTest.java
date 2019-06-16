package definitions.structures.generic.finitedimensional.defs.spaces.impl;

import org.junit.Test;

import definitions.structures.abstr.VectorSpace;
import definitions.structures.generic.finitedimensional.defs.Generator;
import definitions.structures.generic.finitedimensional.defs.IGenerator;
import definitions.structures.generic.finitedimensional.defs.spaces.ISpaceGenerator;

public class CachingTest {

	static IGenerator gen=Generator.getGenerator();
	static ISpaceGenerator spacesGen=gen.getSpacegenerator();
	
	@Test
	public void test() throws Throwable {
		
//		gen.loadCoordinateSpaces();
		for (int i=0;i<100;i++) {
			spacesGen.getFiniteDimensionalVectorSpace(i);
		}

//		gen.loadFunctionSpaces();
		for (int i=0;i<5;i++) {
			spacesGen.getPolynomialFunctionSpace(i, -1, 1);
		}

		gen.saveCoordinateSpaces();
		gen.saveFunctionSpaces();
		VectorSpace space=spacesGen.getFiniteDimensionalVectorSpace(199);
		VectorSpace space2=spacesGen.getPolynomialFunctionSpace(2, -1,1);
	}

}
