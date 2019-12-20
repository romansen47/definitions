package definitions.xmltest;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import definitions.prototypes.AspectJTest;
import definitions.structures.abstr.groups.Group;
import definitions.structures.abstr.groups.impl.FiniteResidueClassRing;

public class GroupGeneratorTest extends AspectJTest{

	private Group group;
	private final int order=100;
	
	@BeforeClass
	public static void prepare() {
		AspectJTest.prepare();
	}
	
	@Before
	public void before() {
		setGroup(getGenerator().getGroupGenerator().getFiniteResidueClassRing(order));
	}
	
	@Test
	public void test() {
		((FiniteResidueClassRing) group).print();
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

}
