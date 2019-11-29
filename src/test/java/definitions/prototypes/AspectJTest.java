package definitions.prototypes;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import definitions.SpringConfiguration;
import definitions.structures.abstr.fields.impl.BinaryField;
import definitions.structures.abstr.fields.impl.ComplexPlane;
import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;

public class AspectJTest {

	private static SpringConfiguration springConfiguration;
	private static Generator generator;
	private static SpaceGenerator spaceGenerator;
	private static RealLine realLine;
	private static ComplexPlane complexPlane;
	private static BinaryField binaryField;
	 
	@BeforeClass
	public static void prepare() {
		springConfiguration = SpringConfiguration.getSpringConfiguration();
		setGenerator((Generator) springConfiguration.getApplicationContext().getBean("generator"));
		setSpaceGenerator(getGenerator().getSpacegenerator());
		setRealLine(RealLine.getInstance());
		setComplexPlane((ComplexPlane) ComplexPlane.getInstance());
		setBinaryField((BinaryField) springConfiguration.getApplicationContext().getBean("binaryField"));
	}

	public static SpringConfiguration getSpringConfiguration() {
		return springConfiguration;
	}

	public static void setSpringConfiguration(SpringConfiguration springConfiguration) {
		AspectJTest.springConfiguration = springConfiguration;
	}

	public static Generator getGenerator() {
		return generator;
	}

	public static void setGenerator(Generator generator) {
		AspectJTest.generator = generator;
	}

	public static SpaceGenerator getSpaceGenerator() {
		return spaceGenerator;
	}

	public static void setSpaceGenerator(SpaceGenerator spaceGenerator) {
		AspectJTest.spaceGenerator = spaceGenerator;
	}

	public static RealLine getRealLine() {
		return realLine;
	}

	public static void setRealLine(RealLine realLine) {
		AspectJTest.realLine = realLine;
	}

	public static ComplexPlane getComplexPlane() {
		return complexPlane;
	}

	public static void setComplexPlane(ComplexPlane complexPlane) {
		AspectJTest.complexPlane = complexPlane;
	}

	public static BinaryField getBinaryField() {
		return binaryField;
	}

	public static void setBinaryField(BinaryField binaryField) {
		AspectJTest.binaryField = binaryField;
	}

}
