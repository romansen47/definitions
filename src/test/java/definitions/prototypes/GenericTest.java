package definitions.prototypes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
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

public abstract class GenericTest {

	public static final Logger logger = LogManager.getLogger(GenericTest.class);

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

	public Logger getLogger() {
		return logger;
	}

	public static RealLine getRealLine() {
		return realLine;
	}

	public static SpaceGenerator getSpaceGenerator() {
		return spaceGenerator;
	}

	@BeforeClass
	public static void prepare() {
		if (springConfiguration == null) {
			setSpringConfiguration(getSpringConfiguration());
		}
	}

	public static ApplicationContextAware getSpringConfiguration() {
		if (springConfiguration == null) {
			logger.debug("Initializing Spring configuration\r");
			setSpringConfiguration(SpringConfiguration.getSpringConfiguration());
			setGenerator((Generator) ((SpringConfiguration) springConfiguration).getApplicationContext()
					.getBean("generator"));
			setSpaceGenerator(getGenerator().getSpaceGenerator());
			setNaturals(GroupGenerator.getInstance().getNaturals());
			setIntegers(GroupGenerator.getInstance().getIntegers());
			setRationals(GroupGenerator.getInstance().getRationals());
			setRealLine(RealLine.getInstance());
			setComplexPlane((ComplexPlane) ComplexPlane.getInstance());
			logger.debug("Created beans:");
			for (final String beanName : ((SpringConfiguration) springConfiguration).getApplicationContext()
					.getBeanNamesForType(Object.class)) {
				logger.info("bean " + beanName);
			}
		}
		Generator.getInstance();
		return springConfiguration;
	}

	@Before
	public void logNameOfTest() {
		getLogger().info("test class: {}", getClass());
	}

	public static void setComplexPlane(final ComplexPlane complexPlane) {
		GenericTest.complexPlane = complexPlane;
	}

	public static void setGenerator(final Generator generator) {
		GenericTest.generator = generator;
	}

	public static void setRealLine(final RealLine realLine) {
		GenericTest.realLine = realLine;
	}

	public static void setSpaceGenerator(final SpaceGenerator spaceGenerator) {
		GenericTest.spaceGenerator = spaceGenerator;
	}

	public static void setSpringConfiguration(final ApplicationContextAware springConfiguration) {
		GenericTest.springConfiguration = springConfiguration;
	}

	/**
	 * @param naturals the naturals to set
	 */
	public static void setNaturals(DiscreetSemiRing naturals) {
		GenericTest.naturals = naturals;
	}

	/**
	 * @param integers the integers to set
	 */
	public static void setIntegers(DiscreetDomain integers) {
		GenericTest.integers = integers;
	}

	/**
	 * @param rationals the rationals to set
	 */
	public static void setRationals(PrimeField rationals) {
		GenericTest.rationals = rationals;
	}

	/**
	 * @return the integers
	 */
	public static DiscreetDomain getIntegers() {
		return integers;
	}

	/**
	 * @return the rationals
	 */
	public static PrimeField getRationals() {
		return rationals;
	}

	/**
	 * @return the naturals
	 */
	public static DiscreetSemiRing getNaturals() {
		return naturals;
	}

}
