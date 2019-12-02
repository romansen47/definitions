package definitions.structures.abstr.groups.impl;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.prototypes.AspectJTest;
import definitions.structures.abstr.vectorspaces.Ring;

public class FiniteCyclicGroupTest extends AspectJTest{

	final static int index=5;
	
	static Ring finiteCyclicGroup;
	
	@BeforeClass 
	public static void prepare() {
		AspectJTest.prepare();
		finiteCyclicGroup = new FiniteCyclicGroup(index);
	}
	
	@Test
	public void test() {
		((FiniteCyclicGroup) finiteCyclicGroup).print();
	}

}
