package definitions.cache;

import java.util.stream.IntStream;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.springframework.context.ApplicationContextAware;

import definitions.SpringConfiguration;
import definitions.structures.abstr.algebra.fields.impl.FieldGenerator;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.functionspaces.EuclideanFunctionSpace;
import definitions.structures.euclidean.vectorspaces.ISpaceGenerator;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;

public class PersistentDataManager {
	ApplicationContextAware springConfiguration = SpringConfiguration.getSpringConfiguration();
	Generator generator = (Generator) ((SpringConfiguration) springConfiguration).getApplicationContext()
			.getBean("generator");
	FieldGenerator fieldGenerator = generator.getFieldGenerator();
	SpaceGenerator spaceGenerator = generator.getSpaceGenerator();
	RealLine realLine = fieldGenerator.getRealLine();
	private int funcSpaces = 20;

	private static final Logger logger = LogManager.getLogger(PersistentDataManager.class);

	public static void main(final String[] args) throws Throwable {
		new PersistentDataManager().load();
	}

	private void load() throws Throwable {
		Generator.getInstance();

		final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		final Configuration config = ctx.getConfiguration();
		final LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
		loggerConfig.setLevel(Level.INFO);
		ctx.updateLoggers();

		logger.info("Regeneration of persistent data\r");

		IntStream.range(1, funcSpaces).forEachOrdered(i -> {
			try {
				logger.info("Loading {}-dimensional trigonometric function space extended by linear functions",
						(2 * i) + 1);
				EuclideanFunctionSpace space = (EuclideanFunctionSpace) spaceGenerator
						.getTrigonometricFunctionSpaceWithLinearGrowth(realLine, i);

				SpaceGenerator.getInstance().getMyCache().getConcreteCache().put(i, space);
				LogManager.getLogger(ISpaceGenerator.class).info(
						"Saved {}-dimensional trigonometric space {} with linear functions to cache", 2 * i + 1, space);

			} catch (final Exception e) {
				logger.error(
						"Failed to reload cached {}-dimensional trigonometric function space extended by linear functions",
						(2 * i) + 1);
				e.printStackTrace();
			}
		});
		this.saveCoordinateSpacesTest();
		logger.info("job done");
	}

	private void saveCoordinateSpacesTest() throws Throwable {
		Generator.getInstance().saveCoordinateSpaces();
	}

}
