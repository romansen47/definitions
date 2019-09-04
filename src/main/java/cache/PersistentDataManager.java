package cache;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.IGenerator;
import definitions.structures.euclidean.vectorspaces.ISpaceGenerator;
import exceptions.WrongClassException;

public class PersistentDataManager {

	final static int funcSpaces = 50;

	final static IGenerator gen = Generator.getGenerator();
	final static ISpaceGenerator spacesGen = gen.getSpacegenerator();

	final static Field realSpace = RealLine.getInstance();

	public void saveCoordinateSpacesTest() throws Throwable {
		gen.saveCoordinateSpaces();
	}

	public static void main(String[] args) throws WrongClassException {
		for (int i = 1; i < funcSpaces; i++) {
			spacesGen.getTrigonometricFunctionSpaceWithLinearGrowth(realSpace, i);
		}
	}

}
