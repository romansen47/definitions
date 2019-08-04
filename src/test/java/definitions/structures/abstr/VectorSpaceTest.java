package definitions.structures.abstr;

import org.junit.Assert;
import org.junit.Test;

import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.field.impl.RealLine;
import definitions.structures.field.scalar.impl.Real;

public class VectorSpaceTest {

	final VectorSpace space=RealLine.getInstance();
	
	final Vector nul=space.nullVec();

	@Test
	public void testContains() {
		Assert.assertTrue(space.contains(nul));
	}

	@Test
	public void testNullVec() {
		Assert.assertTrue(nul.equals(nul));
	}

	@Test
	public void testAdd() {
		Assert.assertTrue(space.add(nul,nul).equals(nul));
	}

	@Test
	public void testStretch() {
		Assert.assertTrue(space.stretch(nul,new Real(-11.1)).equals(nul));
	}

}
