package definitions.xmltest;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import definitions.SpringConfiguration;
import definitions.prototypes.AspectJTest;
import definitions.structures.abstr.groups.CyclicGroup;
import definitions.structures.abstr.groups.GroupElement;

public class BinaryGroupTest extends AspectJTest{
	
	static CyclicGroup binaryGroup;
	static GroupElement identityElement;
	static GroupElement generator;
	
	@BeforeClass 
	public static void prepare() {
		AspectJTest.prepare();
		binaryGroup=(CyclicGroup) SpringConfiguration.getSpringConfiguration().getApplicationContext().getBean("binaryField");
		identityElement=(GroupElement) binaryGroup.getIdentityElement();
		generator=(GroupElement) ((CyclicGroup) binaryGroup).getGenerator();
	}
	
	@Test
	public void testGetIdentityElement() {
		Assert.assertTrue(binaryGroup.getIdentityElement()!=null);
	}

	@Test
	public void testGetOrder() {
		Assert.assertTrue(binaryGroup.getOrder()==2);
	}

	@Test
	public void testOperation() {
		Assert.assertTrue(binaryGroup.operation(identityElement, identityElement)==identityElement);
		Assert.assertTrue(binaryGroup.operation(identityElement, generator)==generator);
		Assert.assertTrue(binaryGroup.operation(generator, identityElement)==generator);
		Assert.assertTrue(binaryGroup.operation(generator, generator)==identityElement);
	}

	@Test
	public void testGetInverseElement() {
		Assert.assertTrue(binaryGroup.getInverseElement((GroupElement) binaryGroup.getIdentityElement())==binaryGroup.getIdentityElement());
		Assert.assertTrue(binaryGroup.getInverseElement((GroupElement) ((CyclicGroup) binaryGroup).getGenerator())==((CyclicGroup)binaryGroup).getGenerator());
	}

}
