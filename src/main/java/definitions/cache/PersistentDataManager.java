package definitions.cache;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
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
	int funcSpaces = 20;

	public static final Logger logger = LogManager.getLogger(PersistentDataManager.class);

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

		final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		final Configuration config = ctx.getConfiguration();
		final LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
		loggerConfig.setLevel(Level.INFO);
		ctx.updateLoggers();

		logger.info("Regeneration of persistent data\r");
		for (int i = 1; i < funcSpaces; i++) {
			try {
				logger.info("Loading {}-dimensional trigonometric function space extended by linear functions",
						(2 * i) + 1);
				spaceGenerator.getTrigonometricFunctionSpaceWithLinearGrowth(realLine, i);
			} catch (final Exception e) {
				logger.error(
						"Failed to reload cached {}-dimensional trigonometric function space extended by linear functions",
						(2 * i) + 1);
				e.printStackTrace();
			}
		}
		this.saveCoordinateSpacesTest();
		logger.info("job done");
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
