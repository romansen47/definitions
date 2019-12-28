package definitions.cache;

import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.IGenerator;
import definitions.structures.euclidean.vectorspaces.ISpaceGenerator;

public class PersistentDataManager {

	final static int funcSpaces = 100;

	final static IGenerator gen = Generator.getInstance();

	final static ISpaceGenerator spacesGen = gen.getSpacegenerator();

	final static Field realSpace = RealLine.getInstance();

	public static void load() {
		System.out.println("Regeneration of persistent data\r");
		for (int i = 1; i < funcSpaces; i++) {
			try {
				System.out.println("Loading " + (2 * i + 1)
						+ "-dimensional trigonometric function space extended by linear functions");
				spacesGen.getTrigonometricFunctionSpaceWithLinearGrowth(realSpace, i);
			} catch (final Exception e) {
				System.out.println("Failed to reload cached " + (2 * i + 1)
						+ "-dimensional trigonometric function space extended by linear functions");
				e.printStackTrace();
			}
		}
	}

	public static void main(final String[] args) throws Exception {
		load();
	}

	public void saveCoordinateSpacesTest() throws Throwable {
		gen.saveCoordinateSpaces();
	}

}
