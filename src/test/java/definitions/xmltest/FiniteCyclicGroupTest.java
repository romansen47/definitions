package definitions.xmltest;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.prototypes.AspectJTest;
import definitions.structures.abstr.groups.impl.FiniteCyclicRing;
import definitions.structures.abstr.vectorspaces.Ring;

public class FiniteCyclicGroupTest extends AspectJTest{

	final static int index=5;
	
	static Ring finiteCyclicGroup;
	
	@BeforeClass 
	public static void prepare() {
		AspectJTest.prepare();
		finiteCyclicGroup = new FiniteCyclicRing(index);
	}
	
	@Test
	public void test() {
		((FiniteCyclicRing) finiteCyclicGroup).print();
	}

}
