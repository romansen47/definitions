package definitions.generictest;

import org.junit.Test;

import definitions.prototypes.GenericTest;
import definitions.structures.abstr.algebra.groups.GroupGenerator;
import definitions.structures.abstr.algebra.rings.DiscreetSemiRing;

public class FiniteFieldsTest extends GenericTest {

	@Test
	public void testFiniteField() {

		DiscreetSemiRing naturals = GroupGenerator.getInstance().getNaturals();

	}

}
