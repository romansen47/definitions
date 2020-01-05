package definitions;

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

	protected static final Logger logger = LogManager.getLogger(AspectJTest.class);
	protected static SpringConfiguration springConfiguration;
	protected static Generator generator;
	protected static SpaceGenerator spaceGenerator;
	protected static RealLine realLine;
	protected static ComplexPlane complexPlane;
	protected static definitions.structures.abstr.algebra.groups.impl.BinaryField binaryField;

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
		if (springConfiguration == null) {
			setSpringConfiguration(SpringConfiguration.getSpringConfiguration());
			setGenerator((Generator) springConfiguration.getApplicationContext().getBean("generator"));
			setSpaceGenerator(getGenerator().getSpaceGenerator());
			setRealLine(RealLine.getInstance());
			setComplexPlane((ComplexPlane) ComplexPlane.getInstance());
			setBinaryField((BinaryField) springConfiguration.getApplicationContext().getBean("binaryField"));
			getLogger().setLevel(Level.INFO);
			org.apache.log4j.BasicConfigurator.configure();
		}
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
