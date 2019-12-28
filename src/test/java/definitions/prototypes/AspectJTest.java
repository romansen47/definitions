package definitions.prototypes;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;

import definitions.SpringConfiguration;
import definitions.structures.abstr.algebra.fields.impl.ComplexPlane;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.groups.impl.BinaryField;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;

public class AspectJTest {

	private static final Logger logger = LogManager.getLogger(AspectJTest.class);

	private static SpringConfiguration springConfiguration;
	private static Generator generator;
	private static SpaceGenerator spaceGenerator;
	private static RealLine realLine;
	private static ComplexPlane complexPlane;
	private static definitions.structures.abstr.algebra.groups.impl.BinaryField binaryField;

	public static BinaryField getBinaryField() {
		return binaryField;
	}

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

	public static SpringConfiguration getSpringConfiguration() {
		return springConfiguration;
	}

	@BeforeClass
	public static void prepare() {
		setSpringConfiguration(SpringConfiguration.getSpringConfiguration());
		setGenerator((Generator) springConfiguration.getApplicationContext().getBean("generator"));
		setSpaceGenerator(getGenerator().getSpacegenerator());
		setRealLine(RealLine.getInstance());
		setComplexPlane((ComplexPlane) ComplexPlane.getInstance());
		setBinaryField((BinaryField) springConfiguration.getApplicationContext().getBean("binaryField"));
		getLogger().setLevel(Level.INFO);
		org.apache.log4j.BasicConfigurator.configure();
	}

	public static void setBinaryField(final BinaryField binaryField) {
		AspectJTest.binaryField = binaryField;
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

	public static void setSpringConfiguration(final SpringConfiguration springConfiguration) {
		AspectJTest.springConfiguration = springConfiguration;
	}

}
