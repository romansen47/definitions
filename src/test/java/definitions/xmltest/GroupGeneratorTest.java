package definitions.xmltest;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import definitions.prototypes.AspectJTest;
import definitions.structures.abstr.groups.Group;
import definitions.structures.abstr.groups.impl.FiniteResidueClassRing;

public class GroupGeneratorTest extends AspectJTest {

	@BeforeClass
	public static void prepare() {
		AspectJTest.prepare();
	}

	private Group group;

	private final int order = 100;

	@Before
	public void before() {
		this.setGroup(getGenerator().getGroupGenerator().getFiniteResidueClassRing(this.order));
	}

	public Group getGroup() {
		return this.group;
	}

	public void setGroup(final Group group) {
		this.group = group;
	}

	@Test
	public void test() {
		((FiniteResidueClassRing) this.group).print();
	}

}
