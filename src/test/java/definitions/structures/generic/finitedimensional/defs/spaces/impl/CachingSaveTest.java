package definitions.structures.generic.finitedimensional.defs.spaces.impl;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.IGenerator;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.ISpaceGenerator;
import exceptions.WrongClassException;

public class CachingSaveTest {

	final static int coordSpaces = 10000;
	final static int funcSpaces = 50;
	
	final static IGenerator gen = Generator.getGenerator();
	final static ISpaceGenerator spacesGen = gen.getSpacegenerator();

	final static Field realSpace = RealLine.getInstance();

	@BeforeClass
	public static void before() throws WrongClassException, ClassNotFoundException, IOException {
		for (int i = 1; i < coordSpaces; i++) {
			spacesGen.getFiniteDimensionalVectorSpace(i);
		}
		for (int i = 1; i < funcSpaces; i++) {
			spacesGen.getTrigonometricFunctionSpaceWithLinearGrowth(realSpace, i);
		}
	}

	@Test
	public void saveCoordinateSpacesTest() throws Throwable {
		gen.saveCoordinateSpaces();
		final int i = 0;
	}

}
