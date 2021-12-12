package definitions.cache;

import org.springframework.context.ApplicationContextAware;

import definitions.SpringConfiguration;
import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.IFieldGenerator;
import definitions.structures.abstr.algebra.groups.IGroupGenerator;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.IGenerator;
import definitions.structures.euclidean.vectorspaces.ISpaceGenerator;

public class PersistentDataManager {

	IGenerator generator;
	IGroupGenerator groupGenerator;
	IFieldGenerator fieldGenerator;
	ISpaceGenerator spaceGenerator;
	Field realLine;
	int funcSpaces = 1000;

	public static void main(final String[] args) throws Throwable {
		new PersistentDataManager().load();
	}

	/**
	 * @param realLine the realLine to set
	 */
	public void setRealLine(Field realLine) {
		this.realLine = realLine;
	}

	public void load() throws Throwable {
		springConfiguration = SpringConfiguration.getSpringConfiguration();
		generator = (Generator) ((SpringConfiguration) springConfiguration).getApplicationContext()
				.getBean("generator");
		groupGenerator = generator.getGroupGenerator();
		fieldGenerator = generator.getFieldGenerator();
		spaceGenerator = generator.getSpaceGenerator();
		realLine = fieldGenerator.getRealLine();
		System.out.println("Regeneration of persistent data\r");
		for (int i = 1; i < funcSpaces; i++) {
			try {
				System.out.println("Loading " + ((2 * i) + 1)
						+ "-dimensional trigonometric function space extended by linear functions");
				spaceGenerator.getTrigonometricFunctionSpaceWithLinearGrowth(realLine, i);
			} catch (final Exception e) {
				System.out.println("Failed to reload cached " + ((2 * i) + 1)
						+ "-dimensional trigonometric function space extended by linear functions");
				e.printStackTrace();
			}
		}
		this.saveCoordinateSpacesTest();
	}

	public void saveCoordinateSpacesTest() throws Throwable {
		generator.saveCoordinateSpaces();
	}

	ApplicationContextAware springConfiguration;

	/**
	 * @return the funcSpaces
	 */
	public int getFuncSpaces() {
		return funcSpaces;
	}

	/**
	 * @param funcSpaces the funcSpaces to set
	 */
	public void setFuncSpaces(int funcSpaces) {
		this.funcSpaces = funcSpaces;
	}

	/**
	 * @return the springConfiguration
	 */
	public ApplicationContextAware getSpringConfiguration() {
		return springConfiguration;
	}

	/**
	 * @param springConfiguration the springConfiguration to set
	 */
	public void setSpringConfiguration(SpringConfiguration springConfiguration) {
		this.springConfiguration = springConfiguration;
	}

	/**
	 * @return the generator
	 */
	public IGenerator getGenerator() {
		return generator;
	}

	/**
	 * @param generator the generator to set
	 */
	public void setGenerator(IGenerator generator) {
		this.generator = generator;
	}

	/**
	 * @return the groupGenerator
	 */
	public IGroupGenerator getGroupGenerator() {
		return groupGenerator;
	}

	/**
	 * @param groupGenerator the groupGenerator to set
	 */
	public void setGroupGenerator(IGroupGenerator groupGenerator) {
		this.groupGenerator = groupGenerator;
	}

	/**
	 * @return the fieldGenerator
	 */
	public IFieldGenerator getFieldGenerator() {
		return fieldGenerator;
	}

	/**
	 * @param fieldGenerator the fieldGenerator to set
	 */
	public void setFieldGenerator(IFieldGenerator fieldGenerator) {
		this.fieldGenerator = fieldGenerator;
	}

	/**
	 * @return the spaceGenerator
	 */
	public ISpaceGenerator getSpaceGenerator() {
		return spaceGenerator;
	}

	/**
	 * @param spaceGenerator the spaceGenerator to set
	 */
	public void setSpaceGenerator(ISpaceGenerator spaceGenerator) {
		this.spaceGenerator = spaceGenerator;
	}

	/**
	 * @return the realLine
	 */
	public Field getRealLine() {
		return realLine;
	}

}
