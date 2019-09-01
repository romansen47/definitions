package definitions.structures.generic.finitedimensional.defs.spaces.impl;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.IGenerator;
import definitions.structures.euclidean.vectorspaces.ISpaceGenerator;

public class CachingSaveTest {

	final static int coordSpaces=200;
	final static int funcSpaces=5;
	
	final static IGenerator gen = Generator.getGenerator();
	final static ISpaceGenerator spacesGen = gen.getSpacegenerator();

	final static Field realSpace = RealLine.getInstance();

	@BeforeClass
	public static void before() {
		// gen.loadCoordinateSpaces();
		for (int i = 0; i < coordSpaces; i++) {
			spacesGen.getFiniteDimensionalVectorSpace(i);
		}

		// gen.loadFunctionSpaces();
		for (int i = 0; i < funcSpaces; i++) {
			spacesGen.getPolynomialFunctionSpace(realSpace, i, 1, true);
		}
	}

	@Test
	public void saveCoordinateSpacesTest() throws Throwable {
		gen.saveCoordinateSpaces();
	}
	
	@Test
	public void saveFunctionSpacesTest() throws Throwable {
		gen.saveFunctionSpaces();
	}

}
