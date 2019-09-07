package cache;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.IGenerator;
import definitions.structures.euclidean.vectorspaces.ISpaceGenerator;

public class PersistentDataManager {

	final static int funcSpaces = 100;

	final static IGenerator gen = Generator.getGenerator();

	final static ISpaceGenerator spacesGen = gen.getSpacegenerator();

	final static Field realSpace = RealLine.getInstance();

	public void saveCoordinateSpacesTest() throws Throwable {
		gen.saveCoordinateSpaces();
	}

	public static void main(String[] args) throws Exception {
		load();
	}

	public static void load() {
		System.out.println("Regeneration of persistent data\r");
		for (int i = 1; i < funcSpaces; i++) {
			try {
				System.out.println("Loading " + (2 * i + 1)
						+ "-dimensional trigonometric function space extended by linear functions");
				spacesGen.getTrigonometricFunctionSpaceWithLinearGrowth(realSpace, i);
			} catch (Exception e) {
				System.out.println("Failed to reload cached " + (2 * i + 1)
						+ "-dimensional trigonometric function space extended by linear functions");
				e.printStackTrace();
			}
		}
	}

}
