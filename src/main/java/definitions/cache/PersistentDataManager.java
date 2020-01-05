package definitions.cache;

import definitions.SpringConfiguration;
import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.IFieldGenerator;
import definitions.structures.abstr.algebra.groups.IGroupGenerator;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.IGenerator;
import definitions.structures.euclidean.vectorspaces.ISpaceGenerator;

public class PersistentDataManager {

	final static int funcSpaces = 100;

	static SpringConfiguration springConfiguration;
	static IGenerator generator;
	static IGroupGenerator groupGenerator;
	static IFieldGenerator fieldGenerator;
	static ISpaceGenerator spaceGenerator;
	static Field realLine;

	public static void load() {
		springConfiguration=SpringConfiguration.getSpringConfiguration();
		generator=(Generator) springConfiguration.getApplicationContext().getBean("generator");
		groupGenerator=generator.getGroupGenerator();
		fieldGenerator=generator.getFieldGenerator();
		spaceGenerator=generator.getSpaceGenerator();
		realLine=fieldGenerator.getRealLine();
		System.out.println("Regeneration of persistent data\r");
		for (int i = 1; i < funcSpaces; i++) {
			try {
				System.out.println("Loading " + (2 * i + 1)
						+ "-dimensional trigonometric function space extended by linear functions");
				spaceGenerator.getTrigonometricFunctionSpaceWithLinearGrowth(realLine, i);
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
		generator.saveCoordinateSpaces();
	}

}
