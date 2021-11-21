package definitions.prototypes;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.junit.BeforeClass;
import org.springframework.context.ApplicationContextAware;

import definitions.SpringConfiguration;
import definitions.structures.abstr.algebra.fields.PrimeField;
import definitions.structures.abstr.algebra.fields.impl.ComplexPlane;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.groups.GroupGenerator;
import definitions.structures.abstr.algebra.rings.DiscreetDomain;
import definitions.structures.abstr.algebra.rings.DiscreetSemiRing;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;

public class AspectJTest {

	private static final Logger logger = LogManager.getLogger(AspectJTest.class);
	private static ApplicationContextAware springConfiguration;
	private static Generator generator;
	private static SpaceGenerator spaceGenerator;
	private static DiscreetSemiRing naturals;
	private static DiscreetDomain integers;
	private static PrimeField rationals;
	private static RealLine realLine;
	private static ComplexPlane complexPlane;

	public static ComplexPlane getComplexPlane() {
		return complexPlane;
	}

	public static Generator getGenerator() {
		return generator;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static RealLine getRealLine() {
		return realLine;
	}

	public static SpaceGenerator getSpaceGenerator() {
		return spaceGenerator;
	}

	public static ApplicationContextAware getSpringConfiguration() {
		return springConfiguration;
	}

	@BeforeClass
	public synchronized static void prepare() {
		final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		final Configuration config = ctx.getConfiguration();
		final LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
		loggerConfig.setLevel(Level.INFO);
		ctx.updateLoggers();
		springConfiguration = getSpringConfiguration();
		if (springConfiguration == null) {
			logger.info("Initializing Spring configuration\r");
			setSpringConfiguration(SpringConfiguration.getSpringConfiguration());
			setGenerator((Generator) ((SpringConfiguration) springConfiguration).getApplicationContext()
					.getBean("generator"));
			setSpaceGenerator(getGenerator().getSpaceGenerator());
			setNaturals(GroupGenerator.getInstance().getNaturals());
			setIntegers(GroupGenerator.getInstance().getIntegers());
			setRationals(GroupGenerator.getInstance().getRationals());
			setRealLine(RealLine.getInstance());
			setComplexPlane((ComplexPlane) ComplexPlane.getInstance());
			logger.info("Created beans:");
			for (final String beanName : ((SpringConfiguration) springConfiguration).getApplicationContext()
					.getBeanNamesForType(Object.class)) {
				logger.info("bean " + beanName);
			}
			System.out.println();
		}
	}

	public static void setComplexPlane(final ComplexPlane complexPlane) {
		AspectJTest.complexPlane = complexPlane;
	}

	public static void setGenerator(final Generator generator) {
		AspectJTest.generator = generator;
	}

	public static void setRealLine(final RealLine realLine) {
		AspectJTest.realLine = realLine;
	}

	public static void setSpaceGenerator(final SpaceGenerator spaceGenerator) {
		AspectJTest.spaceGenerator = spaceGenerator;
	}

	public static void setSpringConfiguration(final ApplicationContextAware springConfiguration) {
		AspectJTest.springConfiguration = springConfiguration;
	}

	/**
	 * @return the naturals
	 */
	public static DiscreetSemiRing getNaturals() {
		return naturals;
	}

	/**
	 * @param naturals the naturals to set
	 */
	public static void setNaturals(DiscreetSemiRing naturals) {
		AspectJTest.naturals = naturals;
	}

	/**
	 * @return the integers
	 */
	public static DiscreetDomain getIntegers() {
		return integers;
	}

	/**
	 * @param integers the integers to set
	 */
	public static void setIntegers(DiscreetDomain integers) {
		AspectJTest.integers = integers;
	}

	/**
	 * @return the rationals
	 */
	public static PrimeField getRationals() {
		return rationals;
	}

	/**
	 * @param rationals the rationals to set
	 */
	public static void setRationals(PrimeField rationals) {
		AspectJTest.rationals = rationals;
	}

}
